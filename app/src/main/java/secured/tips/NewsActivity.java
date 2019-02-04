package secured.tips;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import datafiles.CacheHelper;
import datafiles.NewsAdapter;
import datafiles.NewsFunction;

public class NewsActivity extends AppCompatActivity {
    ActionBar actionBar;
    String myAPI_Key = "417444c0502047d69c1c2a9dcc1672cd";
    //String API_KEY = "8190df9eb51445228e397e4185311a66"; // ### YOUE NEWS API HERE ###
    String NEWS_SOURCE = "espn";
    ListView listNews;
    ProgressBar loader;


    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URL = "url";
    public static final String KEY_URLTOIMAGE = "urlToImage";
    public static final String KEY_PUBLISHEDAT = "publishedAt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_news);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listNews = findViewById(R.id.listNews);
        loader =  findViewById(R.id.loader);
        listNews.setEmptyView(loader);

        DownloadNews newsTask = new DownloadNews();

        if(NewsFunction.isNetworkAvailable(getApplicationContext()))
        {
            newsTask.execute();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            newsTask.execute();
        }

    }

    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String xml = CacheHelper.retrieve(getApplicationContext(),"newsKey");
            if(xml==null||xml.equals("")){}
            else{
                onPostExecute(xml);
            }

        }
        protected String doInBackground(String... args) {
            String xml = "";

            String urlParameters = "";xml = NewsFunction.excuteGet("https://newsapi.org/v2/everything?domains=espnfc.com&language=en&pageSize=45&apiKey="+myAPI_Key, urlParameters);
            return  xml;
        }
        @Override
        protected void onPostExecute(String xml) {
            if(xml==null){
                return;
            }

            if(xml.length()>10){ // Just checking if not empty
                dataList.clear();

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_AUTHOR, jsonObject.optString(KEY_AUTHOR));
                        map.put(KEY_TITLE, jsonObject.optString(KEY_TITLE));
                        map.put(KEY_DESCRIPTION, jsonObject.optString(KEY_DESCRIPTION));
                        map.put(KEY_URL, jsonObject.optString(KEY_URL).toString());
                        map.put(KEY_URLTOIMAGE, jsonObject.optString(KEY_URLTOIMAGE));
                        long date = jsonObject.optLong(KEY_PUBLISHEDAT);
                        String newsDate = DateFormat.format("dd MMM", date).toString();
                        //map.put(KEY_PUBLISHEDAT, newsDate);
                        map.put(KEY_PUBLISHEDAT, jsonObject.optString(KEY_PUBLISHEDAT).toString());

                        dataList.add(map);
                    }

                    //Delete previous cache and cache new info for later use
                    File cache = new File(getApplicationContext() + "/newsKey.srl");
                    cache.delete();
                    CacheHelper.save(getApplicationContext(), "newsKey", xml);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                NewsAdapter adapter = new NewsAdapter(NewsActivity.this, dataList);
                //listNews.setAdapter(adapter);

                listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent i = new Intent(NewsActivity.this, NewsStoryActivity.class);
                        i.putExtra("url", dataList.get(+position).get(KEY_URL));
                        startActivity(i);
                    }
                });

            }else{
                Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }
}

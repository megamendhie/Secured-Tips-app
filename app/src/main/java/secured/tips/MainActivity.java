package secured.tips;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import datafiles.AdsAdapter;
import datafiles.Cache;
import datafiles.CacheHelper;
import datafiles.GlideApp;
import datafiles.NewsAdapter;
import datafiles.NewsFunction;
import datafiles.UserProfile;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {
    String myAPI_Key = "417444c0502047d69c1c2a9dcc1672cd";
    RecyclerView listNews;

    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URL = "url";
    public static final String KEY_URLTOIMAGE = "urlToImage";
    public static final String KEY_PUBLISHEDAT = "publishedAt";

    final int versionCode = BuildConfig.VERSION_CODE;
    final String FB_RC_KEY_TITLE = "update_title";
    final String FB_RC_KEY_DESCRIPTION = "update_description";
    final String FB_RC_KEY_FORCE_UPDATE_VERSION = "force_update_version";
    final String FB_RC_KEY_LATEST_VERSION = "latest_version";
    final HashMap<String, Object> defaultMap = new HashMap<>();

    private CardView crdProfile, crdForum, crdFree, crdVIP;

    private FirebaseAuth mfirebaseAuth;
    FirebaseRemoteConfig mFirebaseRemoteConfig;
    FirebaseUser user;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String TAG = "myApp";
    FirebaseDatabase tipsDatabase, usersDatabase, chatDatabase;
    Cache cache;
    static InterstitialAd mInterstitialAd;
    static boolean called = false;
    ViewPager viewPager;
    TabLayout indicator;
    Timer timer = null;
    TextView txtName, txtUsername, txtPoints;
    Button btnProfile;
    CircleImageView imgDp;
    View header;
    private boolean login = false;
    RequestOptions requestOptions = new RequestOptions();
    List<String>imageUrls, linkUrls;
    private LinearLayout nativeAdContainer;
    private LinearLayout adView;
    private DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        navigationView = findViewById(R.id.nav_view); navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);
        imgDp = header.findViewById(R.id.imgProfilePic); imgDp.setOnClickListener(this);
        txtName = header.findViewById(R.id.txtName);
        txtUsername = header.findViewById(R.id.txtUsername);
        txtPoints = header.findViewById(R.id.txtPoint);
        btnProfile = header.findViewById(R.id.btnProfile); btnProfile.setOnClickListener(this);
        nativeAdContainer = findViewById(R.id.native_ad_container);
        inflater = LayoutInflater.from(MainActivity.this);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        crdProfile = findViewById(R.id.crdProfile); crdProfile.setOnClickListener(this);
        crdForum = findViewById(R.id.crdForum); crdForum.setOnClickListener(this);
        crdFree = findViewById(R.id.crdFree); crdFree.setOnClickListener(this);
        crdVIP = findViewById(R.id.crdVIP); crdVIP.setOnClickListener(this);

        cache  = new Cache(MainActivity.this);

        tipsDatabase = FirebaseDatabase.getInstance();
        usersDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/");
        chatDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/");
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4597711656812814/1799989807");

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        called = prefs.getBoolean("CALLED", false);
        if(!called){
            try {
                tipsDatabase.setPersistenceEnabled(true);
                usersDatabase.setPersistenceEnabled(true);
                chatDatabase.setPersistenceEnabled(true);
            }
            catch (DatabaseException e){
                Log.i(TAG, "onCreate: db error handled");
                Toast.makeText(getApplicationContext(), "db error handled", Toast.LENGTH_SHORT).show();
            }
            editor = prefs.edit();
            editor.putBoolean("CALLED", true);
        }

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4597711656812814~3843067047");

        mfirebaseAuth = FirebaseAuth.getInstance();
        user = mfirebaseAuth.getCurrentUser();
        if (user != null) {
            login = true;
            checkSub();
        }
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                activateSlider();
                checkForUpdate();
            }
        });
    }

    private void checkForUpdate() {
        // Hashmap which contains the default values for all the parameter defined in the remote config server
        defaultMap.put(FB_RC_KEY_TITLE, "Update Available");
        defaultMap.put(FB_RC_KEY_DESCRIPTION, "A new version of the application is available please click below to update the latest version.");
        defaultMap.put(FB_RC_KEY_FORCE_UPDATE_VERSION, ""+versionCode);
        defaultMap.put(FB_RC_KEY_LATEST_VERSION, ""+versionCode);

        // To set the default values for the remote config parameters
        mFirebaseRemoteConfig.setDefaults(defaultMap);
        // To enable the developer mode
        mFirebaseRemoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG).build());

        Task<Void> fetchTask=mFirebaseRemoteConfig.fetch(BuildConfig.DEBUG?0: TimeUnit.HOURS.toSeconds(12));

        fetchTask.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // After config data is successfully fetched, it must be activated before newly fetched
                    // values are returned.
                    mFirebaseRemoteConfig.activateFetched();
                    Boolean visible = true;
                    String title = mFirebaseRemoteConfig.getString(FB_RC_KEY_TITLE);
                    String description = mFirebaseRemoteConfig.getString(FB_RC_KEY_DESCRIPTION);
                    int forceUpdateVersion = Integer.parseInt(mFirebaseRemoteConfig.getString(FB_RC_KEY_FORCE_UPDATE_VERSION));
                    int latestAppVersion = Integer.parseInt(mFirebaseRemoteConfig.getString(FB_RC_KEY_LATEST_VERSION));

                    if (latestAppVersion > versionCode){
                        if(forceUpdateVersion>versionCode)
                            visible = false;
                        updateAlert(title, description, visible);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Fetch Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateAlert(String title, String description, boolean visible){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.update_alert, null);
        builder.setView(dialogView).setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button btnUpdate = alertDialog.findViewById(R.id.btnUpdate);
        Button btnLater = alertDialog.findViewById(R.id.btnLater);
        btnLater.setVisibility(visible? View.VISIBLE : View.GONE);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateApp();
            }
        });
        btnLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        TextView txtTitle = alertDialog.findViewById(R.id.txtTitle);
        TextView txtDescription = alertDialog.findViewById(R.id.txtDescription);
        txtTitle.setText(title);
        txtDescription.setText(description);
    }

    public void loadNews(){
        listNews = findViewById(R.id.listNews);
        DownloadNews newsTask = new DownloadNews();

        if(NewsFunction.isNetworkAvailable(getApplicationContext()))
        {
            newsTask.execute();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            newsTask.execute();
        }
    }

    public void checkSub(){
        final String userID = user.getUid();
        final Date today = new Date();
        usersDatabase.getReference().child("Users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get subscription status and ending date for VIP and TipZone
                UserProfile user = dataSnapshot.getValue(UserProfile.class);
                txtName.setText(user.getA1_firstname()+" "+user.getA2_lastname());
                txtUsername.setText(user.getA3_username());
                txtPoints.setText(String.valueOf(user.getA8_points()+" points"));
                if(user.getA7_imageURL().equals("none")){
                    GlideApp.with(getApplicationContext())
                            .load(R.drawable.dspc)
                            .into(imgDp);
                }
                else{
                    GlideApp.with(getApplicationContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(user.getA7_imageURL())
                            .into(imgDp);
                }
                boolean vipSub = dataSnapshot.child("b2_vip").getValue(boolean.class);
                String vipEnding = dataSnapshot.child("b3_vip_ending").getValue(String.class);
                boolean roomSub = dataSnapshot.child("b4_chat").getValue(boolean.class);
                String chatEnding = dataSnapshot.child("b5_chat_ending").getValue(String.class);

                //convert date from String to Simple Date Format
                SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
                String currentTime = sdf.format(today.getTime());
                Date todaysDate = sdf.parse(currentTime, new ParsePosition(0));
                Date vipEndDate = sdf.parse(vipEnding, new ParsePosition(0));
                Date chatEndDate = sdf.parse(chatEnding, new ParsePosition(0));

                //check if VIP sub still running
                if(vipSub) {
                    if (todaysDate.after(vipEndDate)) {
                        usersDatabase.getReference().child("Users").child(userID).child("b2_vip").setValue(false);
                        cache.setVipSub(false);
                    }
                    else {cache.setVipSub(true);}
                }
                else {cache.setVipSub(false);}

                //check if TipZone sub still running
                if(roomSub) {
                    if (todaysDate.after(chatEndDate)) {
                        usersDatabase.getReference().child("Users").child(userID).child("b4_chat").setValue(false);
                        cache.setRoomSub(false);
                    }
                    else {cache.setRoomSub(true);}
                }
                else {cache.setRoomSub(false);}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                else
                    mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.menu_sign_out:
                signOut();
                break;
            case R.id.menu_contact:
                startActivity(new Intent(getApplicationContext(), ContactActivity.class));
                break;
            case R.id.menu_share:
                shareApp();
                break;
            case R.id.menu_privacy_policy:
                Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://securedtips.co/privacypolicy.html"));
                startActivity(i);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.crdFree:
                startActivity(new Intent(getApplicationContext(), FreeActivity.class));
                break;
            case R.id.crdVIP:
                startActivity(new Intent(getApplicationContext(), VipPageActivity.class));
                break;
            case R.id.imgProfilePic:
            case R.id.btnProfile:
            case R.id.crdProfile:
                if(user!=null) {
                    Log.i(TAG, "onClick: userID is: "+user.getUid() );
                    cache.setUserID(user.getUid());
                    startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));
                }
                else{
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                break;
            case R.id.crdForum:
                startActivity(new Intent(getApplicationContext(), RoomsPageActivity.class));
                break;
        }
    }

    public void onResume(){
        super.onResume();
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_home).setChecked(true);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4597711656812814/1799989807");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mfirebaseAuth = FirebaseAuth.getInstance();
        user = mfirebaseAuth.getCurrentUser();
        if (user != null) {
            if(!login){
                login = true;
                checkSub();
            }
        }
        loadNews();
        loadAds();
    }

    public void shareApp(){
        Intent share = new Intent(Intent.ACTION_SEND);
        String url = "This app is so good. You should try it\n\nhttp://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, url);
        share.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(Intent.createChooser(share, "Invite a friend via:"));
    }

    public void signOut(){
        if(user!=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
            builder.setTitle("Sign Out")
                    .setMessage("You want to signout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mfirebaseAuth.signOut();
                            txtName.setText("_Name");
                            txtUsername.setText("username");
                            txtPoints.setText(String.valueOf("0 points"));
                            GlideApp.with(getApplicationContext()).load(R.drawable.dspc)
                                    .into(imgDp);
                            user=null;
                            login = false;
                            Toast.makeText(getApplicationContext(), "You have signed out", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //do nothing
                        }
                    })
                    .show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Already out", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_free:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(getApplicationContext(), FreeActivity.class));
                break;
            case R.id.nav_vip:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(getApplicationContext(), VipPageActivity.class));
                break;
            case R.id.nav_tipzone:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(getApplicationContext(), RoomsPageActivity.class));
                break;
            case R.id.nav_subscribe:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(this, SubscriptionReloadActivity.class));
                break;
            case R.id.nav_contact:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(getApplicationContext(), ContactActivity.class));
                break;
        }
        return true;
    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < imageUrls.size() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    public void loadAds(){
        Log.i("Dame", "loadAds() called");
        editor = prefs.edit();
        int adCount = prefs.getInt("ADCOUNT", 1);
        if(adCount%5==0) {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.setAdListener(new AdListener(){
                public void onAdLoaded(){
                    mInterstitialAd.show();
                }
            });
        }
        adCount+=1;
        editor.putInt("ADCOUNT", adCount).apply();
    }

    public void rateApp(){
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.

        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
        }
    }

    public  void activateSlider(){
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adView = (LinearLayout) inflater.inflate(R.layout.mainpage_ads_slider, nativeAdContainer, false);
        nativeAdContainer.removeAllViews();
        nativeAdContainer.addView(adView);
        viewPager = adView.findViewById(R.id.adsViewPager);
        indicator = adView.findViewById(R.id.indicator);

        DatabaseReference adsRef=  FirebaseDatabase.getInstance().getReference("Ads_new");
        adsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imageUrls = new ArrayList<>();
                linkUrls = new ArrayList<>();
                if(!dataSnapshot.hasChildren())
                    return;
                for(DataSnapshot adsUrl: dataSnapshot.getChildren()){
                    imageUrls.add(adsUrl.child("ad").getValue(String.class));
                    linkUrls.add(adsUrl.child("link").getValue(String.class));
                }
                viewPager.setAdapter(new AdsAdapter(getApplicationContext(), imageUrls, linkUrls));
                indicator.setupWithViewPager(viewPager, true);
                timer = new Timer();
                timer.scheduleAtFixedRate(new SliderTimer(), 1000, 12000);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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

            String urlParameters = "";xml = NewsFunction.excuteGet("https://newsapi.org/v2/everything?domains=espnfc.com&language=en&pageSize=20&apiKey="+myAPI_Key, urlParameters);
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

                NewsAdapter adapter = new NewsAdapter(MainActivity.this, dataList);
                listNews.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                listNews.setAdapter(adapter);

            }else{
                Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
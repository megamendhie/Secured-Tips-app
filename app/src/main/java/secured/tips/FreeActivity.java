package secured.tips;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class FreeActivity extends AppCompatActivity {

    DatabaseReference mRef;
    ProgressBar progressBar;
    SectionedRecyclerViewAdapter adapt;
    FrameLayout lnrToday;
    TextView txtInfo;
    Button btnSub;
    ActionBar actionBar;

    List<TipDetails> list;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4597711656812814~3843067047");

        loadAds();
        String database_path = "Free";

        txtInfo = (TextView)findViewById(R.id.txtInfo);
        btnSub = (Button) findViewById(R.id.btnSub);
        lnrToday = (FrameLayout) findViewById(R.id.lnrToday);
        progressBar = (ProgressBar)findViewById(R.id.prgBar);


        recyclerView = (RecyclerView)findViewById(R.id.recyclerTest);
        recyclerView.setLayoutManager(new LinearLayoutManager(FreeActivity.this));
        progressBar.setVisibility(View.VISIBLE);

        adapt = new SectionedRecyclerViewAdapter();
        mRef = FirebaseDatabase.getInstance().getReference(database_path);

        loadTips();

    }

    public void loadTips(){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                adapt.removeAllSections();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    list = new ArrayList<TipDetails>();

                    String date = datasnapshot.child("date").getValue(String.class);
                    Log.i("Dame", date);
                    if(date.toLowerCase().equals("nothing")){
                        lnrToday.setVisibility(View.VISIBLE);
                        continue;}
                    DataSnapshot tipsSnap =  datasnapshot.child("tips");
                    list = loadAll(tipsSnap);
                    adapt.addSection(new TipSection(date, list, getApplicationContext()));
                    adapt.notifyDataSetChanged();
                }

                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(adapt);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public List<TipDetails> loadAll(DataSnapshot tipsSnap){
        for (DataSnapshot datasnapshot : tipsSnap.getChildren()) {
            TipDetails tips = datasnapshot.getValue(TipDetails.class);
            list.add(tips);
        }
        return list;
    }

    public void loadAds(){
        Log.i("Dame", "loading ads");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
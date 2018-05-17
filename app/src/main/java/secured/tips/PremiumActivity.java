package secured.tips;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class PremiumActivity extends AppCompatActivity implements OnClickListener {

    Button btnSub;
    private FirebaseAuth mfirebaseAuth;
    FirebaseUser user;
    DatabaseReference mRef;
    LinearLayout preToday;
    ActionBar actionBar;
    String userID;
    ProgressBar progressBar;
    SectionedRecyclerViewAdapter adapt;

    List<TipDetails> list = new ArrayList<TipDetails>();
    RecyclerView recyclerView;

    LayoutInflater inflater;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        preToday = findViewById(R.id.preToday);

        recyclerView = findViewById(R.id.recyclerTest);
        recyclerView.setLayoutManager(new LinearLayoutManager(PremiumActivity.this));
        progressBar = findViewById(R.id.prgBar);
        progressBar.setVisibility(View.VISIBLE);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4597711656812814~3843067047");

        loadAds();

        adapt = new SectionedRecyclerViewAdapter();
        inflater = getLayoutInflater();
        setLayout();
    }

    public void loadTipsZero(){
        Log.i("Dame", "loading tips zero");
        mRef = FirebaseDatabase.getInstance().getReference("Premium");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                adapt.removeAllSections();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    list = new ArrayList<TipDetails>();
                    if(datasnapshot.getKey().toString().equals("day 1")){
                        continue;
                    }
                    String date = datasnapshot.child("date").getValue(String.class);
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

    public void loadTips(){
        Log.i("Dame", "loading tips");
        mRef = FirebaseDatabase.getInstance().getReference("Premium");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                adapt.removeAllSections();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    list = new ArrayList<TipDetails>();
                    String date = datasnapshot.child("date").getValue(String.class);
                    Log.i("Dame", date);
                    if(date.toLowerCase().equals("nothing")){
                        preToday.removeAllViews();
                        view = inflater.inflate(R.layout.activity_prestory, null);
                        preToday.addView(view);
                        btnSub = (Button) findViewById(R.id.btnSub);
                        btnSub.setVisibility(View.GONE);
                        TextView txtInfo = (TextView)findViewById(R.id.txtInfo);
                        txtInfo.setText("Today's tips not available yet. Pls check back.");
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
            public void onCancelled(DatabaseError databaseError) { progressBar.setVisibility(View.GONE);
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

    public void setLayout() {
        mfirebaseAuth = FirebaseAuth.getInstance();
        user = mfirebaseAuth.getCurrentUser();
        if (user!= null) {
            userID = user.getUid().toString();
            mRef = FirebaseDatabase.getInstance().getReference("Active");
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(userID)){
                        loadTips();
                        return;
                    }else {
                        requestSubscription();
                        loadTipsZero();
                        return;
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } else {
            requestSubscription();
            loadTipsZero();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        if (view.getId() == R.id.btnSub) {
            if (user!=null){
                intent = new Intent(this, SubscribeActivity.class);
                startActivity(intent);
            } else {
                intent = new Intent(this, LoginActivity.class);
                intent.putExtra("SENDER", 1);
                startActivity(intent);
                finish();
            }
        }
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

    public void requestSubscription(){
        preToday.removeAllViews();
        view = inflater.inflate(R.layout.activity_prestory, null);
        preToday.addView(view);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnSub.setOnClickListener(this);
    }

}
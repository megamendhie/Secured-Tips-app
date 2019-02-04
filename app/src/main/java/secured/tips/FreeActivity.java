package secured.tips;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import datafiles.Cache;
import datafiles.GlideApp;
import datafiles.UserProfile;
import de.hdodenhof.circleimageview.CircleImageView;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class FreeActivity extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference mRef, usersDatabase;
    private FirebaseAuth mfirebaseAuth;
    FirebaseUser user;
    ProgressBar progressBar;
    SectionedRecyclerViewAdapter adapt;
    Button btnVip, btnTipzone;
    FrameLayout lnrToday;
    TextView txtInfo;
    Button btnSub;
    ActionBar actionBar;
    private DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    TextView txtName, txtUsername, txtPoints;
    Button btnProfile;
    CircleImageView imgDp;
    View header;
    private boolean login = false;
    RequestOptions requestOptions = new RequestOptions();
    static InterstitialAd mInterstitialAd;

    List<TipDetails> list;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        String database_path = "Free";

        txtInfo = findViewById(R.id.txtInfo);
        btnSub = findViewById(R.id.btnSub);
        lnrToday = findViewById(R.id.lnrToday);
        progressBar = findViewById(R.id.prgBar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view); navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);
        imgDp = header.findViewById(R.id.imgProfilePic); imgDp.setOnClickListener(this);
        txtName = header.findViewById(R.id.txtName);
        txtUsername = header.findViewById(R.id.txtUsername);
        txtPoints = header.findViewById(R.id.txtPoint);
        btnProfile = header.findViewById(R.id.btnProfile); btnProfile.setOnClickListener(this);
        btnVip = findViewById(R.id.btnVip); btnVip.setOnClickListener(this);
        btnTipzone = findViewById(R.id.btnTipzone); btnTipzone.setOnClickListener(this);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4597711656812814/1799989807");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        LinearLayoutManager lnrManager = new LinearLayoutManager(getApplicationContext());
        lnrManager.setReverseLayout(true);
        lnrManager.setStackFromEnd(true);

        recyclerView = findViewById(R.id.recyclerTest);
        recyclerView.setLayoutManager(lnrManager);
        progressBar.setVisibility(View.VISIBLE);

        adapt = new SectionedRecyclerViewAdapter();
        mRef = FirebaseDatabase.getInstance().getReference(database_path);
        loadTips(); //loads tips to screen

        //if user is signed in, then set navigation header
        mfirebaseAuth = FirebaseAuth.getInstance();
        user = mfirebaseAuth.getCurrentUser();
        if (user != null) {
            login = true;
            setHeader();
        }

        mInterstitialAd.setAdListener(new AdListener(){
            public void onAdLoaded(){
                mInterstitialAd.show();
            }
        });
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
                    Collections.reverse(list);
                    adapt.addSection(new TipSection(date, list, FreeActivity.this));
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

    public List<TipDetails> loadAll(DataSnapshot tipsSnap) {
        for (DataSnapshot datasnapshot : tipsSnap.getChildren()) {
            TipDetails tips = datasnapshot.getValue(TipDetails.class);
            list.add(tips);
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tips_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                else
                    mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.nav_home:
                finish();
                break;
            case R.id.menu_refresh:
                progressBar.setVisibility(View.VISIBLE);
                loadTips();
                break;
            default:
                finish();
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgProfilePic:
            case R.id.btnProfile:
                Cache cache = new Cache(FreeActivity.this);
                if(user!=null) {
                    cache.setUserID(user.getUid());
                    startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));
                }
                else{
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                break;
            case R.id.btnVip:
                startActivity(new Intent(this, VipPageActivity.class));
                break;
            case R.id.btnTipzone:
                startActivity(new Intent(this, RoomsPageActivity.class));
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_free).setChecked(true);

        //if user signs in recently, then fill navigationView header
        mfirebaseAuth = FirebaseAuth.getInstance();
        user = mfirebaseAuth.getCurrentUser();
        if (user != null) {
            if(!login){
                login = true;
                setHeader();
            }
        }
    }

    public void setHeader(){
        final String userID = user.getUid();
        usersDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference().child("Users").child(userID);
        usersDatabase.keepSynced(true);
        usersDatabase.addValueEventListener(new ValueEventListener() {
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()){
            case R.id.nav_home:
                finish();
                break;
            case R.id.nav_free:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_vip:
                finish();
                startActivity(new Intent(getApplicationContext(), VipPageActivity.class));
                break;
            case R.id.nav_tipzone:
                finish();
                startActivity(new Intent(getApplicationContext(), RoomsPageActivity.class));
                break;
            case R.id.nav_subscribe:
                startActivity(new Intent(this, SubscriptionReloadActivity.class));
                break;
            case R.id.nav_contact:
                startActivity(new Intent(getApplicationContext(), ContactActivity.class));
                break;
        }
        return true;
    }
}
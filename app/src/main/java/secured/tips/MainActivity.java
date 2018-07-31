package secured.tips;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import datafiles.AdsAdapter;
import datafiles.Cache;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView crdProfile, crdForum, crdFree, crdVIP, crdNews, crdPayment;

    private FirebaseAuth mfirebaseAuth;
    FirebaseUser user;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    FirebaseDatabase tipsDatabase, usersDatabse, chatDatabase;
    DatabaseReference mRef;
    Cache cache = new Cache();
    static InterstitialAd mInterstitialAd;
    static boolean calledAlready = false;
    ViewPager viewPager;
    TabLayout indicator;

    private List<Integer> imageDrawable;
    private List<String> buttonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        viewPager = findViewById(R.id.adsViewPager);
        indicator = findViewById(R.id.indicator);
        crdProfile = findViewById(R.id.crdProfile); crdProfile.setOnClickListener(this);
        crdForum = findViewById(R.id.crdForum); crdForum.setOnClickListener(this);
        crdFree = findViewById(R.id.crdFree); crdFree.setOnClickListener(this);
        crdVIP = findViewById(R.id.crdVIP); crdVIP.setOnClickListener(this);
        crdNews = findViewById(R.id.crdNews); crdNews.setOnClickListener(this);
        crdPayment = findViewById(R.id.crdPayment); crdPayment.setOnClickListener(this);

        tipsDatabase = FirebaseDatabase.getInstance();
        usersDatabse = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/");
        chatDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/");

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        calledAlready = prefs.getBoolean("CALLED", false);if(!calledAlready){
            tipsDatabase.setPersistenceEnabled(true);
            usersDatabse.setPersistenceEnabled(true);
            chatDatabase.setPersistenceEnabled(true);
            editor = prefs.edit();
            editor.putBoolean("CALLED", true);
        }

        //MobileAds.initialize(getApplicationContext(), "ca-app-pub-4597711656812814~3843067047");
        //mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-4597711656812814/1799989807");

        activateSlider();
        mfirebaseAuth = FirebaseAuth.getInstance();
        user = mfirebaseAuth.getCurrentUser(); if (user != null) {
            checkSub();
        }
    }

    public  void activateSlider(){
        imageDrawable = new ArrayList<>();
        imageDrawable.add(R.drawable.bg);
        imageDrawable.add(R.drawable.bg_one);
        imageDrawable.add(R.drawable.ads);

        buttonText = new ArrayList<>();
        buttonText.add("Enter");
        buttonText.add("Purcase here");
        buttonText.add("Contact us");

        viewPager.setAdapter(new AdsAdapter(this, imageDrawable, buttonText));
        indicator.setupWithViewPager(viewPager, true);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 8000);

    }
    public void checkSub(){
        final String id = user.getUid();
        final Date today = new Date();
        mRef= FirebaseDatabase.getInstance().getReference("Active");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(id)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
                    String now = sdf.format(today.getTime());
                    String ending  = dataSnapshot.child(id).child("ending").getValue(String.class);

                    Date date1 = sdf.parse(now, new ParsePosition(0));
                    Date date2 = sdf.parse(ending, new ParsePosition(0));

                    if(date1.after(date2)){
                        Log.i("Dame", "after");
                        mRef.child(id).removeValue();
                    }
                }
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
        if (item.getItemId() == R.id.menu_sign_out) {
            if(user!=null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
                builder.setTitle("Sign Out")
                        .setMessage("You want to signout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mfirebaseAuth.signOut();
                                user=null;
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
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.crdFree:
                startActivity(new Intent(getApplicationContext(), FreePageActivity.class));
                break;
            case R.id.crdVIP:
                startActivity(new Intent(getApplicationContext(), VipPageActivity.class));
                break;
            case R.id.crdProfile:
                if(user!=null) {
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
            case R.id.crdPayment:
                startActivity(new Intent(getApplicationContext(), SubscribeActivity.class));
                break;
        }
    }
    public void onResume(){
        //mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mfirebaseAuth = FirebaseAuth.getInstance();
        user = mfirebaseAuth.getCurrentUser();
        super.onResume();
    }


    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < imageDrawable.size() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
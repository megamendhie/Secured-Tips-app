package secured.tips;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView crdProfile, crdForum, crdFree, crdVIP, crdNews, crdPayment;

    private FirebaseAuth mfirebaseAuth;
    FirebaseUser user;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    DatabaseReference mRef;
    static InterstitialAd mInterstitialAd;
    static boolean calledAlready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crdProfile = findViewById(R.id.crdProfile); crdProfile.setOnClickListener(this);
        crdForum = findViewById(R.id.crdForum); crdForum.setOnClickListener(this);
        crdFree = findViewById(R.id.crdFree); crdFree.setOnClickListener(this);
        crdVIP = findViewById(R.id.crdVIP); crdVIP.setOnClickListener(this);
        crdNews = findViewById(R.id.crdNews); crdNews.setOnClickListener(this);
        crdPayment = findViewById(R.id.crdPayment); crdPayment.setOnClickListener(this);


        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //MobileAds.initialize(getApplicationContext(), "ca-app-pub-4597711656812814~3843067047");
        //mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-4597711656812814/1799989807");
        if(!calledAlready){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            calledAlready = true;
        }
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        mfirebaseAuth = FirebaseAuth.getInstance();
        user = mfirebaseAuth.getCurrentUser(); if (user != null) {
            checkSub();
        }
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
            Toast.makeText(getApplicationContext(), "Don't fuck with Mega M.. hahaha", Toast.LENGTH_SHORT).show();

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
                startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));
                break;
            case R.id.crdForum:
                startActivity(new Intent(getApplicationContext(), RoomsPageActivity.class));
                break;
        }
    }
    public void onResume(){
        //mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mfirebaseAuth = FirebaseAuth.getInstance();
        user = mfirebaseAuth.getCurrentUser();
        super.onResume();
    }
}
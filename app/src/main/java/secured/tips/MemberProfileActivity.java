package secured.tips;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import datafiles.Cache;
import datafiles.GlideApp;
import de.hdodenhof.circleimageview.CircleImageView;

public class MemberProfileActivity extends AppCompatActivity implements View.OnClickListener {
    ActionBar actionBar;
    private TabLayout tabLayout;
    String firstName, lastName, Username, gender, imageURL;
    long totalPoints;
    SharedPreferences prefs;
    long[] point = new long[7];
    TextView[] txtviewPoint = new TextView[7];
    LinearLayout[] lnr = new LinearLayout[7];
    private ViewPager viewPager;
    Cache cache;
    TextView txtName, txtUsername, txtGender, txtPoint;
    DatabaseReference mDatabase;
    FirebaseAuth mfirebaseAuth;
    CircleImageView imgProfile;
    ImageView imgGenderIcon;
    String userID;
    StorageReference storageReference;
    RequestOptions requestOptions = new RequestOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);
        storageReference = FirebaseStorage.getInstance().getReference().child("profile_images");

        txtName = findViewById(R.id.txtName);
        txtGender = findViewById(R.id.txtGender);
        txtUsername= findViewById(R.id.txtUsername);
        txtPoint = findViewById(R.id.txtPoint);
        imgProfile = findViewById(R.id.imgProfilePic);
        imgGenderIcon= findViewById(R.id.imgGenderIcon);
        //TextView for individual points
        txtviewPoint[1] = findViewById(R.id.txtPoint1);
        txtviewPoint[2] = findViewById(R.id.txtPoint2);
        txtviewPoint[3] = findViewById(R.id.txtPoint3);
        txtviewPoint[4] = findViewById(R.id.txtPoint4);
        txtviewPoint[5] = findViewById(R.id.txtPoint5);
        txtviewPoint[6] = findViewById(R.id.txtPoint6);

        //Layout for individual points
        lnr[1] = findViewById(R.id.lnr1); lnr[1].setOnClickListener(this);
        lnr[2] = findViewById(R.id.lnr2); lnr[2].setOnClickListener(this);
        lnr[3] = findViewById(R.id.lnr3); lnr[3].setOnClickListener(this);
        lnr[4] = findViewById(R.id.lnr4); lnr[4].setOnClickListener(this);
        lnr[5] = findViewById(R.id.lnr5); lnr[5].setOnClickListener(this);
        lnr[6] = findViewById(R.id.lnr6); lnr[6].setOnClickListener(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        userID = prefs.getString("UserId", "nothing");

        requestOptions.placeholder(R.drawable.dspc);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        cache = new Cache();

        mfirebaseAuth = FirebaseAuth.getInstance();
        if(userID.equals("nothing")){
            finish();
        }
        mDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference().child("Users").child(userID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firstName= dataSnapshot.child("a1_firstname").getValue(String.class);
                lastName= dataSnapshot.child("a2_lastname").getValue(String.class);
                Username= dataSnapshot.child("a3_username").getValue(String.class);
                gender= dataSnapshot.child("a6_gender").getValue(String.class);
                imageURL= dataSnapshot.child("a7_imageURL").getValue(String.class);
                totalPoints= dataSnapshot.child("a8_points").getValue(long.class);

                point[1]= dataSnapshot.child("a8_points_2").getValue(long.class);
                point[2]= dataSnapshot.child("a8_points_3").getValue(long.class);
                point[3]= dataSnapshot.child("a8_points_4").getValue(long.class);
                point[4]= dataSnapshot.child("a8_points_5").getValue(long.class);
                point[5]= dataSnapshot.child("a8_points_6").getValue(long.class);
                point[6]= dataSnapshot.child("a8_points_7").getValue(long.class);
                for(int i=1; i<=6; i++){
                    txtviewPoint[i].setText(String.valueOf(point[i]));
                }

                actionBar.setTitle(firstName + " " + lastName);
                txtName.setText(firstName + " " + lastName);
                txtUsername.setText(Username);
                txtGender.setText(gender);
                txtPoint.setText(String.valueOf(totalPoints) + " points");
                if(gender.toLowerCase().equals("female")){
                    imgGenderIcon.setImageResource(R.drawable.f_icn);
                }

                if(imageURL.equals("none")){
                    GlideApp.with(getApplicationContext())
                            .load(R.drawable.dspc)
                            .into(imgProfile);
                }
                else{
                    GlideApp.with(getApplicationContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(imageURL)
                            .into(imgProfile);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        MemberProfileActivity.ViewPagerAdapter adapter = new MemberProfileActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RecentpostFragment(), "RECENT POSTS");
        adapter.addFragment(new WonGamesFragment(), "WON GAMES");
        viewPager.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }

    @Override
    public void onClick(View view) {
        String popup="";
        switch (view.getId()){
            case R.id.lnr1:
                popup="3-10 odds"; break;
            case R.id.lnr2:
                popup="11-50 odds"; break;
            case R.id.lnr3:
                popup="51-100 odds"; break;
            case R.id.lnr4:
                popup="101-350 odds"; break;
            case R.id.lnr5:
                popup="351+ odds"; break;
            case R.id.lnr6:
                popup="Draws"; break;
        }
        Toast.makeText(getApplicationContext(), popup, Toast.LENGTH_SHORT).show();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
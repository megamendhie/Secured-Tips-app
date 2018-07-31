package secured.tips;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import datafiles.Cache;

public class MemberProfileActivity extends AppCompatActivity {
    ActionBar actionBar;
    private TabLayout tabLayout;
    String firstName, lastName, Username, gender;
    long points;
    private ViewPager viewPager;
    Cache cache = new Cache();
    TextView txtName, txtUsername, txtGender, txtPoint;
    DatabaseReference mDatabase;
    FirebaseAuth mfirebaseAuth;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);

        txtName = findViewById(R.id.txtName);
        txtGender = findViewById(R.id.txtGender);
        txtUsername= findViewById(R.id.txtUsername);
        txtPoint = findViewById(R.id.txtPoint);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        mfirebaseAuth = FirebaseAuth.getInstance();
        userID = cache.getUserID();
        mDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference().child("Users").child(userID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firstName= dataSnapshot.child("a1_firstname").getValue(String.class);
                lastName= dataSnapshot.child("a2_lastname").getValue(String.class);
                Username= dataSnapshot.child("a3_username").getValue(String.class);
                gender= dataSnapshot.child("a6_gender").getValue(String.class);
                points= dataSnapshot.child("a8_points").getValue(long.class);

                txtName.setText(firstName + " " + lastName);
                txtUsername.setText(Username);
                txtGender.setText(gender);
                txtPoint.setText(String.valueOf(points) + " points");
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
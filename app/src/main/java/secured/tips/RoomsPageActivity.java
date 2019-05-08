package secured.tips;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
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
import datafiles.ChatMessage;
import datafiles.GlideApp;
import datafiles.RecommendedAdapter;
import datafiles.UserProfile;
import de.hdodenhof.circleimageview.CircleImageView;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class RoomsPageActivity extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference mRef, mDatabase;
    FirebaseUser user;
    String userID="x";
    ActionBar actionBar;
    private DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    TextView txtName, txtUsername, txtPoints;
    Button btnProfile;
    CircleImageView imgDp;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    View header;
    private boolean login = false;
    RequestOptions requestOptions = new RequestOptions();
    Button btnAbout;
    RecyclerView listRecommended;
    SectionedRecyclerViewAdapter adapter;
    String firstName =", Tipster";
    List<ChatMessage> wonGames;
    List<DatabaseReference> Refs;
    static InterstitialAd mInterstitialAd;
    CardView rm1, rm2,rm3,rm4, rm5, rm7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_page);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view); navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);
        imgDp = header.findViewById(R.id.imgProfilePic); imgDp.setOnClickListener(this);
        txtName = header.findViewById(R.id.txtName);
        txtUsername = header.findViewById(R.id.txtUsername);
        txtPoints = header.findViewById(R.id.txtPoint);
        btnProfile = header.findViewById(R.id.btnProfile); btnProfile.setOnClickListener(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        rm1 = findViewById(R.id.crdRoomOne); rm1.setOnClickListener(this);
        rm2 = findViewById(R.id.crdRoomTwo); rm2.setOnClickListener(this);
        rm3 = findViewById(R.id.crdRoomThree); rm3.setOnClickListener(this);
        rm4 = findViewById(R.id.crdRoomFour); rm4.setOnClickListener(this);
        rm5 = findViewById(R.id.crdRoomFive); rm5.setOnClickListener(this);
        rm7 = findViewById(R.id.crdRoomSeven); rm7.setOnClickListener(this);
        listRecommended = findViewById(R.id.listRecommended);
        listRecommended.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        btnAbout = findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            login = true;
            userID = user.getUid();
            mDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference().child("Users").child(userID);
            mDatabase.keepSynced(true);
            setHeader();
        }

        mRef = FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/").getReference()
                .child("chatrooms").child("room_2");
        mRef.keepSynced(true);
        adapter = new SectionedRecyclerViewAdapter(){
            @Override
            public long getItemId(int position){return position;}

            @Override
            public void setHasStableIds(boolean hasStableIds){
                super.setHasStableIds(hasStableIds);
            }
        };

        FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/").getReference().child("chatrooms")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
        //loads the recommended messages at the bottom
        showRecommendedMesseges();
    }

    private void showRecommendedMesseges(){
        mRef.limitToLast(60).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    adapter.removeAllSections();
                    wonGames = new ArrayList<ChatMessage>();
                    Refs = new ArrayList<DatabaseReference>();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        ChatMessage post = snapshot.getValue(ChatMessage.class);
                        if(String.valueOf(post.getMessageStatus().charAt(0)).equals("1")){
                            DatabaseReference ref = snapshot.getRef();
                            Refs.add(ref);
                            wonGames.add(post);
                        }
                    }
                    Collections.reverse(Refs);
                    Collections.reverse(wonGames);
                    adapter.addSection(new RecommendedAdapter(wonGames, RoomsPageActivity.this, RoomsPageActivity.this, userID, Refs));
                    adapter.notifyDataSetChanged();
                    }
                listRecommended.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                }
        });
    }

    public void popUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage("You must login first")
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                })
                .show();
    }

    public void popUp4(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage("Hello " + firstName
                +".\n\nSure Banker room will be ready on our next version. The best tipsters will post their surest tip for each day here.")
                .setNegativeButton("Alright", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                })
                .show();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);
        switch (view.getId()){
            case R.id.imgProfilePic:
            case R.id.btnProfile:
                Cache cache = new Cache(RoomsPageActivity.this);
                if(user!=null) {
                    cache.setUserID(user.getUid());
                    startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));
                }
                else{
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                break;
            case R.id.crdRoomOne:
                intent.putExtra("ROOM",1);
                startActivity(intent);
                break;
            case R.id.crdRoomTwo:
                if(user==null){
                    popUp();
                    return;
                }
                intent.putExtra("ROOM",2);
                startActivity(intent);
                break;
            case R.id.crdRoomThree:
                if(user==null){
                    popUp();
                    return;
                }
                intent.putExtra("ROOM",3);
                startActivity(intent);
                break;
            case R.id.crdRoomFour:
                if(user==null){
                    popUp();
                    return;
                }
                intent.putExtra("ROOM",4);
                startActivity(intent);
                break;
            case R.id.crdRoomFive:
                if(user==null){
                    popUp();
                    return;
                }
                popUp4();
                break;
            case R.id.crdRoomSeven:
                if(user==null){
                    popUp();
                    return;
                }
                intent.putExtra("ROOM",7);
                startActivity(intent);
                break;
            case R.id.btnAbout:
                startActivity(new Intent(getApplicationContext(), AboutTipzoneActivity.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tipzone_menu, menu);
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
            case R.id.nav_home:
                finish();
                break;
            case R.id.menu_share:
                shareApp();
                break;
            case R.id.menu_privacy_policy:
                Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://securedtips.co/privacypolicy.html"));
                startActivity(i);
                break;
            default:
                finish();
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

    @Override
    public void onResume(){
        super.onResume();
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_tipzone).setChecked(true);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            if(login!=true){
                userID = user.getUid();
                mDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference().child("Users").child(userID);
                login=true;
                showRecommendedMesseges();
                setHeader();
            }
        }
    }

    public void setHeader(){
        mDatabase.addValueEventListener(new ValueEventListener() {
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
                finish();
                startActivity(new Intent(getApplicationContext(), FreeActivity.class));
                break;
            case R.id.nav_vip:
                finish();
                startActivity(new Intent(getApplicationContext(), VipPageActivity.class));
                break;
            case R.id.nav_tipzone:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
}
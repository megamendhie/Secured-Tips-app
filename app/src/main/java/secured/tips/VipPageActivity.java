package secured.tips;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import datafiles.Cache;
import datafiles.GlideApp;
import datafiles.UserProfile;
import de.hdodenhof.circleimageview.CircleImageView;

public class VipPageActivity extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference usersDatabase;
    private FirebaseAuth mfirebaseAuth;
    FirebaseUser user;
    CardView crdVip, crdDraws;
    ActionBar actionBar;
    private DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    TextView txtName, txtUsername, txtPoints;
    Button btnProfile;
    CircleImageView imgDp;
    View header;
    private boolean login = false;
    RequestOptions requestOptions = new RequestOptions();
    TextView txtSub, txtChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_page);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view); navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);
        imgDp = header.findViewById(R.id.imgProfilePic); imgDp.setOnClickListener(this);
        txtName = header.findViewById(R.id.txtName);
        txtUsername = header.findViewById(R.id.txtUsername);
        txtPoints = header.findViewById(R.id.txtPoint);
        btnProfile = header.findViewById(R.id.btnProfile); btnProfile.setOnClickListener(this);
        crdVip = findViewById(R.id.crdVIP); crdVip.setOnClickListener(this);
        crdDraws = findViewById(R.id.crdDraw); crdDraws.setOnClickListener(this);
        txtSub = findViewById(R.id.txtSubscribe); txtChat = findViewById(R.id.txtChat);
        txtSub.setOnClickListener(this);    txtChat.setOnClickListener(this);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        FirebaseDatabase.getInstance().getReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
        //if user is signed in, then set navigation header
        mfirebaseAuth = FirebaseAuth.getInstance();
        user = mfirebaseAuth.getCurrentUser();
        if (user != null) {
            login = true;
            setHeader();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgProfilePic:
            case R.id.btnProfile:
                Cache cache = new Cache(VipPageActivity.this);
                if(user!=null) {
                    cache.setUserID(user.getUid());
                    startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));
                }
                else{
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                break;
            case R.id.crdVIP:
                startActivity(new Intent(this, PremiumActivity.class));
                break;
            case R.id.crdDraw:
                startActivity(new Intent(this, DrawActivity.class));
                break;
            case R.id.txtChat:
                startWhatsApp();
                break;
            case R.id.txtSubscribe:
                startActivity(new Intent(this, SubscriptionReloadActivity.class));
                break;
        }
    }

    public void startWhatsApp(){
        String mssg = "Hello, Secured Tips.\nI need some assistance.";
        String toNumber = "2348132014755";
        PackageManager pkMgt = getPackageManager();
        Uri uri = Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+mssg);
        try {
            Intent whatsApp = new Intent(Intent.ACTION_VIEW);
            whatsApp.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            whatsApp.setData(uri);
            PackageInfo info = pkMgt.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            startActivity(whatsApp);
        }
        catch (PackageManager.NameNotFoundException e){
            Toast.makeText(this, "No whatsapp installed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_vip).setChecked(true);

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
        usersDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference()
                .child("Users").child(userID);
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
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                else
                    mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.nav_home:
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
                mDrawerLayout.closeDrawer(GravityCompat.START);
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

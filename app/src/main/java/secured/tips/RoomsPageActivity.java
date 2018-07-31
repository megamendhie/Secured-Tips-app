package secured.tips;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RoomsPageActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mfirebaseAuth;
    DatabaseReference mDatabase;
    FirebaseUser user;
    ActionBar actionBar;
    Button btnAbout;
    boolean VipSub;
    boolean ChatSub;
    String firstName =",Great Tipster";
    Button btnSubmit, btnShare, btnDelete;
    CardView rm1, rm2,rm3,rm4, rm5, rm6, rm7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_page);
        mfirebaseAuth = FirebaseAuth.getInstance();
        user = mfirebaseAuth.getCurrentUser();

        rm1 = findViewById(R.id.crdRoomOne); rm1.setOnClickListener(this);
        rm2 = findViewById(R.id.crdRoomTwo); rm2.setOnClickListener(this);
        rm3 = findViewById(R.id.crdRoomThree); rm3.setOnClickListener(this);
        rm4 = findViewById(R.id.crdRoomFour); rm4.setOnClickListener(this);
        rm5 = findViewById(R.id.crdRoomFive); rm5.setOnClickListener(this);
        rm6 = findViewById(R.id.crdRoomSix); rm6.setOnClickListener(this);
        rm7 = findViewById(R.id.crdRoomSeven); rm7.setOnClickListener(this);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        btnAbout = findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(this);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
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

    public void popUp2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage("Welcome " + firstName
                +".\n\nYou are either new here or your subscription just expired. The big odd rooms are only for VIP or Forum subscribers.\n\n"
                +"These rooms are more organized, more accurate, and has many other features to help you win big.\n"
                +"Subscribe to access all the big odd rooms.")
                .setPositiveButton("Subscribe now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), SubscribeActivity.class));
                    }
                })
                .setNegativeButton("Later", new DialogInterface.OnClickListener() {
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
            case R.id.crdRoomOne:
                intent.putExtra("ROOM",1);
                startActivity(intent);
                break;
            case R.id.crdRoomTwo:
                intent.putExtra("ROOM",2);
                startActivity(intent);
                break;
            case R.id.crdRoomThree:
                if(user==null){
                    popUp();
                    return;
                }
                if(!confirmSubscribtion()){
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
                if(!confirmSubscribtion()){
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
                if(!confirmSubscribtion()){
                    return;
                }
                intent.putExtra("ROOM",5);
                startActivity(intent);
                break;
            case R.id.crdRoomSix:
                if(user==null){
                    popUp();
                    return;
                }
                if(!confirmSubscribtion()){
                    return;
                }
                intent.putExtra("ROOM",6);
                startActivity(intent);
                break;
            case R.id.crdRoomSeven:
                if(user==null){
                    popUp();
                    return;
                }
                if(!confirmSubscribtion()){
                    return;
                }
                intent.putExtra("ROOM",7);
                startActivity(intent);
                break;
            case R.id.btnSubmit:
                Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();
            case R.id.btnAbout:
                startActivity(new Intent(getApplicationContext(), AboutTipzoneActivity.class));
                break;
        }
    }

    public boolean confirmSubscribtion(){
        boolean subscriber = false;
        mDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference().child("Users").child(user.getUid());
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                VipSub = dataSnapshot.child("b2_vip").getValue(boolean.class);
                ChatSub =  dataSnapshot.child("b4_chat").getValue(boolean.class);
                firstName = dataSnapshot.child("a1_firstname").getValue(String.class);
                Log.i("Faster", "vipsub is " + String.valueOf(VipSub) + ", chatsub is " + String.valueOf(ChatSub));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
        if(VipSub!=true|| ChatSub!=true){
            popUp2();
        }
        else{
            subscriber = true;
        }
        return subscriber;
    }
}

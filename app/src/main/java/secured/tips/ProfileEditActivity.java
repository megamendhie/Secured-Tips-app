package secured.tips;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileEditActivity extends AppCompatActivity implements View.OnClickListener{


    ActionBar actionBar;
    EditText edtFirstName, edtLastName, edtUsername, edtPhone, edtEmail, edtAccount;
    RadioButton rdbMale, rdbFemale;
    ImageView imgAdd, imgProfilePic;
    String firstName, lastName, Username, gender, imageURL, email, account, phone;
    long points;
    FloatingActionButton btnSave;
    DatabaseReference mDatabase;
    FirebaseAuth mfirebaseAuth;
    FirebaseUser user;
    String userID;
    private Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtUsername = findViewById(R.id.edtUsername);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        edtAccount = findViewById(R.id.edtAccountDetails);

        rdbFemale = findViewById(R.id.rdbFemale);
        rdbMale = findViewById(R.id.rdbMale);

        imgAdd = findViewById(R.id.imgAdd); imgAdd.setOnClickListener(this);
        imgProfilePic = findViewById(R.id.imgProfilePic); imgProfilePic.setOnClickListener(this);

        FloatingActionButton btnSave = findViewById(R.id.fltButton);
        btnSave.setOnClickListener(this);

        fillContent();
    }

    private void fillContent() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("profile_images");

        mfirebaseAuth = FirebaseAuth.getInstance();
        user = mfirebaseAuth.getCurrentUser();
        userID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference().child("Users").child(userID);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firstName= dataSnapshot.child("a1_firstname").getValue(String.class);
                lastName= dataSnapshot.child("a2_lastname").getValue(String.class);
                Username= dataSnapshot.child("a3_username").getValue(String.class);
                gender= dataSnapshot.child("a6_gender").getValue(String.class);
                points= dataSnapshot.child("a8_points").getValue(long.class);
                imageURL= dataSnapshot.child("a7_imageURL").getValue(String.class);
                phone = dataSnapshot.child("a5_phone").getValue(String.class);
                email= dataSnapshot.child("a4_email").getValue(String.class);
                account= dataSnapshot.child("b1_account").getValue(String.class);

                edtFirstName.setText(firstName);
                edtLastName.setText(lastName);
                edtUsername.setText(Username);
                edtPhone.setText(phone);
                edtEmail.setText(email);
                edtAccount.setText(account);

                if(gender.toLowerCase().equals("male")){
                    rdbMale.setChecked(true);
                }else if(gender.toLowerCase().equals("female")){
                    rdbFemale.setChecked(true);
                }
                StorageReference sRef = storageReference.child(userID);

                if(imageURL!="none"){
                    Glide.with(getApplicationContext()).load(imageURL).into(imgProfilePic);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fltButton:
                break;
            case R.id.imgAdd:
                break;
            case R.id.imgProfilePic:
                break;
        }

    }
}

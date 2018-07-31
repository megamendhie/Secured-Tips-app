package secured.tips;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSubmit;
    EditText edtEmail, edtPassword, edtUsername, edtFirstName, edtLastName, edtPhone;
    private FirebaseAuth mfirebaseAuth;
    ProgressDialog progressDialog;
    FirebaseUser user;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    DatabaseReference mDatabase;
    ActionBar actionBar;
    RadioGroup rdbGroup;

    private String a1_firstName;
    private String a2_lastName;
    private String a3_username;
    private String a4_email;
    private String   a5_phone = "none";
    private String a6_gender;
    private String a7_imageURL = "none";
    private long   a8_points = 0;
    private long   a9_payment = 0;
    private String b1_account = "none";
    private boolean b2_vip = false;
    private String    b3_vip_ending = "80-01-01";
    private boolean b4_chat = false;
    private String    b5_chat_ending = "80-01-01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mfirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        rdbGroup = findViewById(R.id.rdbGroup);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        edtUsername = findViewById(R.id.edtUsername);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtPhone = findViewById(R.id.edtPhone);
        progressDialog = new ProgressDialog(this);

    }

    @Override
    public void onClick(View view) {
        if(view == btnSubmit) {
            registerUser();
        }

    }

    public void registerUser(){
        a1_firstName = edtFirstName.getText().toString().trim();
        a2_lastName = edtLastName.getText().toString().trim();
        a3_username = edtUsername.getText().toString().trim();
        a4_email = edtEmail.getText().toString().trim();
        a5_phone = edtPhone.getText().toString().trim();

        final String password = edtPassword.getText().toString().trim();
        switch (rdbGroup.getCheckedRadioButtonId()) {
            case R.id.rdbMale:
                a6_gender = "male";
                break;
            case R.id.rdbFemale:
                a6_gender = "female";
                break;
        }

        if (TextUtils.isEmpty(a1_firstName)) {
            Toast.makeText(this,"Please enter your first name",Toast.LENGTH_LONG).show();
            return;}

        if (TextUtils.isEmpty(a3_username)) {
            Toast.makeText(this,"Please enter your username",Toast.LENGTH_LONG).show();
            return;}

        if (TextUtils.isEmpty(a4_email)) {
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;}

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;}

        if (TextUtils.isEmpty(a6_gender)) {
            Toast.makeText(this,"Please choose sex",Toast.LENGTH_LONG).show();
            return;}

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        mfirebaseAuth.createUserWithEmailAndPassword(a4_email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            editor.putString("PASSWORD", password);
                            editor.putString("EMAIL", a4_email);
                            editor.apply();
                            Toast.makeText(getApplicationContext(), "Registration successful.", Toast.LENGTH_SHORT).show();
                            user = mfirebaseAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(a3_username)
                                    .build();

                            user.updateProfile(profileUpdates);

                            finish();
                            storeInfo();
                            //updateUI(user);
                        }else{
                            Toast.makeText(getApplicationContext(), "Registration error. Check your details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void storeInfo(){
        mDatabase = mDatabase.child("Users");
        String key = user.getUid();
        Map<String, Object> details = new HashMap<>();
        details.put("a1_firstname",a1_firstName);
        details.put("a2_lastname",a2_lastName);
        details.put("a3_username",a3_username);
        details.put("a4_email", a4_email);
        details.put("a5_phone", a5_phone);
        details.put("a6_gender", a6_gender);
        details.put("a7_imageURL", a7_imageURL);
        details.put("a8_points", a8_points);
        details.put("a8_points_1", 0);
        details.put("a8_points_2", 0);
        details.put("a8_points_3", 0);
        details.put("a8_points_4", 0);
        details.put("a8_points_5", 0);
        details.put("a9_payment", a9_payment);
        details.put("b1_account", b1_account);
        details.put("b2_vip", b2_vip);
        details.put("b3_vip_ending", b3_vip_ending);
        details.put("b4_chat", b4_chat);
        details.put("b5_chat_ending", b5_chat_ending);

        Map<String, Object> update = new HashMap<>();
        update.put(key, details);
        mDatabase.updateChildren(update);

    }

    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }

}
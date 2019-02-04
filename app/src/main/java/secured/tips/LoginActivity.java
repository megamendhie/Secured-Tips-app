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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import datafiles.Cache;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSubmit;
    EditText edtEmail, edtPassword;
    TextView txtSignUp;
    private FirebaseAuth mfirebaseAuth;
    DatabaseReference mRef;
    ProgressDialog progressDialog;
    FirebaseUser user;
    int intType;
    Cache cache;
    boolean active = false;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intType = getIntent().getIntExtra("SENDER", 0);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        cache  = new Cache(LoginActivity.this);

        mfirebaseAuth = FirebaseAuth.getInstance();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        txtSignUp = (TextView)findViewById(R.id.txtSignUp); txtSignUp.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

        if(prefs.getString("PASSWORD", "X%p8kznAA1")!= "X%p8kznAA1"){
            edtEmail.setText(prefs.getString("EMAIL","myemail@whatever.com"));
            edtPassword.setText(prefs.getString("PASSWORD", "X%p8kznAA1"));
        }

    }

    @Override
    public void onClick(View view) {
        if(view == btnSubmit) {
            loginUser();
        }
        if(view==txtSignUp){
            finish();
            startActivity(new Intent(this, SignUpActivity.class).putExtra("SENDER", intType));
        }
    }

    public void loginUser(){
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;}

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;}

        progressDialog.setMessage("Signing in...");
        progressDialog.show();

        mfirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    editor.putString("PASSWORD", edtPassword.getText().toString().trim());
                    editor.putString("EMAIL", edtEmail.getText().toString().trim());
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Login successful.", Toast.LENGTH_SHORT).show();
                    user = mfirebaseAuth.getCurrentUser();
                    final String userID = user.getUid();
                    FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference()
                            .child("Users").child(userID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean vipSub = dataSnapshot.child("b2_vip").getValue(boolean.class);
                            boolean roomSub = dataSnapshot.child("b4_chat").getValue(boolean.class);
                            cache.setUserID(userID);
                            cache.setVipSub(vipSub);
                            cache.setRoomSub(roomSub);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    onBackPressed();
                }else{
                    Toast.makeText(getApplicationContext(), "Sign in failed. Check your details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }

    @Override
    public void onBackPressed(){
        finish();
        if(intType==1){
            startActivity(new Intent(this, PremiumActivity.class));
        }else if(intType==2){
            startActivity(new Intent(this, DrawActivity.class));
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }

}
package secured.tips;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import datafiles.GlideApp;

public class ProfileEditActivity extends AppCompatActivity implements View.OnClickListener{


    ActionBar actionBar;
    EditText edtFirstName, edtLastName, edtUsername, edtPhone, edtAccount;
    TextView edtEmail;
    RadioButton rdbMale, rdbFemale;
    ImageView imgAdd, imgProfilePic;
    String firstName, lastName, Username, gender, imageURL, email, account, phone;
    FloatingActionButton btnSave;
    DatabaseReference mDatabase;
    FirebaseAuth mfirebaseAuth;
    FirebaseUser user;
    String userID;
    private Uri filePath = null;
    FirebaseStorage storage;
    StorageReference storageReference;
    RequestOptions requestOptions = new RequestOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        storageReference = FirebaseStorage.getInstance().getReference().child("profile_images");

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

        requestOptions.placeholder(R.drawable.dspc);
        btnSave = findViewById(R.id.fltButton);
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
                imageURL= dataSnapshot.child("a7_imageURL").getValue(String.class);
                phone = dataSnapshot.child("a5_phone").getValue(String.class);
                email= dataSnapshot.child("a4_email").getValue(String.class);
                account= dataSnapshot.child("b1_account").getValue(String.class);

                edtFirstName.setText(firstName);
                edtLastName.setText(lastName.equals("none")? "": lastName);
                edtUsername.setText(Username);
                edtPhone.setText(phone);
                edtEmail.setText(email);
                edtAccount.setText(account.equals("none")? "" : account);

                if(gender.toLowerCase().equals("male")){
                    rdbMale.setChecked(true);
                }else if(gender.toLowerCase().equals("female")){
                    rdbFemale.setChecked(true);
                }

                if(imageURL.equals("none")){
                    GlideApp.with(getApplicationContext())
                            .load(R.drawable.dspc)
                            .into(imgProfilePic);
                }
                else{
                    GlideApp.with(getApplicationContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(imageURL)
                            .into(imgProfilePic);
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
                save();
                break;
            case R.id.imgAdd:
                grabImage();
                break;
            case R.id.imgProfilePic:
                break;
        }
    }

    public void grabImage(){
        CropImage.activity()
                .setFixAspectRatio(true)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(data!=null){
                try{
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = bundle.getParcelable("data");
                    imgProfilePic.setImageBitmap(bitmap);
                }
                catch (NullPointerException e){
                    Toast.makeText(getApplicationContext(), "null error", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                filePath = result.getUri();
                uploadImage();
                imgProfilePic.setImageURI(filePath);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void save(){
        String a1,a2,a3, a5, b1;
        a1 = edtFirstName.getText().toString();
        a2 = edtLastName.getText().toString();
        a3 = edtUsername.getText().toString();
        a5 = edtPhone.getText().toString();
        b1 = edtAccount.getText().toString();
        if (TextUtils.isEmpty(a1)) {
            Toast.makeText(this,"Please enter first name",Toast.LENGTH_LONG).show();
            return;}

        if (TextUtils.isEmpty(a2)) {
            Toast.makeText(this,"Please enter last name",Toast.LENGTH_LONG).show();
            return;}

        if (TextUtils.isEmpty(a3)) {
            Toast.makeText(this,"Please enter username",Toast.LENGTH_LONG).show();
            return;}

        if (TextUtils.isEmpty(a5)) {
            Toast.makeText(this,"Please enter phone number",Toast.LENGTH_LONG).show();
            return;}

        HashMap<String, Object> details = new HashMap<>();
        details.put("a1_firstname",a1);
        details.put("a2_lastname",a2);
        details.put("a3_username",a3);
        details.put("a5_phone", a5);
        if (!b1.equals("")) {
            details.put("b1_account", b1);
        }
        mDatabase.updateChildren(details);
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(a3)
                .build();

        user.updateProfile(profileUpdates);
        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
    }


    public void uploadImage(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        final StorageReference ref = storageReference.child(userID);
        ref.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                .addOnSuccessListener(uri -> {
                                    String url = uri.toString();
                                    mDatabase.child("a7_imageURL").setValue(url);
                                    progressDialog.dismiss();
                                    Toast.makeText(ProfileEditActivity.this, "Image saved", Toast.LENGTH_SHORT).show();
                                    imgProfilePic.setImageURI(filePath);
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileEditActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage((int) progress + "%" + " completed" );
                    }
                })
        ;
    }
}

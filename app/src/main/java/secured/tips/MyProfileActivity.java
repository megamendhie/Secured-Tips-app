package secured.tips;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import java.util.ArrayList;
import java.util.List;

public class MyProfileActivity extends AppCompatActivity {
    ActionBar actionBar;
    private TabLayout tabLayout;
    String firstName, lastName, Username, gender, imageURL;
    long points;
    ImageView imgEdit;
    private ViewPager viewPager;
    TextView txtName, txtUsername, txtGender, txtPoint;
    DatabaseReference mDatabase;
    FirebaseAuth mfirebaseAuth;
    FirebaseUser user;
    String userID;
    private Uri filePath;
    ImageView imgProfile;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("profile_images");

        txtName = findViewById(R.id.txtName);
        txtGender = findViewById(R.id.txtGender);
        txtUsername= findViewById(R.id.txtUsername);
        txtPoint = findViewById(R.id.txtPoint);
        imgEdit = findViewById(R.id.imgEdit);
        imgProfile = findViewById(R.id.imgProfilePic);
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //grabImage();
                //cropActivity();
                startActivity(new Intent(getApplicationContext(), ProfileEditActivity.class));
            }
        });

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

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

                txtName.setText(firstName + " " + lastName);
                txtUsername.setText(Username);
                txtGender.setText(gender);
                txtPoint.setText(String.valueOf(points) + " points");
                StorageReference sRef = storageReference.child(userID);

                if(imageURL!="none"){
                    Glide.with(getApplicationContext()).load(imageURL).into(imgProfile);

                    /*
                    sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imUri = uri.toString();
                            Glide.with(getApplicationContext()).load(imUri).into(imgProfile);
                        }
                    });
                    */
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(data!=null){
                try{
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                imgProfile.setImageBitmap(bitmap);
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
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RecentpostFragment(), "RECENT POSTS");
        adapter.addFragment(new WonGamesFragment(), "WON GAMES");
        viewPager.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }

    public void uploadImage(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference ref = storageReference.child(userID);
        ref.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getDownloadUrl().toString();
                        progressDialog.dismiss();
                        mDatabase.child("a7_imageURL").setValue(url);
                        Toast.makeText(MyProfileActivity.this, "Profile picture saved", Toast.LENGTH_SHORT).show();
                        imgProfile.setImageURI(filePath);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(MyProfileActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage((int) progress + "%" + " uploaded" );
                    }
                })
        ;
    }

    public void cropActivity(){
       CropImage.activity()
                .setFixAspectRatio(true)
                .start(this);


        //Start cropping activity for already acquired image
        /*
        CropImage.activity(filePath)
                .setFixAspectRatio(true)
                .start(this);
        */
    }

    public void ImageCropFunction() {

        // Image Crop Code
        Intent CropIntent;
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");

            CropIntent.setDataAndType(filePath, "image/*");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 300);
            CropIntent.putExtra("outputY", 300);
            CropIntent.putExtra("aspectX", 1);
            CropIntent.putExtra("aspectY", 1);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, 1);

        } catch (ActivityNotFoundException e) {

        }
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
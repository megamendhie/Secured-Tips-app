package secured.tips;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flutterwave.raveandroid.RaveConstants;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RavePayManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import datafiles.Cache;
import datafiles.NewsFunction;

public class SubscriptionReloadActivity extends Activity implements View.OnClickListener {
    DatabaseReference mDatabase, mRef;
    FirebaseAuth mfirebaseAuth;
    FirebaseUser user;
    ImageButton btnClose;
    private boolean login = false;
    CardView[] btnPay = new CardView[4];
    boolean acceptAccount, acceptCard, acceptMpesa,acceptGHMobileMoney;
    int index, btnClick = 99;
    TextView txtPrice1,txtPrice2, txtPrice3, txtPrice4;
    String sec = "<p><strong><span style=\"font-size: 12.0pt; " +
            "line-height: 115%; font-family: 'Arial','sans-serif';\">" +
            "Your security is our priority:</span></strong><span style=\"font-size: 12.0pt; " +
            "line-height: 115%; font-family: 'Arial','sans-serif';\"> " +
            "Your card details are not stored on our platform. " +
            "All payments are processed securely by <em><span style=\"color: #002060;\">" +
            "Flutterwave</span></em></span></p>";
    long[] price = {0, 0, 0, 0};
    static Spinner spnCurrency;
    String email, fName, lName, userID;
    String currency, narration;
    String[] Symbol = {"NGN", "EUR", "GBP", "GHS", "KES", "ZAR", "UGX", "USD", "USD"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_reload);
        btnClose = findViewById(R.id.btnClose);
        txtPrice1 = findViewById(R.id.txtPrice1);
        txtPrice2 = findViewById(R.id.txtPrice2);
        txtPrice3 = findViewById(R.id.txtPrice3);
        txtPrice4 = findViewById(R.id.txtPrice4);
        //txtSecure.setText(Html.fromHtml(sec));


        btnPay[0] = findViewById(R.id.btnPay0); btnPay[0].setOnClickListener(this);
        btnPay[1] = findViewById(R.id.btnPay1); btnPay[1].setOnClickListener(this);
        btnPay[2] = findViewById(R.id.btnPay2); btnPay[2].setOnClickListener(this);
        btnPay[3] = findViewById(R.id.btnPay3); btnPay[3].setOnClickListener(this);

        mRef = FirebaseDatabase.getInstance().getReference("Price");
        mRef.keepSynced(true);
        spnCurrency = findViewById(R.id.spnCurrency);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mfirebaseAuth = FirebaseAuth.getInstance();
        user = mfirebaseAuth.getCurrentUser();
        if(user!=null){
            login = true;
            userID = user.getUid();
            mDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference().child("Users").child(userID);
            mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        fName= dataSnapshot.child("a1_firstname").getValue(String.class);
                        lName= dataSnapshot.child("a2_lastname").getValue(String.class);
                        email= dataSnapshot.child("a4_email").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        }

        //listens to selection of currency spinner
        spnCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                index = spnCurrency.getSelectedItemPosition();
                getPrice(index);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getPrice(0);
    }

    public void getPrice(final int index){
        mRef.child(Symbol[index]).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    price[i] = snapshot.getValue(long.class);
                    i++;
                }
                currency = Symbol[index];
                txtPrice1.setText(price[0] + " " + currency);
                txtPrice2.setText(price[1] + " " + currency);
                txtPrice3.setText(price[2] + " " + currency);
                txtPrice4.setText(price[3] + " " + currency);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void pay(double amount, String country){
        String publicKey = "FLWPUBK-6135390cafde2808e6aaf6b9869bf5b9-X";
        String encryptionkey = "9ed4313390654a2c61f4a2b3";
        String txRef = userID + UUID.randomUUID().toString();

        new RavePayManager(this).setAmount(amount)
                .setCountry(country)
                .setCurrency(currency)
                .setEmail(email)
                .setfName(fName)
                .setlName(lName)
                .setNarration(narration)
                .setPublicKey(publicKey)
                .setEncryptionKey(encryptionkey)
                .setTxRef(txRef)
                .acceptAccountPayments(acceptAccount)
                        .acceptCardPayments(
                                acceptCard)
                        .acceptMpesaPayments(acceptMpesa)
                        .acceptGHMobileMoneyPayments(acceptGHMobileMoney)
                        .onStagingEnv(false)
                        .withTheme(R.style.DefaultPayTheme)
                .initialize();

    }

    public String getCountry(){
        switch (index){
            case 1:
            case 2:
                acceptCard = true;
                return "NG";
            case 3:
                acceptCard = false;
                acceptGHMobileMoney = true;
                return "GH";
            case 4:
                acceptCard = false;
                acceptMpesa = true;
                return "KE";
            case 5:
                acceptCard = true;
                acceptAccount = true;
                return "ZA";
            default:
                acceptCard = true;
                return "NG";
        }
    }

    public void popUp2(){
        String sub = (btnClick==0||btnClick==1)? "VIP and Tipzone" : "Tipzone";

        //Display success dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        builder.setTitle("SUCCESSFUL")
                .setCancelable(false)
                .setMessage("Your subscribtion for "+ sub + " was successful. Enjoy the best of Secured Tips.")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
    }

    public void popUp3(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage("No Internet connection")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                setSubEndDate();
                popUp2();
            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SubscriptionReloadActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                builder.setMessage("Error during transaction! Please try again later.\n\n" + message )
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //do nothing
                            }
                        })
                        .show();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void setSubEndDate(){
        final Cache cache = new Cache();
        final Date today = new Date();
        String endDate;

        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        String currentTime = sdf.format(today.getTime());
        Date todaysDate = sdf.parse(currentTime, new ParsePosition(0));
        Calendar c = Calendar.getInstance();
        c.setTime(todaysDate);
        Map<String, Object> update = new HashMap<>();
        switch (btnClick){
            case 0:
                cache.setVipSub(true);
                c.add(Calendar.MONTH, 1); //add 1 month and store to vip_ending
                endDate = sdf.format(c.getTime());
                update.put("b0_k", 10);
                update.put("b2_vip", true);
                update.put("b3_vip_ending", endDate);
                mDatabase.updateChildren(update);
                break;
            case 1:
                cache.setVipSub(true);
                c.add(Calendar.WEEK_OF_YEAR, 2); //add 2 weeks and store to vip_ending
                endDate = sdf.format(c.getTime());
                update.put("b0_k", 10);
                update.put("b2_vip", true);
                update.put("b3_vip_ending", endDate);
                mDatabase.updateChildren(update);
                break;
            case 2:
                cache.setRoomSub(true);
                c.add(Calendar.MONTH, 3); //add 3 month and store to chat_ending
                endDate = sdf.format(c.getTime());
                update.put("b0_k", 10);
                update.put("b4_chat", true);
                update.put("b5_chat_ending", endDate);
                mDatabase.updateChildren(update);
                break;
            case 3:
                cache.setRoomSub(true);
                c.add(Calendar.MONTH, 1); //add 1 month and store to chat_ending
                endDate = sdf.format(c.getTime());
                update.put("b0_k", 10);
                update.put("b4_chat", true);
                update.put("b5_chat_ending", endDate);
                mDatabase.updateChildren(update);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        //Confirm that there's network
        if(!NewsFunction.isNetworkAvailable(getApplicationContext())) {
            popUp3();
            return;
        }
        if (user != null) {
            acceptAccount = false;
            acceptMpesa = false;
            acceptGHMobileMoney = false;
            switch (view.getId()) {
                case R.id.btnPay0:
                    if(price[0]<3){
                        AlertDialog.Builder builder = new AlertDialog.Builder(SubscriptionReloadActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                        builder.setMessage("Price could not load due to slow internet. Please try again later.")
                                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //do nothing
                                    }
                                })
                                .show();
                        return;
                    }
                    btnClick = 0;
                    narration = "1 month VIP";
                    pay(price[0], getCountry());
                    break;
                case R.id.btnPay1:
                    if(price[1]<3){
                        AlertDialog.Builder builder = new AlertDialog.Builder(SubscriptionReloadActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                        builder.setMessage("Price could not load due to slow internet. Please try again later.")
                                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //do nothing
                                    }
                                })
                                .show();
                        return;
                    }
                    btnClick = 1;
                    narration = "2 weeks VIP";
                    pay(price[1], getCountry());
                    break;
                case R.id.btnPay2:
                    if(price[2]<3){
                        AlertDialog.Builder builder = new AlertDialog.Builder(SubscriptionReloadActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                        builder.setMessage("Price could not load due to slow internet. Please try again later.")
                                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //do nothing
                                    }
                                })
                                .show();
                        return;
                }
                    btnClick = 2;
                    narration = "3 months TipZone";
                    pay(price[2], getCountry());
                    break;
                case R.id.btnPay3:
                    if(price[3]<3){
                        AlertDialog.Builder builder = new AlertDialog.Builder(SubscriptionReloadActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                        builder.setMessage("Price could not load due to slow internet. Please try again later.")
                                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //do nothing
                                    }
                                })
                                .show();
                        return;
                    }
                    btnClick = 3;
                    narration = "1 month TipZone";
                    pay(price[3], getCountry());
                    break;
            }
        }else
            {
                popUp();
            }
    }

    @Override
    protected void onResume(){
        super.onResume();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            if(login!=true){
                login=true;
                userID = user.getUid();
                mDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference().child("Users").child(userID);
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        fName= dataSnapshot.child("a1_firstname").getValue(String.class);
                        lName= dataSnapshot.child("a2_lastname").getValue(String.class);
                        email= dataSnapshot.child("a4_email").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }

    }

    public void popUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SubscriptionReloadActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage("You must login first to make payment")
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SubscriptionReloadActivity.this.startActivity(new Intent(SubscriptionReloadActivity.this, LoginActivity.class));
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
}

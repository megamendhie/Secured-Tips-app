package secured.tips;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SubscribeActivity extends Activity {

    Button btnPay;
    static Spinner spnCurrency, spnPeriod;
    DatabaseReference mRef;
    int currency= 0, period = 0;
    String display;
    String paymentUrl;
    String paymentLinks[] = new String[50];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        paymentUrl = "https://rave.flutterwave.com/pay/securedtipsusd1month";
        spnCurrency = findViewById(R.id.spnCurrency);
        spnPeriod = findViewById(R.id.spnPeriod);
        btnPay = findViewById(R.id.btnPay);
        loadLinks();
        paymentUrl = paymentLinks[2];

        spnCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currency = spnCurrency.getSelectedItemPosition();
                setPrice(currency, period);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spnPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                period = spnPeriod.getSelectedItemPosition();
                setPrice(currency, period);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("idad", paymentUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(paymentUrl));
                finish();
                startActivity(intent);
            }
        });
    }

    public void setPrice(int currency, int period){
        if(currency==0 || currency==7){
            switch (period){
                case 2:
                    display = "12 USD";
                    paymentUrl =  paymentLinks[0]; break;
                case 1:
                    display = "24 USD";
                    paymentUrl =  paymentLinks[1]; break;
                case 0:
                    display = "35 USD";
                    paymentUrl =  paymentLinks[2]; break;
            }
        }
        else if(currency==1){
            switch (period){
                case 2:
                    display = "10 GBP";
                    paymentUrl =  paymentLinks[3]; break;
                case 1:
                    display = "20 GBP";
                    paymentUrl =  paymentLinks[4]; break;
                case 0:
                    display = "30 GBP";
                    paymentUrl =  paymentLinks[5]; break;
            }
        }
        else if(currency==2){
            switch (period){
                case 2:
                    display = "10 EUR";
                    paymentUrl =  paymentLinks[6]; break;
                case 1:
                    display = "20 EUR";
                    paymentUrl =  paymentLinks[7]; break;
                case 0:
                    display = "30 EUR";
                    paymentUrl =  paymentLinks[8]; break;
            }
        }
        else if(currency==3){
            switch (period){
                case 2:
                    display = "50 GHS";
                    paymentUrl =  paymentLinks[9]; break;
                case 1:
                    display = "100 GHS";
                    paymentUrl =  paymentLinks[10]; break;
                case 0:
                    display = "150 GHS";
                    paymentUrl =  paymentLinks[11]; break;
            }
        }
        else if(currency==4){
            switch (period){
                case 2:
                    display = "1100 KSH";
                    paymentUrl =  paymentLinks[12]; break;
                case 1:
                    display = "2100 KSH";
                    paymentUrl =  paymentLinks[13]; break;
                case 0:
                    display = "3100 KSH";
                    paymentUrl =  paymentLinks[14]; break;
            }
        }
        else if(currency==5){
            switch (period){
                case 2:
                    display = "4000 NGN";
                    paymentUrl =  paymentLinks[15]; break;
                case 1:
                    display = "8000 NGN";
                    paymentUrl =  paymentLinks[16]; break;
                case 0:
                    display = "10000 NGN";
                    paymentUrl =  paymentLinks[17]; break;
            }
        }
        else if(currency==6){
            switch (period){
                case 2:
                    display = "130 ZAR";
                    paymentUrl =  paymentLinks[18]; break;
                case 1:
                    display = "260 ZAR";
                    paymentUrl =  paymentLinks[19]; break;
                case 0:
                    display = "400 ZAR";
                    paymentUrl =  paymentLinks[20]; break;
            }
        }
        btnPay.setText("PAY: "+display);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    public void loadLinks(){
        mRef= FirebaseDatabase.getInstance().getReference("ZLink");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    paymentLinks[i]= snapshot.getValue(String.class);
                    i++;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

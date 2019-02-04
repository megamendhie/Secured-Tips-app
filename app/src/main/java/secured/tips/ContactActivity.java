package secured.tips;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {
    ActionBar actionBar;
    CardView crdW, crdT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        crdW = findViewById(R.id.crdWhatsapp); crdW.setOnClickListener(this);
        crdT = findViewById(R.id.crdTelegram); crdT.setOnClickListener(this);


        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.crdWhatsapp:
                startWhatsApp();
                break;
            case R.id.crdTelegram:
                startTelegram();
                break;
        }
    }

    public void startTelegram(){
        String mssg = "Hello.\nI need some assistance.";
        String toNumber = "2348132014755";
        PackageManager pkMgt = getPackageManager();
        Uri uri = Uri.parse("http://telegram.me/securedtips");
        try {
            Intent Telegram = new Intent(Intent.ACTION_VIEW);
            Telegram.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            Telegram.setData(uri);
            PackageInfo info = pkMgt.getPackageInfo("org.telegram.messenger", PackageManager.GET_META_DATA);
            startActivity(Telegram);
        }
        catch (PackageManager.NameNotFoundException e){
            Toast.makeText(this, "No Telegram installed", Toast.LENGTH_LONG).show();
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
}

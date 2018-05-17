package secured.tips;

import android.content.ActivityNotFoundException;
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

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class FreePageActivity extends AppCompatActivity implements View.OnClickListener {

    CardView crdFree, crdOver1, crdOver2;
    ActionBar actionBar;
    static InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_page);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4597711656812814~3843067047");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4597711656812814/1799989807");

        crdFree = findViewById(R.id.crdFree); crdFree.setOnClickListener(this);
        crdOver1 = findViewById(R.id.crdOverOne); crdOver1.setOnClickListener(this);
        crdOver2 = findViewById(R.id.crdOverTwo); crdOver2.setOnClickListener(this);
    }

    public void onClick(View v){
        if (v.getId()== R.id.crdFree) {
            startActivity(new Intent(this, FreeActivity.class));}

        if (v.getId()== R.id.crdOverOne){
            startActivity(new Intent(this, OverOneActivity.class));}

        if (v.getId()== R.id.crdOverTwo){
            startActivity(new Intent(this, OverTwoActivity.class));}
    }

    public void rateApp(){
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.

        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
        }
    }

    public void startChat(){
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

    public void shareApp(){
        Intent share = new Intent(Intent.ACTION_SEND);
        String url = "This app is so good. You should try it\n\nhttp://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName().toString();
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, url);
        share.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(Intent.createChooser(share, "Share via:"));
    }


    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }

    /*
    public void loadAds(){
        Log.i("Dame", "loadAds() called");
        editor = prefs.edit();
        int adCount = prefs.getInt("ADCOUNT", 1);
        if(adCount%3==0) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.i("Dame", "Interstatial ad not loadedd yet");
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        }
        adCount+=1;
        editor.putInt("ADCOUNT", adCount).apply();
    }
    */

}

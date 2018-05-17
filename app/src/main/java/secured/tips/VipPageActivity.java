package secured.tips;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

public class VipPageActivity extends AppCompatActivity implements View.OnClickListener {
    CardView crdVip, crdDraws;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_page);
        crdVip = findViewById(R.id.crdVIP); crdVip.setOnClickListener(this);
        crdDraws = findViewById(R.id.crdDraw); crdDraws.setOnClickListener(this);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.crdVIP:
                startActivity(new Intent(this, PremiumActivity.class));
                break;
            case R.id.crdDraw:
                startActivity(new Intent(this, DrawActivity.class));
                break;
        }

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }
}

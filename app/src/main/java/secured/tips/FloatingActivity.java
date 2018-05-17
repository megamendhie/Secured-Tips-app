package secured.tips;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class FloatingActivity extends Activity {
    WebView wbReviw;
    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating);
        btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        String address = intent.getStringExtra("address");

        wbReviw =findViewById(R.id.wbReview);
        WebSettings wbSet = wbReviw.getSettings();
        wbSet.setBuiltInZoomControls(true);
        wbSet.setLoadWithOverviewMode(true);
        wbSet.setUseWideViewPort(true);
        wbSet.setJavaScriptEnabled(true);
        wbReviw.setWebViewClient(new Callback());

        wbReviw.loadUrl(address);
    }

    private class Callback extends WebViewClient {
       @Override
        public void onPageFinished(WebView webView, final String url){
            super.onPageFinished(webView, url);
        }

        @Override
        public void onPageStarted(WebView webView, final String url, Bitmap favicon){
            super.onPageStarted(webView,url,favicon);
        }
    }


}
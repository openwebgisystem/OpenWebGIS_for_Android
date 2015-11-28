package openwebgis_app.openwebgis;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by root on 31.10.15.
 */
public class PopupActivity extends Activity {
    private WebView pwebView;public String shtml;public String url2;//AlertDialog.Builder builder = new AlertDialog.Builder(this);
    @Override
    public void onBackPressed() {
        if (pwebView.canGoBack()) {
            pwebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        Intent intent = getIntent();
       url2 = intent.getStringExtra("URL");
       //url2 = intent.getStringExtra("URL2");
       // builder.setMessage(url2);
      //builder.show();
        pwebView = (WebView) findViewById(R.id.activity_main_webview2);
        WebSettings webSettings = pwebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
       // pwebView.addJavascriptInterface(new DJavaScriptInterface2(this), "AndroidFunction");
        pwebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                      if(url2!=url)
                      {view.loadUrl(url);}
                    return true;}


            public void onPageFinished(WebView view, String url) {}


        });
        pwebView.loadUrl(url2);

        //pwebView.loadUrl("javascript:alert(window.opener)");


        pwebView.setWebChromeClient(new WebChromeClient());

        }


}

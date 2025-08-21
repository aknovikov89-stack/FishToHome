package com.example.fishtohome;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.CookieManager;



public class MainActivity extends AppCompatActivity {

    private WebView myWebView;



    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);


        /* находим WebView элемент по его id */
        myWebView = findViewById(R.id.WebView);
        myWebView.setWebViewClient(new WebViewClient());
        WebSettings settings = myWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        myWebView.setInitialScale(1);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl("https://google.com/");
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true); // принимаем куки
        cookieManager.setAcceptThirdPartyCookies(myWebView, true);


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && this.myWebView.canGoBack()) {
            this.myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

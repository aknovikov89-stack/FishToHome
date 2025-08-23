package com.example.fishtohome;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;
    private List<String> previous = new ArrayList<String>();
    private String mLastUrl;





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
        myWebView.setWebChromeClient(new WebChromeClient());
        WebSettings settings = myWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        myWebView.setInitialScale(1);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        WebBackForwardList history = myWebView.copyBackForwardList();
        for (int i = 0; i < history.getSize(); i++) {
            WebHistoryItem item = history.getItemAtIndex(i);
            // Получить название страницы
            // Другие манипуляции...
        }
        myWebView.loadUrl("https://google.com/");
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true); // принимаем куки
        cookieManager.setAcceptThirdPartyCookies(myWebView, true);

    }

    @Override
    protected void onStop() {
        super.onStop();
        saveWebViewState();
    }

    protected void saveWebViewState() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        WebBackForwardList list = myWebView.copyBackForwardList();
        for (int i = 0; i < list.getSize(); i++) {
            WebHistoryItem item = list.getItemAtIndex(i);
            editor.putString("URL_" + i, item.getUrl()); // Храните URL каждой страницы
        }
        editor.apply();


    }

    @Override
    protected void onStart() {
        super.onStart();
        restoreWebViewState();
    }

    private void restoreWebViewState() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int size = prefs.getInt("HISTORY_SIZE", 0);
        List<String> urls = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            String url = prefs.getString("URL_" + i, "");
            if (!url.isEmpty()) {
                urls.add(url);
            }
        }
        if (!urls.isEmpty()) {
            myWebView.loadUrl(urls.get(0)); // Загрузка первой страницы
        }
    }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event){
            if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
                myWebView.goBack(); // Двигаемся назад по истории
                return true; // Сообщаем, что событие обработано
            }
            return super.onKeyDown(keyCode, event); // Пусть система обработает остальные события
        }

    }

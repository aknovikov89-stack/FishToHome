package com.example.fishtohome;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HistoryManager {

    private static final String PREFS_NAME = "WebViewHistory";
    private static final String HISTORY_KEY = "history_list";

    private Context context;
    private List<String> historyList;

    public HistoryManager(Context context) {
        this.context = context;
        restoreHistoryFromStorage();
    }

    // Метод восстановления истории из Storage
    private void restoreHistoryFromStorage() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        historyList = new ArrayList<>();
        historyList.addAll(prefs.getStringSet(HISTORY_KEY, new HashSet<String>()));
    }

    // Метод сохранения истории в Storage
    private void storeHistoryInStorage() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(HISTORY_KEY, new HashSet<>(historyList));
        editor.apply();
    }

    // Основной метод добавления URL в историю
    public void addToHistory(String url) {
        if (!historyList.contains(url)) {
            historyList.add(url);
            storeHistoryInStorage(); // Сразу же сохраняем обновление
        }
    }

    // Метод для вывода истории
    public List<String> getHistory() {
        return historyList;
    }

    // Пример интеграции с WebView
    public void integrateWithWebView(final WebView myWebView) {
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                addToHistory(url); // Добавляем URL в историю при загрузке страницы
            }
        });
    }
}

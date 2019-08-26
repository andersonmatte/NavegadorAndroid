package br.com.andersonmatte.navegadorandroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private EditText editText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.webView = (WebView) findViewById(R.id.webView1);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.progressBar.setVisibility(View.GONE);
        this.editText = (EditText) findViewById(R.id.editText);
        this.editText.setText(R.string.url_home);

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.loadPagina(this.editText.getText().toString());
    }

    // Clique do botão ir URL.
    public void click(View view) {
        this.loadPagina(this.editText.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_Home) {
            this.editText.setText(R.string.url_home);
            this.loadPagina(this.editText.getText().toString());
        }
        if (id == R.id.action_refresh) {
            this.loadPagina(this.editText.getText().toString());
        }
        if (id == R.id.action_link) {
            quickLinks();
        }
        return super.onOptionsItemSelected(item);
    }

    public void quickLinks() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    // Carrega a URL definida na entrada do APP ou digitada pelo usuário.
    public void loadPagina(String url){
        WebSettings webSetting = webView.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        if (url.startsWith("http://www.") || url.startsWith("https://www.")){
            webView.loadUrl(url);
        } else if (url.startsWith("www.")){
            webView.loadUrl("http://" + url);
        } else {
            webView.loadUrl("http://" + url);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
          progressBar.setVisibility(View.GONE);
        }
    }

}

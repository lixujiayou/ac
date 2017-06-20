// 

// 

package com.inspur.resources.view.help;

import com.inspur.easyresources.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HelpActivity extends Activity
{
    private String url;
    private WebView webview;
    
    public HelpActivity() {
        this.url = "";
    }
    
    private void init() {
      //  this.webview = (WebView)this.findViewById(2131297586);
        final WebSettings settings = this.webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        this.webview.loadUrl(this.url);
        this.webview.setWebViewClient(new webViewClient());
    }
    
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.tongji);
        this.url = this.getIntent().getStringExtra("url");
        this.getWindow().getAttributes().width = (int)(this.getWindow().getWindowManager().getDefaultDisplay().getWidth() * 0.95);
        this.init();
    }
    
    public boolean onKeyDown(final int n, final KeyEvent keyEvent) {
        if (n == 4 && this.webview.canGoBack()) {
            this.webview.goBack();
            return true;
        }
        this.finish();
        return false;
    }
    
    private class webViewClient extends WebViewClient
    {
        public boolean shouldOverrideUrlLoading(final WebView webView, final String s) {
            webView.loadUrl(s);
            return true;
        }
    }
}

package com.rao.hybrid1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btn1;
    private WebView wv;

    private View.OnClickListener btnonClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(MainActivity.this, "test android function", Toast.LENGTH_SHORT).show();
            wv.loadUrl("javascript:getData('我是一个Android传过来的值')");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(btnonClickHandler);
        wv = (WebView)findViewById(R.id.wv);
        wvSetting();
//        wv.loadUrl("https://m.jd.com");
        wv.loadUrl("file:///android_asset/index.html");
        // 添加JS调用Android(Java)的方法接口
        wv.addJavascriptInterface(new MJavascriptInterface(getApplicationContext()), "ToastFunc");
    }

    private void wvSetting() {
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDefaultTextEncodingName("utf-8");
    }

    private void wvClientSetting() {

    }

    private class MJavascriptInterface {
        private Context context;
        public MJavascriptInterface(Context applicationContext) {
            this.context = applicationContext;
        }

        @JavascriptInterface
        public void showToast() { // 可以由前端调用, window.ToastFunc.showToast()
            Toast.makeText(MainActivity.this, "js调起了Android的Toast功能", Toast.LENGTH_SHORT).show();
        }
    }
}
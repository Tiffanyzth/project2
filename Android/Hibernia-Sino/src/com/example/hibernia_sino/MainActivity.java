package com.example.hibernia_sino;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar= (ProgressBar)findViewById(R.id.progressbar);//������
        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("http://www.baidu.com");//����url

        webView.addJavascriptInterface(this,"android");
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);

        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//����ʹ��js

        /**
         * LOAD_CACHE_ONLY: ��ʹ�����磬ֻ��ȡ���ػ�������
         * LOAD_DEFAULT: ��Ĭ�ϣ�����cache-control�����Ƿ��������ȡ���ݡ�
         * LOAD_NO_CACHE: ��ʹ�û��棬ֻ�������ȡ����.
         * LOAD_CACHE_ELSE_NETWORK��ֻҪ�����У������Ƿ���ڣ�����no-cache����ʹ�û����е����ݡ�
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //֧����Ļ����
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

    }

    //WebViewClient��Ҫ����WebView�������֪ͨ�������¼�
    private WebViewClient webViewClient=new WebViewClient(){
        public void onPageFinished(WebView view, String url) {//ҳ��������
            progressBar.setVisibility(View.GONE);
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {//ҳ�濪ʼ����
            progressBar.setVisibility(View.VISIBLE);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("ansen","����url:"+url);
            if(url.equals("http://www.google.com/")){
                Toast.makeText(MainActivity.this,"���ڲ��ܷ���google,���ظ�url",Toast.LENGTH_LONG).show();
                return true;//��ʾ���Ѿ��������
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

    };

    //WebChromeClient��Ҫ����WebView����Javascript�ĶԻ�����վͼ�ꡢ��վtitle�����ؽ��ȵ�
    private WebChromeClient webChromeClient=new WebChromeClient(){
        //��֧��js��alert��������Ҫ�Լ�����Ȼ��ͨ��dialog����
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("ȷ��",null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();

            //ע��:
            //����Ҫ��һ�����:result.confirm()��ʾ:
            //������Ϊȷ��״̬ͬʱ����WebCore�߳�
            //�����ܼ��������ť
            result.confirm();
            return true;
        }

        //��ȡ��ҳ����
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.i("ansen","��ҳ����:"+title);
        }

        //���ؽ��Ȼص�
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("ansen","�Ƿ�����һ��ҳ��:"+webView.canGoBack());
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){//������ذ�ť��ʱ���ж���û����һҳ
            webView.goBack(); // goBack()��ʾ����webView����һҳ��
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    /**
     * JS����android�ķ���
     * @param str
     * @return
     */
    @JavascriptInterface //��Ȼ�ز�����
    public void  getClient(String str){
        Log.i("ansen","html���ÿͻ���:"+str);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //�ͷ���Դ
        webView.destroy();
        webView=null;
    }
}

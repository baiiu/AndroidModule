package com.baiiu.myapplication.module.JavaJsCommunication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baiiu.myapplication.R;
import com.baiiu.myapplication.util.LogUtil;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * author: baiiu
 * date: on 16/7/5 15:45
 * description:
 */
public class JavaJsFragment extends Fragment implements View.OnClickListener {

    FrameLayout frameLayout;
    private WebView webView;

    @Nullable
    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                       @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_java_js, container, false);
        frameLayout = (FrameLayout) view.findViewById(R.id.frameLayoutContainer);

        view.findViewById(R.id.getJSVariables)
                .setOnClickListener(this);

        //webView = new WebView(this);//alert需要.
        /*
        your JavaScript code can call a method in your Android code to display a Dialog,
        instead of using JavaScript's alert() function.
        可以让JS调用Java方法显示对话框,避免使用alert()方法.
         */
        webView = new WebView(getContext().getApplicationContext());
        frameLayout.addView(webView);

        initWebView();
        return view;
    }

    @SuppressLint("JavascriptInterface") private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(false);

        //1. 允许JS调用
        settings.setJavaScriptEnabled(true);

        //2. 设置调用接口
        webView.addJavascriptInterface(new JsObject(), "injectedObject");

        /*
            js中调用alert()方法必须要设置这个

            可以使用alert(xxx)或者prompt(xxx)来传递信息，使用json封装协议
         */
        webView.setWebChromeClient(new WebChromeClient() {
            @Override public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                showToast(message);
                result.confirm();//必须要调这一句
                return true;

                //return super.onJsAlert(view, url, message, result);
            }

            @Override public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
                    JsPromptResult result) {

                showToast(message);
                result.confirm();
                return true;

                //return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                /*
                Java调用JS方法
                必须在此方法执行,等待js加载完成
                 */
                webView.loadUrl("javascript:contentShow('哈哈哈哈哈哈,第一次进来')");
            }
        });

        webView.loadUrl("file:///android_asset/page.html");
    }

    @Override public void onClick(View view) {
        if (view.getId() == R.id.getJSVariables) {
            //Java调用JS方法
            webView.loadUrl("javascript:(function getVariables(){"
                                    + "  return window.injectedObject.onJavaGetVariables(jsVariables);"
                                    + "})()");
        }

    }

    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT)
                .show();
    }

    public class JsObject {
        @JavascriptInterface public void error(final String msg) {
            getActivity().runOnUiThread(new Runnable() {
                @Override public void run() {
                    showToast("错误： " + msg);
                }
            });
        }

        @JavascriptInterface public void success(final String msg) {
            getActivity().runOnUiThread(new Runnable() {
                @Override public void run() {
                    showToast("成功： " + msg);
                }
            });
        }

        @JavascriptInterface public void toast(final String msg) {
            getActivity().runOnUiThread(new Runnable() {
                @Override public void run() {
                    showToast(msg);
                }
            });
        }

        @JavascriptInterface public void onSubmit(final String method, final String url) {
            LogUtil.d(method + ", " + url);
            accessNetWork(method, url);
        }

        @JavascriptInterface public void onJavaGetVariables(String jsVariables) {
            toast(jsVariables);
        }
    }

    private void accessNetWork(final String method, final String remoteurl) {
        //使用简单的线程管理器
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    URL url = new URL(remoteurl);
                    URLConnection conn = url.openConnection();

                    if ("GET".equals(method)) {

                    } else if ("POST".equals(method)) {
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                    }

                    InputStream inputStream = conn.getInputStream();
                    byte[] bytes = new byte[1024];
                    int len;

                    StringBuffer sb = new StringBuffer();
                    while ((len = inputStream.read(bytes)) != -1) {
                        sb.append(new String(bytes, 0, len));
                    }

                    final String content = sb.toString();
                    LogUtil.d(content);

                  /*
                   * Java调用JS,需要在webView的Thread里
                   */
                    webView.post(new Runnable() {
                        @Override public void run() {
                            String s = "javascript:contentShow('" + content + "')";
                            webView.loadUrl(s);
                        }
                    });

                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override public void onDestroy() {
        super.onDestroy();

        if (webView != null) {
            webView.destroy();
            frameLayout.removeView(webView);
            webView = null;
        }
    }
}

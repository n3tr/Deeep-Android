package io.jkn.deeep.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.jkn.deeep.R;


public class WebAuthActivity extends Activity {

    static String LOG_TAG = "WEB_AUTH";
    static String CLIENT_ID = "";
    static String BASE_URL = "https://dribbble.com/oauth/authorize";
    static String CALLBACK_URL = "";

    public static final String AUTH_RESULT_CODE = "co.deeep.WEB_AUTH_RESULT_CODE";

    @InjectView(R.id.webview)
    WebView webView;

    private String state;


    public void handleAuthCallback(String cbUrl) {
        webView.stopLoading();
        Log.d(LOG_TAG,cbUrl);
        Uri uri = Uri.parse(cbUrl);
        String code = uri.getQueryParameter("code");
        String state = uri.getQueryParameter("state");

        if (!state.equals(this.state) || code.equals("")){
            setResult(RESULT_CANCELED);
        }else{
            Intent i = new Intent();
            i.putExtra(AUTH_RESULT_CODE,code);
            setResult(RESULT_OK,i);
        }

        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_auth);
        ButterKnife.inject(this);

        state = UUID.randomUUID().toString();

        String authUrl =  BASE_URL
                + "?"
                + "client_id=" + CLIENT_ID
                + "&redirect_uri=" + CALLBACK_URL
                + "&state=" + state;
        webView.loadUrl(authUrl);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d(LOG_TAG,"onPageStarted: " + url);
                if (url.toLowerCase().contains(CALLBACK_URL) && !url.toLowerCase().contains(BASE_URL)) {
                    handleAuthCallback(url);
                    return;
                }
                super.onPageStarted(view, url, favicon);
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_auth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package io.jkn.deeep.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import butterknife.ButterKnife;
import butterknife.InjectView;
import io.jkn.deeep.R;
import io.jkn.deeep.models.UserDO;
import io.jkn.deeep.models.response.ErrorResponse;
import io.jkn.deeep.models.response.TokenResponse;
import io.jkn.deeep.manager.ApplicationManager;
import io.jkn.deeep.services.DeeepClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends Activity {

    private static final String LOG_TAG = "LOGIN_ACT";

    private ProgressDialog pdLogin;

    @InjectView(R.id.btnConnect)
    Button btnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, WebAuthActivity.class);
                startActivityForResult(i,0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case RESULT_OK:
                Log.d(LOG_TAG,"Code: "+data.getStringExtra(WebAuthActivity.AUTH_RESULT_CODE));
                handleAccessCode(data.getStringExtra(WebAuthActivity.AUTH_RESULT_CODE));
                break;
            case RESULT_CANCELED:
                Log.d(LOG_TAG,"Result Cancel");
                break;
            default:
                return;
        }
    }

    public void handleAccessCode(String code) {

        showLoadingDialogWithText(getString(R.string.connecting_to_dribbble));

        DeeepClient.getInstance(this).doAuthWithCode(code,new Callback<TokenResponse>() {
            @Override
            public void success(TokenResponse tokenResponse, Response response) {
                Log.d(LOG_TAG,tokenResponse.getAccessToken());
                ApplicationManager.getInstance(LoginActivity.this).setUserAccessToken(tokenResponse.getAccessToken());
                fetchUserProfile();
            }

            @Override
            public void failure(RetrofitError error) {

                ErrorResponse errRes = (ErrorResponse) error.getBodyAs(ErrorResponse.class);


                dismissLoadingDialog();
                if (errRes == null){
                    showAlertWithText(errRes.getErrorDesciption());
                }
            }
        });
    }

    private void fetchUserProfile() {
        showLoadingDialogWithText(getString(R.string.loading_user_profile));
        DeeepClient.getInstance(this).fetchCurrentUser(new DeeepClient.OnClientResponseCallback<UserDO, ErrorResponse>() {
            @Override
            public void onResponse(UserDO user, Response response) {
                Log.d(LOG_TAG, user.getName());
                ApplicationManager.getInstance(LoginActivity.this).setCurrentUser(user);
                dismissLoadingDialog();

                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(ErrorResponse error, RetrofitError rawError) {
                dismissLoadingDialog();
                showAlertWithText(error.getErrorDesciption().toString());
            }
        });
    }

    private void showLoadingDialogWithText(String text) {
        if (pdLogin == null){
            pdLogin = new ProgressDialog(this,ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pdLogin.setIndeterminate(true);
        }
        pdLogin.setMessage(text);
        pdLogin.show();
    }

    private void dismissLoadingDialog() {
        if (pdLogin != null) {
            pdLogin.dismiss();
        }
        pdLogin = null;
    }

    private void showAlertWithText(String text){
        AlertDialog alert = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();
        alert.setTitle("Sorry, Something went wrong");
        alert.setMessage(text);

        alert.setButton(DialogInterface.BUTTON_POSITIVE,"GOT IT",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

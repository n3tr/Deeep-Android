package io.jkn.deeep.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;

import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.jkn.deeep.R;
import io.jkn.deeep.fragments.DetailListFragment;

import io.jkn.deeep.models.ShotDO;

public class ShotDetailActivity extends ActionBarActivity {

    public static final String EXTRA_SHOT = "io.jkn.deeep.EXTRA_SHOT_DETAIL";
    private ShotDO shot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_detail);


        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_action_bar);
        setSupportActionBar(toolbar);
        setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
//        if (currentApiVersion >= Build.VERSION_CODES.LOLLIPOP){
//            getWindow().setStatusBarColor(0x33000000);
//
//            View decorView = getWindow().getDecorView();
//
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            float marginTop =  TypedValue.applyDimension(
//                    TypedValue.COMPLEX_UNIT_DIP, 25, this.getResources()
//                            .getDisplayMetrics());
//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            lp.setMargins(0, (int) marginTop,0,0);
//            toolbar.setLayoutParams(lp);
//        }



        shot = getIntent().getParcelableExtra(EXTRA_SHOT);

        if (savedInstanceState == null) {
            DetailListFragment shotDetailFragment = DetailListFragment.newInstance(shot);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer,shotDetailFragment,"SHOT_DETAIL_FRAGMENT")
                    .commit();
        }

    }


    @Override
    public void onBackPressed() {
        finishActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shot_detail, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_open_in_web) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(shot.getHtmlUrl()));
            startActivity(browserIntent);
            return true;
        }

        if (id == R.id.menu_action_share) {

            String myAppId = "342144839324047";

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, this.shot.getTitle() + " - " + this.shot.getHtmlUrl());
            shareIntent.putExtra("com.facebook.platform.extra.APPLICATION_ID", myAppId);
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
        }

        if (id == android.R.id.home) {
            finishActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void finishActivity() {
        finish();
        overridePendingTransition(R.anim.scale_to_regular,R.anim.left_slide_out);
    }
}

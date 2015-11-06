package io.jkn.deeep.views.detail;

import android.content.Context;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import io.jkn.deeep.R;
import io.jkn.deeep.models.ShotDO;
import io.jkn.deeep.models.response.ErrorResponse;
import io.jkn.deeep.services.DeeepClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailReboundView extends FrameLayout {



    private ImageView imageViewPlaceholder;
    private LinearLayout layoutBody;
    private TextView tvReboundTitle;
    private TextView tvReboundUser;
    private ImageView imageViewRebound;
    private boolean isLoading;

    private ShotDO shot;
    private ShotDO reboundSourceShot;

   private ReboundViewOnClickListener reboundViewOnClickListener;

    public DetailReboundView(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public DetailReboundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    public DetailReboundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    private void initInflate() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.detail_rebound_view, this);
    }

    private void initInstances() {
        imageViewPlaceholder = (ImageView)findViewById(R.id.imageViewPlaceholder);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.blink_image_anim);
        imageViewPlaceholder.startAnimation(myFadeInAnimation);

        layoutBody = (LinearLayout)findViewById(R.id.layoutReboundBody);
        tvReboundTitle = (TextView)findViewById(R.id.tvReboundTitle);
        tvReboundUser = (TextView)findViewById(R.id.tvReboundUser);
        imageViewRebound = (ImageView) findViewById(R.id.imageViewRebound);


        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoading)
                    return;

                if (getReboundSourceShot() != null)
                reboundViewOnClickListener.onClickReboundView(getReboundSourceShot());
            }
        });

        isLoading = true;
    }

    private void initWithAttrs(AttributeSet attrs) {

    }

    public void setShot(ShotDO shot){
        final int reboundSourceId = shot.getReboundSourceId();
        if (reboundSourceId == 0) return;

        this.shot = shot;


        fetchReboundShot(reboundSourceId);



    }

    private void fetchReboundShot(int reboundSourceId) {
        Log.d("ReboundView","Start fetch rebound shot");
        DeeepClient.getInstance(getContext()).getShot(reboundSourceId,new DeeepClient.OnClientResponseCallback<ShotDO, ErrorResponse>() {
            @Override
            public void onResponse(ShotDO shot, Response response) {
                isLoading = false;

                getShot().setReboundSourceShot(shot);
                reboundSourceShot = shot;
                handleReboundShot(shot);
            }

            @Override
            public void onFailure(ErrorResponse error, RetrofitError rawError) {
                isLoading = false;
                // TODO: show retry load view
            }
        });
    }

    private void handleReboundShot(ShotDO shot) {
        tvReboundTitle.setText(shot.getTitle());
        tvReboundUser.setText(shot.getUser().getName());

        AlphaAnimation anim = new AlphaAnimation(0, 1.0f);
        anim.setDuration (300);
        layoutBody.startAnimation (anim);
        anim.setFillBefore(true);
        anim.setFillAfter(true);

        imageViewPlaceholder.setVisibility(INVISIBLE);

        Picasso.with(getContext()).load(shot.getShotImage().getTeaserUrl()).into(imageViewRebound, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
    }

    public ShotDO getReboundSourceShot() {
        return reboundSourceShot;
    }

    public ShotDO getShot() {
        return shot;
    }

    public void setReboundViewOnClickListener(ReboundViewOnClickListener reboundViewOnClickListener) {
        this.reboundViewOnClickListener = reboundViewOnClickListener;
    }

    public interface ReboundViewOnClickListener {
        public void onClickReboundView (ShotDO shot);
    }



}

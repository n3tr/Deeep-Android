package io.jkn.deeep.views.detail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import io.jkn.deeep.R;
import io.jkn.deeep.models.ShotImageDO;

public class DetailImageView extends RelativeLayout {


    private ImageView imageView;
    private ProgressBar progressBar;

    public DetailImageView(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public DetailImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    public DetailImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    private void initInflate() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.detail_image_view, this);
    }

    private void initInstances() {
        imageView = (ImageView) findViewById(R.id.imageView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

    }

    private void initWithAttrs(AttributeSet attrs) {

    }

    public void setShotImage(ShotImageDO shotImage) {
        String imageURL = shotImage.getHidpiUrl() != null ? shotImage.getHidpiUrl() : shotImage.getNormalUrl();
        Picasso.with(getContext())
                .load(imageURL)
                .placeholder(R.drawable.detail_image_placeholder)
                .into(imageView,new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(INVISIBLE);
                    }
                });
    }
}

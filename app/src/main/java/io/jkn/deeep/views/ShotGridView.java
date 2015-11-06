package io.jkn.deeep.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import io.jkn.deeep.R;
import io.jkn.deeep.models.ShotDO;

/**
 * TODO: document your custom view class.
 */
public class ShotGridView extends RelativeLayout {


    private ImageView imageView;

    public ShotGridView(Context context) {
        super(context);
        init(null, 0);
    }

    public ShotGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ShotGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init(AttributeSet attrs, int defStyle) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.shot_grid_view,this);
        imageView = (ImageView)findViewById(R.id.imageView);
    }

    public void setShot(ShotDO shot) {
        imageView.setImageResource(0);
        Picasso.with(getContext())
                .load(shot.getShotImage().getNormalUrl())
                .placeholder(R.drawable.grid_image_placeholder)
                .into(imageView);
    }

}

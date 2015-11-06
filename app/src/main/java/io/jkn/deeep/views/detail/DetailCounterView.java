package io.jkn.deeep.views.detail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.jkn.deeep.R;
import io.jkn.deeep.models.ShotDO;
import io.jkn.deeep.utils.CircleTransform;

public class DetailCounterView extends FrameLayout {


    private TextView tvLikeCount;
    private TextView tvBucketCount;
    private TextView tvViewCount;

    public DetailCounterView(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public DetailCounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    public DetailCounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    private void initInflate() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.detail_counter_view, this);
    }

    private void initInstances() {
        tvLikeCount = (TextView)findViewById(R.id.tvLikeCount);
        tvViewCount = (TextView)findViewById(R.id.tvViewCount);
        tvBucketCount = (TextView)findViewById(R.id.tvBucketCount);

    }

    private void initWithAttrs(AttributeSet attrs) {

    }

    public void setShot(ShotDO shot){
        tvLikeCount.setText(String.valueOf(shot.getLikesCount()));
        tvViewCount.setText(String.valueOf(shot.getViewsCount()));
        tvBucketCount.setText(String.valueOf(shot.getBucketsCount()));
                
    }


}

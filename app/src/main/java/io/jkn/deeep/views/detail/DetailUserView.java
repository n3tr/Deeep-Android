package io.jkn.deeep.views.detail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import io.jkn.deeep.R;
import io.jkn.deeep.models.ShotDO;
import io.jkn.deeep.models.ShotImageDO;
import io.jkn.deeep.utils.CircleTransform;

public class DetailUserView extends FrameLayout {


    private ImageView imageView;
    private TextView tvUserName;
    private TextView tvShotName;

    public DetailUserView(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public DetailUserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    public DetailUserView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    private void initInflate() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.detail_member_view, this);
    }

    private void initInstances() {
        imageView = (ImageView) findViewById(R.id.imageView);
        tvUserName = (TextView)findViewById(R.id.tvUserName);
        tvShotName = (TextView)findViewById(R.id.tvShotName);

    }

    private void initWithAttrs(AttributeSet attrs) {

    }

    public void setShot(ShotDO shot){
        tvUserName.setText(shot.getUser().getName());
        tvShotName.setText(shot.getTitle());
        Picasso.with(getContext())
                .load(shot.getUser().getAvatar_url())
                .placeholder(R.drawable.detail_avatar_placeholder)
                .transform(new CircleTransform())
                .into(imageView);

    }


}

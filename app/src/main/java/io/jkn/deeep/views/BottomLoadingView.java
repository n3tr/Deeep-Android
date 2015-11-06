package io.jkn.deeep.views;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.jkn.deeep.R;
import io.jkn.deeep.models.CommentDO;
import io.jkn.deeep.utils.CircleTransform;

public class BottomLoadingView extends FrameLayout {


    private TextView tvCommentBody;
    private ImageView imageView;
    private TextView tvCommentUser;

    public BottomLoadingView(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public BottomLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    public BottomLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    private void initInflate() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.bottom_loading_view, this);
    }

    private void initInstances() {

    }

    private void initWithAttrs(AttributeSet attrs) {

    }




}

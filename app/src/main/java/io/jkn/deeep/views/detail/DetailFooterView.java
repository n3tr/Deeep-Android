package io.jkn.deeep.views.detail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import io.jkn.deeep.R;

public class DetailFooterView extends FrameLayout {


    private TextView footerText;

    public DetailFooterView(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public DetailFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    public DetailFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    private void initInflate() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.detail_footer_view, this);
    }

    private void initInstances() {
        footerText = (TextView)findViewById(R.id.footerText);
    }

    private void initWithAttrs(AttributeSet attrs) {

    }

    public void setText (String text){
        footerText.setText(text);
    }




}

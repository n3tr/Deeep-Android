package io.jkn.deeep.views.detail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.jkn.deeep.R;
import io.jkn.deeep.models.ShotDO;

public class DetailActionView extends LinearLayout {




    public DetailActionView(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public DetailActionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    public DetailActionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    private void initInflate() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.detail_action_view, this);
    }

    private void initInstances() {


    }

    private void initWithAttrs(AttributeSet attrs) {

    }

    public void setShot(ShotDO shot){

                
    }


}

package io.jkn.deeep.views.detail;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import io.jkn.deeep.R;
import io.jkn.deeep.models.ShotDO;

public class DetailDescriptionView extends FrameLayout {


    private TextView tvDescription;

    public DetailDescriptionView(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public DetailDescriptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    public DetailDescriptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    private void initInflate() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.detail_description_view, this);
    }

    private void initInstances() {
        tvDescription = (TextView)findViewById(R.id.tvDescription);



    }

    private void initWithAttrs(AttributeSet attrs) {

    }

    public void setShot(ShotDO shot){
        String desc;
        if (shot.getDescription() != null ){
            desc = shot.getDescription();
        }else{
            desc = "<p>No description...</p>";
            tvDescription.setTextColor(0xffc0c0c0);
        }
        tvDescription.setText(Html.fromHtml(desc));
        tvDescription.setMovementMethod(LinkMovementMethod.getInstance());
        tvDescription.setLinkTextColor(0xff3D6DC7);
        stripUnderlines(tvDescription);

    }

    private void stripUnderlines(TextView textView) {
        Spannable s = (Spannable)textView.getText();
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for (URLSpan span: spans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            s.setSpan(span, start, end, 0);
        }
        textView.setText(s);
    }

    private class URLSpanNoUnderline extends URLSpan {
        public URLSpanNoUnderline(String url) {
            super(url);
        }
        @Override public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }


}

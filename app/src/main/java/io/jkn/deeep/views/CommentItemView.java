package io.jkn.deeep.views;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.jkn.deeep.R;
import io.jkn.deeep.models.CommentDO;
import io.jkn.deeep.models.ShotDO;
import io.jkn.deeep.utils.CircleTransform;

public class CommentItemView extends FrameLayout {


    private TextView tvCommentBody;
    private ImageView imageView;
    private TextView tvCommentUser;
    private TextView tvCommentDate;

    public CommentItemView(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public CommentItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    public CommentItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    private void initInflate() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.comment_item_view, this);
    }

    private void initInstances() {

        tvCommentBody = (TextView) findViewById(R.id.tvCommentBody);
        tvCommentUser = (TextView) findViewById(R.id.tvCommentUser);
        tvCommentDate = (TextView) findViewById(R.id.tvCommentDate);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    private void initWithAttrs(AttributeSet attrs) {

    }

    public void setComment(CommentDO comment) {
        tvCommentBody.setText(Html.fromHtml(comment.getBody()));
        tvCommentBody.setMovementMethod(LinkMovementMethod.getInstance());
        tvCommentBody.setLinkTextColor(0xff3D6DC7);
        stripUnderlines(tvCommentBody);

        tvCommentUser.setText(comment.getUser().getName());

//        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Calendar today = Calendar.getInstance();

        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(comment.getCreatedAt());

//        long diff = today.getTimeInMillis() - thatDay.getTimeInMillis();

        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(thatDay.getTimeInMillis(), today.getTimeInMillis(),DateUtils.MINUTE_IN_MILLIS);

//        String dateString = df.format(comment.getCreatedAt());
        tvCommentDate.setText(timeAgo);


        Picasso.with(getContext())
                .load(comment.getUser().getAvatar_url())
                .placeholder(R.drawable.detail_avatar_placeholder)
                .transform(new CircleTransform())
                .into(imageView);
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

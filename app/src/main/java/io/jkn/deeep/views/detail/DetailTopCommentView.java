package io.jkn.deeep.views.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import io.jkn.deeep.R;
import io.jkn.deeep.models.AttachmentDO;
import io.jkn.deeep.models.CommentDO;
import io.jkn.deeep.models.ShotDO;
import io.jkn.deeep.models.response.ErrorResponse;
import io.jkn.deeep.services.DeeepClient;
import io.jkn.deeep.views.CommentItemView;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailTopCommentView extends FrameLayout {


    private LinearLayout layoutContent;
    private LinearLayout layoutLoading;
    private RelativeLayout layoutFooter;

    public DetailTopCommentView(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public DetailTopCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    public DetailTopCommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    private void initInflate() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.detail_topcomments_view, this);
    }

    private void initInstances() {
        layoutContent = (LinearLayout) findViewById(R.id.layoutContent);
        layoutLoading = (LinearLayout) findViewById(R.id.layoutLoading);
        layoutFooter = (RelativeLayout) findViewById(R.id.layoutFooter);


    }

    private void initWithAttrs(AttributeSet attrs) {

    }

    public void setShot(ShotDO shot) {
        if (shot.getCommentsCount() == 0) {
            showNoComment();
            return;
        }

        showLoading();


        DeeepClient.getInstance(getContext()).getComment(shot.getId(),1,new DeeepClient.OnClientResponseCallback<CommentDO[], ErrorResponse>() {
            @Override
            public void onResponse(CommentDO[] comments, Response response) {
                handleCommentsResponse(comments);
            }

            @Override
            public void onFailure(ErrorResponse error, RetrofitError rawError) {

            }
        });

    }

    private void showLoading() {
        layoutLoading.setVisibility(VISIBLE);
    }

    private void handleCommentsResponse(CommentDO[] comments) {
        CommentDO[] displayComments;
        if (comments.length > 4) {
            displayComments = Arrays.copyOfRange(comments,0,5);
        }else{
            displayComments = comments;
        }

        for (int i = 0; i < displayComments.length; i++) {
            CommentItemView commentItem = createCommentItem(displayComments[i]);
            layoutContent.addView(commentItem,layoutContent.getChildCount() - 1);
        }


        AlphaAnimation anim = new AlphaAnimation(1.0f, 0f);
        anim.setDuration (300);
        layoutLoading.startAnimation(anim);

        anim.setFillBefore(true);
        anim.setFillAfter(true);


        layoutContent.setVisibility(VISIBLE);
        layoutFooter.setVisibility(VISIBLE);
        layoutLoading.setVisibility(GONE);

    }

    private CommentItemView createCommentItem(CommentDO displayComment) {
        CommentItemView commentItemView = new CommentItemView(getContext());
        commentItemView.setComment(displayComment);
        return commentItemView;
    }

    private void showNoComment() {

    }


}

package io.jkn.deeep.views.detail;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.jkn.deeep.R;
import io.jkn.deeep.models.AttachmentDO;
import io.jkn.deeep.models.ShotDO;
import io.jkn.deeep.models.response.ErrorResponse;
import io.jkn.deeep.services.DeeepClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailAttachmentView extends FrameLayout {



    private LinearLayout layoutContent;
    private ArrayList<ImageView> imageList;

    public DetailAttachmentView(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public DetailAttachmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    public DetailAttachmentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    private void initInflate() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.detail_attachment_view, this);
    }

    private void initInstances() {
        layoutContent = (LinearLayout) findViewById(R.id.layoutContent);



    }

    private void initWithAttrs(AttributeSet attrs) {

    }

    public void setShot(ShotDO shot){
        if (shot.getAttachmentsCount() == 0) return;

        imageList = new ArrayList<ImageView>();
        for (int i = 0; i < shot.getAttachmentsCount(); i++){
            ImageView imageView = createPlaceholderImageView();
            layoutContent.addView(imageView);
            imageList.add(imageView);
        }


        DeeepClient.getInstance(getContext()).getAttachmentsForShot(shot.getId(),new DeeepClient.OnClientResponseCallback<AttachmentDO[], ErrorResponse>() {
            @Override
            public void onResponse(AttachmentDO[] data, Response response) {
                handleAttachmentResponse(data);
            }

            @Override
            public void onFailure(ErrorResponse error, RetrofitError rawError) {

            }
        });


                
    }

    private ImageView createPlaceholderImageView() {
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 57, getResources().getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 72, getResources().getDisplayMetrics());
        int marginRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,height);

        lp.setMargins(0,0,marginRight,0);


        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(lp);
        imageView.setBackgroundColor(0xffe0e0e0);
        return imageView;
    }

    private void handleAttachmentResponse(AttachmentDO[] attachments) {

        for (int i = 0; i < attachments.length; i++)
        {
            final AttachmentDO attachment = attachments[i];
            ImageView imageView;
            if (i >= imageList.size()){
                imageView = createPlaceholderImageView();
                imageList.add(imageView);
            }else{
                imageView = imageList.get(i);
            }

            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(attachment.getAttachmentUrl()));
                    getContext().startActivity(browserIntent);
                }
            });

            Picasso.with(getContext())
                    .load(attachment.getThumbnailUrl())
                    .into(imageView);

        }
    }


}

package io.jkn.deeep.fragments;



import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;

import io.jkn.deeep.R;
import io.jkn.deeep.activity.ShotDetailActivity;
import io.jkn.deeep.models.ShotDO;
import io.jkn.deeep.views.detail.DetailActionView;
import io.jkn.deeep.views.detail.DetailAttachmentView;
import io.jkn.deeep.views.detail.DetailCounterView;
import io.jkn.deeep.views.detail.DetailDescriptionView;
import io.jkn.deeep.views.detail.DetailReboundView;
import io.jkn.deeep.views.detail.DetailTopCommentView;
import io.jkn.deeep.views.detail.DetailUserView;
import io.jkn.deeep.views.detail.DetailImageView;
import io.jkn.deeep.views.ScrollViewExt;
import io.jkn.deeep.views.ScrollViewListener;


public class ShotDetailFragment extends Fragment {

    private ShotDO shot;
    private LinearLayout layoutContent;
    private ScrollViewExt scrollView;
    private DetailImageView headerImageView;

    public ShotDetailFragment() {
        super();
    }

    public static ShotDetailFragment newInstance(ShotDO shot) {
        ShotDetailFragment fragment = new ShotDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("shot", shot);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shot = getArguments().getParcelable("shot");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shot_detail, container, false);
        initInstances(inflater, rootView, savedInstanceState);
        return rootView;
    }

    private void initInstances(LayoutInflater inflater, View rootView, Bundle savedInstanceState) {
        setRetainInstance(true);

        scrollView = (ScrollViewExt) rootView.findViewById(R.id.scrollView);
        scrollView.setScrollViewListener(new ScrollViewListener() {
            public ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.pinkColor));

            @Override
            public void onScrollChanged(ScrollViewExt scrollView, int x, int y, int oldx, int oldy) {


                int topOffset = scrollView.getScrollY();
                int height = headerImageView.getHeight() - ((ActionBarActivity)getActivity()).getSupportActionBar().getHeight();
                double per = (double)topOffset / (double)height;


                if (per > 1) per = 1.0f;

                if (topOffset < 0) per = 0;

                if (per > 0) {
                    ((ActionBarActivity)getActivity())
                            .getSupportActionBar()
                            .setTitle("Shot");
                }else{
                    ((ActionBarActivity)getActivity())
                            .getSupportActionBar()
                            .setTitle("");
                }


                cd.setAlpha((int)( per*255.0));
                ((ActionBarActivity)getActivity())
                        .getSupportActionBar()
                        .setBackgroundDrawable(cd);
            }
        });


        layoutContent = (LinearLayout) rootView.findViewById(R.id.layoutContent);


        setupImageView();
        setupUserView();
        setupReboundShot();
        setupCounterView();
        setupActionView();
        setupDetailView();

        setAttachmentView();


        setupTopCommentView();


//        listView = (ListView) rootView.findViewById(R.id.listView);
//        adapter = new ShotListAdapter(shot);
//        listView.setAdapter(adapter);
//        final DetailImageView header = new DetailImageView(getActivity());
//        listView.addHeaderView(header);
//        header.setShotImage(shot.getShotImage());
//
//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                int topOffset = -(header.getTop());
//
//                if (header.getTop() >= 0){
//                    ((ActionBarActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_bg));
//                }else{
//
//                    int height = header.getHeight() - ((ActionBarActivity)getActivity()).getSupportActionBar().getHeight();
//                    double per = (double)topOffset / (double)height;
////                    per += 0.4f;
//
//
//                   if (per > 1) per = 1.0f;
//                    ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.pinkColor));
//                    cd.setAlpha((int)( per*255.0));
//                    ((ActionBarActivity)getActivity())
//                            .getSupportActionBar()
//                            .setBackgroundDrawable(cd);
//                }
//
//            }
//        });


    }

    private void setupActionView() {
        DetailActionView view = new DetailActionView(getActivity());
        layoutContent.addView(view);
    }

    private void setupTopCommentView() {
        DetailTopCommentView view = new DetailTopCommentView(getActivity());
        view.setShot(shot);
        layoutContent.addView(view);
    }

    private void setAttachmentView() {
        if (shot.getAttachmentsCount() == 0) return;
        DetailAttachmentView view = new DetailAttachmentView(getActivity());
        view.setShot(shot);
        layoutContent.addView(view);
    }

    private void setupDetailView() {
        DetailDescriptionView view = new DetailDescriptionView(getActivity());
        view.setShot(shot);
        layoutContent.addView(view);
    }

    private void setupReboundShot() {
        if (shot.getReboundSourceId() == 0) return;
        DetailReboundView view = new DetailReboundView(getActivity());
        view.setShot(shot);
        layoutContent.addView(view);

        view.setReboundViewOnClickListener(new DetailReboundView.ReboundViewOnClickListener() {
            @Override
            public void onClickReboundView(ShotDO shot) {
                Intent i = new Intent(getActivity(), ShotDetailActivity.class);
                i.putExtra(ShotDetailActivity.EXTRA_SHOT,shot);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.left_slide_in, R.anim.scale_down);

            }
        });
    }

    private void setupCounterView() {
        DetailCounterView view = new DetailCounterView(getActivity());
        view.setShot(shot);
        layoutContent.addView(view);
    }

    private void setupUserView() {
        DetailUserView view = new DetailUserView(getActivity());
//        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        view.setShot(shot);
        layoutContent.addView(view);
    }

    private void setupImageView() {
        headerImageView = new DetailImageView(getActivity());
//        headerImageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        headerImageView.setShotImage(shot.getShotImage());
        layoutContent.addView(headerImageView);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}

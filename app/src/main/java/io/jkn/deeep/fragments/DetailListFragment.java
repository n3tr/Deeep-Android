package io.jkn.deeep.fragments;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.jkn.deeep.R;
import io.jkn.deeep.activity.ShotDetailActivity;
import io.jkn.deeep.models.CommentDO;
import io.jkn.deeep.models.ShotDO;
import io.jkn.deeep.models.response.ErrorResponse;
import io.jkn.deeep.services.DeeepClient;
import io.jkn.deeep.views.BottomLoadingView;
import io.jkn.deeep.views.CommentItemView;
import io.jkn.deeep.views.ScrollViewExt;
import io.jkn.deeep.views.ScrollViewListener;
import io.jkn.deeep.views.detail.DetailActionView;
import io.jkn.deeep.views.detail.DetailAttachmentView;
import io.jkn.deeep.views.detail.DetailCounterView;
import io.jkn.deeep.views.detail.DetailDescriptionView;
import io.jkn.deeep.views.detail.DetailFooterView;
import io.jkn.deeep.views.detail.DetailImageView;
import io.jkn.deeep.views.detail.DetailReboundView;
import io.jkn.deeep.views.detail.DetailTopCommentView;
import io.jkn.deeep.views.detail.DetailUserView;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DetailListFragment extends Fragment {

    private ShotDO shot;
    private DetailImageView headerImageView;
    private ListView listView;
    private LinearLayout headerView;
    private DetailListAdapter adapter;
    private ArrayList<CommentDO> commentList;
    private int currentCommentPage = 1;
    private ColorDrawable cd;

    public DetailListFragment() {
        super();
    }

    public static DetailListFragment newInstance(ShotDO shot) {
        DetailListFragment fragment = new DetailListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_detail_list, container, false);
        initInstances(inflater, rootView, savedInstanceState);
        return rootView;
    }

    private void initInstances(LayoutInflater inflater, View rootView, Bundle savedInstanceState) {
//        setRetainInstance(true);



        listView = (ListView) rootView.findViewById(R.id.listView);

        setupImageView();
        setupUserView();
        setupReboundShot();
        setupCounterView();
        setupActionView();
        setupDetailView();
        setAttachmentView();


        if (commentList == null) {
            commentList = new ArrayList<>();
        }


        adapter = new DetailListAdapter(shot,commentList);
        listView.setAdapter(adapter);
        listView.addHeaderView(getHeaderView());
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                if ((totalItemCount != 0 && commentList.size() < shot.getCommentsCount() )
                        && (firstVisibleItem + visibleItemCount) >= totalItemCount - 2) {
                    fetchComments();
                }

                int topOffset = -(headerView.getTop());
                int height = headerImageView.getHeight() - ((ActionBarActivity)getActivity()).getSupportActionBar().getHeight();
                double per = (double)topOffset / (double)height;

                if (per > 0) {
                    ((ActionBarActivity)getActivity())
                            .getSupportActionBar()
                            .setTitle("Shot");
                }else{
                    ((ActionBarActivity)getActivity())
                            .getSupportActionBar()
                            .setTitle("");
                }

                if (per > 1) per = 1.0f;
                if (topOffset < 0) per = 0;
                ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.pinkColor));
                cd.setAlpha((int)( per*255.0));
                ((ActionBarActivity)getActivity())
                        .getSupportActionBar()
                        .setBackgroundDrawable(cd);

            }
        });


        fetchComments();


    }

    private void fetchComments() {

        if (shot.getCommentsCount() == 0){
            adapter.setNoComment(true);
            return;
        }


        if (adapter.isLoading())
            return;

        adapter.setLoading(true);

        DeeepClient.getInstance(getActivity()).getComment(shot.getId(),currentCommentPage, new DeeepClient.OnClientResponseCallback<CommentDO[], ErrorResponse>() {
            @Override
            public void onResponse(CommentDO[] data, Response response) {
                currentCommentPage++;
                commentList.addAll(Arrays.asList(data));


                if (commentList.size() >= shot.getCommentsCount() || data.length == 0)
                    adapter.setAllLoaded(true);

                adapter.setLoading(false);

            }

            @Override
            public void onFailure(ErrorResponse error, RetrofitError rawError) {
                adapter.setLoading(false);
            }
        });

    }

    private void setupActionView() {
        DetailActionView view = new DetailActionView(getActivity());
        getHeaderView().addView(view);
    }

    private void setupTopCommentView() {
        DetailTopCommentView view = new DetailTopCommentView(getActivity());
        view.setShot(shot);
        getHeaderView().addView(view);
    }

    private void setAttachmentView() {
        if (shot.getAttachmentsCount() == 0) return;
        DetailAttachmentView view = new DetailAttachmentView(getActivity());
        view.setShot(shot);
        getHeaderView().addView(view);
    }

    private void setupDetailView() {
        DetailDescriptionView view = new DetailDescriptionView(getActivity());
        view.setShot(shot);
        getHeaderView().addView(view);
    }

    private void setupReboundShot() {
        if (shot.getReboundSourceId() == 0) return;
        DetailReboundView view = new DetailReboundView(getActivity());
        view.setShot(shot);
        getHeaderView().addView(view);

        view.setReboundViewOnClickListener(new DetailReboundView.ReboundViewOnClickListener() {
            @Override
            public void onClickReboundView(ShotDO shot) {
                Intent i = new Intent(getActivity(), ShotDetailActivity.class);
                i.putExtra(ShotDetailActivity.EXTRA_SHOT, shot);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.left_slide_in, R.anim.scale_down);

            }
        });
    }

    private void setupCounterView() {
        DetailCounterView view = new DetailCounterView(getActivity());
        view.setShot(shot);
        getHeaderView().addView(view);
    }

    private void setupUserView() {
        DetailUserView view = new DetailUserView(getActivity());

        view.setShot(shot);
        getHeaderView().addView(view);
    }

    private void setupImageView() {
        headerImageView = new DetailImageView(getActivity());

        headerImageView.setShotImage(shot.getShotImage());
        getHeaderView().addView(headerImageView);
    }

    public LinearLayout getHeaderView() {
        if (headerView == null){
            // Create header view wrapper
            headerView = new LinearLayout(getActivity());
            headerView.setOrientation(LinearLayout.VERTICAL);
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(lp);
        }
        return headerView;
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


    public class DetailListAdapter extends BaseAdapter{



        private ShotDO shot;
        private List<CommentDO> comments;
        private boolean loading;
        private boolean noComment = false;
        private boolean allLoaded = false;

        public DetailListAdapter(ShotDO shot,List<CommentDO> comments) {
            this.shot = shot;
            this.comments = comments;
        }

        public void setLoading(boolean loading) {
            this.loading = loading;
            this.notifyDataSetChanged();
        }

        public void setNoComment(boolean noComment) {
            this.noComment = noComment;
            this.notifyDataSetChanged();
        }

        public void setAllLoaded(boolean allLoaded) {
            this.allLoaded = allLoaded;
            this.notifyDataSetChanged();

        }

        public boolean isLoading() {
            return loading;
        }

        @Override
        public int getCount() {

            if (noComment || allLoaded)
                return comments.size() + 1;

            if (comments == null) {
                return 0;
            }

            if (loading)
                return comments.size() + 1;

            return comments.size();
        }

        @Override
        public CommentDO getItem(int position) {
            return comments.get(position);
        }

        @Override
        public long getItemId(int position) {
            if (loading && position == comments.size()) return 0;
            return comments.get(position).getId();
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public int getItemViewType(int position) {

            if ((noComment || allLoaded) && position == comments.size())
                return 2;

            if (loading && position == comments.size())
                return 1; // Loading

            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if ((noComment || allLoaded) && position == comments.size() ) {
                DetailFooterView footer;
                if (convertView == null) {
                    footer = new DetailFooterView(parent.getContext());
                }else{
                    footer = (DetailFooterView)convertView;
                }

                if (allLoaded)
                    footer.setText(shot.getCommentsCount() + " Comments");

                if (noComment)
                    footer.setText("No Comments");

                return footer;
            }

            // if adapter loading and position is last
            if (loading && position == comments.size()){
                BottomLoadingView loadingView;
                if (convertView == null){
                    loadingView = new BottomLoadingView(parent.getContext());

                }else{
                    loadingView = (BottomLoadingView)convertView;
                }

                return loadingView;
            }

            CommentItemView commentView;
            if (convertView == null) {
                commentView = new CommentItemView(parent.getContext());
            }else{
                commentView = (CommentItemView)convertView;
            }

            commentView.setComment(getItem(position));

            return commentView;
        }
    }

}



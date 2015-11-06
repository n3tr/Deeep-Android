package io.jkn.deeep.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.jkn.deeep.R;
import io.jkn.deeep.activity.ShotDetailActivity;
import io.jkn.deeep.adapters.ShotGridAdapter;
import io.jkn.deeep.models.ShotDO;
import io.jkn.deeep.models.response.ErrorResponse;
import io.jkn.deeep.services.DeeepClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by n3tr on 1/5/2015 AD.
 */
public abstract class ShotGridFragment extends Fragment {

    GridView gridView;

    private List<ShotDO> shotList = new ArrayList<>();
    private ShotGridAdapter adapter;

    protected boolean isLoading = false;
    protected int currentPage = 1;
    private SwipeRefreshLayout swRefresh;
    private ProgressBar progressBar;


    public ShotGridFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_popular, container, false);

        // Adapter Configure
        adapter = new ShotGridAdapter(shotList);

        swRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swRefresh);
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                Log.d("ShotGrid","onReload");
                prepareFetchShots();

            }
        });

        // Grid view Configure
        gridView = (GridView)rootView.findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
        gridView.setOnScrollListener(gridViewOnScrollListener);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShotDO shot = adapter.getItem(position);
                Intent i = new Intent(getActivity(), ShotDetailActivity.class);
                i.putExtra(ShotDetailActivity.EXTRA_SHOT,shot);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.left_slide_in,R.anim.scale_down);
            }
        });

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);



        Log.d("ShotGrid","onCreateView");
        if (shotList.isEmpty()) {
            Log.d("ShotGrid","FirstFetch");
            progressBar.setVisibility(View.VISIBLE);
            prepareFetchShots();
        }


        return rootView;
    }


    private void prepareFetchShots(){
        if (isLoading){
            return;
        }
        isLoading = true;
        fetchShots(currentPage,clientResponseCallback);
    }

    /**
     *
     */
    private AbsListView.OnScrollListener gridViewOnScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {


        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            swRefresh.setEnabled(firstVisibleItem == 0);
            if (totalItemCount != 0 && (firstVisibleItem + visibleItemCount) >= totalItemCount - 4) {
                // Load more here

                prepareFetchShots();
            }
        }
    };


    /**
     *
     */
    private    DeeepClient.OnClientResponseCallback clientResponseCallback = new DeeepClient.OnClientResponseCallback<ShotDO[], ErrorResponse>() {
        @Override
        public void onResponse(ShotDO[] shots, Response response) {
            if (currentPage == 1){
                shotList.clear();
            }
            shotList.addAll(Arrays.asList(shots));
            swRefresh.setRefreshing(false);
            adapter.notifyDataSetChanged();
            isLoading = false;
            currentPage++;
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onFailure(ErrorResponse error, RetrofitError rawError) {
            if (rawError.getCause() instanceof SocketTimeoutException){
                Toast.makeText(getActivity(), "Timeout Na Nuu!!", Toast.LENGTH_SHORT).show();
            }

            if (error != null) {
                Toast.makeText(getActivity(), error.getErrorDesciption().toString(), Toast.LENGTH_SHORT).show();
            }
            swRefresh.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
            isLoading = false;
        }
    };


    /**
     *
     * @param page
     */
    abstract protected void fetchShots(int page,DeeepClient.OnClientResponseCallback callback);

}

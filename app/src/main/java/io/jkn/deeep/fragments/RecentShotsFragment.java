package io.jkn.deeep.fragments;

import android.os.Bundle;

import io.jkn.deeep.services.DeeepClient;

/**
 * Created by n3tr on 1/5/2015 AD.
 */
public class RecentShotsFragment extends ShotGridFragment {

    public static ShotGridFragment newInstance() {
        ShotGridFragment fragment = new RecentShotsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void fetchShots(int page, DeeepClient.OnClientResponseCallback callback) {
        DeeepClient.getInstance(getActivity()).fetchRecentlyShots(page,callback);
    }
}

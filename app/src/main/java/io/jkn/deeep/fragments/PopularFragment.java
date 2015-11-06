package io.jkn.deeep.fragments;


import android.os.Bundle;

import io.jkn.deeep.services.DeeepClient;

public class PopularFragment extends ShotGridFragment {

    public static ShotGridFragment newInstance() {
        ShotGridFragment fragment = new PopularFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void fetchShots(int page,DeeepClient.OnClientResponseCallback defaultCallback) {
        DeeepClient.getInstance(getActivity()).fetchPopularShots(page, defaultCallback);
    }

}

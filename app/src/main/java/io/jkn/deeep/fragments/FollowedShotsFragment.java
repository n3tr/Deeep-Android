package io.jkn.deeep.fragments;

import io.jkn.deeep.services.DeeepClient;

/**
 * Created by n3tr on 1/5/2015 AD.
 */
public class FollowedShotsFragment extends ShotGridFragment {

    public static ShotGridFragment newInstance(){
        return new FollowedShotsFragment();
    }

    @Override
    protected void fetchShots(int page, DeeepClient.OnClientResponseCallback callback) {
        DeeepClient.getInstance(getActivity()).getFollowedShots(page,callback);
    }
}

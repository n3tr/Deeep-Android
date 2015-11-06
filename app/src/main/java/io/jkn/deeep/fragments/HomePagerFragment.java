package io.jkn.deeep.fragments;


import android.app.ActionBar;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import io.jkn.deeep.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePagerFragment extends Fragment {

    private ViewPager viewPager;

    public HomePagerFragment() {
        // Required empty public constructor
    }

    public static HomePagerFragment newInstance() {
        HomePagerFragment fragment = new HomePagerFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_home_pager, container, false);


        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return FollowedShotsFragment.newInstance();
                    case 1:
                        return PopularFragment.newInstance();
                    case 2:
                        return RecentShotsFragment.newInstance();
                }

                return null;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Following";
                    case 1:
                        return "Popular";
                    case 2:
                        return "Recent";
                }

                return "";

            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        PagerSlidingTabStrip pagerTabStrip = (PagerSlidingTabStrip) rootView.findViewById(R.id.pagerTab);
        pagerTabStrip.setViewPager(viewPager);

        return rootView;
    }

}

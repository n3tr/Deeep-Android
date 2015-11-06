package io.jkn.deeep.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import io.jkn.deeep.models.ShotDO;
import io.jkn.deeep.views.ShotGridView;

/**
 * Created by n3tr on 1/5/2015 AD.
 */
public class ShotGridAdapter extends BaseAdapter {

    private List<ShotDO> shotList;

    public ShotGridAdapter(List<ShotDO> shotList) {
        super();
        this.shotList = shotList;
    }

    @Override
    public int getCount() {
        return shotList.size();
    }

    @Override
    public ShotDO getItem(int position) {
        return shotList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShotGridView view;
        if (convertView == null){
            view = new ShotGridView(parent.getContext());
        }else{
            view = (ShotGridView) convertView;
        }

        view.setShot(getItem(position));
        return view;
    }
}

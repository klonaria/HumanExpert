package com.human.expert;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Michal365 on 15.04.2014.
 */
public class MenuFragment extends Fragment implements AdapterView.OnItemClickListener {
    private Activity mActivity;
    private ListView mListMenu;
    private String[] list = {"FIRST ITEM", "SECOND ITEM", "THIRD ITEM",
            "FOUR ITEM","FIVE ITEM","SIX ITEM"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.menu_fragment, null);
            mListMenu = (ListView) v.findViewById(R.id.lvMenu_MF);
            mListMenu.setAdapter(new ArrayAdapter<String>(mActivity, R.layout.item_list, list));
            mListMenu.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", position);
        detailFragment.setArguments(bundle);
        ((MainActivity) mActivity).showDetailFragment(detailFragment);
    }
}

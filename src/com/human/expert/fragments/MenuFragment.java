package com.human.expert.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.human.expert.DownloadScenarios;
import com.human.expert.R;
import com.human.expert.adapters.ScenariosAdapter;
import com.human.expert.variables.Constants;
import com.human.expert.variables.ListsData;

/**
 * Created by Michal365 on 15.04.2014.
 */
public class MenuFragment extends Fragment implements AdapterView.OnItemClickListener {
    private Activity mActivity;
    private ListView mListMenu;
    private ScenariosAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.menu_fragment, null);
            mListMenu = (ListView) v.findViewById(R.id.lvMenu_MF);

            setAdapterFromList();
            mListMenu.setAdapter(mAdapter);
            mListMenu.setOnItemClickListener(this);
        return v;
    }

    private void setAdapterFromList(){
        mAdapter = new ScenariosAdapter(mActivity);
        if (ListsData.getScenariosList() == null){
            ListsData.initScenariouList();
            DownloadScenarios downloadScenarios = new DownloadScenarios(mActivity, mAdapter);
            downloadScenarios.execute(Constants.URL_DOMAIN + Constants.FIELD_SCENARIOS + Constants.URL_RM);
        }
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
        bundle.putInt(Constants.BUNDLE_POSITION, position);
        detailFragment.setArguments(bundle);
        ((MainActivity) mActivity).showDetailFragment(detailFragment);
    }
}

package com.human.expert.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
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
        mListMenu.setOnItemClickListener(this);
        return v;
    }

    private void setAdapterFromList() {
        mAdapter = new ScenariosAdapter(mActivity);
        if (ListsData.getScenariosList() == null) {
            if (isInternetConnected(mActivity)) {
                ListsData.initScenariosList();
                DownloadScenarios downloadScenarios = new DownloadScenarios(mActivity, mAdapter);
                downloadScenarios.execute(Constants.URL_DOMAIN + Constants.FIELD_SCENARIOS + Constants.URL_RM);
                mListMenu.setAdapter(mAdapter);
            } else Toast.makeText(mActivity,
                    mActivity.getResources().getString(R.string.no_internet),
                    Toast.LENGTH_SHORT).show();
        } else
            mListMenu.setAdapter(mAdapter);
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
        bundle.putInt(Constants.FIELD_ID, ListsData.getScenariosList().get(position).caseId);
        detailFragment.setArguments(bundle);
        clearBackStack();
        ((MainActivity) mActivity).showDetailFragment(detailFragment);
    }

    private void clearBackStack() {
        FragmentManager fm = mActivity.getFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    private static boolean isInternetConnected(Context context) {
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet = false;
        try {
            if (con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null) {
                internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
            }
        } catch (Exception e) {
            Log.d(Constants.LOG_ERROR, "Error with internet: " + e.toString());
        }
        return (wifi || internet);
    }
}

package com.human.expert.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import com.human.expert.R;

public class MainActivity extends Activity {

    private boolean mScreenOrientation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mScreenOrientation = getResources().getBoolean(R.bool.portrait);
        if (mScreenOrientation) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getFragmentManager().
                    beginTransaction().
                    replace(R.id.flMainContent_M, new MenuFragment()).
                    commit();
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getFragmentManager().
                    beginTransaction().
                    replace(R.id.flMainContent_M, new MenuFragment()).
                    commit();
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                if (getFragmentManager().getBackStackEntryCount() > 0){
                    getFragmentManager().popBackStack();
                if (getFragmentManager().getBackStackEntryCount() == 1) {
                    getActionBar().setHomeButtonEnabled(false);
                    getActionBar().setDisplayHomeAsUpEnabled(false);
                    getActionBar().setTitle(getResources().getString(R.string.app_name));
                }}
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            getActionBar().setDisplayHomeAsUpEnabled(false);
            getActionBar().setTitle(getResources().getString(R.string.app_name));
        }
    }

    public void showDetailFragment(Fragment fragment) {
        if (mScreenOrientation) {
            getFragmentManager().
                    beginTransaction().
                    replace(R.id.flMainContent_M, fragment).
                    addToBackStack(null).
                    commit();
        } else
            getFragmentManager().
                    beginTransaction().
                    replace(R.id.flDetail_M, fragment).
                    addToBackStack(null).
                    commit();
    }
}

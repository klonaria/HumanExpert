package com.human.expert;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Michal365 on 15.04.2014.
 */
public class DetailFragment extends Fragment {
    private Activity mActivity;
    private ListView mListQuestions;
    private TextView mTitleDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.detail_fragment, null);
        mListQuestions = (ListView) v.findViewById(R.id.lvQuestionsDF);
        mTitleDescription = (TextView) v.findViewById(R.id.tvDescriptionText_DF);

        ImageView icon_description = new ImageView(mActivity);
        icon_description.setImageResource(R.drawable.ic_launcher);
        mListQuestions.addHeaderView(icon_description);
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
}

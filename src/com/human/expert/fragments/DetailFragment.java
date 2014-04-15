package com.human.expert.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.human.expert.R;
import com.human.expert.adapters.CasesModel;
import com.human.expert.adapters.ScenariosAdapter;
import com.human.expert.adapters.ScenariosModel;
import com.human.expert.variables.Constants;
import com.human.expert.variables.ListsData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Michal365 on 15.04.2014.
 */
public class DetailFragment extends Fragment implements View.OnClickListener{
    private Activity mActivity;
    private Button btnPositive, btnNegative;
    private TextView mTitleDescription;
    private ImageView mLogoDescription;
    private int position = 0;
    private ScenariosModel dataPositive, dataNegative;
    private CasesModel dataDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.detail_fragment, null);
        mTitleDescription = (TextView) v.findViewById(R.id.tvDescriptionText_DF);
        mLogoDescription = (ImageView) v.findViewById(R.id.imDescriptionLogo_DF);
        btnPositive = (Button) v.findViewById(R.id.btnPositive);
        btnNegative = (Button) v.findViewById(R.id.btnNegative);
        btnPositive.setOnClickListener(this);
        btnNegative.setOnClickListener(this);

        DownloadedCases downloadedCases = new DownloadedCases();
        downloadedCases.execute(
                Constants.URL_DOMAIN +
                        Constants.URL_CASES +
                        Constants.URL_RM +
                        ListsData.getScenariosList().get(position).caseId);
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        if (getArguments().containsKey(Constants.BUNDLE_POSITION))
        position = getArguments().getInt(Constants.BUNDLE_POSITION);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNegative:
                DownloadedCases downloadedCasesNegative = new DownloadedCases();
                downloadedCasesNegative.execute(
                        Constants.URL_DOMAIN +
                                Constants.URL_CASES +
                                Constants.URL_RM +
                                dataNegative.caseId);
                break;
            case R.id.btnPositive:
                DownloadedCases downloadedCasesPositive = new DownloadedCases();
                downloadedCasesPositive.execute(
                        Constants.URL_DOMAIN +
                                Constants.URL_CASES +
                                Constants.URL_RM +
                                dataPositive.caseId);
                break;
        }
    }

    private class DownloadedCases extends AsyncTask<String, Void, Void> {
        private Dialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new Dialog(mActivity);
            mProgressDialog.requestWindowFeature(mProgressDialog.getWindow().FEATURE_NO_TITLE);
            mProgressDialog.setContentView(R.layout.progress_layout);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Void scenariousModels) {
            super.onPostExecute(scenariousModels);
            if (mProgressDialog != null)
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            if (dataNegative != null && dataPositive != null) {
                btnNegative.setText(dataNegative.text);
                btnPositive.setText(dataPositive.text);
                mLogoDescription.setImageBitmap(dataDescription.image);
                mTitleDescription.setText(dataDescription.text);
            } else Toast.makeText(mActivity, "End Expert Assistance", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(String... params) {
            parseList(params[0]);
            return null;
        }

        private void parseList (String _url){
            try {
                URL url = new URL(_url);

                InputStream dataStream = url.openConnection().getInputStream();
                InputStreamReader isr = new InputStreamReader(dataStream, "UTF-8");
                BufferedReader rd = new BufferedReader(isr);
                StringBuilder data = new StringBuilder();

                String line = "";
                try {
                    while ((line = rd.readLine()) != null) {
                        data.append(line);
                    }
                } catch (IOException e) {}
                dataStream.close();

                JSONObject temp  = new JSONObject(data.toString());
                JSONObject _case = temp.getJSONObject(Constants.FIELD_CASE);
                dataDescription = new CasesModel(
                        _case.getString(Constants.FIELD_TEXT),
                        downloadBitmap(_case.getString(Constants.FIELD_IMAGE)),
                        _case.getInt(Constants.FIELD_ID));
                JSONArray _answers = _case.getJSONArray(Constants.FIELD_ANSWERS);
                JSONObject object_positive = _answers.getJSONObject(0);
                JSONObject object_negative = _answers.getJSONObject(1);
                dataPositive = new ScenariosModel(
                        object_positive.getString(Constants.FIELD_TEXT),
                        object_positive.getInt(Constants.FIELD_ID),
                        object_positive.getInt(Constants.FIELD_CASE_ID));

                dataNegative = new ScenariosModel(
                        object_negative.getString(Constants.FIELD_TEXT),
                        object_negative.getInt(Constants.FIELD_ID),
                        object_negative.getInt(Constants.FIELD_CASE_ID));

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        private final Bitmap downloadBitmap(final String url_icon) {
            Bitmap result_icon = null;
            try {
                URL imageUrl = new URL(url_icon);
                if(imageUrl != null) {
                    result_icon = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result_icon;
        }
    }
}

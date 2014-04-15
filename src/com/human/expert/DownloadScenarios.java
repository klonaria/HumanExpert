package com.human.expert;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
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
public class DownloadScenarios extends AsyncTask<String, Void, Void> {
    private Activity mContext;
    private Dialog mProgressDialog;
    private ScenariosAdapter mScAdapter;

    public DownloadScenarios(Activity context, ScenariosAdapter _adapter){
        this.mContext = context;
        this.mScAdapter = _adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new Dialog(mContext);
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
        mScAdapter.notifyDataSetChanged();
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
            JSONArray arrayScenarious = temp.getJSONArray(Constants.FIELD_SCENARIOS);

            for (int i = 0; i < arrayScenarious.length(); i++){
                JSONObject item = arrayScenarious.getJSONObject(i);
                ListsData.getScenariosList().add(new ScenariosModel(
                        item.getString(Constants.FIELD_TEXT),
                        item.getInt(Constants.FIELD_ID),
                        item.getInt(Constants.FIELD_CASE_ID)));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

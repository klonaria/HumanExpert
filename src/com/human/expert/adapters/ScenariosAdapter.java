package com.human.expert.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.human.expert.R;
import com.human.expert.variables.ListsData;

/**
 * Created by Michal365 on 15.04.2014.
 */
public class ScenariosAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mLf;

    public ScenariosAdapter (Context context){
        this.mContext = context;
        mLf = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return ListsData.getScenariosList().size();
    }

    @Override
    public Object getItem(int position) {
        return ListsData.getScenariosList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView field = null;
        if (convertView == null){
            convertView = mLf.inflate(R.layout.item_scanarios_list, null);
            field = (TextView) convertView.findViewById(R.id.tvItem_IL);
            convertView.setTag(field);
        } field = (TextView) convertView.getTag();
        field.setText(ListsData.getScenariosList().get(position).text);
        return convertView;
    }

}

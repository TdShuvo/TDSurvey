package com.survey.shuvo.technodhaka.tdsurvey.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.survey.shuvo.technodhaka.tdsurvey.DBHelper.DynamicTableQuesDataModel;
import com.survey.shuvo.technodhaka.tdsurvey.R;

import java.util.ArrayList;

/**
 * Created by TD02 on 3/6/2017.
 */
public class DynamicTableQusDataModelAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<DynamicTableQuesDataModel> datas = new ArrayList<DynamicTableQuesDataModel>();


    public DynamicTableQusDataModelAdapter(Activity activity, ArrayList<DynamicTableQuesDataModel> datas) {
        this.activity = activity;
        this.datas = datas;
    }


    public int getCount() {
        return datas.size();
    }


    public Object getItem(int position) {
        return datas.get(position);
    }


    public long getItemId(int position) {
        return position;
    }

    public DynamicTableQuesDataModel getDynamicDataQuestion(int pos) {
        return (DynamicTableQuesDataModel) getItem(pos);
    }

    private ViewHolder holder;


    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final DynamicTableQuesDataModel data = getDynamicDataQuestion(position);

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            row = inflater.inflate(R.layout.list_row_dynamic_table_question, null);

            holder = new ViewHolder();
            /**
             * view reference
             */

            holder.tv_dtQus = (TextView) row.findViewById(R.id.dt_qus_row_tv_dtQus);
//            holder.ibtn_go = (ImageButton) row.findViewById(R.id.dt_q_row_ibtn_go);


            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.tv_dtQus.setText(data.getqText());


/*        holder.ibtn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/

/**
 *  Change the color of background & text color Dynamically in list view
 */
        if (position % 2 == 0) {
            row.setBackgroundColor(Color.WHITE);
            changeTextColor(activity.getResources().getColor(R.color.blue));
        } else {
            row.setBackgroundColor(activity.getResources().getColor(R.color.list_divider));
            changeTextColor(activity.getResources().getColor(R.color.black));
        }


        return row;
    }

    private void changeTextColor(int color) {
        holder.tv_dtQus.setTextColor(color);
    }

    private static class ViewHolder {
        TextView tv_dtQus;
//        ImageButton ibtn_go;
    }

}

package com.survey.shuvo.technodhaka.tdsurvey.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.survey.shuvo.technodhaka.tdsurvey.DBHelper.DynamicDataIndexDataModel;
import com.survey.shuvo.technodhaka.tdsurvey.activity.DTResponseActivity;
import com.survey.shuvo.technodhaka.tdsurvey.activity.DT_ReportActivity;
import com.survey.shuvo.technodhaka.tdsurvey.R;
import com.survey.shuvo.technodhaka.tdsurvey.utils.KEY;

import java.util.ArrayList;

/**
 * Created by TD02 on 3/6/2017.
 */
public class DynamicDataIndexAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    //private SQLiteHandler sqLiteHandler;
    // private SessionManager session;
    private ArrayList<DynamicDataIndexDataModel> datas = new ArrayList<DynamicDataIndexDataModel>();

    public DynamicDataIndexAdapter(Activity activity, ArrayList<DynamicDataIndexDataModel> data) {
        this.activity = activity;
        this.datas = data;
        // this.sqLiteHandler = sqLiteHandler;
        //  session = new SessionManager(activity);


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

    public DynamicDataIndexDataModel getDynamicDataIndex(int pos) {
        return (DynamicDataIndexDataModel) getItem(pos);
    }

    ViewHolder holder;


    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        final DynamicDataIndexDataModel data = getDynamicDataIndex(position);


        // convert xml layout  to java object
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            row = inflater.inflate(R.layout.list_row_dynamic_data_index, null);

            holder = new ViewHolder();

            //view reference
            holder.llRow = (LinearLayout) row.findViewById(R.id.llRow);
            holder.tv_dtTitle = (TextView) row.findViewById(R.id.dt_index_row_tv_dtTitle);
            holder.tv_awardName = (TextView) row.findViewById(R.id.dt_index_row_tv_AwardName);
            holder.tv_progName = (TextView) row.findViewById(R.id.dt_index_row_tv_ProgramName);
            holder.tv_ActivityName = (TextView) row.findViewById(R.id.dt_index_row_tv_ActivityTitle);
            holder.iv_Go = (ImageView) row.findViewById(R.id.dt_index_row_ibtn_go);
            holder.iv_view = (ImageView) row.findViewById(R.id.dt_index_row_ibtn_view);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.tv_dtTitle.setText(data.getDtTittle());
        holder.tv_awardName.setText("Award Name : " + data.getAwardName());
        String progName = data.getProgramCode().equals("000") ? " Cross Cutting" : data.getProgramName();
        holder.tv_progName.setText("Program Name : " + progName);
        holder.tv_ActivityName.setText("Activity Title  : " + data.getPrgActivityTitle());


        holder.iv_Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // no need comment code  all table viewed by access
//                if(sqLiteHandler.checkDTBasic(datas.getDtBasicCode(), session.getStaffId())) {
                Intent intent = new Intent(activity.getApplicationContext(), DTResponseActivity.class);
                Log.e("ShuvoData",data.toString());
                intent.putExtra(KEY.DYNAMIC_INDEX_DATA_OBJECT_KEY, data);
                activity.startActivity(intent);
//                }else{
//                    Toast.makeText(activity.getApplicationContext(), "You do not have access permission", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        holder.iv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // no need comment code all viewed by access
//                if(sqLiteHandler.checkDTBasic(datas.getDtBasicCode(), session.getStaffId())) {
                Intent intent = new Intent(activity.getApplicationContext(), DT_ReportActivity.class);
                intent.putExtra(KEY.DYNAMIC_INDEX_DATA_OBJECT_KEY, data);
                activity.startActivity(intent);
//                }else{
//                    Toast.makeText(activity.getApplicationContext(), "You do not have access permission", Toast.LENGTH_SHORT).show();
//                }
            }
        });

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
        holder.tv_dtTitle.setTextColor(color);
        holder.tv_awardName.setTextColor(color);
        holder.tv_progName.setTextColor(color);
        holder.tv_ActivityName.setTextColor(color);
    }

    private static class ViewHolder {
        LinearLayout llRow;
        TextView tv_dtTitle;
        TextView tv_awardName;
        TextView tv_progName;
        TextView tv_ActivityName;
        ImageView iv_Go;
        ImageView iv_view;
    }

}

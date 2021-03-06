package com.survey.shuvo.technodhaka.tdsurvey.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.survey.shuvo.technodhaka.tdsurvey.DBHelper.DTSurveyTableDataModel;
import com.survey.shuvo.technodhaka.tdsurvey.R;

import java.util.ArrayList;

/**
 * Created by TD02 on 3/7/2017.
 */
public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.SurveyItemViewHolder>{


    private ArrayList<DTSurveyTableDataModel> dtSurveyTableDataModels;

    private Context context;

    public SurveyAdapter(ArrayList<DTSurveyTableDataModel> dtSurveyTableDataModels, Context context) {
        this.dtSurveyTableDataModels = dtSurveyTableDataModels;
        this.context = context;
    }

    @Override
    public SurveyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_survey_row, parent, false);
        return new SurveyItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SurveyItemViewHolder holder, final int position) {
        final DTSurveyTableDataModel dtSurveyTableDataModel = dtSurveyTableDataModels.get(position);
        holder.tvQuestion.setText(dtSurveyTableDataModel.getDtqText());
        if (dtSurveyTableDataModel.getDtaValue().equalsIgnoreCase("Y")) {
            holder.tvAnswer.setText("Yes");
        } else if (dtSurveyTableDataModel.getDtaValue().equalsIgnoreCase("N")) {
            holder.tvAnswer.setText("No");
        } else {
            holder.tvAnswer.setText(dtSurveyTableDataModel.getDtaValue());
        }
    }

    @Override
    public int getItemCount() {
        return dtSurveyTableDataModels.size();
    }

    public static class SurveyItemViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvQuestion, tvAnswer;
        protected LinearLayout llContainer;

        public SurveyItemViewHolder(View itemView) {
            super(itemView);
            tvQuestion = (TextView) itemView.findViewById(R.id.tvQuestion);
            tvAnswer = (TextView) itemView.findViewById(R.id.tvAnswer);
            llContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);
        }
    }

}

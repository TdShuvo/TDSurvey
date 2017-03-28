package com.survey.shuvo.technodhaka.tdsurvey.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.survey.shuvo.technodhaka.tdsurvey.SurveyFragment;
import com.survey.shuvo.technodhaka.tdsurvey.data_model.SurveyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TD02 on 3/7/2017.
 */
public class ReportViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private ArrayList<SurveyModel> surveyModels = new ArrayList<>();
    private long baseId = 0;

    public SurveyModel getSurveyModels(int position){
        return surveyModels.get(position);
    }

    public ReportViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    public void addFragments(ArrayList<SurveyModel> surveyModels){
        this.surveyModels = surveyModels;
        for (SurveyModel surveyModel :
                surveyModels) {
            mFragmentList.add(SurveyFragment.newInstance(surveyModel));
            mFragmentTitleList.add("Response " + surveyModel.getSurveyNum());
        }
    }

    public void removeFragment(int position){
        mFragmentList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Response "+(position+1)+" of "+mFragmentList.size();
    }

}

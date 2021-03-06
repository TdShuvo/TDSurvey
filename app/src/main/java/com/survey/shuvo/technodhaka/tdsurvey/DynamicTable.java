package com.survey.shuvo.technodhaka.tdsurvey;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


import com.survey.shuvo.technodhaka.tdsurvey.DBHelper.DynamicDataIndexDataModel;
import com.survey.shuvo.technodhaka.tdsurvey.DBHelper.SQLiteHandler;
import com.survey.shuvo.technodhaka.tdsurvey.activity.BaseActivity;
import com.survey.shuvo.technodhaka.tdsurvey.adapter.DynamicDataIndexAdapter;

public class DynamicTable extends BaseActivity {

    /**
     * list view to show Dynamic table list
     */
    private ListView listView;
    private Button btnHome, btnDTSearch;
    private final Context mContext = DynamicTable.this;
    private SQLiteHandler sqlH;

    private EditText edtDTSearch;
    private String idCountry;

    private DynamicDataIndexAdapter adapter = null;
    private static ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_table);

        initialize();

        // get intent
        Intent intent = getIntent();
       // idCountry = intent.getStringExtra(KEY.COUNTRY_ID);
        idCountry = "0002";

        // anonymous object
        new LoadListView(idCountry, "").execute();

        setListener();
    }

    private void setListener() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity((Activity) mContext);
            }
        });
        btnDTSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicTableSearch();
            }
        });
    }

    /**
     * @since :data craft
     * this method get the specific dynamic table   want to survey .
     */
    private void dynamicTableSearch() {
        // why data craft did this Faisal don't know
/*                        dataArray.clear();
                adapter = new DynamicDataIndexAdapter((Activity) mContext, dataArray);
                listView.setAdapter(adapter);*/

        if (edtDTSearch.getText().toString().length() == 0) {
            new LoadListView(idCountry, "").execute();

        } else {
            new LoadListView(idCountry, edtDTSearch.getText().toString()).execute();
        }

    }

    private void initialize() {
        viewReference();
        sqlH = new SQLiteHandler(mContext);
    }

    private void viewReference() {
        listView = (ListView) findViewById(R.id.lvDynamicTableIndex);
        btnHome = (Button) findViewById(R.id.btnHomeFooter);
        btnDTSearch = (Button) findViewById(R.id.btn_DTSearch);
        edtDTSearch = (EditText) findViewById(R.id.edt_DTSearch);

        // button er Gaiyeb korra jonno
        Button button = (Button) findViewById(R.id.btnRegisterFooter);
        button.setVisibility(View.GONE);
    }


    /**
     * calling getWidth() and getHeight() too early:
     * When  the UI has not been sized and laid out on the screen yet..
     *
     * @param hasFocus the value will be true when UI is focus
     */

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        addIconHomeButton();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addIconHomeButton() {
        btnHome.setText("");
        Drawable imageHome = getResources().getDrawable(R.drawable.home_b);
        btnHome.setCompoundDrawablesRelativeWithIntrinsicBounds(imageHome, null, null, null);
        setPaddingButton(mContext, imageHome, btnHome);
    }


    /**
     * AsyncTask class
     */
    private class LoadListView extends AsyncTask<Void, Integer, String> {
        private String temcCode;
        private String temdtTitle;

        public LoadListView(String temcCode, String temdtTitle) {
            this.temcCode = temcCode;
            this.temdtTitle = temdtTitle;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startProgressBar("Data is Loading");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideProgressBar();

            if (adapter != null && adapter.getCount() > 0) {
                if (adapter.getCount() != 0) {
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                } else {
                    new ADNotificationManager().showInfromDialog(mContext, "NO Data", "No data Found");
                }
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            loadDynamicIndex(temcCode, temdtTitle);
            return "success";
        }
    }

    private void hideProgressBar() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void startProgressBar(String msg) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(msg);
        pDialog.setCancelable(true);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
    }

    /**
     * @param cCode Country Code
     *              Add search Option in Dynamic index
     */

    private void loadDynamicIndex(final String cCode, final String dtName) {
       List<DynamicDataIndexDataModel> dataList = sqlH.getDynamicTableIndexList(cCode, dtName, "0001");
       // List<DynamicDataIndexDataModel> dataList = sqlH.getDynamicTableIndexList("022", "24", "0001");
        ArrayList<DynamicDataIndexDataModel> dataArray = new ArrayList<DynamicDataIndexDataModel>();

        if (dataList.size() != 0) {
            dataArray.clear();

            for (DynamicDataIndexDataModel data : dataList) {

                //add contacts data in arrayList
                dataArray.add(data);
            }

            //Assign the Adapter in list
            adapter = new DynamicDataIndexAdapter((Activity) mContext, dataArray);
        }

    }
}


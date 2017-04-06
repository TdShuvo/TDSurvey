package com.survey.shuvo.technodhaka.tdsurvey.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.survey.shuvo.technodhaka.tdsurvey.DBHelper.SQLiteHandler;
import com.survey.shuvo.technodhaka.tdsurvey.R;
import com.survey.shuvo.technodhaka.tdsurvey.controller.AppConfig;
import com.survey.shuvo.technodhaka.tdsurvey.controller.AppController;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {


    public static final String DYNAMIC_TABLE = "dynamic_table";
    public static final String ALL_DATA = "all_data";
    public static final String ENU_TABLE = "enu_table";
    SQLiteHandler sqlH ;

  //  SharedPreferences.Editor editor;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqlH = new SQLiteHandler(this);
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
        JSONArray jaary = new JSONArray();

        Toast.makeText(this, "Check in for login..", Toast.LENGTH_SHORT).show();
        checkLogin("nkalam","p3",jaary,"4");
       // setUserID("0001");
    }



    /**
     * function to verify login details in mysql db
     */
    public void checkLogin(final String user_name, final String password, final JSONArray selectedVilJArry, final String operationMode) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Downloading  data .");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.API_LINK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                /***
                 * @deis: IN THIS STRING RESPONSE WRITE THE JSON DATA
                 *
                 */
                AppController.getInstance().getRequestQueue().getCache().clear();
                writeJSONToTextFile(response, ALL_DATA);

                // DOING STRING OPERATION TO AVOID ALLOCATE CACHE MEMORY
                String errorResult = response.substring(9, 14);

                boolean error = !errorResult.equals("false");
                if (!error) {
                    Log.d("TAG", "Before downLoad RegNHouseHold 6" + "  user_name:" + user_name + " password :" + password + " selectedVilJArry:" + selectedVilJArry + "operationMode:" + operationMode);

                    Toast.makeText(MainActivity.this, "Downloading Dynamic Data", Toast.LENGTH_SHORT).show();
                    downLoadDynamicData(user_name, password, selectedVilJArry, operationMode);

                } else {
                    // Error in login. Invalid UserName or Password
                    hideDialog();
                    String errorMsg = response.substring(response.indexOf("error_msg") + 11);
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("shuvoTag", "Login Error: " + error + " Stack Tracr = " + error.getStackTrace() + " Detail = " + error.getMessage());
                hideDialog();
                showAlert("Failed to retrieve data\r\nPlease try again checking your internet connectivity, Username and Password.");

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "PhEUT5R251");
                params.put("task", "is_valid_user");
                params.put("user_name", user_name);
                params.put("password", password);
                params.put("lay_r_code_j", selectedVilJArry.toString());
                params.put("operation_mode", operationMode);
                Log.d("TAG1", "params:" + params.toString());
                return params;
            }

        };
        Log.d("TAG1", "strReq:" + strReq.toString());

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public void downLoadDynamicData(final String user_Name, final String pass_word, final JSONArray selectedVilJArry, final String operationMode) {
        // Tag used to cancel the request
        String tag_string_req = "req_ass_prog";
        Log.d("shuvoTag", "operationMode: " + operationMode);

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.API_LINK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                /***
                 *  IN THIS STRING RESPONSE WRITE THE JSON DATA
                 *
                 */
                AppController.getInstance().getRequestQueue().getCache().clear();
                writeJSONToTextFile(response, DYNAMIC_TABLE);

                Log.d("ShuvoTag", " After Loading Dynamic Table in txt last stap :7");


                // hideDialog();


                /**
                 *  DOING STRING OPERATION TO AVOID ALLOCATE CACHE MEMORY
                 */

                String errorResult = response.substring(9, 14);

/**
 * If Json String  get False than it return false
 */
                boolean error = !errorResult.equals("false");

                if (!error) {

                    /**
                     *  Launch main activity
                     */
        /*            Intent intent = new Intent(MainActivity.this, DynamicTable.class);
                   // setUserID(user_Name);
                   // setUserPassword(pass_word);
                  //  editor.putBoolean(IS_APP_FIRST_RUN, true);
                   // editor.commit();

                    startActivity(intent);*/
                    /**
                     * IF GET NO ERROR  THAN GOTO THE MAIN ACTIVITY
                     */
                    downLoadEnuTable(user_Name, pass_word);
//
                } else {
                    // Error in login. Invalid UserName or Password
                    String errorMsg = response.substring(response.indexOf("error_msg") + 11);
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ShuvoTag", "Login Error: " + error + " Stack Tracr = " + error.getStackTrace() + " Detail = " + error.getMessage());
                // hide the mdialog
                hideDialog();
                showAlert("Failed to retrieve data\r\nPlease try again checking your internet connectivity, Username and Password.");


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "PhEUT5R251");
                params.put("task", "is_down_load_dynamic_table");
                params.put("user_name", user_Name);
                params.put("password", pass_word);
                params.put("lay_r_code_j", selectedVilJArry.toString());
                params.put("operation_mode", operationMode);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void downLoadEnuTable(final String user_Name, final String pass_word) {
        // Tag used to cancel the request
        String tag_string_req = "enu";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.API_LINK_ENU, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // clear catch
                AppController.getInstance().getRequestQueue().getCache().clear();
                writeJSONToTextFile(response, ENU_TABLE);

                Log.d("ShuvoTag", " After Loading Dynamic Table in txt last stap :7");


                hideDialog();


                /**
                 *  DOING STRING OPERATION TO AVOID ALLOCATE CACHE MEMORY
                 */

                String errorResult = response.substring(9, 14);

/**
 * If Json String  get False than it return false
 */
                boolean error = !errorResult.equals("false");

                if (!error) {


                    /**
                     * IF GET NO ERROR  THAN GOTO THE MAIN ACTIVITY
                     */

                  //  setLogin(true);        // login success
                    /**
                     *  Launch main activity
                     */
                    Intent intent = new Intent(MainActivity.this, ParseDataActivity.class);
                  //  setUserID(user_Name);
                  //  setUserPassword(pass_word);
                   // editor.putBoolean(IS_APP_FIRST_RUN, true);
                  //  editor.commit();

                    startActivity(intent);
                } else {
                    // Error in login. Invalid UserName or Password
                    String errorMsg = response.substring(response.indexOf("error_msg") + 11);
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ShuvoTag", "Login Error: " + error + " Stack Tracr = " + error.getStackTrace() + " Detail = " + error.getMessage());
                // hide the mdialog
                hideDialog();
                showAlert("Failed to retrieve data\r\nPlease try again checking your internet connectivity, Username and Password.");


            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}

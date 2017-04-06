package com.survey.shuvo.technodhaka.tdsurvey;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderAnswer;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by TD02 on 4/6/2017.
 */

public class garbase {
    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }


  /*  private JSONObject uploadToServer() throws IOException, JSONException {
        String query = "https://example.com";
        String json = "{\"key\":1}";

        URL url = new URL(query);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");

        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.close();

        // read the response
        InputStream in = new BufferedInputStream(conn.getInputStream());
        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
        JSONObject jsonObject = new JSONObject(result);


        in.close();
        conn.disconnect();

        return jsonObject;
    }*/

    /*  public void answerUpload(HolderAnswer holderAnswer) throws IOException, JSONException {
        *//*  String otherParametersUrServiceNeed =  "Company=acompany&Lng=test&MainPeriod=test&UserID=123&CourseDate=8:10:10";
        String request = "http://10.0.2.2:8080/api/values/putAnswer";

        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");
       // connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
        connection.setUseCaches (false);

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
        wr.writeBytes(otherParametersUrServiceNeed);

        JSONObject answer = new JSONObject();
        answer.put("answer",holderAnswer.answer);// Set the parameter and the
        answer.put("user_id", String.valueOf(holderAnswer.userId));
        answer.put("country_id", String.valueOf(holderAnswer.countryId));
        answer.put("question_id", String.valueOf(holderAnswer.questionId));
        answer.put("qt_id", String.valueOf(holderAnswer.questionTypeId));
        answer.put("scequence_id", String.valueOf(holderAnswer.secquenceId));

        wr.writeBytes(answer.toString());

        wr.flush();
        wr.close();*//*

*//*
        HttpURLConnection connection = null;
        try {
            String requestAnswer = "http://10.0.2.2:8080/api/values/putAnswer";
            URL url=new URL(requestAnswer);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());

            JSONObject answer = new JSONObject();
            answer.put("answer",holderAnswer.answer);// Set the parameter and the
            answer.put("user_id", String.valueOf(holderAnswer.userId));
            answer.put("country_id", String.valueOf(holderAnswer.countryId));
            answer.put("question_id", String.valueOf(holderAnswer.questionId));
            answer.put("qt_id", String.valueOf(holderAnswer.questionTypeId));
            answer.put("scequence_id", String.valueOf(holderAnswer.secquenceId));

            streamWriter.write(answer.toString());
            streamWriter.flush();
            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();

                Log.d("test", stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                Log.e("test", connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception){
            Log.e("test", exception.toString());
            return null;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }*//*

       URL url;
        String request = "http://10.0.2.2:8080/api/values/putAnswer";
        HttpURLConnection conn;
        ObjectOutputStream object;
        try{
            url = new URL(request);
           // conn  = (HttpURLConnection) url.getConnection();

            conn.setDoOutput(true); //this is to enable writing
            conn.setDoInput(true);  //this is to enable reading

        }catch (Exception e){

        }
    }*/
    public void sendDataToServer(HolderAnswer holderAnswer){

      /*  HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://naviserver.azurewebsites.net/api/Person");
       // Person person = new Person("dave", 30);
        Gson gson = new Gson();
        String json = gson.toJson(holderAnswer);
        StringEntity body = new StringEntity(json);
        httpPost.setEntity(body);
        HttpResponse response = httpClient.execute(httpPost);*/

        final String URL = "http://10.0.2.2:8080/api/values/putAnswer";
// Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("answer",holderAnswer.answer);// Set the parameter and the value which has been passing to your server here.
        params.put("user_id", String.valueOf(holderAnswer.userId));
        params.put("country_id", String.valueOf(holderAnswer.countryId));
        params.put("question_id", String.valueOf(holderAnswer.questionId));
        params.put("qt_id", String.valueOf(holderAnswer.questionTypeId));
        params.put("scequence_id", String.valueOf(holderAnswer.secquenceId));

        JsonObjectRequest request_json = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Process os success response

                        Log.e("shuvoJsonResponse", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error1: ", error.getStackTrace() + " "+ error.getMessage());
            }
        });

// add the request object to the queue to be executed
      //  MySingleTone.getInstance(ResponseRecordingActivity.this).addToRequestQue(request_json);


    }

    private void makeJsonObjReq(final HolderAnswer holderAnswer) {

        String url = "http://10.0.2.2:8080/api/values/putAnswer";
        // RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // You will get the response here

                        // Toast.makeText(ResponseRecordingActivity.this, "shuvo"+ response, Toast.LENGTH_SHORT).show();
                        Log.e("ShuvoJson",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // You will get error here

                        Log.e("ShuvoJson",error.toString());

                        error.printStackTrace();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String> map = new HashMap<>();
                map.put("answer",holderAnswer.answer);// Set the parameter and the value which has been passing to your server here.
                map.put("user_id", String.valueOf(holderAnswer.userId));
                map.put("country_id", String.valueOf(holderAnswer.countryId));
                map.put("question_id", String.valueOf(holderAnswer.questionId));
                map.put("qt_id", String.valueOf(holderAnswer.questionTypeId));
                map.put("scequence_id", String.valueOf(holderAnswer.secquenceId));
                return map;
            }
        };
        //queue.add(stringRequest);
        //  AppController.getInstance().addToRequestQueue(stringRequest, "shuvo");

      //  MySingleTone.getInstance(ResponseRecordingActivity.this).addToRequestQue(stringRequest);

        // showProgressDialog();

     /*   String url ="http://10.0.2.2:8080/api/values/putAnswer";
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("userId", String.valueOf(holderAnswer.userId));
        postParam.put("questionId", String.valueOf(holderAnswer.questionId));
        postParam.put("countryId", String.valueOf(holderAnswer.countryId));
        postParam.put("questionTypeId", String.valueOf(holderAnswer.questionTypeId));
        postParam.put("scquenceId", String.valueOf(holderAnswer.secquenceId));
        postParam.put("surveyId", String.valueOf(holderAnswer.surveyId));
        postParam.put("answer", holderAnswer.answer);

        JsonObjectRequest jsonReq = new JsonObjectRequest
                (Request.Method.GET, url, new JSONObject(postParam), new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d("Server", "Läuft");
                    }
                },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Log.d("Server","onErrorResponse");
                            }
                        });
        AppController.getInstance().addToRequestQueue(jsonReq);*/


    }

}

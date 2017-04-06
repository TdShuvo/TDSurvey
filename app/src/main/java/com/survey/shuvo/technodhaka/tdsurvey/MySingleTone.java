package com.survey.shuvo.technodhaka.tdsurvey;

import android.content.Context;
import android.support.v4.util.Pools;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by TD02 on 4/4/2017.
 */

public class MySingleTone {

    private static MySingleTone mInstance;
    private RequestQueue requestQueue;
    private static Context mcntx;

    public MySingleTone(Context context) {
        this.mcntx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MySingleTone getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleTone(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mcntx.getApplicationContext());

        }
        return requestQueue;
    }

    public <T> void addToRequestQue(Request<T> request) {
        requestQueue.add(request);
    }
}

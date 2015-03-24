package com.dblappdev.hitch.network;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by guusleijsten on 19/03/15.
 */
public class getUrlResponseTask extends AsyncTask<String, Void, JSONObject> {

    private static final String BASE_URL = "http://www.yrrep.me/hitch/";

    @Override
    protected JSONObject doInBackground(String... params) {
        try {

            //------------------>>
            HttpGet httpget = new HttpGet(build(params));
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httpget);

            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);

                return new JSONObject(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
     protected void onPreExecute() {
        super.onPreExecute();
    }



    protected void onPostExecute(JSONObject result) {
    }

    /**
     * Builds an url by parameters.
     *
     * @param query the params
     * @return string in url format
     */
    private static String build(String[] query) {
        String url = BASE_URL;
        for (int i = 0; i < query.length; i++) {
            url += (i == 0)? "?" : "&";
            url += query[i];
        }
        return url;
    }
}
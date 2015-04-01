package com.dblappdev.hitch.route;

import android.content.Context;
import android.content.Intent;
import android.media.MediaRouter;
import android.os.AsyncTask;
import android.util.Log;
import com.dblappdev.hitch.app.RouteActivity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.concurrent.Callable;

/**
 * Created by Ziad on 3/19/2015.
 */
public class GetRouteDirectionsTask extends  AsyncTask<String, Void, Document> {

    /** Context of the parent activity in which this was called */
    private Callable<Void> callback;
    private Route route;

    public GetRouteDirectionsTask(Callable<Void> callback, Route route){
        this.callback = callback;
        this.route = route;
    }

    @Override
    protected Document doInBackground(String... params) {

        HttpResponse response;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(params[0]);
            response = httpClient.execute(httpPost, localContext);
            InputStream in = response.getEntity().getContent();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in);
            return doc;
        } catch (Exception e) {
            Log.d("getDocument() response", e.toString() ,e.getCause());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Document doc) {
        if (doc == null) {
            Log.d("RetrieveMapDocTask.onPostExecute()", "error fetching doc details");
        } else {
            Log.d("RetrieveMapDocTask.onPostExecute()", "successfully returned doc");
            try {
                route.setDirections(new GMapV2Direction().getDirection(doc));
                callback.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

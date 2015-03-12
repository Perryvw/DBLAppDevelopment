package com.dblappdev.hitch.app;

import java.io.BufferedReader;
import org.json.*;
import java.io.InputStreamReader;
import java.net.URL;

public class HttpURLConnection {

    private final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_PREFIX = "http://www.yrrep.me/hitch/?";

    /**
     * Retrieves the response to a get request.
     *
     * @param get the get request (key=value&key2=value2)
     * @return JSONObject made by the response.
     * @throws Exception
     */
    public JSONObject sendGet(String get) throws Exception {

        String url = GET_PREFIX + get;

        URL obj = new URL(url);
        java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        //int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'GET' request to URL : " + url);
        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return new JSONObject(response.toString());
    }
}
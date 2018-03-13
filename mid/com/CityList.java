package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class CityList {
	public static void main(String[] args) throws IOException {

		StringBuilder urlBuilder = new StringBuilder(
				"http://openapi.tago.go.kr/openapi/service/TrainInfoService/getCtyCodeList"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=ALkdL%2FXvmEgUjYzFfGg82sWA1ip%2FdlwyW9mIHKLHJK8OFkm%2FxlvzZqG6ZqdvLsCWhs%2F%2Ft6pJJzgiJTMTL60dcg%3D%3D");
		urlBuilder.append("&" + "_type=json"); 

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		//System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        String jsonstr = sb.toString();
		//System.out.println(urlBuilder);
		System.out.println("json : ");
		System.out.println(jsonstr);
		System.out.println();
		System.out.println();

		JSONObject object = (JSONObject) JSONValue.parse(jsonstr);

		JSONObject response = (JSONObject) object.get("response");
		JSONObject body = (JSONObject) response.get("body");
		JSONObject items = (JSONObject) body.get("items");

		JSONArray item = (JSONArray) items.get("item");

		for (int i = 0; i < item.size(); i++)
		{
			JSONObject data = (JSONObject) item.get(i);
			System.out.println(data.get("cityname").toString() +" : " + data.get("citycode").toString());
		}

	}
}
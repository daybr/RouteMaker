package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class TrainTime {
	public static void main(String[] args) throws IOException {

		String dep, arr, deptime;
		Scanner scan = new Scanner(System.in);

		System.out.printf("출발지 id를 입력하세요  :   ");
		dep = scan.nextLine();

		System.out.printf("도착지 id를 입력하세요  :   ");
		arr = scan.nextLine();

		System.out.printf("출발 시간을 입력하세요  :   ");
		deptime = scan.nextLine();

		StringBuilder urlBuilder = new StringBuilder(
				"http://openapi.tago.go.kr/openapi/service/TrainInfoService/getStrtpntAlocFndTrainInfo"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=ALkdL%2FXvmEgUjYzFfGg82sWA1ip%2FdlwyW9mIHKLHJK8OFkm%2FxlvzZqG6ZqdvLsCWhs%2F%2Ft6pJJzgiJTMTL60dcg%3D%3D"); 
		urlBuilder.append("&" + URLEncoder.encode("depPlaceId", "UTF-8") + "=" + URLEncoder.encode(dep, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("arrPlaceId", "UTF-8") + "=" + URLEncoder.encode(arr, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("depPlandTime", "UTF-8") + "=" + URLEncoder.encode(deptime, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
 
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
		// System.out.println(urlBuilder);
		System.out.println("json : ");
		System.out.println(jsonstr);
		System.out.println();
		System.out.println();

		JSONObject object = (JSONObject) JSONValue.parse(jsonstr);

		JSONObject response = (JSONObject) object.get("response");
		JSONObject body = (JSONObject) response.get("body");
		JSONObject items = (JSONObject) body.get("items");

		JSONArray item = (JSONArray) items.get("item");

		for (int i = 0; i < item.size(); i++) {
			JSONObject data = (JSONObject) item.get(i);
			System.out.println(data.get("depplacename").toString() + " 에서 " + data.get("arrplacename").toString()
					+ "   " + data.get("traingradename").toString());
			System.out.println("출발시간 : " + data.get("depplandtime").toString() + "  도착시간 : "
					+ data.get("arrplandtime").toString() + "  요금 : " + data.get("adultcharge").toString());
			System.out.println();
		}

	}
}
package JDBC;

import JDBC.Member;
import JDBC.MemberService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ApiExplorer {
    static final String API_URL = "http://openapi.seoul.go.kr:8088";
    static final String KEY = "666751717366706736334942437857";
    static final String FILE_TYPE = "json";
    static final String SERVICE_NAME = "TbPublicWifiInfo";
    static final String ENCODING_TYPE = "UTF-8";
    OkHttpClient client = new OkHttpClient();
//    static int start = 17600;
//    static int end = 17605;

    // url 만들기
    private URL buildUrl(int start, int end){
        StringBuilder urlBuilder = new StringBuilder(API_URL); /*URL*/
        try {
            urlBuilder.append("/" + URLEncoder.encode(KEY, ENCODING_TYPE)); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
            urlBuilder.append("/" + URLEncoder.encode(FILE_TYPE, ENCODING_TYPE)); /*요청파일타입 (xml,xmlf,xls,json) */
            urlBuilder.append("/" + URLEncoder.encode(SERVICE_NAME, ENCODING_TYPE)); /*서비스명 (대소문자 구분 필수입니다.)*/
            urlBuilder.append("/" + URLEncoder.encode(String.valueOf(start), ENCODING_TYPE)); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
            urlBuilder.append("/" + URLEncoder.encode(String.valueOf(end), ENCODING_TYPE)); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        URL url = null;
        try {
            url = new URL(urlBuilder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public String httpGet(int start, int end) {
        URL url = buildUrl(start, end);
        if (url == null) {
            return "";
        }

        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            String res = response.body().string();
            return res;
        } catch (IOException e) {
            return "error: " + e.getMessage();
        }

    }


    public List<Member> parseJson(String json) {
        JsonElement element = JsonParser.parseString(json);
        JsonArray itemArr = element.getAsJsonObject()
                .get("TbPublicWifiInfo").getAsJsonObject()
                .get("row").getAsJsonArray();

        Gson gson = new Gson();
        List<Member> result = new ArrayList<>();

        for (int i = 0; i < itemArr.size(); i++) {
            Member wifi = gson.fromJson(itemArr.get(i), Member.class);
            result.add(wifi);
        }
        return result;
    }

//    public void registerResult(List<Member> items) {
//        MemberService memberService = new MemberService();
//
//        for (Member mem : items) {
//            memberService.register(mem.X_SWIFI_MGR_NO, mem.X_SWIFI_WRDOFC, mem.X_SWIFI_MAIN_NM, mem.X_SWIFI_ADRES1, mem.LAT, mem.LNT);
//        }
//
//        memberService.list();
//    }




//    // 데이터get
//    public void dataGet() throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        MemberService memberService = new MemberService();
//        memberService.truncate();
//        int start = 1;
//        int end = 999;
//
//        while (true) {
//            String urlBuilder = this.buildUrl(start, end);
//            Request request = new Request.Builder().url(urlBuilder).build();
//            Response response = null;
//            response = client.newCall(request).execute();
//            String res = response.body().string();
//
//            if (!res.contains("MESSAGE\":\"해당하는 데이터가 없습니다.")) {
//                // json
//                JsonElement element = JsonParser.parseString(res);
//                JsonArray itemArr = element.getAsJsonObject()
//                        .get("TbPublicWifiInfo").getAsJsonObject()
//                        .get("row").getAsJsonArray();
//
//                Gson gson = new Gson();
//                List<Member> result = new ArrayList<>();
//
//                for (int i = 0; i < itemArr.size(); i++) {
//                    Member wifi = gson.fromJson(itemArr.get(i), Member.class);
//                    result.add(wifi);
//                }
//
////                 데이터베이스 입력
//
//                start += 999;
//                end += 999;
//           }  else {
//                break;
//           }
//
//        }
//    }
//



//    public static void main(String[] args) {
//
//        try {
//            dataGet(buildUrl());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }

}

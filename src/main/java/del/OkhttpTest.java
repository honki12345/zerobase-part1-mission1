package del;

import JDBC.Member;
import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class OkhttpTest {
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
        urlBuilder.append("/" +  URLEncoder.encode("666751717366706736334942437857","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("17500","UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode("17700","UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/

        OkHttpClient client = new OkHttpClient();

        // URL 출력
        System.out.println("URL: " + urlBuilder);

        Request request = new Request.Builder().url(String.valueOf(urlBuilder)).build();


        // 데이터 수신
//        String res = null;
//        try (Response response = client.newCall(request).execute()) {
//            String res = response.body().string();
//        } catch (IOException e) {
//            System.out.println("Error: " + e.getMessage());
//        }



       Response response = client.newCall(request).execute();
       String res = response.body().string();

        if (!res.contains("MESSAGE\":\"해당하는 데이터가 없습니다.")) {

            // json
            JsonElement element = JsonParser.parseString(res);


            JsonArray itemArr = element.getAsJsonObject()
                    .get("TbPublicWifiInfo").getAsJsonObject()
                    .get("row").getAsJsonArray();

            Gson gson = new Gson();
            List<Member> result = new ArrayList<>();
            for (int i = 0; i < itemArr.size(); i++) {
                Member wifi = gson.fromJson(itemArr.get(i), Member.class);
                result.add(wifi);
            }


            // 출력
            for (Member wifi : result) {
//                System.out.println(wifi.X_SWIFI_ADRES1 + ": " + wifi.X_SWIFI_MAIN_NM);
                System.out.println(wifi);
            }

        }





    }

}

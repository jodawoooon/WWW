package com.ssafy.api.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Service
public class KakaoAPI {

    public HashMap<String, Object> getAccessToken(String authorize_code){
        String access_Token = "";
        String refresh_Token = "";

        HashMap<String, Object> Token = new HashMap<>();
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=1e31c0b3e807829e950f0236c26efec6");
            sb.append("&redirect_uri=https://localhost:8080/kakao/callback");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            Token.put("accessToken",access_Token);
            Token.put("refreshToken",refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Token;
    }

    // UserProfile를 받아오는 메소드(userId, email, profileImage, name, phoneNumber)
    public HashMap<String, Object> getUserProfile(String access_Token, String refresh_Token){
        String accessToken = access_Token;
        String refreshToken = refresh_Token;
        String userid="";
        String profileImageUrl = "";
        String name = "";

        // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            // 요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            userid = element.getAsJsonObject().get("id").getAsString();
            profileImageUrl = kakao_account.getAsJsonObject().get("profile").getAsJsonObject().get("profile_image_url").getAsString();
            name = kakao_account.getAsJsonObject().get("profile").getAsJsonObject().get("nickname").getAsString();

            JsonElement email = kakao_account.getAsJsonObject().get("email");

            userInfo.put("accessToken", accessToken);
            userInfo.put("refreshToken", refreshToken);
            userInfo.put("userid", userid);
            userInfo.put("profileImageUrl", profileImageUrl);
            userInfo.put("name", name);
            userInfo.put("phoneNumber", "");
            userInfo.put("birthday", "");

            if(email!=null){
                userInfo.put("email", email.getAsString());
            }else{
                userInfo.put("email" , "");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    // UserInfo를 받아오는 메소드(userId, email)
    public HashMap<String, Object> getUserInfo(String accessToken, String refreshToken){
        String userid="";
        JsonElement email;

        // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            // 요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            userid = element.getAsJsonObject().get("id").getAsString();
            email = kakao_account.getAsJsonObject().get("email");


            userInfo.put("userid", userid);
            if(email!=null){
                userInfo.put("email" , email.getAsString());
            }else{
                userInfo.put("email" , "");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }


    public void Logout(String access_Token) {
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        try {

            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


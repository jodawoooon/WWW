package com.ssafy.api.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Service
public class KakaoAPI {

    @Autowired
    RedisService redisService;

    public HashMap<String, Object> getAccessToken(String authorize_code){
        String access_Token = "";
        String refresh_Token = "";
        String access_Token_expire = "";
        String refresh_Token_expire = "";

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
            //sb.append("&redirect_uri=http://localhost:8080/kakao/callback");
            sb.append("&redirect_uri=https://j5a605.p.ssafy.io/kakao/callback");
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
            access_Token_expire = element.getAsJsonObject().get("expires_in").getAsString();
            refresh_Token_expire = element.getAsJsonObject().get("refresh_token_expires_in").getAsString();

            Token.put("accessToken",access_Token);
            Token.put("refreshToken",refresh_Token);
            Token.put("accessTokenExpire",access_Token_expire);
            Token.put("refreshTokenExpire",refresh_Token_expire);

            System.out.println("Token: " + Token);
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Token;
    }

    // UserInfo를 받아오는 메소드(userId, email)
    public HashMap<String, Object> getUserInfo(String accessToken){
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

            String userId = element.getAsJsonObject().get("id").getAsString();
            String name = kakao_account.getAsJsonObject().get("profile").getAsJsonObject().get("nickname").getAsString();

            userInfo.put("userId", userId);
            userInfo.put("name",name);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    // Token 정보 보기 (토큰 만료 여부)
    public int checkAccessToken(String accessToken, String refreshToken) {
        int responseCode = -1;
        String reqURL = "https://kapi.kakao.com/v1/user/access_token_info";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseCode;
    }


    public HashMap<String, Object> renewAccessToken(String refreshToken){
        String renew_accessToken = "";
        String renew_refreshToken = "";
        String renew_accessToken_expire = "";
        String renew_refreshToken_expire = "";

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
            sb.append("grant_type=refresh_token");
            sb.append("&client_id=1e31c0b3e807829e950f0236c26efec6");
            sb.append("&refresh_token=" + refreshToken);
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

            renew_accessToken = element.getAsJsonObject().get("access_token").getAsString();
            renew_accessToken_expire = element.getAsJsonObject().get("expires_in").getAsString();


            Token.put("accessToken",renew_accessToken);
            Token.put("accessTokenExpire",renew_accessToken_expire);

            if(element.equals("refresh_Token")){
                renew_refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();
                renew_refreshToken_expire = element.getAsJsonObject().get("refresh_token_expires_in").getAsString();
                Token.put("refreshToken",renew_refreshToken);
                Token.put("refreshTokenExpire",renew_refreshToken_expire);
            }

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Token;
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


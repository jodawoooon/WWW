package com.ssafy.common.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoLoginUser {
    private Long id;
    private Properties properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @Setter
    @ToString
    public static class Properties {
        @JsonProperty("nickname")
        private String nickname;

        @JsonProperty("profile_image")
        private String profileImage;

        @JsonProperty("thumbnail_image")
        private String thumbnailImage;
    }

    @Getter
    @Setter
    @ToString
    public static class KakaoAccount {

        // 이메일 정보가 카카오에 존재하는지에 대한 여부
        @JsonProperty("has_email")
        private Boolean hasEmail;

        // 카카오계정의 이메일 유효 여부. 간혹 이메일 실소유자에 의해 카카오계정 이메일이 무효화 되는 경우 값이 false로 내려갑니다.
        @JsonProperty("is_email_valid")
        private Boolean isEmailValid;

        // 인증받은 카카오계정 이메일인지 여부
        @JsonProperty("is_email_verified")
        private Boolean isEmailVerified;

        @JsonProperty("email")
        private String email;

        // 이메일을 얻기 위해 추가 동의가 필요한지에 대한 여부
        @JsonProperty("email_needs_agreement")
        private Boolean emailNeedsAgreement;

        // 성별 정보가 카카오에 존재하는지에 대한 여부
        @JsonProperty("has_gender")
        private Boolean hasGender;

        // 성별을 얻기 위해 추가 동의가 필요한지에 대한 여부
        @JsonProperty("gender_needs_agreement")
        private Boolean genderNeedsAgreement;

        // 사용자 카카오계정의 성별. female/male
        @JsonProperty("gender")
        private String gender;

        // 생일 정보가 카카오에 존재하는지에 대한 여부
        @JsonProperty("has_birthday")
        private Boolean hasBirthday;

        // 생일을 얻기 위해 추가 동의가 필요한지에 대한 여부
        @JsonProperty("birthday_needs_agreement")
        private Boolean birthdayNeedsAgreement;

        //사용자 카카오계정의 생일. MMDD 형식
        @JsonProperty("birthday")
        private String birthday;

        // 연령대를 얻기 위해 추가 동의가 필요한지에 대한 여부
        @JsonProperty("age_range_needs_agreement")
        private Boolean ageRangeNeedsAgreement;

        // 사용자 카카오계정의 연령대. 1~9/10~14/15~19/20~29/30~39 ... 80~89/90~
        @JsonProperty("age_range")
        private String ageRange;
    }
}
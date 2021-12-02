# 🚩 WWW

> 서울 6반 5팀 A605 ✨  
> 조다운 김규일 김지현 박종대 이현건 최혜원  
> 
SSAFY 특화프로젝트 최우수상 🥇

![image](https://user-images.githubusercontent.com/38212743/138669899-a8b7c28e-cb7b-4831-8606-133a2e515895.png)

<br><br>


### [🎞 서비스 소개 - UCC 보러가기](https://youtu.be/rtSYMApmKVI)

### [🕶 직접 실행해보기! 포팅 매뉴얼 보러가기](https://github.com/WWW-2021/WWW/blob/develop/exec/A605_WWW_%ED%8F%AC%ED%8C%85_%EB%A7%A4%EB%89%B4%EC%96%BC.md)

### [🎡시연 시나리오](https://github.com/WWW-2021/WWW/blob/develop/exec/A605_WWW_%EC%8B%9C%EC%97%B0_%EC%8B%9C%EB%82%98%EB%A6%AC%EC%98%A4.md)

<br><br>

<center> 누구든지, 언제, 어디서나
사회적 거리를 유지하고
방역수칙을 준수하면서
실천할 수 있는 </center>

**걷기 운동**

WWW와 함께 걸어보세요!

<br>

### 🗂 Content
[🔈 프로젝트 소개](#-프로젝트-소개)
   <br>
   - [📑 타겟 및 목표](#-타겟-및-목표)
   - [📑 개발 환경](#-개발-환경)
   - [📑 핵심 기능](#-핵심-기능)
   - [📑 기술 스택](#-기술-스택)
   - [📑 서버 아키텍처](#-서버-아키텍처)
   - [📑 ERD Diagram](#-erd-diagram)
   - [📑 와이어프레임](#-와이어프레임)
   - [📑 목업](#-목업)
   - [📑 기능명세서](#-기능명세서)
   - [📑 스토리보드](#-스토리보드)
   - [📑 API 명세서](#-api-명세서)
   - [📑 팀 소개](#-팀-소개)
     <br>
<br><br>

## 💡 프로젝트 소개
### 프로젝트 명 : WWW
혼자 산책하는 당신의 친구가 되어드릴게요!
오늘부터 WWW와 함께 걸어보세요 🏃‍🏃‍


<br>

### 🔔 타겟 및 목표
#### 서비스 타겟 : 
- 혼자 산책하는 사람
- 색다른 산책 코스를 찾고 싶은 사람
- 산책 기록을 정리해서 관리하고 싶은 사람
- 걷기 운동을 재미있게 지속하고 싶은 사람
<br>

#### 서비스 목표 :
걷기 운동을 장려하여 사용자의 건강을 증진하도록 돕는 우리동네 산책 정보 플랫폼

#### 특장점
      빅데이터 활용 서비스 제공
      MapReduce 알고리즘 구현 및 데이터 활용
      실시간 위치(GPS)를 기반으로한 유저 친화적 서비스
      실시간 산책의 거리,속도,시간과 이동 경로를 지도에 Polyline으로 시각화하여 효율적 산책 제공 
      비 로그인 시 비정상적 접근 제어
      데이터 보호를 위한 SSL 프로토콜 적용
      Kakao API와 Redis를 활용한 Auth 구현
      Jenkins 활용한 CI/CD 환경 구축
      
      
<br><br>

### 📌 핵심 기능
![image](https://user-images.githubusercontent.com/38212743/138669965-babc62e5-58ab-4c78-9fc6-54e164281dab.png)


#### 1. 우리동네 산책로 정보 제공
![image](https://user-images.githubusercontent.com/38212743/138672443-e45953e2-07db-485e-b696-3922677f4f19.png)![image](https://user-images.githubusercontent.com/38212743/138672549-27366380-63d3-46d2-b3d5-7763c2c7e9a0.png)

- 지역별 산책로 상세 정보 제공(소요 시간, 거리, 위치 등)
- 자체 리뷰 서비스를 이용한 각 산책로별 평점 정보 제공
- 산책로를 기준으로 주변 가까운 편의시설 정보 제공
- 사용자 선호에 따른 산책로 검색필터 기능 제공

#### 2. 개인 산책 기록 분석 및 관리
![image](https://user-images.githubusercontent.com/38212743/138672699-d2b43c69-817a-4165-a5de-007e10300ad8.png)
![산책기록녹화320](https://user-images.githubusercontent.com/38212743/138670048-26eb219b-d3bc-4b57-aaaf-821cc01b31d3.gif)

- 사용자의 산책을 추적하여 속도, 거리 기록 저장
- 관심 코스 등록 및 조회 기능
- 코스 별 리뷰 기능
- 주, 월별 산책 기록 분석 (누적, 평균 산책 시간, 소모 칼로리 및 이전 기록과 비교 분석)

#### 3. 날씨, 인기 코스, 건강 뉴스, 랭킹 시스템, 등 산책 관련 정보 제공
![image](https://user-images.githubusercontent.com/38212743/138672814-a75f1710-0755-4873-8019-8feb03219d62.png)
<br><br>

### 📌 구현 기능
      - 로그인 
         - 카카오 로그인 및 Redis 서버와 리프레시 토큰을 이용한 액세스 토큰 갱신 구현

      - 산책 기록 및 분석
        - 사용자의 산책을 GPS와 KakaoMap을 이용해 실시간으로 추적하여 속도, 거리, 시간을 측정하고, 경로를 지도에 그린다.
        - 산책 종료 시 기록을 DB에 저장한다    
        - 사용자의 산책 기록을 월/주 단위로 분석하고 이전 기록과 비교한다.
        - 저번주, 저번달의 기록과 이번주, 이번달의 기록을 하루 평균 산책 시간으로 비교해 이모티콘으로 피드백

     - 산책로 정보 제공
        - 공공데이터를 활용하여 GPS 위치 기반으로 산책로 정보를 제공한다
        - 무한 스크롤 적용하여 5개씩 목록 조회
        - 가까운순, 인기순, 코스길이, 시간을 기준으로 산책로를 검색 및 필터링 할 수 있다.
        - 북마크 버튼을 이용해 관심코스를 등록 및 해제할 수 있다.
        - 데이터 크롤링과 hadoop mapreduce를 활용하여 산책로 주변 가까운 편의시설 데이터를 제공한다

    - 최근코스/관심코스
        - 산책을 완료한 코스는 사용자가 직접 별점을 남기는 리뷰를 제출하여 산책로에 대한 정보를 다른 유저들과 공유할 수 있습니다.
        - 별점은 반개씩 선택하여 리뷰를 남길 수 있습니다.
        - 리뷰를 작성한 코스는 코스에 리뷰를 남긴 사용자들의 점수를 평균 내어 나타냅니다.

    - 산책 관련 정보 (메인 페이지)
        - 사용자 위치 기반으로 5일간의 날씨 예보를 제공합니다.
        - 사용자 지역의 인기 코스(있을 경우)를 제공합니다.
         - 유저들의 랭킹을 제공하여 서비스의 재미 요소를 더했습니다.
        - 건강 관련 뉴스를 제공하여 사용자 친화적인 기능을 더했습니다.

![image](https://user-images.githubusercontent.com/38212743/138670324-7fb610aa-4554-46d2-9f1c-2ae6e33c43df.png)

<br><br>

### 🛠 개발 환경

- Server : AWS EC2
  - Ubuntu 20.04.1

<br>

- Backend
  - Java : Java 1.11.0
  - Framework : SpringBoot 2.4.5
  - ORM : JPA(Hibernate)
  - Nginx : 1.18.0 (Ubuntu)
  - IDE : Intellij 2021.1.3 / Visual Studio Code 1.59
  - Dependency tool : gradle-6.7
  - Database : MariaDB-10.3.31

<br>

- Frontend
  - HTML5, CSS3, Javascript(Es6)
  - Vue : 2.6.11



<br><br>


### 🔨 기술 스택
![image](https://user-images.githubusercontent.com/38212743/138670145-3a16eedd-c4de-435a-8b48-f549bb72a73b.png)
<br><br>



### 📕 서버 아키텍처
![image](https://user-images.githubusercontent.com/38212743/138670219-c12fd860-b682-43fe-9f9f-b616bdda3a7e.png)
<br><br>


<br><br>

### 💎 기능명세서
https://docs.google.com/spreadsheets/u/1/d/19Pb-d5bscMnr2dc-7nmCO7X8Nj2EPDKWBpAYYuhqqzU/edit#gid=0

<br><br>

### 📖 와이어프레임
https://www.figma.com/file/A3bdz10b9lIgVysqhM6w07/%EC%82%B0%EC%B1%85?node-id=31%3A524


<br><br>

### 📌 ERD Diagram
![image](https://user-images.githubusercontent.com/38212743/138670441-09648ecd-9878-4918-9406-139ee43a320f.png)
<br><br>


### 🎨 목업
https://www.figma.com/file/A3bdz10b9lIgVysqhM6w07/%EC%82%B0%EC%B1%85?node-id=31%3A524
<br><br>

### 🎬 스토리보드
https://www.figma.com/file/A3bdz10b9lIgVysqhM6w07/%EC%82%B0%EC%B1%85?node-id=112%3A445
<br><br>

### 📬 API 명세서
https://www.notion.so/API-27908e2c72a944fbaf8a377399b34859
<br><br>

### 📢 팀 소개
![image](https://user-images.githubusercontent.com/38212743/138670367-5334a45d-f083-49d3-8601-28936bea09dc.png)
[🔗 노션 링크](https://www.notion.so/A605-5da3fd96c78a4c5580bbbb800a4c41bc)

<br><br>

### 테스트 계정 정보
    ID : doglivetest@kakao.com
    PW : ssafy123

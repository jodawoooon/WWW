# Porting Manual

> SSAFY 5기 특화프로젝트 서울 6반 5팀 WWW

조다운 김규일 김지현 박종대 이현건 최혜원

# 📖  목차

[1️⃣ 개발 환경 세팅](#🛠-개발-환경-세팅)

[2️⃣ 계정 정보](#🔒-계정-정보)

[3️⃣ 프로퍼티 정보](#🔑-프로퍼티-정보)

[4️⃣ 빌드 및 배포 방법](#📢-빌드-및-배포-방법)

[5️⃣ 외부 TOKEN/KEY](#📡-외부-TOKEN/KEY)



# 🛠 개발 환경 세팅

- Server : AWS EC2
  - Ubuntu 20.04.1
  - Docker 20.10.8
- Backend
  - Java : Java 1.11.0
  - Framework : SpringBoot 2.4.5
  - ORM : JPA(Hibernate)
  - Nginx : 1.18.0 (Ubuntu)
  - IDE : Intellij 2021.1.3 / Visual Studio Code 1.59
  - Dependency tool : gradle-6.7
  - Database : MariaDB-10.3.31
- Frontend
  - HTML5, CSS3, Javascript(Es6)
  - Vue : 2.6.11

# 🔒 계정 정보

### 📲 DB 접속 계정 정보

- ID : www
- PW : walk123

### 📲 Jenkins 접속 계정 정보

- ID : ssafy
- PW : 1234
- Jenkins URL
  - [http://j5a605.p.ssafy.io:8081](http://j5a605.p.ssafy.io:8081)

# 🔑 프로퍼티 정보

```bash
backend
└── src
		└──main
			└──resources
				├── application.yml
				└── application-db.yml
```

- **application.yaml** (프로젝트 셋팅, 배포 셋팅)
- **application-db.yaml** (DB 셋팅 정보)

# 📢 빌드 및 배포 방법

## 1. 로컬 실행 가이드

### 	1. Java 11 설치

```
sudo apt-get install openjdk-11-jdk
```



### 2. Project Git Clone

```
git clone https://lab.ssafy.com/s05-bigdata-dist/S05P21A605.git
```



### 3. Build

```
// backend root directory
./gradlew build
```



### 4. 실행

```
nohup java -jar build/libs/*.jar --server.port=XXXX >> server.log 2>&1&
// localhost:XXXX
```



### 5. 종료

```
kill -9 $(lsof -t -i:[port_number])
```



## 2. EC2 배포 가이드

### 1. Nginx 설치

```
sudo apt-get update
sudo apt-get upgrade
sudo apt-get install nginx
```



### 2. letsencrypt 설치

```
sudo apt-get update -y & sudo apt-get install letsencrypt -y
```

### 3. 인증서 발급

```
// fullchain.pem/privkey.pem file path 확인 필요
sudo letsencrypt certonly --standalone -d [your_domain_name]

```

### 4. Nginx 설정

```
cd /etc/nginx/sites-availables/
sudo vi default
```

![image-20211005143801206](https://user-images.githubusercontent.com/19733033/135974554-acd580f5-8825-4452-b0ef-0c5c42a8c99f.png)

![image-20211005143959957](https://user-images.githubusercontent.com/19733033/135974580-5b196f89-c1b6-47a8-ae7c-d75f7019601e.png)

- http(80)은 https(443)으로 redirect
- https(443)은 localhost 로 reverse proxy



### 5. Nginx 실행

```
sudo servicectl nginx start
```



# 📡 외부 TOKEN/KEY

## 1. Kakao Map API

- 

## 2. ???

- 
# 취뽀 - GAN 모델 기반 취업 사진 생성 어플리케이션

## 서비스 소개
* 주제 : 비싼 취업 사진은 그만! 언제 어디서나 무료 취업 사진을 찍을 수 있도록 딥러닝 기술을 활영한 취업 사진 생성 어플리케이션
* 핵심 기능 :
    * 취업 사진 느낌의 호감형 분위기 합성
    * 사용자가 선택한 헤어스타일, 정장 그리고 배경을 자연스럽게 합성
    * 얼굴 잡티 제거, 사진 왜곡, 색조 메이크업 등 사용자가 원하는 방향으로 직접 보정 가능
    
* 팀장 : 
   * 강지연 / PM, 회원 인증 및 취업공고 서버개발, 보정 기능 개발, 데이터 수집 및 모델학습 
* 팀원 : 
   * 김서영 / 취업공고 서버 개발, 안드로이드 개발, 데이터 수집 및 모델학습 </br>
   * 김서현 / 안드로이드 개발, 보정 기능 개발, 데이터 수집 및 모델학습 

<img width="300" height="500" src="./resources/메인페이지.png"/> <img width="300" height="500" src="./resources/마이페이지1.png"/>

<img width="300" height="500" src="./resources/가이드라인1.png"/> <img width="300" height="500" src="./resources/가이드라인2.png"/>

<img width="300" height="500" src="./resources/합성전.png"/> <img width="300" height="500" src="./resources/합성결과_긴머리.png"/>

<img width="300" height="500" src="./resources/배경제거.png"/> <img width="300" height="500" src="./resources/배경합성결과.png"/>

## 서비스 구성도
![Chuibbo 구성도](https://user-images.githubusercontent.com/43838022/158013757-f4e6a703-7044-46b5-ba21-8714a43f05f2.png)

## ERD
<img src="./resources/chuibbo-db.png"/>

## Spring boot API
![Chuibbo Swagger UI](https://user-images.githubusercontent.com/43838022/158013281-63e5602d-d3f3-4596-8f7c-f68540e3297a.png)

## Flask API 1
![Chuibbo-Flask Swagger UI](https://user-images.githubusercontent.com/43838022/158013270-834dca89-b5fe-4c43-92cf-ec5adc5154bb.png)

## Flask API 2
![Chuibbo-Flask-Rembg Swagger UI](https://user-images.githubusercontent.com/43838022/158013279-f74efdf4-bd66-450f-8ba3-e3c380f759a6.png)

## 모델 test 결과
<img src="./resources/test.png"/>

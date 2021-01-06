프로젝트 명 : 맛(MAT)
   Project name MAT

=============

이 프로젝트는 2020년 진행한 한이음 프로젝트로 2020/5/11일부터 현재까지 개발중에 있습니다.
   프로젝트 멘티 : 김종은, 이예린, 김채연
   프로젝트 멘토 : 임성현


This is an HANIUM Project
   Start 2020/5/11 ~ keep developing
   Project Mentee : Jongeun Kim, Yerin Lee, Chaeyeon Kim
   Project Mentor : Seonghyun Lim


-------------
# Negative Menu Recommendation System (NMRS)

## Overview
|데이터 수집|모델링|UI/UX|
|:------:|:------------------:|:------------:|
|사용자 평점|네거티브 알고리즘 적용|수동으로 별점 입력하는 기능|
|메뉴별 해시태그|메뉴 간 상관분석 및 비선호 메뉴 제거|해당 메뉴의 유사 메뉴 출력|

## Dataset
- 각 섹터(치킨, 피자) 별 대표 프렌차이즈 5개 선정 후 전체 메뉴 데이터 수집
- 전체 메뉴 대상 인스타그램 해시태그(#) 상위 100개 게시물 크롤링
- 각 메뉴에 대한 유저 평점 데이터 셋 수집(랜덤 값)

## Modeling
- 유저에게 최근 소비한 메뉴명을 Input으로 받는다.
- 해당 메뉴 정보의 해시태그를 바탕으로 차원축소 방법인
- 특이값분해(Singular Value Decomposition, SVD)를 사용한 평점 및 비선호 메뉴 예측


# Introduction
프로젝트 소개: 네거티브 추천 알고리즘을 바탕으로 한 메뉴 리뷰 애플리케이션

제안배경 : 내가 오늘 뭘 먹고 싶은지 망설일 때가 많다.
그럴 때에는 내가 싫어하는 것을 빼고 추천하는 네거티브 추천을 통해
내가 모르는 음식을 추천받아보자.
그리고 하나만 선택한 뒤에는 이와 어울리는 조합을 SNS를 통해 추천을 받아보자.
어제와 오늘이 다른 내 까다로운 입맛을 맞춰줄 수 있지 않을까?

## Class Introduction
-  LoginActivity : 로그인을 담당하는 클래스로, 로그인 성공하면 MainActivity로 이동함.
-  RegisterActivity : 회원가입을 담당하는 클래스,로 회원정보를 데비터베이스에 저장함.
-  MainActivity : 앱의 메인 화면으로 음식의 카테고리와 추천음식을 보여줌.
-  MyPageActivity : 마이페이지를 보여주는 클래스로, 그동안 썼던 리뷰들을 볼 수 있음.
-  TotalMapActivity : 자신이 리뷰를 썼던 가게들의 위치를 지도위에 마크로 표시하여 보여줌
-  ~Request : 웹호스팅을 통하여 데이터베이스에 원하는 정보 접근을 할 수 있도록 도와주는 요청 클래스
-  ~CursorAdapter: 커스텀 커서 어댑터로 리스트뷰에 데이터를 넣음
-  BrandViewActivity: 브랜드 목록을 불러옴
-  MenuListActivity: 메뉴목록을 불러옴
-  MenuViewActivity: 하나의 메뉴창을 불러옴

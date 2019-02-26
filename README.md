![2](https://user-images.githubusercontent.com/20393064/53322463-d3febd00-391e-11e9-9b36-d5975eeaceb9.png)

# JMT 
## 프로젝트 개요
음식점 사장님들이 직접 업로드한 메뉴 사진과 정보를 스와이프 매칭을 통해 이구역의 JMT를 찾아보세요!

### 사장님
> 가게 정보를 등록하고 자신있는 음식 사진을 올려보세요! 소비자들은 가까운 거리에 있는 등록된 음식 사진들을 랜덤으로 수신할 수 있습니다.

### 손님
> 주위에 있는 메뉴들을 랜덤으로 받아보며 마음에 드는 메뉴가 나올때까지 스와이프 해보세요! 마음에 드는 음식을 선택하면 해당 음식점에 대한 정보를 얻을 수 있습니다.

## 프로젝트 설명
- 기획의도
> 평소 친구들과 메뉴 고르기 전에 고민과 상의를 하는 경우가 많은데, 먹고 싶은 
> 메뉴를 바로 떠올려 내는 것이 어려웠습니다. 메뉴 선택 고민도 해결하고 싶었고, 먹고 
> 싶은 메뉴가 나올때까지 음식 카드를 넘기는 재미도 느끼고 싶었습니다.

- 주요 기능
> 음식점 사장님들이 음식점 위치를 등록 후, 직접 메뉴 촬영하여 업로드
> 손님은 특정 위치를 선택하여, 선택 위치 주변의 메뉴들을 수신
> 수신된 메뉴들은 랜덤으로 섞이게 되고, 카드 형태로 화면에 노출
> 메뉴 카드는 화면에 1장씩 노출되며, 드래그를 하면 다음 메뉴 카드로 이동되는 방식
> 카드를 넘기다가 특정 메뉴가 마음에 들면 해당 카드를 선택
> 카드가 선택되면 해당 메뉴가 존재하는 음식점의 상세 정보를 화면에 노출

- 기존에 존재하는 비슷한 서비스와 차별되는 점
> 기존에 존재하는 랜덤 메뉴 추천 앱들은 메뉴 이름을 뽑아주는 방식(ex 제육볶음, 비빔밥)
> 혹은 특정 식당에 존재하는 메뉴 정보들을 랜덤으로 뽑아주는 방식(ex, 학교 학식 메뉴 랜덤 추천)


## 팀 구성원
- [윤찬혁](https://github.com/ch-Yoon)
- [조재훈](https://github.com/nusurprise)
- [박다예](https://github.com/parkdaye)

## 프로젝트 문서
#### 디자인
- adobe xd를 활용해 프로젝트의 디자인을 만들었습니다.
- [디자인 문서 보기](https://xd.adobe.com/view/835826a9-8dc7-4de5-67a7-056c77cad0ab-f569/?fullscreen&hints=off)
#### 기능 명세서
- 기능의 우선순위와 사용기술, 상세설명이 적혀있는 문서입니다.
- [기능 명세서 보기](https://docs.google.com/spreadsheets/d/1gO3unq7qZ-FccYGy5GK9pq1_KbzYy0tBdkdrn0i3Fq4/edit#gid=0)
#### 프로젝트 일정
- 개인별 프로젝트 일정이 있는 문서입니다.
- [프로젝트 일정 보기](https://docs.google.com/spreadsheets/d/1gO3unq7qZ-FccYGy5GK9pq1_KbzYy0tBdkdrn0i3Fq4/edit#gid=973287441)
#### DB 설계
- 데이터베이스 테이블 설계 문서입니다.
- [DB 설계서 보기](https://docs.google.com/spreadsheets/d/1gO3unq7qZ-FccYGy5GK9pq1_KbzYy0tBdkdrn0i3Fq4/edit#gid=240237984)
#### 서버 API 문서
- swagger를 활용하여 api 문서를 만들었습니다.
- [서버 API 문서 보기](http://ec2-54-180-49-154.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui.html#/)
#### 코딩 컨벤션
- 팀 내에서 함께 정의하고 사용한 코딩 컨벤션입니다.
- [코딩 컨벤션 문서 보기](https://github.com/ch-Yoon/boostcampts-menu_random_choice_style_guide)

## 구현 기능
### 상점정보 가져오기 및 편집
![image](https://user-images.githubusercontent.com/20393064/53328605-b2590200-392d-11e9-91fa-d6df5f85b8dc.png)
- 상점정보를 가져오고 해당 상점정보를 편집할 수 있는 기능 
- 주요 사용기술 : 디스크캐싱

### 소비자 맵뷰 및 검색기능
![image](https://user-images.githubusercontent.com/20393064/53328033-6d809b80-392c-11e9-9473-de329992ec4e.png)
- 반경 및 카테고리 선택 옵션에 따라 주위의 메뉴정보들을 가져옴
- 주요 사용기술 : kakao map api, kakao search api

### 스와이프 기능
[![structure](https://user-images.githubusercontent.com/20294749/53284785-50b55e00-379c-11e9-9c0a-f030fd2f2caa.png)](https://user-images.githubusercontent.com/20294749/53284785-50b55e00-379c-11e9-9c0a-f030fd2f2caa.png)
- 사용자가 자신의 마음에 드는 메뉴가 나올 때까지 메뉴를 드래그하여 스와이프 
- 해당 기능은 오픈소스로 등록 : [오픈소스 문서 보기](https://github.com/ch-Yoon/Overlap_Loop_View#hello-welcome-to-overlaploopview)

## 사용 기술 및 라이브러리
- Kakao Map Api
- Kakao Search Api
- Kakao OAuth API
- Retrofit
- okHttp
- Glide
- Recyclerview
- SQLiteDatabase
- ted permission
- Ucrop
- crashlytics
- Spring Boot
- mySql

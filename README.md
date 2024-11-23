## 호주 집 렌트 사기 방지를 위한 서비스
-----  



### 목차


-----  

### 프로젝트 소개


----
### 주요 기능

- 집 게시글 조회, 등록 기능
- 포인트 충전, 출금 기능
- **안전 거래 기능**

----
### 서비스 화면

----

### 모듈 역할

- fl-admin : 서비스 관리자가 사용할 요청을 처리하는 모듈
- fl-api : 사용자 요청을 처리하는 모듈
- fl-batch : spring batch 처리를 위한 모듈
- fl-chatting : 웹 소켓으로 구현한 채팅 기능을 관리하는 모듈
- fl-common : 프로젝트 전역으로 사용할 수 있는 Utils 성 기능을 관리하는 모듈
- fl-core : Domain, Repository 를 관리하는 모듈 
- fl-service : Service 계층, 비지니스 로직을 처리하는 모듈
- fl-redis : Redis 캐싱 관련 기능을 구현한 모듈

----



<div align="center">

<!-- logo -->
### 호주 집 렌트 사기 방지를 위한 서비스

</div> 

## 📝 프로젝트 소개


```
호주에서 집 렌트시 발생하는 계약금 사기 빈도가 점진적으로 증가하고 있다. 이는 기존에 존재하는 "FlateMate", "호주나라"와 같은
서비스가 임대인, 임차인을 매칭해주는 역할까지만 수행하기 때문에 발생하는 문제로 보여진다.

계약금을 관리해주는 중계인 역할인 "안전 거래" 기능을 도입한다면 위와 같은 문제를 해결할 것으로 보인다. "안전 거래" 는 실제 계약이 성사된 후 계약금을
임대인에게 송금하는 기능으로써의 역할을 담당한다.

"안전 거래" 기능을 해당 서비스에서 중점적으로 구현하며 이전보다 안전한 집 렌트 문화를 조성하는 것을 최종 목표로 귀결되기 위해 시작한 프로젝트입니다.
```



<br />

> 화면 구성과 프로토 타입 중 원하는 것을 사용해주세요.

### 화면 구성
|Screen #1|Screen #2|
|:---:|:---:|
|<img src="https://user-images.githubusercontent.com/80824750/208456048-acbf44a8-cd71-4132-b35a-500047adbe1c.gif" width="400"/>|<img src="https://user-images.githubusercontent.com/80824750/208456234-fb5fe434-aa65-4d7a-b955-89098d5bbe0b.gif" width="400"/>|

### 프로토타입
<img src="https://user-images.githubusercontent.com/80824750/208454673-0449e49c-57c6-4a6b-86cf-66c5b1e623dc.png">

<br />

## 🗂️ APIs
작성한 API는 아래에서 확인할 수 있습니다.

👉🏻 [API 바로보기](/backend/APIs.md)


<br />

## ⚙ 기술 스택
> skills 폴더에 있는 아이콘을 이용할 수 있습니다.
### Back-end
서버 개발 :


### Infra
<div>
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/AWSEC2.png?raw=true" width="80">
</div>

### Tools
<div>
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Github.png?raw=true" width="80">
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Notion.png?raw=true" width="80">
</div>

<br />

## 🛠️ 프로젝트 아키텍쳐
![no-image](https://user-images.githubusercontent.com/80824750/208294567-738dd273-e137-4bbf-8307-aff64258fe03.png)






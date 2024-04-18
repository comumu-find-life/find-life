## 호주 워킹홀리데이를 위한 집, 직업 서칭 서비스



-----  

`호주 워킹홀리데이를 준비 혹은 참여중인 사람들에게 집과 직업을 쉽게 구할 수 있는 서비스`

`보증금 사기와 같은 범죄가 빈번히 발생하고 있어, 이 문제를 해결하고자 예방할 수 있는 가이드를 제공하고 인증된 집 과 직업 게시물이 올라올 수 있게 관리합니다.`


----

### 프로젝트 구조

- fl-api : 사용자 요청을 처리하는 모듈
- fl-authority : spring security, oauth 등 접근 보안, 권한을 관리하는 모듈
- fl-batch : spring batch 처리를 위한 모듈
- fl-chatting : 웹 소켓으로 구현한 채팅 기능을 관리하는 모듈
- fl-common : 프로젝트 전역으로 사용할 수 있는 Utils 성 기능을 관리하는 모듈
- fl-core : Domain, Repository 를 관리하는 모듈 
- fl-service : Service 계층, 비지니스 로직을 처리하는 모듈

**주의 할점 :** 
- fl-core 모듈 내부에서 기능을 구현할때 너무 많은 역할을 담당 x 서비스가 커질 수록 core 안에서 비니지스 로직이 흐를 수 있음
- dto 변환은 fl-service 모듈 내부에서 처리

----

### 프로젝트 버전

```
- Spring : 3.2.2
- Java : 17
- DataBase : MySql 8.0.27, Redis
```

----

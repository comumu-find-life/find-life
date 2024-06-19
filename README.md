## 호주 워킹홀리데이를 위한 집, 직업 서칭 서비스



-----  

`호주 워킹홀리데이를 준비 혹은 참여중인 사람들에게 집을 쉽게 구할 수 있는 서비스`

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
- fl-redis : Redis 캐싱 관련 기능을 구현한 모듈

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
## 논의 방안

- 예외 처리 관련 Exception 을 어디서 관리할건가 ?
- DTO 는 common 모듈에서 관리?
- DTO 네이밍 (요청은 xxxRequest, 응답은 xxxResponse)
- 테스트코드 작업
- Entity 필드 제한 걸기

----

## 기능 명세서

### User 관련 기능
- [ ] 로그아웃 기능
- [x] 로그인 기능
  - [x] 이메일 로그인
- [ ] 프로필 수정
- [ ] 프로필 추가 정보 등록 (국적, 프로필사진, 나이)
- [x] 회원가입 기능
  - [ ] 이메일 인증 
- [ ] 사용자 단일 조회 (pk 로 조회)
  - [ ] 등록한 집 게시글 개수 포함
  - [ ] 성사된 안전거래 횟수 포함

### Home 관련 기능
- [x] 집 게시글 등록 기능 
  - [x] 입력받은 집 주소 정보가 유효한 정보인지 검증하는 기능 
- [ ] 집 게시글 sold out 기능 (안전거래 or 직접 제어)
- [x] 집 게시글 삭제 기능
- [ ] 집 게시글 수정 기능
- [x] 집 게시글 단일 조회
- [ ] 사용자 Idx 로 등록한 모든 집 게시글 조회
- [ ] 집 게시글 관심목록 조회 (id 가 없으면 예외 반환하는게 아닌 빈 리스트로 반환)
- [x] 집 게시글 전체 조회 (Google Map 에서 사용)
- [ ] 집 게시글 페이징 조회 (필터없이)
- [x] 집 게시글 도시 이름으로 필터링 조회

### ProtectedDeal 관련 기능
- [ ] 안전 거래 생성 기능
  - [ ] 집주인 계좌 입력 받기(필수)
- [ ] 안전 거래 조회 기능
- [ ] 안전 거래 상태 변경 기능
  - [ ] 안전 거래 최종 성사시 보증금/계약금 집 주인에게 전송
  - [ ] 안전 거래 취소시 보증금/계약금은 세입자에게 전송

### Chatting 관련 기능
- [ ] 채팅 시작 기능 
- [ ] 채팅방 목록 조회 기능
- [ ] 채팅 내용 조회 기능
- [ ] 채팅 전송 기능
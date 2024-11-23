
<div align="center">

<!-- logo -->
## 호주 집 렌트 사기 방지를 위한 서비스 (개인 프로젝트)

</div> 

### 📝 프로젝트 소개


```
호주에서 집 렌트시 발생하는 계약금 사기 빈도가 점진적으로 증가하고 있다. 이는 기존에 존재하는 "FlateMate", "호주나라"와 같은
서비스가 임대인, 임차인을 매칭해주는 역할까지만 수행하기 때문에 발생하는 문제로 보여집니다.

계약금을 관리해주는 중계인 역할인 "안전 거래" 기능을 도입한다면 위와 같은 문제를 해결할 수 있다 판단했습니다. "안전 거래" 는 실제 계약이 성사된 후 계약금을
임대인에게 송금하는 기능으로써의 역할을 담당합니다.

"안전 거래" 기능을 해당 서비스에서 중점적으로 구현하며, 이전보다 안전한 집 렌트 문화를 조성하는 것을 최종 목표로 귀결되기 위해 시작한 프로젝트입니다.
```

<br />

### ⚙ 기술 스택

**Backend** : `Spring Boot`, "JPA", "QueryDSL"  
**Database** : `MySQL`, `MongoDB`  
**Frontend** : `Flutter`  


### 기능 소개


<details>
  <summary><b>📍 집 게시글 조회 기능 - 전체 보기</b></summary>
  
|메인화면|집 리스트|맵 클러스터|필터링|주소 검색|
|:---:|:---:|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/93780742-9e11-4839-b258-6165eb578643" width="200"/>|<img src="https://github.com/user-attachments/assets/1bf1eca2-1cfa-45b3-b03d-5bc28be3fd39" width="200"/>|<img src="https://github.com/user-attachments/assets/d84937b8-fea5-48dd-8abf-37c0ab1c490c" width="200"/>|<img src="https://github.com/user-attachments/assets/b58e4d1b-7c9a-47a2-a41c-8b9ba64ac89f" width="200"/>|<img src="https://github.com/user-attachments/assets/01bb6b2b-4f2a-49b8-935c-7cfb34cdec7b" width="200"/>


|상세 화면1|상세 화면2| 대화 전송|
|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/bdb029f3-5edd-4314-a781-d640f49d6ce0" width="200"/>|<img src="https://github.com/user-attachments/assets/a9a2cb03-8075-4ca8-86b6-9e8bed7fa595" width="200"/>|<img src="https://github.com/user-attachments/assets/9ece2fab-65b9-499c-a156-0f39ac28723a" width="200"/>

**기능 설명**
```
메인화면 - 호주의 대도시를 리스트로 만들어 클릭시 해당 지역의 게시물을 조회한다.
집 리스트 - 게시된 집 게시물을 조회한다.
맵 클러스트 - Google Map 을 연동해 Cluster 기능을 구현했다. 줌인,아웃으로 집 조회가 가능한다.
필터링 - 조회하고자 하는 집 정보를 필터링한다.
주소 검색 - Google Map API 를 연동해 글자 이벤트가 발생하는 즉시 주소를 조회한다.
상세 화면1 - 집 게시물의 상세 정보를 조회한다.
상세 화면2 - 집 게시물의 상세 정보를 조회한다.
대화 전송 - 하단의 Send Message 버튼을 눌러 임대인과의 대화를 시작한다.
```

</details>

<details>
  <summary><b>📍 집 게시글 등록 기능 - 전체 보기</b></summary>

|등록 시작|이미지 등록|주소 등록|주소 검증|가격 등록|
|:---:|:---:|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/100f996c-0680-4463-b97a-441787d8a94d" width="200"/>|<img src="https://github.com/user-attachments/assets/b187d7bb-f4b9-4469-887b-090a8f2b7ebc" width="200"/>|<img src="https://github.com/user-attachments/assets/c9f2eaa3-05fa-47b1-a31b-9d777c8ac923" width="200"/>|<img src="https://github.com/user-attachments/assets/319d9bbe-9991-49b4-bbe6-52a3c9344de6" width="200"/>|<img src="https://github.com/user-attachments/assets/1d21236a-d7d1-4b5e-ac92-5b92cedd97c2" width="200"/>|

|상세정보 등록1|상세정보 등록2|등록 완료|
|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/e81e36f9-8eab-4ece-a034-303281f2e0d2" width="200"/>|<img src="https://github.com/user-attachments/assets/1daaba46-a649-42e4-b584-1ca79b16f69d" width="200"/>|<img src="https://github.com/user-attachments/assets/bac0af26-cfb8-46f7-a2f3-9885be5f9a6a" width="200"/>|


**기능 설명**
```
등록 시작 - 집 게시물 등록을 시작한다.
이미지 등록 - 집 사진을 등록한다. (최소 한장, 최대 10장)
주소 등록 - 호주 주소 양식에 맞춰 주소를 등록한다.
주소 검증 - 입력한 주소가 맞는지 검증한다.
가격 등록 - 렌트비, 보증금, 공과금을 입력한다.
상세 정보 등록 1 - 집의 상세 정보를 등록한다. (ex. 집 형태, 성별, 침대, 화장실, 인원)
상세 정보 등록 2 - 집의 상세 정보를 등록한다. (ex. 주차 여부, 옵션)
등록 완료 - 등록 완료를 알린다.
```

</details>


<details>
  <summary><b>📍 안전 거래 동작 과정 - 전체 보기</b></summary>
  <img src="https://github.com/user-attachments/assets/47d42ac7-def0-4627-ac2d-3e2206f5dd3a" width="600" height="800"/>
</details>

<details>
  <summary><b>📍 안전 거래 화면 - 전체 보기</b></summary>
  
|거래생성1|거래생성2|거래생성3|거래생성4|
|:---:|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/cfa2b75b-448f-4d30-8d4b-d99f4216cdf3" width="200"/>|<img src="https://github.com/user-attachments/assets/e9a48f22-1c4b-440d-8a3f-c30d7a928858" width="200"/>|<img src="https://github.com/user-attachments/assets/26da86c9-3cce-4732-b7c8-70d5597a04d7" width="200"/>|<img src="https://github.com/user-attachments/assets/1a2f78eb-fc00-4185-b24c-0c55dc341049" width="200"/>|

|거래수락1|거래수락2|거래수락3|
|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/75b6f83a-bec0-45f9-9822-ce2f68acd622" width="200"/>|<img src="https://github.com/user-attachments/assets/c9af18b7-8606-4b82-8fbf-75e45782ed4d" width="200"/>|<img src="https://github.com/user-attachments/assets/093afd8c-7ae9-4940-ae52-7044fcffa7ed" width="200"/>|

|거래 완료1|거래 완료2|거래 완료3|거래 완료4|거래 목록|
|:---:|:---:|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/e6d54231-827f-4edd-bc15-1e73701eca4b" width="200"/>|<img src="https://github.com/user-attachments/assets/1f51c1ae-a0cb-4185-9204-32770e59079d" width="200"/>|<img src="https://github.com/user-attachments/assets/efe08ef8-09be-4b74-b419-01d0f84a56eb" width="200"/>|<img src="https://github.com/user-attachments/assets/889b72fb-73bd-451e-8e85-c3dd5f115b98" width="200"/>|<img src="https://github.com/user-attachments/assets/c11676b9-016e-4e71-b2d9-d5f4bed6dea9" width="200"/>|

**기능 설명**
```
[안전 거래 생성]
  거래 생성1 - 임대인이 "Deal" 버튼을 눌러 안전거래를 생성한다.
  거래 생성2 - 임대인이 "계약금"을 입력한다.
  거래 생성3 - 안전거래 정보를 확인 후 생성한다.
  거래 생성4 - 안전거래 생성이 완료되면 채팅 화면 위젯으로 나타난다.
```
```
[거래 수락]
  거래 수락1 - 임차인은 생성된 안전 거래를 조회한다.
  거래 수락2 - 임차인은 거래 정보를 확인 후 수락한다.(임차인 포인트 차감)
  거래 수락3 - 임차인이 거래를 수락하면 채팅 화면 위젯으로 나타난다.
```
```
[거래 완료]
  거래 완료1 - 진행중인 거래 위젯을 클릭한다.
  거래 완료2 - 임차인과 임대인이 실제로 만나 거래가 성사되면, 임차인은 거래 완료 버튼을 누른다.(임대인 포인트 증가)
  거래 완료3 - 완료된 거래 내역을 조회한다.
  거래 완료4 - 거래 완료 위젯이 채팅 화면으로 나타난다.
  거래 목록 - 마이페이지에서 완료된 거래 정보를 조회한다.
```
</details>


<details>
  <summary><b>📍 포인트 충전 기능 - 전체 보기</b></summary>
  
|계좌 등록|포인트 충전|입출금 내역|
|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/f9e06a13-d5d3-4589-a194-9ac39b195d6b" width="200"/>|<img src="https://github.com/user-attachments/assets/0889bd2a-2e34-43af-89b9-4fa34bcc691e" width="200"/>|<img src="https://github.com/user-attachments/assets/bf230286-092f-42b4-9844-b65540d94052" width="200"/>|

```
[거래 완료]
  계좌 등록 - 계좌 정보가 등록한다.
  포인트 충전 - 포인트를 충전한다. (Paypal or 계좌이제)
  입출금 내역 - 포인트 입출금 내역을 조회한다.
```

</details>


<br />

### 🗂️ ERD

<br />

### 🛠️ 프로젝트 아키텍쳐
![no-image](https://user-images.githubusercontent.com/80824750/208294567-738dd273-e137-4bbf-8307-aff64258fe03.png)

<br />

### 📹 시연 영상
![no-image](https://user-images.githubusercontent.com/80824750/208294567-738dd273-e137-4bbf-8307-aff64258fe03.png)






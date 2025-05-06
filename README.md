# ⏰ TicToc - Server

## 🖥️ 프로젝트 소개
당신의 시간에 가치를 매기다, 시간 거래 경매 플랫폼입니다.
<div style="display: flex; flex-wrap: wrap; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/339cb265-d614-4496-bb6f-31a263892259" width="80%">
</div>

## 🕰️ 개발 기간
* 25.01.11 - 진행 중

## ⚙️ 개발 환경
- **Java** : 21
- **IDE** : IntelliJ IDEA
- **Framework** : Springboot(3.3.7)
- **Database** : MySQL
- **ORM** : Hibernate (Spring Data JPA 사용)

## 🧑‍🤝‍🧑 멤버 구성
<p>
    <a href="https://github.com/M-ung">
      <img src="https://avatars.githubusercontent.com/u/126846468?v=4" width="100">
    </a>
</p>

## 🟢 ERD
<div style="display: flex; flex-wrap: wrap; gap: 10px;">
    <img src="https://github.com/user-attachments/assets/dae658ad-8106-4882-aa18-fde1e0b55361" width="80%">
</div>

## 🛠️ 시스템 아키텍처
<div style="display: flex; flex-wrap: wrap; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/b701ea05-8de5-40bc-b6ab-4bfbd0f59a8f" width="80%">
</div>

## 🛠️ 소프트웨어 아키텍처
<div style="display: flex; flex-wrap: wrap; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/4905b31c-43ec-4b8e-ada3-45309a00dca2" width="80%">
</div>

## 🔥 TroubleShooting
- 📍 돈이 걸린 문제‼️ 1명만 입찰 성공 ✅, 4999명 입찰 실패 ❌
  - https://velog.io/@_mung/TroubleShooting-TicToc-%EC%8B%9C%EA%B0%84%EC%9D%B4-%EC%83%9D%EB%AA%85%EC%9D%B4%EB%8B%A4-%EC%9E%85%EC%B0%B0-%EB%88%84%EA%B0%80-%EB%A8%BC%EC%A0%80-%ED%96%88%EC%9D%84%EA%B9%8C
- 📍 장기 프로젝트 이대로.. 괜찮을까..❓💦💦
  -  https://velog.io/@_mung/TroubleShooting-TicToc-%EC%9E%A5%EA%B8%B0-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%9D%B4%EB%8C%80%EB%A1%9C..-%EA%B4%9C%EC%B0%AE%EC%9D%84%EA%B9%8C
- 📍 Service 계층 이거.. 너무 무거운데..❓ 🏋🏋
  - https://velog.io/@_mung/TroubleShooting-TicToc-%EB%B9%84%EC%A7%80%EB%8B%88%EC%8A%A4-%EB%A1%9C%EC%A7%81-%EB%84%88%EB%AC%B4-%EB%AC%B4%EA%B1%B0%EC%9A%B4%EB%8D%B0-kds50uem
- 📍 경매 관리 중에 입찰이⁉️ 동시성 충돌을 막아라‼️‼️
  - https://velog.io/@_mung/TroubleShooting-TicToc-%EA%B2%BD%EB%A7%A4-%EA%B4%80%EB%A6%AC-%EC%A4%91%EC%97%90-%EC%9E%85%EC%B0%B0%EC%9D%B4-%EB%8F%99%EC%8B%9C%EC%84%B1-%EC%B6%A9%EB%8F%8C%EC%9D%84-%EB%A7%89%EC%95%84%EB%9D%BC
- 📍 1초마다 스케줄러 돌려서 경매 종료 확인..❓ 이거 괜찮을까..❓
  - https://velog.io/@_mung/TroubleShooting-TicToc-1%EC%B4%88-%EB%A7%88%EB%8B%A4-%EC%8A%A4%EC%BC%80%EC%A4%84%EB%9F%AC-%EB%8F%8C%EB%A0%A4%EC%84%9C-%EA%B2%BD%EB%A7%A4-%EC%A2%85%EB%A3%8C-%ED%99%95%EC%9D%B8..-%EC%9D%B4%EA%B1%B0-%EA%B4%9C%EC%B0%AE%EC%9D%84%EA%B9%8C
- 📍 사용자 로그인 기록 관리 어떻게 해야 할까‼️❓‼️❓
  - https://velog.io/@_mung/TroubleShooting-TicToc-%EC%82%AC%EC%9A%A9%EC%9E%90-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B8%B0%EB%A1%9D-%EA%B4%80%EB%A6%AC-%EC%96%B4%EB%96%BB%EA%B2%8C-%ED%95%B4%EC%95%BC-%ED%95%A0%EA%B9%8C 

## 📁 디렉토리 구조
```
tictoc-api ## 🗂️ API 관련 모듈
├── Dockerfile
├── build.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── tictoc
    │   │       ├── TicTocApiApplication.java
    │   │       ├── auction 
    │   │       │   ├── adapter (## 🗂️ API 요청을 처리하는 컨트롤러)
    │   │       │   ├── dto (## 🗂️ 데이터 전송 객체)
    │   │       │   ├── mapper (## 🗂️ 객체 변환)
    │   │       │   ├── port (## 🗂️ 비지니스 로직 인터페이스)
    │   │       │   ├── application (## 🗂️ 비지니스 로직 구현체)
    │   │       ├── bid
    │   │       │   ├── adapter (## 🗂️ API 요청을 처리하는 컨트롤러)
    │   │       │   ├── dto (## 🗂️ 데이터 전송 객체)
    │   │       │   ├── mapper (## 🗂️ 객체 변환)
    │   │       │   ├── port (## 🗂️ 비지니스 로직 인터페이스)
    │   │       │   ├── application (## 🗂️ 비지니스 로직 구현체)
    │   │       ├── user
    │   │       │   ├── adapter (## 🗂️ API 요청을 처리하는 컨트롤러)
    │   │       │   ├── dto (## 🗂️ 데이터 전송 객체)
    │   │       │   ├── mapper (## 🗂️ 객체 변환)
    │   │       │   ├── port (## 🗂️ 비지니스 로직 인터페이스)
    │   │       │   ├── application (## 🗂️ 비지니스 로직 구현체)
    │   │       ├── config
    │   │       │   ├── CorsFilter.java 
    │   │       │   ├── SpringDocOpenApiConfig.java (## 📄 Swagger)
    │   │       │   ├── WebConfig.java
    │   │       │   └── security
    │   ├── resources
    │   │   ├── application.yml (## 📄 공통 application.yml)
    │   │   ├── application-dev.yml (## 📄 개발 application.yml)
    │   │   ├── application-prod.yml (## 📄 배포 application.yml)

tictoc-batch (## 🗂️ Spring Batch 관련 모듈)
├── Dockerfile
├── build.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── tictoc
    │   │       ├── TicTocBatchApplication.java
    │   │       ├── userLoginHistory
    │   │       │   ├── config (## 🗂️ Spring Batch)
    │   │       │   ├── event (## 🗂️ Kafka Consumer)
    │   │       │   ├── scheduler (## 🗂️ Spring Batch Scheduler)
    │   ├── resources
    │   │   ├── application.yml (## 📄 공통 application.yml)
    │   │   ├── application-dev.yml (## 📄 개발 application.yml)
    │   │   ├── application-prod.yml (## 📄 배포 application.yml)

tictoc-common (## 🗂️ 공통 로직 관련 모듈)
├── build.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── tictoc
    │   │       ├── TicTocCommonApplication.java
    │   │       ├── annotation (## 🗂️ 커스텀 어노테이션)
    │   │       ├── aspect (## 🗂️ AOP)
    │   │       ├── config (## 🗂️ 공통 설정)
    │   │       ├── constants (## 🗂️ 공통 상수)
    │   │       ├── error (## 🗂️ 커스텀 에러)
    │   │       ├── model (## 🗂️ 공통 객체)
    │   ├── resources
    │   │   ├── application-common.yml (## 📄 common application.yml)

tictoc-domain (## 🗂️ 도메인 관련 모듈)
├── build.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── tictoc
    │   │       ├── TicTocDomainApplication.java
    │   │       ├── auction
    │   │       │   ├── adapter (## 🗂️ DB와 연결하는 구현체)
    │   │       │   ├── dto (## 🗂️ 데이터 전송 객체)
    │   │       │   ├── event (## 🗂️ Redis Event)
    │   │       │   ├── exception (## 🗂️ 커스텀 예러)
    │   │       │   ├── model (## 🗂️ 도메인 엔티티)
    │   │       │   ├── port (## 🗂️ DB와 연결하는 인터페이스)
    │   │       │   ├── repository (## 🗂️ DB와 접근하는 저장소)
    │   │       ├── bid
    │   │       │   ├── adapter (## 🗂️ DB와 연결하는 구현체)
    │   │       │   ├── dto (## 🗂️ 데이터 전송 객체)
    │   │       │   ├── exception (## 🗂️ 커스텀 예러)
    │   │       │   ├── model (## 🗂️ 도메인 엔티티)
    │   │       │   ├── port (## 🗂️ DB와 연결하는 인터페이스)
    │   │       │   ├── repository (## 🗂️ DB와 접근하는 저장소)
    │   │       ├── config (## 🗂️ 도메인 설정)
    │   │       ├── notification
    │   │       │   ├── model (## 🗂️ 도메인 엔티티)
    │   │       ├── payment
    │   │       │   ├── model (## 🗂️ 도메인 엔티티)
    │   │       ├── propfile
    │   │       │   ├── adapter (## 🗂️ DB와 연결하는 구현체)
    │   │       │   ├── model (## 🗂️ 도메인 엔티티)
    │   │       │   ├── port (## 🗂️ DB와 연결하는 인터페이스)
    │   │       │   ├── repository (## 🗂️ DB와 접근하는 저장소)
    │   │       ├── user
    │   │       │   ├── adapter (## 🗂️ DB와 연결하는 구현체)
    │   │       │   ├── dto (## 🗂️ 데이터 전송 객체)
    │   │       │   ├── exception (## 🗂️ 커스텀 예러)
    │   │       │   ├── model (## 🗂️ 도메인 엔티티)
    │   │       │   ├── port (## 🗂️ DB와 연결하는 인터페이스)
    │   │       │   ├── repository (## 🗂️ DB와 접근하는 저장소)
    │   ├── resources
    │   │   ├── application-domain.yml (## 📄 domain application.yml)

tictoc-external (## 🗂️ 와부 로직 관련 모듈)
├── build.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── tictoc
    │   │       ├── TicTocExternalApplication.java
    │   │       │   ├── config (## 🗂️ openFeign 설정)
    │   │       ├── kakao (## 🗂️ Kakao 연결)
    │   ├── resources
    │   │   ├── application-external.yml (## 📄 external application.yml)

tictoc-infrastructure (## 🗂️ Redis, Kafka 관련 모듈)
├── build.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── tictoc
    │   │       ├── TicTocInfrastructureApplication.java
    │   │       ├── kafka 
    │   │       │   ├── config (## 🗂️ Kafka 설정)
    │   │       │   ├── event (## 🗂️ Kafka Producer)
    │   │       │   ├── exception (## 🗂️ 커스텀 예러)
    │   │       ├── redis 
    │   │       │   ├── auction
    │   │       │   │   ├── adapter (## 🗂️ Redis 비지니스 로직 구현체)
    │   │       │   │   ├── port (## 🗂️ Redis 비지니스 로직 인터페이스)
    │   │       │   ├── config (## 🗂️ Redis 설정)
    │   │       │   ├── exception (## 🗂️ 커스텀 예러)
    │   ├── resources
    │   │   ├── application-infrastructure.yml (## 📄 infrastructure application.yml)
```

## 📝 규칙

- **1️⃣ 커밋 컨벤션 규칙**
    - Feat: 새로운 기능 추가
    - Fix: 버그 수정
    - Docs: 문서 수정
    - Style: 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
    - Refactor: 코드 리팩토링
    - Test: 테스트 코드, 리팩토링 테스트 코드 추가
    - Chore: 빌드 업무 수정, 패키지 매니저 수정

- **2️⃣ Branch 규칙**
    - 각자의 깃 타입과 이슈번호를 딴 branch 명을 사용한다.
    - 예시
        - git checkout -b 타입/#이슈번호
        - git checkout -b feature/#5

- **3️⃣ Commit message 규칙**
    - "타입(앞글자를 대문자로): 커밋 메세지 - #이슈번호" 형식으로 작성한다.
    - 예시
        - Feat: 커밋 내용 - #이슈번호
        - Feat: 로그인 구현 - #5
    - 아래 참고 자료를 최대한 활용한다.
        - https://meetup.nhncloud.com/posts/106

- **4️⃣ 쓰기/읽기 규칙**
    - 쓰기 기능
        - 객체 이름
            - 엔티티 + Command + 계층
            - 예시
                - `UserCommandController`
        - Transactional
            - `@Transactional`
    - 조회 기능
        - 객체 이름
            - 엔티티 + Query + 계층
            - 예시
                - `UserQueryService`
        - Transactional
            - `@Transactional(readOnly = true)`

- **5️⃣ DTO 규칙**
    - DTO 이름
        - 엔티티명 + Response/Request + DTO
        - 예시
            - `UserResponseDTO`
            - `PostRequestDTO`
    - DTO 타입
        - Default -> `record`
        - Querydsl 사용시 -> `class`

## 📚 참고 자료

- **Mysql Master/Slave**
    - https://pinomaker.com/146
- **git 커밋 메세지**
    - https://meetup.nhncloud.com/posts/106

# ⏰ TicToc - Server

## 🖥️ 프로젝트 소개
당신의 시간에 가치를 매기다, 시간 거래 경매 플랫폼입니다.
<div style="display: flex; flex-wrap: wrap; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/292b193b-9585-41b1-9951-73af07682fd9" width="80%">
</div>

<br>

## 🟢 ERD

<br>

## 🛠️ 시스템 아키텍처 구조
<div style="display: flex; flex-wrap: wrap; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/ea6fab02-e5be-453b-b551-ea96796cbfed" width="80%">
</div>

<br>

## 🛠️ 헥사고날 아키텍처 구조
<div style="display: flex; flex-wrap: wrap; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/5bfcd479-4fbb-487f-83dc-e407758a9b0d" width="80%">
</div>

<br>

## 📁 디렉토리 구조
```
.
tictoc-api ## 🗂️ API 관련 모듈
├── Dockerfile
├── build.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── tictoc
    │   │       ├── TicTocApiApplication.java
    │   │       ├── auction 
    │   │       │   ├── controller (## 🗂️ API 요청을 처리하는 컨트롤러)
    │   │       │   ├── dto (## 🗂️ 데이터 전송 객체)
    │   │       │   ├── mapper (## 🗂️ 객체 변환)
    │   │       │   ├── port (## 🗂️ 비지니스 로직 인터페이스)
    │   │       │   ├── service (## 🗂️ 비지니스 로직 구현체)
    │   │       ├── bid
    │   │       │   ├── controller (## 🗂️ API 요청을 처리하는 컨트롤러)
    │   │       │   ├── dto (## 🗂️ 데이터 전송 객체)
    │   │       │   ├── mapper (## 🗂️ 객체 변환)
    │   │       │   ├── port (## 🗂️ 비지니스 로직 인터페이스)
    │   │       │   ├── service (## 🗂️ 비지니스 로직 구현체)
    │   │       ├── config
    │   │       │   ├── CorsFilter.java 
    │   │       │   ├── SpringDocOpenApiConfig.java (## 📄 Swagger)
    │   │       │   ├── WebConfig.java
    │   │       │   └── security
    │   │       ├── user
    │   │       │   ├── controller (## 🗂️ API 요청을 처리하는 컨트롤러)
    │   │       │   ├── dto (## 🗂️ 데이터 전송 객체)
    │   │       │   ├── mapper (## 🗂️ 객체 변환)
    │   │       │   ├── port (## 🗂️ 비지니스 로직 인터페이스)
    │   │       │   ├── service (## 🗂️ 비지니스 로직 구현체)
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
    │   │       │   ├── adaptor (## 🗂️ DB와 연결하는 구현체)
    │   │       │   ├── dto (## 🗂️ 데이터 전송 객체)
    │   │       │   ├── event (## 🗂️ Redis Event)
    │   │       │   ├── exception (## 🗂️ 커스텀 예러)
    │   │       │   ├── model (## 🗂️ 도메인 엔티티)
    │   │       │   ├── port (## 🗂️ DB와 연결하는 인터페이스)
    │   │       │   ├── repository (## 🗂️ DB와 접근하는 저장소)
    │   │       ├── bid
    │   │       │   ├── adaptor (## 🗂️ DB와 연결하는 구현체)
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
    │   │       │   ├── adaptor (## 🗂️ DB와 연결하는 구현체)
    │   │       │   ├── model (## 🗂️ 도메인 엔티티)
    │   │       │   ├── port (## 🗂️ DB와 연결하는 인터페이스)
    │   │       │   ├── repository (## 🗂️ DB와 접근하는 저장소)
    │   │       ├── user
    │   │       │   ├── adaptor (## 🗂️ DB와 연결하는 구현체)
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
    │   │       │   │   ├── adaptor (## 🗂️ Redis 비지니스 로직 구현체)
    │   │       │   │   ├── port (## 🗂️ Redis 비지니스 로직 인터페이스)
    │   │       │   ├── config (## 🗂️ Redis 설정)
    │   │       │   ├── exception (## 🗂️ 커스텀 예러)
    │   ├── resources
    │   │   ├── application-infrastructure.yml (## 📄 infrastructure application.yml)
```
<br>

## 🕰️ 개발 기간
* 25.01.11 - 진행 중
  
<br>
  
## ⚙️ 개발 환경
- `Java 21`
- **IDE** : IntelliJ IDEA
- **Framework** : Springboot(3.3.7)
- **Database** : MySQL
- **ORM** : Hibernate (Spring Data JPA 사용)

<br>
  
## 🧑‍🤝‍🧑 멤버 구성
<p>
    <a href="https://github.com/M-ung">
      <img src="https://avatars.githubusercontent.com/u/126846468?v=4" width="100">
    </a>
</p>
  
<br>
  
## 📝 규칙
  
- **커밋 컨벤션**
    - Feat: 새로운 기능 추가
    - Fix: 버그 수정
    - Docs: 문서 수정
    - Style: 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
    - Refactor: 코드 리팩토링
    - Test: 테스트 코드, 리팩토링 테스트 코드 추가
    - Chore: 빌드 업무 수정, 패키지 매니저 수정
  
- **Branch 규칙**
    - 각자의 깃 타입과 이슈번호를 딴 branch 명을 사용한다.
    - 예시
        - git checkout -b 타입/#이슈번호
        - git checkout -b feature/#5
  
- **Commit message 규칙**
    - "타입(앞글자를 대문자로): 커밋 메세지 - #이슈번호" 형식으로 작성한다.
    - 예시
        - Feat: 커밋 내용 - #이슈번호
        - Feat: 로그인 구현 - #5
  
- **DTO 규칙**
    - 엔티티명 + Response/Request + DTO
    - 예시
        - UserResponseDTO
        - PostRequestDTO
     
<br>
  
## 📚 참고 자료

- **Mysql Master/Slave**
    - https://pinomaker.com/146
- **git 커밋 메세지**
    - https://meetup.nhncloud.com/posts/106

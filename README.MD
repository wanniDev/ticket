# Ticket
- 콘서트 예약 서비스 구현하기
- 대기열 시스템을 구축하고, 예약 서비스는 작업가능한 유저만 수행할 수 있도록 해야합니다.
- 사용자는 좌석예약 시에 미리 충전한 잔액을 이용합니다.
- 좌석 예약 요청시에, 결제가 이루어지지 않더라도 일정 시간동안 다른 유저가 해당 좌석에 접근할 수 없도록 합니다.

프로젝트를 구성하는데 필요한 마일스톤은 https://github.com/wanniDev/ticket/milestones 에서 확인할 수 있습니다.

## 브랜치 전략: Github Flow
Github Flow는 main 브랜치를 배포 가능한 상태로 유지하고, 새로운 기능이나 버그 수정을 위한 브랜치를 생성하여 작업한 후, 작업이 완료되면 master 브랜치로 PR을 보내는 방식입니다.
### Github Flow를 사용하는 이유
- 간단하고 직관적이다.
- 각 기능별 작업 기간이 짧기때문에 브랜치를 생성하고 병합하는 과정이 빠른 전략이 필요합니다.
- 동시에 여러 기능을 개발하는 경우에도 충돌이 발생할 확률이 낮은 방법이 필요합니다.
### Github Flow 작업 흐름
1. 브랜치 생성: main 브랜치 하나에 의존하기 때문에 브랜치 생성시, 브랜치 이름을 통해 의도를 명확하게 드러내는 것이 중요합니다.
2. 개발 작업 및 커밋과 푸시
   - 개발을 진행하면서 커밋을 남깁니다.
   - 커밋을 남길 때는 커밋 메시지를 명확하게 작성합니다.
   - 수시로 push 합니다.
   - remote 저장소에 작업 내역을 공유하여 다른 사람들도 확인할 수 있도록 합니다.
3. Pull Request 생성
4. 코드 리뷰 및 피드백
5. 테스트: 단위 테스트 뿐만 아니라, 라이브 서버에 직접 배포해보면서, 실제로 배포할 수 있는 상태인지 검증합니다.
### 커밋 메세지 제목 작성 가이드

> 출처 : [Git Commit Message StyleGuide](https://github.com/slashsbin/styleguide-git-commit-message#message-subjectfirst-line)

**"태그: 제목"의 형태이고, : 뒤에 공백이 있음에 유의한다.**

| 태그 이름 | 설명                                                         |
| --------- | ------------------------------------------------------------ |
| Feat      | 새로운 기능을 추가할 경우                                    |
| Fix       | 버그를 고친 경우                                             |
| !HOTFIX   | 급하게 치명적인 버그를 고쳐야하는 경우                       |
| Refactor  | 프로덕션 코드 리팩토링                                       |
| Comment   | 필요한 주석 추가 및 변경                                     |
| Document  | 문서를 수정한 경우                                           |
| Test      | 테스트 추가, 테스트 리팩토링(프로덕션 코드 변경 X)           |
| Chore     | 빌드 task 업데이트, 패키지 매니저를 설정하는 경우(프로덕션 코드 변경 X) |
| Rename    | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우           |
| Remove    | 파일을 삭제하는 작업만 수행한 경우                           |


## 요구사항 분석
콘서트 예약 서비스를 구현하기 위해선 아래 6가지의 api가 필요합니다.
- 유저 토큰 발급 API
- 예약 가능 날짜 조회 API
- 예약 가능 좌석 조회 API
- 좌석 예약 요청 API
- 잔액 충전 / 조회 API
- 결제 API

## 플로우 차트
### 0. 유저 토큰 대기열 확인
1. 클라이언트 토큰 입력 수신
2. 토큰이 유효한지 확인
3. 토큰이 유효하면 대기열 확인(peek)
4. 대기열 순번의 토큰과 클라이언트 토큰이 일치하는지 확인
5. 일치하면 true 리턴
6. 일치하지 않으면 false 리턴

### 1. 유저 토큰 발급 API
![tokencreate drawio](https://github.com/wanniDev/ticket/assets/81374655/daecde77-3214-4149-9bf2-598279b55fb3)

### 2. 예약 가능 날짜 조회 API
![reservation_date_search drawio](https://github.com/wanniDev/ticket/assets/81374655/03b9cf95-6b12-4f64-9a07-584f26596919)

### 3. 예약 가능 좌석 조회 API
![reservation_seats_search drawio](https://github.com/wanniDev/ticket/assets/81374655/48b2d329-48b8-4749-b9d1-66f0f0b24f97)

### 4. 좌석 예약 요청 API
![reservation drawio](https://github.com/wanniDev/ticket/assets/81374655/45c31430-9b9d-4ef4-8fac-b68e2292f055)

### 5. 잔액 충전 API
![balance_charge drawio](https://github.com/wanniDev/ticket/assets/81374655/20615ee3-111c-487c-b3a4-48656c1e9aa5)

### 6. 잔액 조회 API
![balance_search drawio](https://github.com/wanniDev/ticket/assets/81374655/cbcc6bf8-8802-433e-80c1-942cfd0f43a0)

### 7. 결제 API
![payment drawio](https://github.com/wanniDev/ticket/assets/81374655/1a1b9aee-dd5a-4a2a-8376-c91eab917948)

## 시퀀스 다이어그램
> 이 부분은 새로운 다이어그램 작성 툴에 대한 학습을 해보기위해 별도로 진행해보는 작업입니다. </br>
> 따라서, 아직 모든 내용이 제대로 반영된 부분은 아닐 수 있습니다.

### 0. 유저 토큰 대기열 검증
현재 주어진 토큰이 대기열 우선순위에 들었는지 확인하는 API 입니다.
```mermaid
sequenceDiagram
    participant C as Client
    participant A as API
    participant DB as Database
    C->>A: api 요청(POST /api/token/peek)
    A->>A: api 요청 바디 구성(토큰ID)
    A->>DB: 제일 최근의 Token 조회 쿼리 요청
    DB->>DB: 제일 최근의 Token 조회 쿼리 수행
    DB-->>A: 제일 최근의 Token 조회 결과 return
    opt 존재 하지 않는 토큰
        A-->>C: NOT_FOUND 오류
    end
    A-->>A: 요청한 토큰ID와 제일 최근의 토큰ID 비교
    A-->>C: 제일 최근의 토큰ID 일치여부 응답 메시지 리턴
```

### 1. 유저 토큰 발급 API
```mermaid
sequenceDiagram
    participant C as Client
    participant A as API
    participant DB as Database
    C->>A: api 요청 처리 위임
    A->>DB: 사용자 조회
    DB->>DB: 사용자 식별 정보 확인
    DB-->>A: 사용자 식별 정보 결과 return
    opt 사용자 식별 불가 
        A-->>C: 400 Bad Request
    end
    A->>A: 토큰 생성
    A-->>C: api 응답

```

### 2. 예약 가능 좌석 조회 API
- 목적 : 사용자가 특정 날짜와 좌석에 대해 예약을 요청할 수 있어야 합니다.
- 핵심 요구사항 :
  - 사용자는 특정 날짜를 선택하여 그 날짜에 대한 예약 가능 좌석을 확인할 수 있어야 합니다.
- 제약사항 :
  - 이미 예약되었거나 임시 배정된 좌석은 예약 가능한 좌석 목록에 포함되지 않아야 합니다.
```mermaid
sequenceDiagram
    participant C as Client
    participant F as Filter
    participant A as API
    participant DB as Database
    C->>A: api 요청(GET /api/concert/seat/{date})
    A->>F: 요청 헤더의 토큰 복호화
    F->>DB: 복호화된 토큰 조회 쿼리 요청
    DB->>DB: 복호화된 토큰 조회 쿼리 수행
    DB-->>F: 복호화된 토큰 조회 결과 return
    alt 토큰이 만료된 경우
        F-->>C: expired token error
    else 토큰 검증 실패 
        F-->>C: Invalid token error
    end
    F->>A: api 요청 처리 위임
    A->>DB: 해당 일자 좌석 조회 쿼리 요청
    DB->>DB: 해당 일자 좌석 조회 쿼리 수행
    DB-->>A: 해당 일자 좌석 조회 결과 return
    A-->>A: 해당 좌석에 예약가능한 좌석 필터링 및 오름차순 정렬
    A-->>C: 해당 일자 좌석 조회 결과 return
```

### 3. 좌석 예약 요청 API
- 목적 : 사용자가 특정 날짜와 좌석에 대해 예약을 요청할 수 있어야 합니다.
- 핵심 요구사항 :
  - 사용자는 토큰을 사용하여 예약 요청을 해야 합니다.
  - 예약이 성공적으로 요청되면, 해당 좌석은 일정 시간 동안 사용자에게 임시 배정됩니다.
- 제약사항 :
  - 한 사용자가 동시에 여러 좌석을 예약할 수 없습니다.
  - 임시 배정 시간 내에 결제가 완료되지 않으면, 좌석 배정이 자동으로 해제됩니다.
```mermaid
sequenceDiagram
  participant C as Client
  participant F as Filter
  participant A as API
  participant DB as Database
  C->>A: api 요청(GET /api/concert/seat/{date})
  A->>F: 요청 헤더의 토큰 복호화
  F->>DB: 복호화된 토큰 조회 쿼리 요청
  DB->>DB: 복호화된 토큰 조회 쿼리 수행
  DB-->>F: 복호화된 토큰 조회 결과 return
  alt 토큰이 만료된 경우
    F-->>C: expired token error
  else 토큰 검증 실패
    F-->>C: Invalid token error
  end
  F->>A: api 요청 처리 위임
  A->>DB: 해당 일자 예약 가능한 콘서트, 좌석 조회 쿼리 요청
  DB->>DB: 해당 일자 예약 가능한 콘서트, 좌석 조회 쿼리 수행
  DB-->>A: 해당 일자 예약 가능한 콘서트, 좌석 조회 결과 return
  opt 예약 가능한 콘서트 및 좌석이 없는 경우
    A-->>C: Not Found Available concert
  end
  A->>DB: 예약 정보 저장 쿼리 요청
  DB->>DB: 예약 정보 저장 쿼리 수행
  DB-->>A: 예약 정보 저장 결과 return
  A-->>C: 예약 정보 저장 결과 return
```
### 4. 잔액 충전 / 조회 API
#### 잔액 충전
- 목적 : 사용자가 예약 결제를 위해 잔액을 충전할 수 있어야 합니다.
- 핵심 요구사항 :
  - 사용자는 특정 금액을 지정하여 잔액을 충전할 수 있습니다.
- 제약사항 :
  - 충전 금액은 양수여야 합니다. 
```mermaid
sequenceDiagram
  participant C as Client
  participant F as Filter
  participant A as API
  participant DB as Database
  C->>A: api 요청
  A->>F: 요청 헤더의 토큰 복호화
  F->>DB: 복호화된 토큰 조회 쿼리 요청
  DB->>DB: 복호화된 토큰 조회 쿼리 수행
  DB-->>F: 복호화된 토큰 조회 결과 return
  alt 토큰이 만료된 경우
    F-->>C: expired token error
  else 토큰 검증 실패
    F-->>C: Invalid token error
  end
  F->>A: api 요청 처리 위임
  A->>DB: 사용자 잔액 조회 쿼리 요청
  DB->>DB: 사용자 잔액 조회 쿼리 수행
  DB-->>A: 사용자 잔액 조회 결과 return
  A->>DB: 사용자 잔액 업데이트 쿼리 요청
  DB->>DB: 사용자 잔액 업데이트 쿼리 수행
  DB-->>A: 사용자 잔액 업데이트 결과 return
  A-->>C: 사용자 잔액 업데이트 결과 return
```

#### 잔액 조회
- 목적 : 사용자가 자신의 현재 잔액을 조회할 수 있어야 합니다.
- 핵심 요구사항 :
  - 사용자는 자신의 잔액 정보를 언제든지 조회할 수 있어야 합니다.
- 제약사항:
  - 조회는 사용자 본인의 잔액에 한정됩니다.
```mermaid
sequenceDiagram
  participant C as Client
  participant F as Filter
  participant A as API
  participant DB as Database
  C->>A: api 요청
  A->>F: 요청 헤더의 토큰 복호화
  F->>DB: 복호화된 토큰 조회 쿼리 요청
  DB->>DB: 복호화된 토큰 조회 쿼리 수행
  DB-->>F: 복호화된 토큰 조회 결과 return
  alt 토큰이 만료된 경우
    F-->>C: expired token error
  else 토큰 검증 실패
    F-->>C: Invalid token error
  end
  F->>A: api 요청 처리 위임
  A->>DB: 사용자 잔액 조회 쿼리 요청
  DB->>DB: 사용자 잔액 조회 쿼리 수행
  DB-->>A: 사용자 잔액 조회 결과 return
  A-->>C: 사용자 잔액 조회 결과 return
```
    
### 5. 결제 API
> 해당 API는 PG 사에서 제공하는 결제 모달창 UI를 통해 결제 정보를 입력한 이후에 호출되는 API입니다.</br>
> 이번 프로젝트에서는 예약과 대기열등 핵심 비즈니스 로직 구현에 집중하기 위해 PG 연동은 생략하였습니다. </br>
```mermaid
sequenceDiagram
  participant C as Client
  participant F as Filter
  participant AO as AOP
  participant Q as 대기열
  participant A as API
  participant PG as PaymentGateway
  participant DB as Database

  C->>F: api 요청
  F->>DB: 토큰 검증
  opt 잘못된 토큰
    F-->>C: 401 Unauthorized
  end
  alt 대기열 사용가능
    AO->>A: api 요청 처리 위임
  else 대기열 사용불가
    AO-->>C: 429 Too Many Requests
  end
  A->>DB: 결제 정보 검증
  opt 결제 정보 불일치
    A-->>C: 400 Bad Request
  end
  A->>PG: 결제 승인 요청
  PG-->>A: 결제 승인 결과 수신
  A->>DB: 결제 승인 결과(결제 내역) 저장
```
- 목적: 사용자가 좌석 예약에 대해 결제를 완료할 수 있어야 합니다.
- 핵심 요구사항:
  - 사용자는 잔액을 이용하여 예약한 좌석에 대한 결제를 진행할 수 있어야 합니다.
  - 결제가 성공하면, 예약이 확정되고 해당 좌석은 사용자에게 배정됩니다.
- 제약사항:
  - 사용자의 잔액은 결제 금액 이상이어야 합니다.
  - 결제 요청은 유효한 예약 정보에 대해서만 가능해야 합니다.

## 데이터베이스 스키마
> tsid는 Time Series ID의 약자로, 고유한 시간을 나타내는 문자열입니다. 현재 프로젝트에서는 pk의 보조키로 활용되고 있습니다.
```mermaid
erDiagram
    user {
        bigint id pk
        varchar tsid
    }
    point {
      bigint id pk
      varchar tsid
      decimal balance
      datetime create_time
      datetime update_time
    }
    point_usage {
      bigint id pk
      varchar tsid
      varchar transaction_type
      bigint user_id fk
      bigint point_id fk
    }
    token {
        bigint id pk
        varchar tsid
        datetime issued_time
        varchar status
        varchar user_tsid
    }
    concert {
        bigint id pk
        varchar tsid
        decimal price
        date date
    }
    
    seat { 
        bigint id pk
        varchar tsid
        varchar number
        varchar status
        bigint concert_id
    }

    reservation {
        bigint id pk
        string tsid
        string status
        datetime reservation_time
        datetime expiration_time
        bigint user_id fk
        bigint seat_id fk
    }
    
    payment {
        bigint id pk
        varchar tsid
        decimal amount
        datetime payment_time
    }
    
    concert ||--o{ seat : "1"
    point ||--o{ point_usage : "1"
    user ||--o{ point_usage : "1"
    user ||--o| token : "1"
    user ||--o{ reservation : "1"
```


# Ticket
- 콘서트 예약 서비스 구현하기
- 대기열 시스템을 구축하고, 예약 서비스는 작업가능한 유저만 수행할 수 있도록 해야합니다.
- 사용자는 좌석예약 시에 미리 충전한 잔액을 이용합니다.
- 좌석 예약 요청시에, 결제가 이루어지지 않더라도 일정 시간동안 다른 유저가 해당 좌석에 접근할 수 없도록 합니다.

프로젝트를 구성하는데 필요한 마일스톤은 https://github.com/wanniDev/ticket/milestones 에서 확인할 수 있습니다.

## 요구사항 분석
콘서트 예약 서비스를 구현하기 위해선 아래 6가지의 api가 필요합니다.
- 유저 토큰 발급 API
- 예약 가능 날짜 조회 API
- 예약 가능 좌석 조회 API
- 좌석 예약 요청 API
- 잔액 충전 / 조회 API
- 결제 API

### 유저 토큰 발급 API
- 묙젹 : 사용자가 서버와 상호작용하기 위한 토큰을 발급받을 수 있어야 합니다.
- 핵심 요구사항 :
    - 사용자 식별 정보를 기반으로 토큰을 생성합니다.
    - 토큰은 사용자의 대기열 위치와 만료 시간 정보를 포함해야 합니다.
    - 발급된 토큰은 예약 시스템 접근에 필수적입니다.
- 제약사항 :
    - 토큰은 한 번에 하나의 사용자에게만 발급되어야 합니다.
    - 토큰은 발급 후 제한된 시간 동안만 유효해야 합니다.
- 특이사항 :
    - 예약 서비스 구현에 집중하기위해, JWT를 적용하진 않습니다.
    - 그러나 확장성을 토큰 발급 기능을 구성해야합니다.

### 예약 가능 좌석 조회 API
- 목적 : 사용자가 특정 날짜와 좌석에 대해 예약을 요청할 수 있어야 합니다.
- 핵심 요구사항 :
  - 사용자는 특정 날짜를 선택하여 그 날짜에 대한 예약 가능 좌석을 확인할 수 있어야 합니다.
- 제약사항 :
  - 이미 예약되었거나 임시 배정된 좌석은 예약 가능한 좌석 목록에 포함되지 않아야 합니다.

### 좌석 예약 요청 API
- 목적 : 사용자가 특정 날짜와 좌석에 대해 예약을 요청할 수 있어야 합니다.
- 핵심 요구사항 :
  - 사용자는 토큰을 사용하여 예약 요청을 해야 합니다.
  - 예약이 성공적으로 요청되면, 해당 좌석은 일정 시간 동안 사용자에게 임시 배정됩니다.
- 제약사항 :
  - 한 사용자가 동시에 여러 좌석을 예약할 수 없습니다.
  - 임시 배정 시간 내에 결제가 완료되지 않으면, 좌석 배정이 자동으로 해제됩니다.

### 잔액 충전 / 조회 API
#### 잔액 충전
- 목적 : 사용자가 예약 결제를 위해 잔액을 충전할 수 있어야 합니다.
- 핵심 요구사항 :
  - 사용자는 특정 금액을 지정하여 잔액을 충전할 수 있습니다.
- 제약사항 :
  - 충전 금액은 양수여야 합니다. 

#### 잔액 조회
- 목적 : 사용자가 자신의 현재 잔액을 조회할 수 있어야 합니다.
- 핵심 요구사항 :
  - 사용자는 자신의 잔액 정보를 언제든지 조회할 수 있어야 합니다.
- 제약사항:
  - 조회는 사용자 본인의 잔액에 한정됩니다.
    
### 결제 API
- 목적: 사용자가 좌석 예약에 대해 결제를 완료할 수 있어야 합니다.
- 핵심 요구사항:
  - 사용자는 잔액을 이용하여 예약한 좌석에 대한 결제를 진행할 수 있어야 합니다.
  - 결제가 성공하면, 예약이 확정되고 해당 좌석은 사용자에게 배정됩니다.
- 제약사항:
  - 사용자의 잔액은 결제 금액 이상이어야 합니다.
  - 결제 요청은 유효한 예약 정보에 대해서만 가능해야 합니다.

## API spec

### 1. 유저 토큰 발급 API

- **Endpoint:** `/api/token`

- **Method:** `POST`

- **Description:** 사용자에게 대기열 관리를 위한 토큰을 발급합니다.

- **Request Body:**

  ```json
  {
    "userId": "string"
  }
  ```

- **Response:**

  - 200 OK

    ```json
    {
      "token": "string",
      "queuePosition": "number",
      "expirationTime": "datetime"
    }
    ```

  - 400 Bad Request

    ```json
    {
      "error": "잘못된 사용자 ID"
    }
    ```

### 2. 예약 가능 날짜 조회 API

- **Endpoint:** `/api/concerts/dates`

- **Method:** `GET`

- **Description:** 예약 가능한 모든 날짜를 조회합니다.

- **Responses:**

  - 200 OK

    ```json
    {
      "dates": ["yyyy-mm-dd", "yyyy-mm-dd"]
    }
    ```

### 3. 예약 가능 좌석 조회 API

- **Endpoint:** `/api/concerts/seats`

- **Method:** `GET`

- **Description:** 특정 날짜에 대한 예약 가능한 좌석 정보를 조회합니다.

- **Query Parameters:**

  - `date`: `string` (format: "yyyy-mm-dd")

- **Responses:**

  - **200 OK**

  ```json
  {
    "date": "yyyy-mm-dd",
    "availableSeats": ["1", "2", "3"]
  }
  ```

  - **404 Not Found**

  ```json
  {
    "error": "찾을 수 없는 좌석 번호입니다."
  }
  ```

  - **400 Bad Request**

  ```json
  {
    "error": "잘못된 형식의 날짜입니다."
  }
  ```

  

### 4. 좌석 예약 요청 API

- **Endpoint:** `/api/reservations`

- **Method:** `POST`

- **Description:** 특정 날짜와 좌석에 대한 예약 요청을 합니다.

- **Request Body:**

  ```json
  {
    "userId": "string",
    "date": "yyyy-mm-dd",
    "seatNumber": "number",
    "token": "string"
  }
  ```

- **Response:**

  -  **200 OK**

    ```json
    {
      "reservationId": "string",
      "status": "Reserved",
      "expirationTime": "datetime"
    }
    ```

  - **400 Bad Request**

    ```json
    {
      "error": "잘못된 형식의 요청 파라미터입니다."
    }
    ```

  - **401 Unauthorized**

    ```json
    {
      "error": "잘못되었거나 이미 만료된 토큰입니다."
    }
    ```

### 5. 잔액 충전 API

- **Endpoint:** `/api/balance/recharge`

- **Method:** `POST`

- **Description:** 사용자의 잔액을 충전합니다.

- **Request Body:**

  ```json
  {
    "userId": "string",
    "amount": "number"
  }
  ```

- **Response:**

  - **200 OK**

    ```json
    {
      "userId": "string",
      "newBalance": "number"
    }
    ```

  - **400 Bad Request**

    ```json
    {
      "error": "잘못된 요청 파라미터 입니다."
    }
    ```

### 6. 잔액 조회 API

- **Endpoint:** `/api/balance`
- **Method:** `GET`
- **Description:** 사용자의 현재 잔액을 조회합니다.
- **Query Parameters:**
  - `userId`: `string`
- **Responses:**
  - **200 OK**

- - ```json
    {
      "userId": "string",
      "currentBalance": "number"
    }
    ```

  - **404 Not Found**

    ```json
    {
      "error": "찾을 수 없는 사용자입니다."
    }
    ```

### 7. 결제 API

- **Endpoint:** `/api/payments`

- **Method:** `POST`

- **Description:** 좌석 예약에 대한 결제를 진행합니다.

- **Request Body:**

  ```json
  {
    "userId": "string",
    "reservationId": "string",
    "amount": "number"
  }
  ```

- **Responses: **

  - **200 OK**

    ```json
    {
      "paymentId": "string",
      "status": "Completed"
    }
    ```

  - **400 Bad Request**

    ```json
    {
      "error": "잘못된 잔액 표시 형식이거나 잔액이 부족합니다."
    }
    ```

  - 401 Unauthorized

    ```json
    {
      "error": "잘못되 사용자 ID 혹은 예약 ID 입니다."
    }
    ```

    
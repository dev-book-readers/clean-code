## 1. 테스트 코드의 중요성

- 실수 바로 잡아줌
- 테스트 케이스는 변경이 쉽도록 한다.
- 없으면 모든 변경은 잠정적인 버그다.
- 지저분한 테스트 코드는 안하느니만 못하다.

--> Effective Unit Test: 책 참고

- 테스트는 자동화 되어야 한다.

## 2. 테스트의 종류
- Unit Test: 프로그램 내부의 개별 컴포넌트 동작 테스트. 배포하기 전에 자동으로 실행되도록.
- Integration Test: 통합 테스트(interaction)
- E2E Test: 유저의 관점

## 3. Unit Test 작성
- 테스트 라이브러리 사용
  - JUnit, Mockito 등
- Test Double: 원본 객체를 대신하는 객체
  - Stub: 원래 구현을 단순하게, 프로그래밍된 항목에만 응답
  - Spy: 위의 역할을 하면서 호출 정보를 기록
  - Mock: 행위 검증을 위해 가짜 객체를 만들어 테스트(호출에 대한 동작을 정의)
    - stub은 상태 mock은 행위
- given, when, then 패턴

## 4. FIRST 원칙
- Fast: 빨리 돌아야 한다. 자주 돌리기 때문
- Independent: 독립적으로, 의존 X
- Repeatable: 반복가능하게
- Self-Vaildating: 자가검증(boolean 값)
- Timely: 실제코드를 구현하기 직전에 구현

## 5. 오픈소스 속 Unit Test
## TTD 법칙
1. 실패하는 단위 테스트를 작성하기 전까지 코드 작성 X
2. 컴파일은 실패하지 않으면서 실행이 실패하도록 단위 테스트 작성
3. 만들어놓은 테스트를 통화할 정도로만 실제 코드를 작성

## 깨끗한 테스트 코드 유지하기
테스트 코드가 지저분하다면, 없는것과 진배하다.  

### 테스트느느 유연성, 유지보수성, 재사용성을 제공한다.
테스트 케이스가 없다면 모든 변경이 잠정적인 버그다.

## 깨끗한 테스트 코드
- 테스트 코드의 의도를 흐리는 자질구레한 코드는 함수나 클래스를 통해 숨기자.
### 도메인에 특화된 테스트언어
도메인에 특화된 언어(DSL) : 흔히 쓰는 시스템 조작 API를 사용해는 대신 API 위에다 함수와 유틸리티를 구현한 후 그것을 사용하라.

### 이중 표준
테스트 API 코드에 적용하는 표준은 실제 코드에 적용하는 표준과 다르다.  
한눈에 보고 바로 이해할 수 있도록 가독성 있게 작성하라. 가독성을 위해 실제 코드의 표준을 어긋날 수도 있다.   
실제 환경에서는 절대로 안되지만(주로 메모리나 CPU 효율 문제) 테스트 환경에서는 전혀 문제가 없는 방식이 있다.  코드의 가독성을 높이기 위해서라면 테스트코드는 효율을 무시할 수 있다.
EX. StringBuffer 대신 += 로 문자열 컨켓

## 테스트 당 assser 하나
given/when-then 형식의 경우 assert 하나로 구현하기가 더 불합리하다.  
하나에 집중하기보다는 최대한 줄이는 방식으로 사용하자.  
테스트 당 개념 하나를 작성하라

## FIRST
- Fast : 테스트의 속도가 빨라야한다.  
- Independent : 각 테스트는 의존하면 안된다. 모두 독립적이어야한다.  
- Repeatable : 그 어떤 환경에서도 반복 가능해야한다.  
- Self-Validating : 테스트는 bool 값으로 결과를 내야한다. 수동 검사가 들아가면 안된다.  
- Timely : 단위 테스트는 테스트하려는 실제 구현하기 직전에 구현한다.  

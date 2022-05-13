#07 예외처리

### 우아하고 고상하게 오류를 처리하는 기법과 고려 사항

    # 오류코드보다 예외를 사용하라.
    
    : 원래는 예외를 지원하지 않느 언어가 많았다.
      1. 오류 플래그 설정 2. 호출자에게 오류 코드를 반환하는 식의 방법
---
    # Try-Catch-Finally 문부터 작성하라.
    
    : try 블록은 트랜잭션과 비슷하다.
      예외가 발생할 코드는 Try-Catch-Finally 문으로 시작 
      try 블록에서 뭔가 예외가 생겨도 처리하기 쉬워짐.
---
    # NULL을 반환하거나, 전달하지 마라.

    : NULL을 반환하고 싶은 경우, 자바에서는 Collections.emptyList()라는것도 있다.
    
    - NULL 반환의 단점들 -
    호출자에게 null을 체크할 의무를 줌
    NullPointerException 의 발생 위험이 있음
    null확인이 너무 많아짐

## 1. 예외처리 방식
- 오류코드 리턴 ㄴㄴ, 예외를 던지자.
- 오류가 발생한 부분에서 예외를 던진다.
  - 별도의 처리가 필요한 예외라면 checked exception 으로 던진다.
- checked exception 에 대한 예외처리를 하지 않는다면 메서드 선언분에 throws 를 명시해야 한다.
- 예외를 처리할 수 있는 곳에서 catch!

## 2. Unchecked Exception
- exception 을 바로 상속받으면 checked
  - IOException, SQLException
- RuntimeException 을 상속하면 unchecked
  - NullPointerException, IllegalArgumentException, IndexOutOfBoundsException

### 2-1 Effective Java -  Exception 규약
- 자바 언어 명세가 요구하는 것은 아니지만,
  업계에 널리 퍼진 규약으로 Error 클래스를 상속해 하위 클래스를 만드는 일은 자제하자.
- 즉, 사용자가 직접 구현하는 unchecked throwable 은 모두
  RuntimeException 의 하위 클래스여야한다.

refer: https://www.nextree.co.kr/p3239/

## 3. Exception 잘 쓰기
- 예외에 메세지 담기
  - 오류가 발생한 원인과 위치를 찾기 쉽도록, 전후 상황을 충분히 덧붙인다.
  - 실패한 연산 이름과 유형등 정보를 담는다.
- exception wrapper
  - 예외가 너무 많을때, throws 
    예외를 감싸는 클래스를 만든다.

## 4. 실무 예외 처리 패턴
- getOrElse
  - 예외대신 기본값 리턴
    - null 이 아닌 기본값: null 보다는 size 가 0인 컬렌션이 훨씬 안전하다.
    - 도메인에 맞는 기본값
- getOrElseThrow
  - 기본값이 없다면 null 대신 예외를 던진다.
- 실무에서는 보통 자신의 예외를 정의한다.
  - 에러 로그에서 우리가 발생시킨 예외라는 것을 인지가능
  - 다른 라이브러리에서 발생한 에러와 섞이지 않는다
  - 에러 종류를 나열할 수 있다.

```java
public class MyProjectException extends RuntimeException {
  private MyErrorCode errorCode;
  private String errorMessage;

  public MyProjectException(MyErrorCode errorCode) {
    // ...
  }

  public void setErrorCode(MyErrorCode errorCode, String errorMessage) {
    // ...
  }
}

public enum MyErrorCode {
    private String defaultErrorMessage;
    
    INVALID_REQUEST("잘못된 요청입니다."),
    DUPLICATE_REQUEST("기존 요청과 중복되어 처리할수 없습니다."),
      
    // ...  
    INTERNAL_SERVER_ERROR("처리중 에러가 발생하였습니다.");
}

//호출부
if (request.getUserName() == "null") {
   throw new MyProjectException(ErrorCode.INVALID_REQUEST, "userName is null");
}
```
# 5. 오픈소스 속 Exception 살펴보기
- pass


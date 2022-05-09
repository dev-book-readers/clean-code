# 1. 주석을 최대한 쓰지말자
- 주석은 나쁜코드를 보완을 하지 못한다
  - 코드의 품질이 나쁘기 때문
  ```java
  // 직원에게 복지 혜택을 받을 자격이 있는지 확인한다
  if ((employee.flgs & HOURLY_FLAG) && employee.age > 65))
  
  // 의미있는 이름을 지으면 해결
  if (employee.is
  ```
  - 주석에 책임을 전가하지 말자
  - 코드로도 의도를 표현할 수 있다.

- 주석은 방치될수 있다(기능이 추가된다면?)

# 2. 좋은 주석이란?
- 구현에 대한 정보를 제공
- 의도와 중요성을 설명
```java
// 값을 저장할 trim으로 공백제거 필요
String userName = userNameInput.trim();
```
- TODO, FIXME 주석
  - TODO: 앞으로 할일, 지금은 ㄴㄴ 나중에 해야 할 일
  - FIXME: 문제가 있지만 당장 수정할 필요는 ㄴㄴ일때, 가능한 빨리 수정
    - IDE에서 하이라이팅 되고 따로 볼 수 있음
    
# 3. annotation
- 코드에 대한 메타데이터
```java
@Deprecated
- 컴파일러가 warning을 발생시킨다. IDE에서 사용시 표시됌.
@NotThreadSafe
- ThreadSafe 하지 않음을 나타냄
```

# 4. javaDoc
- Java 코드에서 API 문서를 HTML 형식으로 생성해주는 도구
```java
/**
 * Please see the {@link com.hwaner.javadoc.person} class for~~~
 * @author hwaner
 */
public class hwaner extends Person {
    // methods
}

/**
 * <p>sdfdsaf
 * <a href="http://dsfsadf>s@@</a>
 * </p>
 * @param 함수에 들어오는 인자 설명
 * @return 리턴값이 뭔지
 * @see 참고내용
 * @since 버전
 */
 
```
- 리더모드: 깔끔히 볼 수 있음

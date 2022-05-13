인터페이스 제공자 : 적용성을 최대한 넓히려고 함<br>

인터페이스 사용자 : 자신의 요구에 집중하는 인터페이스를 원함

이런 서로의 초점이 다르기 때문에 시스템 경계에서 문제가 생기기도 함<br><br>

- 경계 인터페이스인 Map을 Sensors 안으로 숨기는 방법을 사용한다.
```java
public class Sensors {
  private Map sensors = new HashMap();
  
  public Sensor getById(String id) {
    return (Sensor) sensors.get(id);
    }
}
```




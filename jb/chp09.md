# Chp.09 : 단위 테스트

## **[ TDD 법칙 3가지 ]**

TDD(Test Driven Development)는 실제 코드를 짜기 전에 단위 테스트부터 짜라고 요구한다.

아래 **3가지 규칙**을 따르면 개발과 테스트가 대략 30초 주기로 묶인다 :

1. 실패하는 단위 테스트를 작성할 때까지 실제 코드를 작성하지 않는다.
2. 컴파일은 실패하지 않으면서 실행이 실패하는 정도로만 단위 테스트를 작성한다.
3. 현재 실패하는 테스트를 통과할 정도로만 실제 코드를 작성한다.

## [ 깨끗한 테스트 코드 유지하기 ]

- ‘지저분해도 빨리’ 완성하기 위해 변수명 신경쓰지 않고 테스트 함수를 간결하거나 너무 서술적이게 작성하는 등 **그저 실행만 되는 코드를 작성하는 것은 테스트를 안하니만 못하다**.
- 하지만 테스트 코드가 없으면 시스템 안정성 검증을 못하므로 코드가 망가진다.

  → 테스트 코드는 실제 코드 못지 않게 중요하므로 깨끗하게 짜야 한다.

- **단위 테스트** : 코드에 유연성, 유지보수성, 재사용성을 제공

  → 테스트 커버리지가 높을수록 공포는 줄어든다 (코드 유지보수/변경이 쉬워짐)


## [ 깨끗한 테스트 코드 ]

- 테스트 코드를 짤 때 가독성이 매우 중요하다
    - 바로 본론으로 시작해서 필요한 자료 유형과 함수만 사용

[ 도메인에 특화된 테스트 언어 (DSL) ]

- DSL로 테스트 코드를 구현하는 기법 :
    - API 위에 함수, utility를 구현한 후 그 함수와 utility를 사용 (시스템 조작 API 사용 X)
    - 구현한 함수, utility는 테스트 코드에서 사용하는 특수 API가 됨

[ 이중 표준 ]

- 테스트 API 코드에 적용하는 표준은 실제 코드에 적용하는 표준과 확실히 다르다.
    - 단순함, 간결함, 풍부한 표현력
    - 실제 코드보다 효율성 낮아도 괜찮음 b/c 테스트 환경에서만 돌아가는 코드이므로

```java
// 변경 전
@Test
public void turnOnLoTempAlarmAtThreashold() throws Exception {
hw.setTemp(WAY_TOO_COLD);
controller.tic();
assertTrue(hw.heaterState());
assertTrue(hw.blowerState());
assertFalse(hw.coolerState());
assertFalse(hw.hiTempAlarm());
assertTrue(hw.loTempAlarm());
}

// 변경 후 #1
@Test
public void turnOnLoTempAlarmThreshold() throws Exception {
wayTooCold();
assertEquals("HBchL", hw.getState());
}

// 변경 후 #2
public String getState() {
String state = "";
state += heater ? "H" : "h";
state += blower ? "B" : "b";
state += coolear ? "C" : "c";
state += hiTempAlarm ? "H" : "h";
state += loTempAlarm ? "L" : "l";
return state;
}
```

- HBchL: (heater, blower, cooler, hi-temp-alarm, lo-temp-alarm) / 대문자는 켜짐, 소문자는 꺼짐
- 코드의 성능 < 깔끔함이 우선 → character를 append하는 방식(#2)이 채택됨!

## [ 테스트 당 assert 하나 ]

- 개념 당 assert문 수를 최소로 줄이기
- 테스트 함수 하나는 개념 1개만 테스트

## [ F.I.R.S.T ]

깨끗한 테스트 코드는 아래 5가지 규칙을 따른다 :

1. Fast (빠르게)

   : 테스트는 빨라야한다. 그래야 자주 테스트를 돌릴 수 있고, 코드 품질 및 성능을 개선할 수 있다.

2. Independent (독립적으로)

   : 각 테스트는 순서 상관없이 독립적으로 실행되어야한다. 만약 서로 의존성이 높아질 경우, 테스트 하나가 실패하면 나머지도 연달아 실패하게 되므로 정확한 원인을 진단하기 어려워진다.

3. Repeatable (반복 가능하게)

   : 테스트는 어떠한 환경 (대중교통 안, 노트북 환경 등) 에서도 반복이 가능해야한다. 즉, 네트워크 연결된 환경에서 테스트는 지속적으로 수행되어야 한다.

4. Self-Validating (자가 검증하는)

   : 테스트는 bool값(true, false)으로 결과를 내야한다. 그렇지 않으면 테스트 통과 여부를 알지못하는 사용자는 주관적으로 판단하게 되고 지루한 수작업 평가를 거치게 된다.

5. Timely (적시에)

   : 단위 테스트는 실제 코드를 구현하기 직전에 작성/진행되어야 한다.
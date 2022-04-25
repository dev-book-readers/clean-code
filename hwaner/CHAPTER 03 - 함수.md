# 1. SOLID
- SRP(단일책임원칙): 한 클래스는 하나의 책임만 가져야 한다.
- OCP(개방-폐쇄원칙): 소프트웨어 요소는 확장에는 열려있으나, 변경에는 닫혀 있어야한다.
  - 변경비용은 가능한 줄이고, 확장비용은 가능한 극대화
  - 추상화와 다형성 활용
- LSP(리스코프 치환원칙): 서브타입은 언제나 기반타입으로 교체할 수 있어야 한다.
  - 서브타입은 기반타입의 약속을 지켜야한다.(접근제한자, 예외 포함)
  - 상속, 인터페이스 이용해 확장성 획득
- ISP(인터페이스 분리원칙): 자신이 사용하지 않는 인터페이스는 구현하지 말아야 한다.
  - 가능한 최소의 인터페이스
  - SRP(클래스의 단일책임) -> ISP(인터페이스 단일책임)
- DIP(의존성 역전 원칙)
  1. 상위모델은 하위모델에 의존 X, 둘다 추상화에 의존.
  2. 추상화는 세부사항에 의존 X, 세부사항은 추상화에 따라 달라진다.
     1. 추상화를 매개로 관계를 느슨하게 한다.
  ### ex1
```java
class PaymentController {
    @RequestMapping(value = "/api/payment", method = RequestMethod.POST)
    public void payment(@RequestBody shinhanCardDto.PaymentRequest req) {
        shinhanCardPaymentService.pay(req);
    }
}

class ShinhanCardPaymentService {
    public void pay(ShinhanCardDto.PaymentRequest req) {
        shinhanCardApi.pay(req);
    }
}

// 새로운 카드사가 추가된다면?

@RequestMapping(value = "/api/payment"), method = RequestMethod.POST)
public void payment(@RequestBody shinhanCardDto.PaymentRequest req) {
    if(req.getType() == CardType.SHINHAN) {
        shinhanCardPaymentService.pay(req);
    } else if(req.getType() == CardType.WOORI) {
        wooriCardPaymentService.pay(req);
    }
}

// DIP 적용
class PaymentController {
    @RequestMapping(value = "/api/payment"), method = RequestMethod.POST)
    public void payment(@RequestBody CardPaymentDto.PaymentRequest req) {
        final CardPaymentService cardPaymentService = 
                cardPaymentFactory.getType(req.getType());
        cardPaymentService.pay(req);
    }
}

public interface CardPaymentService {
    void pay(CardPaymentDto.PaymentRequest req);
}

class shinhanCardPaymentService {
    @Override
    public void pay(CardPaymentDto.PaymentRequest req) {
        shinhanCardApi.pay(req);
    }
}
```
# 2. 간결한 함수 작성하기
# 3. 함수 인수
- 0 ~ 2개 (객체를 넘겨서 줄여보자)
- 가변인자는 잘 사용하지 않는다.
# 4. 안전한 함수 작성하기
- Side Effect 없는 함수: 함수와 관계없는 외부상태를 변경시키면 별로임
  - ex) 패스워드 체크함수인데 세션초기화 기능도 있는것
# 5. 함수 리팩터링
1. 기능을 구현하는 서투른 함수 작성
2. 테스트 코드를 작성(분기마다 빠짐없이)
3. 리팩터링(2번까지의 아웃풋은 동일하다는 것을 전제로, 함수 쪼개고 이름 바꾸고 중복 제거하고 등등 코드의 가독성 향상 및 성능개선)

# java-lotto-refactor
> 내가 작성한 부분이 피드백 받은 내용과 너무 차이가 있어서 로또 게임 프로젝트를 다시 작성해본다.

## 반복문 대신 재귀함수로 구현하는 방법

기존 나의 접근 방식
```
private void getPurchaseMoneyFromUserInput() {
    System.out.println("구입금액을 입력해주세요.");
    long longValue;
    do {
        longValue = getLongValue();
    } while (!GameValidator.checkValidMoney(longValue));

    money = longValue;
}
```
이런식으로 반복문을 통해 입력을 받았다.

피드백: 반복문 대신 재귀함수로 구현한다.
```
private long getPrice() {
    try {
        System.out.println("구입 금액을 입력해 주세요");
        return Long.parseLong(sc.nextLine());
    } catch (IllegalArgumentException e) {
        System.out.println("정상적 숫자가 아닙니다.");
        return getPrice();
    }
}
```

## 객체로 포장하기

기존 나의 접근 방식
```
public class GamePlay {
    private static final int LOTTO_PRICE = 1000;
    private final Scanner sc;
    private long money;
    private List<Lotto> purchasedLottoList;
    private Lotto lastWeekWinningLotto;
    private WinningLotto winningLotto;
    private int bonusNumber;
    
    ...
}
```
원시 타입을 그냥 필드로 썼다면,

피드백: 원시 타입과 문자열을 포장하라
```java
public class Money {
    private static final int MONEY_PER_LOTTO = 1_000;
    private final long money;

    public Money(long money) {
        if (money < MONEY_PER_LOTTO) {
            throw new IllegalArgumentException("로또 구입금액은 1000원 이상이어야 합니다.");
        }
        this.money = money;
    }

    public long getMoney() {
        return money;
    }
}
```
돈 이라는 개념도 객체로 만들어서 관리할 수 있다. 이렇게 작성하니 게임 진행 환경에서 예외처리가 한결 깔끔해지는 것 같다. 

## 객체에 메세지를 보내기

중요한것은 값을 꺼내지 않고 메세지를 보내 로직을 구현한다.
기존 나의 접근 방식
```
public Rank match(Lotto userLotto) {
    int count = (int) userLotto.getNumbers().stream()
            .filter(i -> lotto.getNumbers().contains(i))
            .count();

    boolean isBonusMatch = userLotto.getNumbers().stream()
            .anyMatch(i -> i == bonusNo);

    return Rank.valueOf(count, isBonusMatch);
}
```
몇 개 일치하는지 구해서 Rank를 반환하는 함수이다. 기존의 나는 로또 객체를 받아와 안에 있는 리스트의 값을 꺼내서 계산하였다.

피드백: 객체에 메세지를 보내 값을 구하는 방법
```
    public Rank match(Lotto userLotto) {
        // 객체에 메세지를 보낸다
        int countOfMath = userLotto.countOfMatch(lotto);
        boolean isBonusMatch = userLotto.isContains(bonusNo);

        return Rank.valueOf(countOfMath, isBonusMatch);
    }
```
하지만 이런식으로 객체 자체에 메세지를 보내서 구한다면 훨씬 간결해진다.

물론 Lotto 클래스에 함수를 추가하였다.
```
public int countOfMatch(Lotto lotto) {
    return (int) lotto.getNumbers().stream()
            .filter(numbers::contains).count();
}
```

## 절절한 자료구조 활용

기존 나의 접근 방식
```
private List<Rank> initializeRankList() {
    List<Rank> rankList = new ArrayList<>();
    purchasedLottoList.forEach(lotto -> rankList.add(winningLotto.match(lotto)));
    return rankList;
}

private HashMap<Rank, Long> getRankLongHashMap(List<Rank> rankList) {
    HashMap<Rank, Long> rankLongHashMap = new LinkedHashMap<>();
    calculateLottoHit(rankList, rankLongHashMap);
    return rankLongHashMap;
}

private void calculateLottoHit(List<Rank> rankList, HashMap<Rank, Long> rankLongHashMap) {
    Arrays.stream(Rank.values()).forEach(rank -> rankLongHashMap.put(rank, 0L));
    rankList.forEach(rank -> rankLongHashMap.put(rank, rankLongHashMap.get(rank) + 1));
}
```

GamePlay클래스 내부에서 많은 메소드들을 작성해서 읽히 힘들었다.

피드백: 클래스로 분리한다.

```java
public class LottoResult {
    private Map<Rank, Integer> result = new HashMap<>();

    public LottoResult() {
        for (Rank rank : Rank.values()) {
            result.put(rank, 0);
        }
    }

    public void putResult(Rank rank) {
        result.put(rank, result.get(rank) + 1);
    }
}
```
LottoResult 클래스를 통해서 Map이라는 자료구조를 감쌀 수 있다. 그리고 GamePlay함수에서 이 객체를 생성해 쓰니까 훨씬 읽기 좋아졌다.
한 단계 추상화를 하여 내부적 구현은 숨기고 하려는 역할에만 (여기서는 로또 당첨 결과를 핸들링하는 역할) 집중할 수 있다.

### 결론
이론적으로 배웠던 내용이라도 실제 작은 프로그램을 작성할 때 적용을 전혀 하지 못했다. 실제로 프로젝트를 진행해보면서 보완해 나가자.

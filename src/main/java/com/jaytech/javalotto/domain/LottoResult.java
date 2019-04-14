package com.jaytech.javalotto.domain;

import java.util.HashMap;
import java.util.Map;

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

    public void printHittingStatus() {
        result.entrySet().stream()
                .filter(i -> i.getKey().getWinningMoney() != 0)
                .forEach(i -> i.getKey().printResult(result.get(i.getKey())));
    }

    public void printProfitRatio(Money money) {
        long totalMoney = 0;
        for (Rank rank : Rank.values()) {
            totalMoney += rank.prize(result.get(rank));
        }
        System.out.print("총 수익률은 ");
        System.out.format("%.3f", (double) totalMoney / money.getMoney());
        System.out.println("입니다.");
    }
}

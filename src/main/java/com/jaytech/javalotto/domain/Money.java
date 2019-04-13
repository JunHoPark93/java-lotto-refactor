package com.jaytech.javalotto.domain;

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

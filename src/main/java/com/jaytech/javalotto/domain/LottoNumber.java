package com.jaytech.javalotto.domain;

public class LottoNumber {
    private static final int LOTTO_MIN = 1;
    private static final int LOTTO_MAX = 45;
    private final int number;

    public LottoNumber(int number) {
        if (number < LOTTO_MIN || LOTTO_MAX < number) {
            throw new IllegalArgumentException("로또 숫자 범위가 맞지 않습니다.");
        }
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}

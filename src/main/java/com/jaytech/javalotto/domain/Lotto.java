package com.jaytech.javalotto.domain;

import java.util.List;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public int countOfMatch(Lotto lotto) {
        return (int) lotto.getNumbers().stream()
                .filter(numbers::contains).count();
    }

    public boolean isContains(int bonusNumber) {
        return numbers.contains(bonusNumber);
    }
}

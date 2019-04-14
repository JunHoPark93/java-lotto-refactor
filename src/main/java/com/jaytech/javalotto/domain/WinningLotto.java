package com.jaytech.javalotto.domain;

public class WinningLotto {
    private final Lotto lotto;
    private final int bonusNo;

    public WinningLotto(Lotto lotto, int bonusNo) {
        this.lotto = lotto;
        this.bonusNo = bonusNo;
    }

    public Rank match(Lotto userLotto) {
        // 객체에 메세지를 보낸다
        int countOfMath = userLotto.countOfMatch(lotto);
        boolean isBonusMatch = userLotto.isContains(bonusNo);

        return Rank.valueOf(countOfMath, isBonusMatch);
    }
}

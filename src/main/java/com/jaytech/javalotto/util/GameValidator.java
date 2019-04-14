package com.jaytech.javalotto.util;

import java.util.HashSet;
import java.util.List;

public class GameValidator {
    private static final int LOTTO_PRICE = 1000;
    private static final int MIN_LOTTO_NUM = 1;
    private static final int MAX_LOTTO_NUM = 45;
    private static final int LOTTO_SIZE = 6;

    public static boolean checkValidLottoSize(List<Integer> list) {
        return list.size() == LOTTO_SIZE;
    }

    public static boolean validLottoRange(List<Integer> list) {
        return list.stream().allMatch(i -> MIN_LOTTO_NUM <= i &&  i <= MAX_LOTTO_NUM);
    }
    public static boolean uniqueLottoNumber(List<Integer> list) {
        HashSet<Integer> set = new HashSet<>(list);
        return set.size() == LOTTO_SIZE;
    }
}

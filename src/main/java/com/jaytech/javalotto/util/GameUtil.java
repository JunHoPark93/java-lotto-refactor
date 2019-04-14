package com.jaytech.javalotto.util;

import com.jaytech.javalotto.domain.Lotto;
import com.jaytech.javalotto.exception.LottoException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameUtil {
    public static Lotto convertLottoFromUserInput(String userInput) throws LottoException {
        List<Integer> list = Arrays.stream(userInput.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        if (!GameValidator.checkValidLottoSize(list)) {
            throw new LottoException("로또 숫자는 6개여야 합니다.");
        }
        if (!GameValidator.validLottoRange(list)) {
            throw new LottoException("로또 숫자 범위는 1~45 입니다.");
        }
        if (!GameValidator.uniqueLottoNumber(list)) {
            throw new LottoException("로또 숫자는 서로 중복될 수 없습니다");
        }
        return new Lotto(list);
    }

    public static void printLottoList(List<Lotto> purchasedLottoList) {
        purchasedLottoList.stream().map(Lotto::getNumbers).forEach(System.out::println);
    }
}

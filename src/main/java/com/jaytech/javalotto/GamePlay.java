package com.jaytech.javalotto;

import com.jaytech.javalotto.domain.*;
import com.jaytech.javalotto.exception.LottoException;
import com.jaytech.javalotto.util.GameUtil;
import com.jaytech.javalotto.util.LottoGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GamePlay {
    private static final int LOTTO_PRICE = 1000;
    private final Scanner sc = new Scanner(System.in);
    private Money money;
    private List<Lotto> purchasedLottoList;
    private Lotto lastWeekWinningLotto;
    private WinningLotto winningLotto;
    private LottoNumber bonusNumber;
    private LottoResult lottoResult;

    public void play() {
        getMoneyFromUser();
        generateLottoWithNumber(money.getMoney() / LOTTO_PRICE);
        GameUtil.printLottoList(purchasedLottoList);
        getWinningLottoNumberFromUser();
        getBonusNumberFromUser();
        makeWinningLotto();
        calculateResult();
        GameUtil.printResult(lottoResult, money);
    }

    private void calculateResult() {
        lottoResult = new LottoResult();
        purchasedLottoList.stream()
                .map(lotto -> winningLotto.match(lotto))
                .forEach(lottoResult::putResult);
    }

    private void getMoneyFromUser() {
        try {
            money = new Money(getPrice());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            getMoneyFromUser();
        }
    }

    private long getPrice() {
        try {
            System.out.println("구입 금액을 입력해 주세요");
            return Long.parseLong(sc.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("정상적 숫자가 아닙니다.");
            return getPrice();
        }
    }

    private void generateLottoWithNumber(long purchaseNumber) {
        System.out.println(purchaseNumber + "개를 구매했습니다.");
        List<Lotto> lottoList = new ArrayList<>();
        for (long i = 0; i < purchaseNumber; i++) {
            lottoList.add(LottoGenerator.generateLotto());
        }
        purchasedLottoList = lottoList;
    }

    private void getWinningLottoNumberFromUser() {
        try {
            System.out.println("지난 주 당첨번호를 입력해 주세요.");
            lastWeekWinningLotto = GameUtil.convertLottoFromUserInput(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("정수형식이 아닙니다.");
            getWinningLottoNumberFromUser();
        } catch (LottoException e) {
            System.out.println(e.getMessage());
            getWinningLottoNumberFromUser();
        }
    }

    private void getBonusNumberFromUser() {
        try {
            bonusNumber = new LottoNumber(getBonusNumber());
            checkValidBonusBall(bonusNumber, lastWeekWinningLotto);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            getBonusNumberFromUser();
        }
    }

    private void checkValidBonusBall(LottoNumber bonusNumber, Lotto lastWeekWinningLotto) {
        if (lastWeekWinningLotto.getNumbers().contains(bonusNumber.getNumber())) {
            throw new IllegalArgumentException("보너스 숫자는 로또 번호 6자리와 중복될 수 없습니다.");
        }
    }

    private int getBonusNumber() {
        try {
            System.out.println("보너스 볼을 입력해 주세요.");
            return Integer.parseInt(sc.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("정상 로또 번호가 아닙니다.");
            return getBonusNumber();
        }
    }

    private void makeWinningLotto() {
        winningLotto = new WinningLotto(lastWeekWinningLotto, bonusNumber.getNumber());
    }
}

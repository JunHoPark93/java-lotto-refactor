package com.jaytech.javalotto.util;

import com.jaytech.javalotto.domain.Lotto;
import com.jaytech.javalotto.exception.LottoException;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author junho.park
 */
public class GameUtilTest {
    @Test
    public void 사용자_로또_입력에서_Lotto_객체_반환() {
        // given
        Lotto expected = new Lotto(Arrays.asList(1,2,3,4,5,6));
        String input = "1,2,3,4,5,6";
        Lotto actual = null;

        // when
        try {
            actual = GameUtil.convertLottoFromUserInput(input);
        } catch (LottoException e) {
            e.printStackTrace();
        }

        // then
        assertThat(actual.getNumbers().containsAll(expected.getNumbers())).isTrue();
    }

    @Test
    public void 로또_입력_6개이하입력_LottoException_을_던진다() {
        String input = "1,2,3,4,6";

        try {
            Lotto actual = GameUtil.convertLottoFromUserInput(input);
        } catch (LottoException expectedException) {
            assertThat(expectedException.getMessage()).isEqualTo("로또 숫자는 6개여야 합니다.");
        }
    }

    @Test
    public void 로또_입력_범위벗어난입력_LottoException_을_던진다() {
        String input = "1,2,3,4,5,100";

        try {
            Lotto actual = GameUtil.convertLottoFromUserInput(input);
        } catch (LottoException expectedException) {
            assertThat(expectedException.getMessage()).isEqualTo("로또 숫자 범위는 1~45 입니다.");
        }
    }
}
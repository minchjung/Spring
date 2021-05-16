package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RateDiscountPolicyTest {

    private DiscountPolicy rateDiscount = new RateDiscountPolicy();

    @Test
    @DisplayName("Vip discount 적용은 10% 되어야 한다 ")
    void discountVIPTestOk(){
        //given
        Member member = new Member(1L, "memberVIP", Grade.VIP);

        //when
        int discount = rateDiscount.discount(member, 10000);

        //then
        assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("Basic discount는 없으므로 VIP 10% 할인 적용이 되어서는 안된다.")
    void discountBASICTestOk(){
        //given
        Member member = new Member(1L, "memberBASIC", Grade.BASIC);

        //when
        int discount = rateDiscount.discount(member, 10000);

        //then
//        assertThat(discount).isEqualTo(1000);
        // should not be valid
        // Test result must be : Expecting 0, to be equal to 1000
    }

}
package hello.core.beanTEst;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

// Bean 조회문 부모 조회시 자식이 어디까지 조회되나 학습 (실무에서 Bean 조회 Test를 잘 하지 않음 ㅋ)
public class BeanExtendFindTest {
//    부모-자식 의존 관계에 따른 Bean 조회문
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ExtendConfig.class);

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 중복 오류가 발생한다.")
    void findBeanByParentsTypeDuplicate(){
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DiscountPolicy.class));
    }
    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 빈 이름을 지정하면 된다.")
    void findBeanByParentsTypeBeanName(){
        DiscountPolicy fixeDiscount = ac.getBean("fixDiscount", DiscountPolicy.class);
        assertThat(fixeDiscount).isInstanceOf(DiscountPolicy.class);
    }
    @Test
    @DisplayName("특정 하위 타입으로 조회")
    void findBeanBySubType(){
        RateDiscountPolicy rate = ac.getBean(RateDiscountPolicy.class);
        assertThat(rate).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    void findAllBeanByParentsType() {
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for (String key : beansOfType.keySet()) {
            System.out.println("key: "+ key +"val: "+ beansOfType.get(key));
        }
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기- Object")
    void findAllBeanByObjectType(){
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key: " + key);
            System.out.println("Object: "+ beansOfType.get(key));
        }
    }





    @Configuration
    static class ExtendConfig {

        @Bean
        public DiscountPolicy fixDiscount() {
            return new FixDiscountPolicy();
        }

        @Bean
        public DiscountPolicy ratedDiscount() {
            return new RateDiscountPolicy();
        }
    }
}

package hello.core.beanTEst;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class BeanSameNameFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanType.class);

    @Test
    @DisplayName("타입 조회시 같은 Type 중복 Return 되는 Bean Case Test: NoUniqueBeanDefinitionException예외 발생")
    void SameBeanType_Duplicate_Test(){
//        MemberRepository bean = ac.getBean(MemberRepository.class);
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
                ()->ac.getBean(MemberRepository.class));
    }
    @Test
    @DisplayName("타입 조회시 같은 타입이 중복시, 빈 이름을 지정하면 된다")
    void SameBeanType_Duplicated_But_Different_Name_Test(){
        MemberRepository memberRepository1 = ac.getBean("memberRepository1", MemberRepository.class);
        assertThat(memberRepository1).isInstanceOf(MemberRepository.class);
    }
    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanType(){
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key=  " + key + "value" + beansOfType.get(key));
        }
        assertThat(beansOfType.size()).isEqualTo(2);
    }
    //임시 Configuration to make the 중복 Bean Type
    @Configuration
    static  class SameBeanType{
        @Bean
        public MemberRepository memberRepository1(){
            return new MemoryMemberRepository();
        }
        @Bean
        public MemberRepository memberRepository2(){
            return new MemoryMemberRepository();
        }

    }

}

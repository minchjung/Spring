package hello.core.beanTEst;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class BeanBasicFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회 ")
    void findBeanByName(){
        // Spring에 등록된 Bean 객체의 Type= AppConfig에 설정한 MemberService 객체 Type과 같은지 Test
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
    }
    @Test
    @DisplayName("빈 타입으로 조회 (인터페이스)")
    void FindBeanByType(){
        // Spring에 등록된 Bean 객체의 Type= AppConfig에 설정한 MemberService 객체 Type과 같은지 Test
        MemberService bean = ac.getBean(MemberService.class);
        assertThat(bean).isInstanceOf(MemberService.class);

    }
//  NOT Important
    @Test
    @DisplayName("빈 구체 타입으로 조회(인터페이스x -구체 객체타입)")
    void FindBeanByTpye2(){
        // Spring에 등록된 Bean 객체의 Type= AppConfig에 반환하는 구체 타입인 MemberServiceImpl Type과 같은지 Test
        MemberServiceImp memberServiceImp = ac.getBean(MemberServiceImp.class);
    }
    @Test
    @DisplayName("빈 이름으로 조회2- Wrong,예외 케이스 TEST")
    void FindBeanByName2(){
//        MemberService noNameInBean = ac.getBean("noNameInBean", MemberService.class);
//      위 먼저 테스트해서 나오는 예외 type을 assertThrows로 설정해주고 Test
        org.junit.jupiter.api.Assertions.assertThrows(NoSuchBeanDefinitionException.class,
                ()->ac.getBean("noNameInBean", MemberService.class));
    }
}


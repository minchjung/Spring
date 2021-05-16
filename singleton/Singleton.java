package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Singleton {

//    순수 자바 클래스가 Client(Service)단의 요청에 객체를 무수히 많이 생성해야 하는 상황을 가정 해보고 Test Case를 짜 본다
    // 이는 Spring의 singlton을 비교 이해하기 위함

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너 ")
    void pureContainer() {
        // 순수 자바 수동 DI로 객체를 2개 생성해 보고 그냥 Test만 돌려본다 (두개 다른지.. )
        AppConfig appConfig = new AppConfig();

        // 요청 마다 객체를 생성 1
        MemberService memberService1 = appConfig.memberService();
        // 요청 마다 객체를 생성 2
        MemberService memberService2 = appConfig.memberService();

        // 다른거 그냥 Test
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }
    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singltonServiceTest(){
        //객체 1
        SingletonService instance1 = SingletonService.getInstance();
        //객체 2
        SingletonService instance2 = SingletonService.getInstance();
        //여러번 불러도 instance는 하나
        Assertions.assertThat(instance1).isSameAs(instance2);
    }
    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);
        // Spring은 bean 객체를 SingleTon으로 관리해 준다. 호출 마다 객체를 생성해 반환 하지 않고, 같은 객체를 반환한다.
        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }
}

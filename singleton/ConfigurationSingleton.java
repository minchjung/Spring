package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImp;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingleton {

    @Test
    @DisplayName("싱글톤 관리로 Bean에 등록된, MemoryRepository 생성자 주입 method 는 2번 이상 호출 하지 않는다.")
    void ConfigurationSingletonTest(){

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImp memberService = ac.getBean("memberService", MemberServiceImp.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        // AppConfig 각각 DI 메소드에, 호출 로그를 미리 남겨 두었다.
        // memberService. orderService 객체는 DI로 MemberRepository Class 가 필요하고,
        // 이는 AppConfig 의 또 다른 메소드 중 하나인 (MemberRepository 의 구체 impl 을 설정 해주는)
        // memoryRepository 메소드에 의해 결정 된다.
        // 참고로 여기서 MemberRepository 의 구체 impl-type 은 MemoryMemberRepository 이다.

        // => 결론 적으로, memberService, orderService 의  객채 생성화는 (by Bean 호출)
        // 순수 자바 코드 로직에 의해 memberRepository 메소드 호출이 2번 일어 나야 한다.
        // 그리고 두 객체 각각의 맴버 변수로 가지고 있는 MemberRepository 객체는 다른 주소 값 을 가지는게 옳다.

        MemberRepository memberRepository_by_memberService = memberService.getMemberRepository();
        System.out.println("memberRepository_by_memberService = " + memberRepository_by_memberService);

        MemberRepository memberRepository_by_orderService = orderService.getMemberRepository();
        System.out.println("memberRepository_by_orderService = " + memberRepository_by_orderService);

        // 그러나  스프링은 이를 Configuration bitecode 작업을 통해 Singleton  관리를 해 주기 때문에,
        // 스프링 컨테이너에 등록된 bean 객체는 컨테이너에 등록 하며 생성한 객체 하나의 주소값만 을 가지며, 이를 반환한다,
        // 즉, bean 등록시 최초 한번만 생성하게 된고
        // 자바 코드 -생성자 DI 메소드를 2번 이상 호출 하지 않게 된다.(자바 로직으로 여기서 정확히 3번 호출 하게 된다)
        // memberRepository_by_memberService 와 memberRepository_by_orderService 두 객체는 같은 객체임을 확인 할 수 있다.
    }

    // AppConfig 에서 @Configuration <-- 문구를 주석 처리 하였다.
    @Test
    @DisplayName("Configuration 없이 등록된 bean들은 singleton 관리가 되지 않는다.")
    void singletonTest_without_configuration_biteCode(){
        // Spring-@Configuration 작업을 거치지 않고, 같은 방법으로 위 작업을 Test 한다면,
        // 컨테이너에 @bean<- 표시된 빈 등록은 문제 없이 수행 가능 하지만,
        // 등록된 bean 이 두번 이상 호출 될때, 호출 마다 새로운 객체를 생성하며 반환 해 주게 되는것을 아래에서 확인 가능 하다.
        // 즉, singleton 방식이 지켜 지지 않음을 알 수 있다.

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        // 위 작업만 Test 한 결과
        // (참조) AppConfig 하위 bean 들을 모두 bean 등록 처리가 되며. AppConfig는 되지 않는다.
        // 또한, memberRepository 메소드가 3번 호출 됨을 알 수 있다.

        //memberService 객체의 memoryRepository  와 orderService 객체의 memoryRepository 가 같은지 체크하자
        // 싱글톤 관리가 안되고 각각 생성 되었으므로, 다름을 알 수 있다.
        MemberServiceImp memberService = ac.getBean(MemberServiceImp.class);
        OrderServiceImpl orderService = ac.getBean(OrderServiceImpl.class);

        MemberRepository memberRepository_by_memberService = memberService.getMemberRepository();
        MemberRepository memberRepository_by_orderService = orderService.getMemberRepository();

        System.out.println("memberRepository_by_memberService = " + memberRepository_by_memberService);
        System.out.println("memberRepository_by_orderService = " + memberRepository_by_orderService);
        // @Configuration <-- 주석 해제 해주자
        // MemberServiceImp 과 OrderServiceImp 에 Test 용도의 getMemberRepository 주석 해제 필요 but not yet
        //끝
    }




}

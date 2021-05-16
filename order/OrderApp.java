package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImp();
//        OrderService orderService = new OrderServiceImpl();

        //Member,Repository,DiscountPolicy,Order 의 의존성을 구현체로 부터
        // 완전히 분리시키며 OCP,DI를 준수
        // (오직 인터페이스에만 의존하며, 각각의 생성자에서 구체적인 구현체를 외부[AppConfig]로부터 받아서 할당한다.)
        // ([AppConfig]는 각각의 Service-클래스 생성자에 필요한 parameter[구체화된 구현체]를 주입해서 생성자를 실행시켜준다)

//       AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();

//      SPring Container 사용
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);


        //create Member to join (Needs MemberService )
        Long memberID = 1L;
        Member member= new Member(memberID, "mamBA", Grade.VIP);
        memberService.join(member);


        //Set the Order via discountPolicy (Needs OrderService) 
        Order order = orderService.createOrder(memberID, "itemA", 20000);
        System.out.println("order = " + order);
    }
}

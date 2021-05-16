package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImp;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MemberService memberService(){
        System.out.println("Call : AppConfig.memberService");
        return new MemberServiceImp(memberRepository());

    }
    @Bean
    public MemberRepository memberRepository() {
        System.out.println("Call :  AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService(){
        System.out.println("Call :  AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
//         Lombok 처리로 인해 주석 처리
//        return null ;
    }
    @Bean
    public DiscountPolicy discountPolicy(){

        return new RateDiscountPolicy();
    }
}

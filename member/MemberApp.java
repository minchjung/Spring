package hello.core.member;

import hello.core.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
        //       신규등록 MemberServiceImpl 구현체에서 처리

        //MemberService memberService = new MemberServiceImp();

//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member); // << join

        System.out.println("new member = " + member.getName()); //<< 저장소에서 조회
        System.out.println("find member = " + memberService.findMember(1L).getName()); //<< 조회
//      일치 여부 테스트
    }
}

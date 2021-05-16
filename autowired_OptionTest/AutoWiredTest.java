package hello.core.autowiredOption;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutoWiredTest {

    @Test
    void autoWiredOptionTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(NoBeanAppConfig.class);
    }

    // 임의 TestBean으로 등록 @Configuration 없이 그냥 주입 등록해 bean등록 객체로 설정
    // 이 녀석이 Spring 등록 될때 이녀석의 method 의존 관계 설정을 option 처리해 살펴보자
    static class NoBeanAppConfig{

        // 1번: @Autowired(required = false)
        @Autowired(required = false)
        public void setNoBean1(NoBean noBean1){
            System.out.println("noBean1 = " + noBean1);
        }
        // Spring 에서 의존 관계가 등록 되지 않은 bean 이라면 호출 자체를 하지 않는다.


        // 2번 : @Nullable 처리
        @Autowired
        public void setNoBean2(@Nullable  NoBean noBean2) {
            System.out.println("noBean2 = " + noBean2);
            // 호출 하지만 의존 관계 설정 되지 않는다.
        }
        @Autowired
        // 3번 : java Optional 사용
        public void setNoBean3(Optional<NoBean> noBean3){
            System.out.println("noBean3 = " + noBean3);
        }
        // 호출 하지만 의존 관계 설정 되지 않는다.
    }
    // Bean 등록 아닌 객체 임의 생성
    static class NoBean {

    }
}

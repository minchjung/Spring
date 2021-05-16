package hello.core.licecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTEst {

    @Test
    void lifeCycleTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);

        NetworkClient networkClient = ac.getBean(NetworkClient.class);
        ac.close();
    }


    @Configuration
    static class LifeCycleConfig {
        // initMethod 와 destroyMethod 를 실행할 메소드 bean을 따로 등록해준다 by annotation
        // 스브링빈 이나 스프링 코드에 의존 하지 않고 초기화 와 종료가 가능 하다는 큰 장점이 있다.
        // 빈의 destory 메소드는 특이하게 default 로 추론 설정되있다 inferred
        // close 나 destory, shutdown 등의 메소드를 찾아서 실행 시켜준다.  자동 등록 처럼 실행된다.
//        @Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }

    }
}

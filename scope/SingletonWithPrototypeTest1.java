package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.assertj.core.api.Assertions;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

public class SingletonWithPrototypeTest1 {

    @Test
    void protoTypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoTypeBean.class);

        ProtoTypeBean protoTypeBean1 = ac.getBean(ProtoTypeBean.class);
        protoTypeBean1.addCount();
        Assertions.assertThat(protoTypeBean1.getCount()).isEqualTo(1);

        ProtoTypeBean protoTypeBean2 = ac.getBean(ProtoTypeBean.class);
        protoTypeBean2.addCount();
        Assertions.assertThat(protoTypeBean2.getCount()).isEqualTo(1);

    }
    @Test
    void SingletonClientsPrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, ProtoTypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        Assertions.assertThat(clientBean1.logic()).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
//        Assertions.assertThat(clientBean2.logic()).isEqualTo(2);
        Assertions.assertThat(clientBean2.logic()).isEqualTo(1);

    }
    @Scope("singleton")
    static class ClientBean{
//        private final ProtoTypeBean protoTypeBean;
        @Autowired
        private Provider<ProtoTypeBean> protoTypeBeanProvider;

//        private ObjectProvider<ProtoTypeBean> protoTypeBeanObjectProvider;
        //ObjectFactory <-- 도 가능함. (ObjectProvider의 Interface임)
        // 생성자 DI 가 아니라 ProtoType 객체가 필요할 때 마다 생성 하므로 필요한 로직 메소드 아래에서 의존 관계를 만들어준다.
//        @Autowired
//        public ClientBean(ProtoTypeBean protoTypeBean){
//            this.protoTypeBean = protoTypeBean;
//        }
        public int logic(){
//            Dependency Look-up
            ProtoTypeBean protoTypeBean = protoTypeBeanProvider.get();
            protoTypeBean.addCount();
            return protoTypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class ProtoTypeBean {
        private int count ;

        public void addCount(){
            this.count++;
        }
        public int getCount(){
            return count ;
        }
        @PostConstruct
        public void init(){
            System.out.println("ProtoTypeBean.init" + this);
        }
        @PreDestroy
        public void destroy(){
            System.out.println("ProtoTypeBean.destroy");
        }

    }
}

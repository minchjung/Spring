package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);

        // Include annotation 할당된 beanA 는 당연히 등록되고
        BeanA beanA = ac.getBean(BeanA.class);
        Assertions.assertThat(beanA).isNotNull();
        // Exclude annotation 할당된 beanB 는 등록되지 않음
//        BeanB beanB = ac.getBean(BeanB.class);
        org.junit.jupiter.api.Assertions.assertThrows(NoSuchBeanDefinitionException.class,
        () -> ac.getBean(BeanB.class));
    }

    @Configuration
    @ComponentScan(
            includeFilters =  @ComponentScan.Filter(type=FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @ComponentScan.Filter(type=FilterType.ANNOTATION ,classes = MyExcludeComponent.class)
    )
    static class ComponentFilterAppConfig{

    }
}

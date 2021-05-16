package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Configuration
@ComponentScan(
        // Spring-Boot 다른 Configuration 수동 bean 등록 과정을 없애 줘야 하므로, Configuration Annotation은 bean
        // component 대상에서 제외 시켜주자 (교육 설정상, 자동 등록시 수동 등록 과정을 빼 주는 것 )
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoConfig {
    // @Component: bean 등록 대상 Class 에  annotation을 붙여준다. 그리고 DI 는 @Autowired를 붙여주면,
    //의존 관계 연결 필요시 주입 Type을 등록된 bean 중에 탐색해 해당 type을 연결 주입 해주게 된다.
}

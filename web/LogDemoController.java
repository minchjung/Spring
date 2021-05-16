package hello.core.web;


import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request){
        String requestURL = request.getRequestURL().toString();
        // Spring proxy 객체가 생성한 가짜 Mylogger 클래스가 주입되 있기 때문에
        // Spring이 임의로 생성한  가짜 Mylogger 클래스가 나타남
        System.out.println("Who the Fuck are you, Mylogger=" +myLogger.getClass());
        myLogger.setRequestURL(requestURL);
        // request 호출 된 시점에서 진짜 Mylogger 생성주기에 맞게 생성된 원본 클래스를 위임 해주는 로직이  proxy 객체에 존재.
        // 주입된 가짜 클래스는 singleton 주기와 같기 때문에 사실상 request 주기, prototype등 의 한계성 빈 주기와는 상관이 없다.
        // 다만, 실제 생성 주기에 맞게 생성되 빈 등록 처리가 되면, proxy에서 조회 후 실제 클래스로 바꿔주는 기능을 담당한다.
        // 같은 로직으로, destroy 시, 생명 주기가 끝난 해당 클래스를 삭제하고, 다시 가짜 클래스를 주입해 의존성을 유지 시켜준다.
        myLogger.log("Controller Test");

        logDemoService.logic("testid");
        return "OK";
        //Provider 를 사용하든, Proxy 객체를 사용하든, bean 객체의 필요 시점까지, 의존성 관계를 지연 시켜주는게 핵심이다.
    }

}

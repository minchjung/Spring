package hello.core.licecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient{
    private String url ;

    public NetworkClient(){
        System.out.println("생성자 호출 and  url = " + url);

    }
    public void setUrl(String url) {
        this.url = url;
    }
    // 서비스 시작시 호출
    public void connect(){
        System.out.println("connect to = "+ url);
    }
    public void call(String message){
        System.out.println("call to =" + url +"Message= " + message);
    }
    //서비스 종료시 호출
    public void disconnect(){
        System.out.println("disconnect to ="+ url);
    }
    @PostConstruct
    public void init() {
        // 이후, 초기화 작업을 진행 해주거나 알림 메세지 등으로 로그 분석이 가능 하게 만들어 주는 방식으로 활용
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메세지");
    }
    @PreDestroy
    public void close() {
        //App 사용 종료시점에 log 메세지나 기능들을 호출 해주는 방식으로 활용
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
}



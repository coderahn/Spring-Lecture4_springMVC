package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Slf4j : 아래 log 코드 귀찮으면 이 어노테이션 사용
@Slf4j
@RestController
public class LogTestController {
    //Logger는 org.lsf4j.Logger 인터페이스 사용하고 getClass()로 자신을 넣으면 됨
    //private final Logger log = LoggerFactory.getLogger(getClass());
    //private final Logger log = LoggerFactory.getLogger(LogTestController.class);


    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        log.trace("trace log={}", name);
        log.debug("debug log={}", name); //개발서버
        log.info("info log={}", name);
        log.warn("warn log={}", name); //경고
        log.error("error log={}", name); //에러

        //2022-06-15 23:21:27.804  INFO 15428 --- [nio-8080-exec-4] hello.springmvc.basic.LogTestController  : info log=Spring
        //nio-8080-exec-4는 쓰레드풀에서 실행한 쓰레드

        //@RestController를 사용했기 때문에 viewName이 아니라 String 바로 반환
        return "ok";
    }
}

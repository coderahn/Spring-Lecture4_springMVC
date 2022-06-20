package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * HttpServletRequest로 HTTP 메시지 바디에서 데이터를 읽어온 후 문자 변환 -> Jackson라이브러리로 자바객체 변환
     */
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        //json형식 요청 데이터를 그대로 출력
        //messageBody={"username":"hello", "age": "20"}
        log.info("messageBody={}", messageBody);

        //json 형식으로 요청 온 데이터를 HelloData로 역직렬화.
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

        //역직렬화된 HelloData 데이터 출력
        //username=hello, age=20
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }

    /**
     * 요청 데이터를 바로 objectMapper로 역직렬화하여 객체로 변환
     */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        //json형식 요청 데이터를 그대로 출력
        //messageBody={"username":"hello", "age": "20"}
        log.info("messageBody={}", messageBody);

        //json 형식으로 요청 온 데이터를 HelloData로 역직렬화.
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

        //역직렬화된 HelloData 데이터 출력
        //username=hello, age=20
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * @RequestBody생략하면 @ModelAttribute가 생략된 것으로 판단 되므로 생략 불가
     * 요청 데이터를 objectMapper 없이 바로 객체로 받을 수 있음
     * json 데이터 형식(content-type:application/json)을 받으면 HTTP메시지컨버터가 ObjectMapper역할을 해줌
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * HttpEntity로도 json형식을 받을 수 있다.
     * json 데이터 형식(content-type:application/json)을 받으면 HTTP메시지컨버터가 ObjectMapper역할을 해줌
     */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) throws IOException {
        HelloData data = httpEntity.getBody();
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return "ok";
    }

    /**
     * 반환타입도 HelloData타입으로 가능
     * HttpMessageConverter에 의해 객체가 json문자형태로 변환되어 반환
     * 요청시 json to object / 응답시 object to json
     * 응답 나갈 때 json으로 나갈지 판단은 요청시 accept 확인해야 함
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) throws IOException {
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return data;
    }
}

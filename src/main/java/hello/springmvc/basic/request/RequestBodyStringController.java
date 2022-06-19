package hello.springmvc.basic.request;

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
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        
        //내가 받은 바이트스트림을 어떤 인코딩 할지 지정 필요
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

    /**
     * InputStream, OutputStream, Writer 등을 직접 받을 수 있음
     * @throws IOException
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        //내가 받은 바이트스트림을 어떤 인코딩 할지 지정 필요
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        responseWriter.write("ok");
    }

    /**
     * HttpEntity : httpHeader, httpBody 편리하게 조회 가능
     * RequestEntity, ResponseEntity는 HttpEntity를 상속한 객체(몇 가지 기능 더 제공)
     * 스프링MVC 내부에서 HTTP 메시지 바디를 읽어 문자 및 객체 변환하여 전달하는데, 이 때 HTTPMessageConverter 기능 사용
     * @throws IOException
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {

        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("ok");
    }

    /**
     * 요청 메시지를 @RequestBody로 처리 가능
     * 헤더정보 필요시 @RequestHeader, HttpEntity 추가 사용
     * 메시지 바디 직접 조회하는 기능은 요청 파라미터를 조회하는 @RequestParam, @ModelAttribute와는 전혀 관계 없음
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {

        log.info("messageBody={}", messageBody);

        return "ok";
    }
}

package com.sda.javakrk24.library.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@Slf4j
public class HelloController {

    //    @GetMapping("/hello")
    @RequestMapping(method = RequestMethod.GET, path = "/hello", produces = MediaType.TEXT_HTML_VALUE)
    /* w response header content-type zostanie ustawiony na wartosc text/html */
    public String helloWorld() {
        return "<h1>Hello World!</h1>";
    }

    @GetMapping("/hello2")
    public String helloSomeone(HttpServletRequest servletRequest) {
        String name = servletRequest.getParameter("name");
        return String.format("<h1>Hello %s!</h1>", Optional.ofNullable(name).orElse("World"));
    }

    @GetMapping("/hello3")
    public String helloBySpring(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name) {
        return String.format("<h1>Hello %s!</h1>", name);
    }

    @GetMapping("/hello4/{name}")
    public String helloWithPathVariable(@PathVariable("name") String name){
        return String.format("<h1>Hello %s!</h1>", name);
    }

    @PostMapping("/hello")
    public String logAndReturn(@RequestBody String request){
        log.info("endpoint /postEndpoint called with" + request);
        return request;
    }

}

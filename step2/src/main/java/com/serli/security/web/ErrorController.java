package com.serli.security.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/error")
public class ErrorController {

    @GetMapping( produces = "text/html")
    void error(HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/");
    }
}

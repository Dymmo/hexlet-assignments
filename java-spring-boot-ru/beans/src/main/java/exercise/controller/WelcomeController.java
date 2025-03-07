package exercise.controller;

import exercise.daytime.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

// BEGIN
@RestController
@RequestMapping(path = "/welcome")
public class WelcomeController {
    @Autowired
    private ApplicationContext ctx;

    @GetMapping
    public String get() {
        Daytime daytime = (Daytime) ctx.getBean("Daytime");
        return daytime.getName();
    }
}
// END

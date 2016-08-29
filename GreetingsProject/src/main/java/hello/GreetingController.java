package main.java.hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
private final static Logger LOGGER = Logger.getLogger(GreetingController.class.getName()); 
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		LOGGER.info("In greeting");
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
							LOGGER.info("End greeting");
    }
}
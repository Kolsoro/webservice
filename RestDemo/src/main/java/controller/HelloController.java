package controller;

import java.util.Locale;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import beans.HelloWorldBean;

@RestController
public class HelloController {
	
	@Autowired
	private MessageSource messageSource;
	
   @GetMapping("/hello-world")
	public HelloWorldBean helloWorld() {
		return new HelloWorldBean("HelloWorld!!!");
	}
   
   @GetMapping("/hello-world/path/{name}+{name2}")
   public HelloWorldBean helloPath(@PathVariable("name") String naam,@PathVariable("name2") String naam2) {
	   return  new HelloWorldBean(String.format("Hello World,%s,hi %s", naam,naam2));
   }

   @GetMapping("/hello-World-international")
   public String helloWorldInternational(//@RequestHeader(name="Accept-Language" ,required = false) Locale locale)
		   ){
	   return messageSource.getMessage("good.morning.message", null,"Default message", LocaleContextHolder.getLocale());
   }
   
   
}

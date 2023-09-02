package edu.fra.uas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import edu.fra.uas.service.MessageService;

@SpringBootApplication
public class BeanBeispielExtendedApplication {

	@Autowired
	private MessageService messageService;

	public static void main(String[] args) {
		SpringApplication.run(BeanBeispielExtendedApplication.class, args);
	}

	@Bean
	CommandLineRunner init(){
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) {
				messageService.setMessage("Hello World");
				System.out.println(messageService.getMessage());
				messageService.setMessage("--> HHHOHHH <--");
				System.out.println(messageService.getMessage());
			}
			
		};
	}

}

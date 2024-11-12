package edu.fra.uas;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import edu.fra.uas.model.User;
import edu.fra.uas.service.UserService;

@SpringBootApplication
public class GraphQlClientApplication {

	private static final Logger log = LoggerFactory.getLogger(GraphQlClientApplication.class);

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(GraphQlClientApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return args -> {
			log.info("### Start GraphQL-Client ###");

			log.info("--> Retrieve all users");
			List<User> usersList = userService.getAllUsers();
			if (usersList != null) {
				for (User user : usersList) {
					log.info(user.toString());
				}
			}

			log.info("--> Retrieve 3 users starting by id 2");
			usersList = userService.getUsers(3, 1);
			if (usersList != null) {
				for (User user : usersList) {
					log.info(user.toString());
				}
			}

			log.info("--> Get user by id 3");
			User user = userService.getUserById(3);
			if (user != null)
				log.info(user.toString());

			log.info("--> Add a new user: Darth Vader");
			user = userService.addUser("USER", "DARTH", "VADER", "vader@imperium.univ", "deathstar");
			if (user != null)
				log.info(user.toString());

			log.info("--> Update user with id 4");
			user = userService.updateUser(4, "USER", "Bruce", "Banner", "hulk@marvel.com", "UAAAARRRGGG!!!");
			if (user != null)
				log.info(user.toString());
			
			log.info("--> Delete user with id: 7");
			Integer integer = userService.deleteUser(7);
			if (integer != null){
				if (integer.intValue() == 1) {
					log.info("User deleted!");
				}
			} else {
				log.info("User not found!");
			}
			
			log.info("### Stop GraphQL-Client ###");
			System.exit(0);
		};
	}
}

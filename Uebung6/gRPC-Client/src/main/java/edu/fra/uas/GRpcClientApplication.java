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
public class GRpcClientApplication {

	private static final Logger log = LoggerFactory.getLogger(GRpcClientApplication.class);

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(GRpcClientApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return args -> {
			log.info("### Start gRPC-Client ###");

			log.info("--> Retrieve all users");
			List<User> usersList = userService.getAllUsers();
			if (usersList != null) {
				for (User user : usersList) {
					log.info(user.toString());
				}
			}

			log.info("--> Retrieve user with id 3");
			User user = userService.getUserById(3L);
			if (user != null) {
				log.info(user.toString());
			}

			log.info("--> Create new user");
			User newUser = new User(20, "HUMAN", "Bruce", "Lee", "bruce@lee.hk", "nunchaku");
			user = userService.createUser(newUser);
			if (user != null) {
				log.info(user.toString());
			}

			log.info("--> Retrieve all users");
			usersList = userService.getAllUsers();
			if (usersList != null) {
				for (User user2 : usersList) {
					log.info(user2.toString());
				}
			}

			log.info("--> Update user with id 20");
			newUser = new User(20, "HUMAN", "Bruce", "Lee", "bruce@lee.hk", "sai_fork");

			log.info("--> Retrieve all users");
			usersList = userService.getAllUsers();
			if (usersList != null) {
				for (User user2 : usersList) {
					log.info(user2.toString());
				}
			}

			log.info("--> Delete user with id 7");
			Boolean result = userService.deleteUser(7L);
			if (result) {
				log.info("User with id 7 deleted");
			}

			log.info("### Stop gRPC-Client ###");
			System.exit(0);
		};
	}

}


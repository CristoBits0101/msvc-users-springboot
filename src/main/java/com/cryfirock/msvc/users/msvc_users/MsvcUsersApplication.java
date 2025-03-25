package com.cryfirock.msvc.users.msvc_users;

/**
 * Dependencies
 */
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.boot.SpringApplication;

@EnableAspectJAutoProxy
@SpringBootApplication
public class MsvcUsersApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(MsvcUsersApplication.class, args);
	}

}

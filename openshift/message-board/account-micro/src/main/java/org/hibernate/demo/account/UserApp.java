package org.hibernate.demo.account;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.thorntail.Main;

@ApplicationPath( "/" )
public class UserApp extends Application {
	public static void main(String... args) throws Exception {
		Main.main(args);
	}
}

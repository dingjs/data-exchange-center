package data.exchange.center.ommp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPasswordEncoder {

	public static void main(String[] args) {
		String password = new BCryptPasswordEncoder().encode("admin");
		System.out.println(password);
	}
}

package com.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CrudApplication {


	@GetMapping("/home")
	public String Simple() {
		return "Done serving";
	}

	public static void main(String[] args)  {
//		System.setProperty("javax.net.ssl.trustStore", "none");
		SpringApplication.run(CrudApplication.class, args);
	}

}

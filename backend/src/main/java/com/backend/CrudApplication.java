package com.backend;

import com.backend.Entity.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CrudApplication {


	@GetMapping("/home")
	public String Simple() {
		return "Done serving";
	}

	public static void main(String[] args)  {
		SpringApplication.run(CrudApplication.class, args);
	}
}

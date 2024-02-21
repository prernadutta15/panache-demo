package org.example.panachedemo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PanacheDemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(PanacheDemoApplication.class, args);
		System.out.println("Hello Prerna!");
	}
}

package org.example.panachedemo;

import ch.qos.logback.core.CoreConstants;
import org.example.panachedemo.models.Product;
import org.example.panachedemo.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PanacheDemoApplicationTests {

	@Autowired
	private ProductRepository productRepository;
	@Test
	void contextLoads() {
	}

	@Test
	void exploreQueries()
	{
		List<Product> productList = productRepository.findByTitleContaining("prod");
		System.out.println("____________________________________");
		for(Product prod: productList)
			System.out.print(prod);
		System.out.println("____________________________________");
	}
}

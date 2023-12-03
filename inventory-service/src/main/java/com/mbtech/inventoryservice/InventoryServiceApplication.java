package com.mbtech.inventoryservice;

import com.mbtech.inventoryservice.model.Inventory;
import com.mbtech.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	public CommandLineRunner loadDataTest(InventoryRepository inventoryRepository){
		return args -> {

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("ABGL12365");
			inventory1.setQuantity(2);
			inventoryRepository.save(inventory1);

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("ABGL0000");
			inventory2.setQuantity(4);
			inventoryRepository.save(inventory2);


			Inventory inventory3 = new Inventory();
			inventory3.setSkuCode("ABGL0000Z2");
			inventory3.setQuantity(5);
			inventoryRepository.save(inventory3);

			Inventory inventory4 = new Inventory();
			inventory4.setSkuCode("ABGL0000ZAA2");
			inventory4.setQuantity(0);
			inventoryRepository.save(inventory4);


		};

	}

}

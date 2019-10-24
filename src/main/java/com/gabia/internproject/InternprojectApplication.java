package com.gabia.internproject;


import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;


@SpringBootApplication
public class InternprojectApplication implements CommandLineRunner {


	@Autowired
	private ApplicationContext context;


	public static void main(String[] args) {
		SpringApplication.run(InternprojectApplication.class, args);


	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(Arrays.asList(context.getBeanDefinitionNames()));
	}




}

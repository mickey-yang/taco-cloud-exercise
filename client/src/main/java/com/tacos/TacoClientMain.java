package com.tacos;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TacoClientMain {
    public static void main(String[] args) {
        SpringApplication.run(TacoClientMain.class, args);
        System.out.println("Hello world!");
    }
}
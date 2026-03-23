package com.accenture.onlinepizzeriaapi;

import org.springframework.boot.SpringApplication;

public class TestOnlinePizzeriaApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(OnlinePizzeriaApiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}

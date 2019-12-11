package top.weimumu.loginapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.weimumu.loginapi.dao")
public class LoginApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginApiApplication.class, args);
    }

}

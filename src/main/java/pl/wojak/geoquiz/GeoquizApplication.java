package pl.wojak.geoquiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeoquizApplication {

    public static void main(String[] args) {

        SpringApplication.run(GeoquizApplication.class, args);

        System.out.println("cześć tu geoquiz");
    }
}

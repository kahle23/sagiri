package sagiri;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The startup class.
 * @author Sagiri
 */
@Slf4j
@SpringBootApplication
@MapperScan(basePackages = {"sagiri.*.mapper"})
public class SagiriApplication {

    /**
     * The program entry.
     * @param args The input parameter
     */
    public static void main(String[] args) {
        SpringApplication.run(SagiriApplication.class, args);
        log.info("The sagiri start success! ");
    }

}

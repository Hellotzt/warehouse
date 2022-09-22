package com.tzt.warehouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 帅气的汤
 */
@SpringBootApplication
@MapperScan("com.tzt.warehouse.mapper")
public class WareHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WareHouseApplication.class, args);

    }

}

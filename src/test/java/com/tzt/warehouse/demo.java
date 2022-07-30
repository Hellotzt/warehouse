package com.tzt.warehouse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author：帅气的汤
 */
@SpringBootTest
public class demo {
    @Test
    public int test() {
        int a = 1;
        try {
            int b = 1 / 0;
            return a;
        } catch (Exception e) {
            System.out.println(11);
            return a;
        } finally {
            ++a;
        }
    }

    @Test
    public void test2(){
        String str1 = "123";
        int num = 123;
        String str2 = num+"";
        String str4 = num+"";
        System.out.println(str2==str4);
        String str3 = 123 +"";
        System.out.println(str1 == str2);
        System.out.println(str1 ==str3);
    }
}

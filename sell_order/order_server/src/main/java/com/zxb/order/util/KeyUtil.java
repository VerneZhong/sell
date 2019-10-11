package com.zxb.order.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-09-25 16:45
 */
public class KeyUtil {

    public static String getUniqueKey() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}

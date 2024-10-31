package org.hae.tasklogue.utils;

import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class TaskIdGenerator {
    private static final String PREFIX = "#task_";
    public String generateTaskId() {
        Random random = new Random();


        int randomNumber = 100 + random.nextInt(900);


        String randomNumberString = String.valueOf(randomNumber);


        return PREFIX + randomNumberString;
    }
}

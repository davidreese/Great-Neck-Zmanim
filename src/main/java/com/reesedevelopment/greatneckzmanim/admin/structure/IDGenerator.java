package com.reesedevelopment.greatneckzmanim.admin.structure;

import com.reesedevelopment.greatneckzmanim.tools.NumberTools;

import java.util.Date;

public interface IDGenerator {
//    private String id = generateID();

    default String generateID(Character prefix) {
        Long timeSinceEpoch = (new Date()).getTime();
        return String.format("%s%s%s", prefix, timeSinceEpoch.toString().substring(5), random());
    }

    private String random() {
        return String.valueOf(NumberTools.getRandomNumber(1000, 9999));
    }
}

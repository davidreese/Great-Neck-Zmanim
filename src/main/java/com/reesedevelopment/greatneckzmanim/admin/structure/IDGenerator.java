package com.reesedevelopment.greatneckzmanim.admin.structure;

import com.reesedevelopment.greatneckzmanim.tools.NumberTools;

import java.util.Date;

public interface IDGenerator {
//    private String id = generateID();

    default String generateID(Character prefix) {
        Long timeSinceEpoch = (new Date()).getTime();
        return String.format("%s%s%s", prefix, timeSinceEpoch, random());
    }

    private String random() {
        return String.valueOf(NumberTools.getRandomNumber(100, 999));
    }
}

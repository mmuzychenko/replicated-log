package com.replicated.log.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReplicatedLogUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReplicatedLogUtils.class);

    public static void pauseSecondaryServer(int seconds) {
        LOGGER.info("Pause secondary server.");
        try {
            for (int i = 1; i <= seconds; i++) {
                LOGGER.info("wait {}s...", i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

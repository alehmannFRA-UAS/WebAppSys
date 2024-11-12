package edu.fra.uas.v2setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Drilling implements Work {

    private static final Logger LOGGER = LoggerFactory.getLogger(Drilling.class);

    public void doWork() {
        LOGGER.info(" --> drill a hole into the wall");
    }
}

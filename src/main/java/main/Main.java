package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class.getName());
    private static final int CONFIG_FILE_INDEX = 0;

    public static void main(String[] args) {
        if (args.length > 0) {
            if (!(new File(args[CONFIG_FILE_INDEX])).exists()) {
                logger.error("config file named {} supplied but couldn't be found", args[CONFIG_FILE_INDEX]);
            }
            MSClient msClient = new MSClient(args[CONFIG_FILE_INDEX]);
            msClient.start();
        } else {
            logger.error("No config file supplied, quiting.");
        }
    }


}
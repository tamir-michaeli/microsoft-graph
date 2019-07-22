package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class.getName());
    private static final int CONFIG_FILE_INDEX = 0;

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length > 0) {
            MSClient msClient = new MSClient(args[CONFIG_FILE_INDEX]);
            msClient.start();
        } else {
            logger.error("No config file supplied, quiting.");
        }
    }
}
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public abstract class Log {
    protected static Logger logger;
    protected static FileHandler fh;
    protected static final File f = new File("log.txt");
    private static void Proccess() throws SecurityException, IOException {
        fh = new FileHandler("log.txt", true);
        logger = Logger.getLogger("LOGGER");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        logger.setLevel(Level.FINE);
    }
    // NECESSARY FOR ATS'S LOGGING FUNCTION
    public static void tryCreateLog(){
        try {
            boolean result = f.createNewFile();
            Proccess();
            if (result) {
                log_msg("Log creation is completed.", true);
            }
        } catch (Exception ignored){}
    }
    public static void log_msg(String msg, boolean fine){
        if (fine)
            logger.fine(msg);
        else
            logger.warning(msg);
    }
}

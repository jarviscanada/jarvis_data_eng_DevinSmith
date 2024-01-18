package ca.jrvs.apps.grep;

import java.util.List;
import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        //Use default logger config
        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }
    }

    @Override
    public String getRegex() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setRegex(String regex) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public String getOutFile() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setOutFile(String outFile) {
        // TODO Auto-generated method stub
        
    }
}

package ca.jrvs.apps.grep;

import java.util.List;
import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.BasicConfigurator;


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
    public void process() throws IOException {
        //TODO actual logic
    }

    @Override
    public List<File> listFiles(String rootDir) {
        //TODO actual logic
        return new List<File>();
            
    }

    @Override
    public List<String> readLines(File inputFile) {
        //TODO actual logic
        List<String> myList = new List<String>();
        return myList;

    }

    @Override
    public boolean containsPattern(String line) {
        //TODO real logic
        return false;
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException{
        //TODO real logic
    }

    @Override
    public String getRootPath() { return rootPath; }

    @Override 
    public void setRootPath(String rootPath) { this.rootPath = rootPath;}

    @Override
    public String getRegex() { return regex; }

    @Override
    public void setRegex(String regex) { this.regex = regex; }
    
    @Override
    public String getOutFile() { return outFile; }

    @Override
    public void setOutFile(String outFile) { this.outFile = outFile; }
        
}

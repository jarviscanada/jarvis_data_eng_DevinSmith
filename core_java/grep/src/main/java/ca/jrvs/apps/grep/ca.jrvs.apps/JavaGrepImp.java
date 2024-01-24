package ca.jrvs.apps.grep;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.BasicConfigurator;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



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
        List<File> listedFiles = new listFiles(rootDir);
        List<String> matchedLines = new List<String>();
        for (File file : listedFiles) {
            List<String> listedStrings = readLines(file);
            for (String str : listedStrings) {
                if (containsPattern(str)) {
                    matchedLines.add(str);
                }
            }
        }
        writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(String rootDir) {
        File directoryPath = new File(rootDir);
        File filesList[] = directoryPath.listFiles();
        List<File> returnableList = new List<File>();
        for (File file : filesList) {
            if (file.isDirectory()) {
                List<File> innerList = listFiles(file.getAbsolutePath());
                if (innerList != null) {
                    returnableList.add(innerList);
                }
            }
            else {
                returnableList.add(file);
            }
        }
        return returnableList;
    }

    // http://www.java2s.com/Tutorial/Java/0180__File/ReadLinesreadfiletolistofstrings.htm
    // Parses the input file into an array of individual lines
    @Override
    public List<String> readLines(File inputFile) {
        if (!inputFile.exists()) {
            return new ArrayList<String>();
        }
        BufferedReader newReader = new BufferedReader(new FileReader(inputFile));
        List<String> myList = new ArrayList<String>();
        String line = new reader.readLine();
        while (line != null) {
            myList.add(line);
            line = newReader.readLine();
        }
        return myList;

    }

    // https://www.w3schools.com/java/java_regex.asp
    @Override
    public boolean containsPattern(String line) {
        Pattern regexToCheck = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher toBeCompared = pattern.matcher(line);
        boolean isMatchFound = matcher.find();
        if (isMatchFound) {
            return true;
        }
        return false;
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException{

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile));
        for (String str : lines) {
            bufferedWriter.write(str + System.lineSeparator());
        }
        bufferedWriter.close();
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

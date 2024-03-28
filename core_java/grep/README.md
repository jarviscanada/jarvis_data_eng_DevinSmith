# Introduction
In this project, the goal was to develop an app that operates similar to the grep command line utility using core java. This means that it takes in some text and searches a file or files 
and checks the text to see if there are instances of that text within the referenced file. This project used core java, and the following libraries; java.util for List, ArrayList and regex.Pattern,
java.io for FileWriter, FileReader, BufferedReader, BufferedWriter and IOException. It also used org.slf4j for Logger and LoggerFactory. Maven was used to compile the project and to pull the libraries,
and it was written within VSCode. It was then made into a Docker image for deployment.

# Quick Start
To use the app, you must use the following command:

java -cp {apps classpath} ca.jrvs.apps.grep.JavaGrepImp {Desired Regex} {Path of file/s to be checked} {relative path of the output file along with the file e.g. ./output.txt}

# Implementation

## Pseudocode
The process method of the application runs as follows:

`linesMatched = []`

`for file in recursiveFileList(rootDir)`

` for line in readLines(file)`

`     if containsPattern(line)`

`       linesMatched.add(line)`

`writeToFile(linesMatched)`

## Performance Issue
There is a memory issue from the amount of instances from writing multiple lines to the output file. I dealt with this using buffered writer. 
By default it would have to write each character to the output file, but buffered writer avoids this by filling up a buffer before writing to the file, minimizing
the amount of writes to the file, and thus saving memory.

# Test
To test this I initially ran through the functions with sample data and a logger. This allowed me to check the outputs of the component methods
and compare the results with my expectations of the outputs. Once the individual components were tested, the process method itself was tested to
see the outputs and confirm that the entire workflow was to expectations.

# Deployment
In order to make this app convenient to use on different machines, it was dockerized into a docker image for ease of use and easy deployment. This was done by first
signing into Docker in the terminal.

`docker_user=your_docker_id`

`docker login -u ${docker_user} --password-stdin`

Then defining a DockerFile

`cat > Dockerfile << EOF`

`FROM openjdk:8-alpine`

`COPY target/grep*.jar /usr/local/app/grep/lib/grep.jar`

`ENTRYPOINT ["java","-jar","/usr/local/app/grep/lib/grep.jar"]`

`EOF`

Then I packaged the app in the terminal 

`mvn clean package`

`docker build -t ${docker_user}/grep .`

I then confirmed this by checking my docker file and confirming it worked with a test regex:

`docker image ls | grep "grep"`

`docker run --rm \`

`-v `pwd`/data:/data -v `pwd`/log:/log \`

`${docker_user}/grep .*Romeo.*Juliet.* /data /log/grep.out`


After that the image was pushed to dockerhub for easy deployment:

`docker push ${docker_user}/grep`

# Improvement
Recomendations for this project would include the following:
- Impliment lambda
- Using streams for memory optimization
- Expand the heap memory

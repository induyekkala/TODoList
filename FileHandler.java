package com.novare.IndividualProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;
import com.novare.IndividualProject_TestCases.*;

public class FileHandler {

    /**
     * Initialization of variables
     */
    public File file;
    public FileWriter fileWriter;
    private static final String Line_Separator = "\n";
    private Path file_Path = new File("/Users/induyekkala/ToDoList.csv").toPath();
    private Charset charset = Charset.defaultCharset();
    private String filePath = "/Users/induyekkala/ToDoList.csv"; // Creating File header
    private UserTask userTask = new UserTask();
    /**
     * creates a file if file is not exist in the path
     * 
     * @param filePath
     */
    public void createFile(String filePath) {
	try {
	    file = new File(filePath);
	    fileWriter = new FileWriter(file, true);

	    if (!file.exists()) {
		file.createNewFile();

	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    /**
     * Once the CSV file is created it writes the content to the file and append the
     * rows to the file until the size of the arraylist
     * 
     * @param filePath
     * @param usersTask
     */

    public boolean writeCsv(String filePath, List<UserTask> usersTask) {
	boolean status = false;
	String HEADER = "Id,  TaskTitle,  DueDate,  Status,  ProjectName";
	try {
	    file = new File(filePath);
	    fileWriter = new FileWriter(file, true);

	    if (file.length() == 0) {
		fileWriter.write(HEADER);
		fileWriter.append(Line_Separator);
	    }
	    for (UserTask user : usersTask) {
		fileWriter.write(String.valueOf(+user.getTaskId()));

		fileWriter.write(",");
		fileWriter.write(user.getTaskTitle());

		fileWriter.write(",");
		fileWriter.write(user.getTaskDueDate());

		fileWriter.write(",");
		fileWriter.write(user.getTaskStatus());

		fileWriter.write(",");
		fileWriter.write(user.getProjectName());
		fileWriter.write(Line_Separator);
		status = true;
	    }

	} catch (Exception e) {
	    status = false;
	    e.printStackTrace();
	} finally {
	    try {
		fileWriter.close();
	    } catch (IOException ie) {

		ie.printStackTrace();
	    }
	}
	return status;

    }
    /**
     * It return true if file already exist else return false to check whether file
     * is exist or not
     * 
     * @param filepath
     * @return
     */

    public boolean read(String filepath) {
	File file = new File(filepath);
	if (file.exists()) {

	    return true;
	} else {
	    return false;
	}

    }
    /**
     * To read the content of the file from CSV and prints on the console Streams
     * are used to read the data from a file
     * 
     * @param filePath
     */
    public void readCSV(String filePath) {
	try {
	    try (Stream<String> stream = Files.lines(Paths.get(filePath))) {

		stream.forEach(System.out::println);
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * This function helps to find the last row line number in a file. Using this
     * function calculating the ID of the task
     *
     * @return
     */
    public int findLineNumber() {
	BufferedReader reader = null;
	int lines = 1;
	try {
	    reader = new BufferedReader(new FileReader(filePath));
	    reader.readLine();
	    while (reader.readLine() != null)
		lines++;
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		reader.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return lines;

    }
}

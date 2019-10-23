package com.novare.IndividualProject;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class TaskManager {

    private Path file_Path = new File("/Users/induyekkala/ToDoList.csv").toPath();
    private Charset charset = Charset.defaultCharset();
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private String filePath = "/Users/induyekkala/ToDoList.csv";
    private FileHandler fileHandler = new FileHandler();
    private ArrayList<UserTask> listOfTasks = new ArrayList<UserTask>();
    private File file_delete = new File(filePath);
    private Scanner input = new Scanner(System.in);

    public String[] sortProjectName() {
	String[] userTaskArray = { "" };
	try {
	    List<String> taskList = Files.readAllLines(file_Path, charset);
	    if (taskList != null) {
		taskList.remove(0);
		userTaskArray = taskList.toArray(new String[] {});
		Arrays.sort(userTaskArray, new Comparator<String>() {
		    public int compare(String firstProjectName, String secondProjectName) {
			return (firstProjectName.split(",")[4]).compareTo((secondProjectName.split(",")[4]));
		    }
		});

	    } else {
		userTaskArray = null;
		throw new FileNotFoundException();
	    }
	} catch (Exception e) {
	    System.out.println("File is not found please check");
	}
	return userTaskArray;
    }

    // Sort the Tasks with due Date
    public String[] sortDueDate() {
	String[] userTaskArray = { "" };
	try {
	    List<String> taskList = Files.readAllLines(file_Path, charset);
	    if (taskList != null) {
		taskList.remove(0);
		userTaskArray = taskList.toArray(new String[] {});
		Arrays.sort(userTaskArray, new Comparator<String>() {
		    public int compare(String firstTaskDate, String secondTaskDate) {
			try {
			    return dateFormat.parse(firstTaskDate.split(",")[2])
				    .compareTo(dateFormat.parse(secondTaskDate.split(",")[2]));
			}

			catch (ParseException e) {
			    throw new IllegalArgumentException(e);
			}

		    }
		});

	    }

	    else {
		userTaskArray = null;
		throw new FileNotFoundException();
	    }
	} catch (Exception e) {
	    System.out.println("File is not found please check");
	}
	return userTaskArray;
    }

    public UserTask findTask(String taskTitle) {

	UserTask task = new UserTask();

	try {

	    int i = 0;
	    File file = new File(filePath);
	    BufferedReader read = new BufferedReader(new FileReader(file));
	    String st = "";
	    if (file.length() != 0) {

		while ((st = read.readLine()) != null) {
		    if (st.contains(taskTitle)) {

			String fileContent[] = st.split(",");
			task = new UserTask(Integer.parseInt(fileContent[0]), fileContent[1], fileContent[2],
				fileContent[3], fileContent[4]);

			return task;
		    }
		}
	    } else {

		throw new FileNotFoundException();
	    }
	} catch (Exception e) {
	    task = null;
	    System.out.println("File not found exception");
	}
	return null;
    }

    public ArrayList<UserTask> listOfTasks() {
	try {
	    File file = new File(filePath);
	    BufferedReader read = new BufferedReader(new FileReader(file));
	    String st = "";
	    if (file.exists()) {
		while ((st = read.readLine()) != null) {
		    String fileContent[] = st.split(",");
		    if (!st.contains("Id")) {
			UserTask task = new UserTask(Integer.parseInt(fileContent[0]), fileContent[1], fileContent[2],
				fileContent[3], fileContent[4]);
			listOfTasks.add(task);
		    }
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return listOfTasks;
    }

    public boolean isDuplicateTask(String taskTitle) {
	listOfTasks();
	boolean status = true;
	int i = 0;
	for (i = 0; i < listOfTasks.size(); i++) {
	    UserTask userTask = listOfTasks.get(i);
	    String title = userTask.getTaskTitle();
	    System.out.println(title);
	    if (title.equals(taskTitle)) {
		status = false;
		break;
	    } else {
		status = true;
	    }
	}
	return status;
    }

    public String removeTask(String taskTitle) {
	listOfTasks();
	String message = "";
	Iterator<UserTask> iterator = listOfTasks.iterator();
	while (iterator.hasNext()) {
	    if (iterator.next().getTaskTitle().equals(taskTitle)) {
		iterator.remove();
		message = "Task removed from the list";
	    } else {
		message = "No task in the TODO list";
	    }
	}
	file_delete.delete();
	try {
	    fileHandler.writeCsv(filePath, listOfTasks);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return message;

    }

    public String editTaskStatus(String taskTitle) {

	String message = "";
	UserTask usertask = new UserTask();
	try {
	    Reader reader = Files.newBufferedReader(Paths.get(filePath));

	    CSVReader csvReader = new CSVReader(reader);

	    List<String[]> userTaskList = csvReader.readAll();

	    for (int i = 0; i < userTaskList.size(); i++) {
		String[] strArray = userTaskList.get(i);
		for (int j = 1; j < strArray.length; j++) {
		    {

			if (strArray[j].equalsIgnoreCase(taskTitle)) {
			    System.out.println(2);

			    userTaskList.get(i)[3] = "Done";
			    System.out.println(3);// Target replacement
			    message = "task status marked as Done ";
			    break;
			} else {
			    message = "difficulty in changing the task status  ";
			}
		    }
		}
	    }

	    reader.close();
	    FileWriter fileWriter = new FileWriter(filePath);
	    StringBuilder sb = new StringBuilder();
	    for (final String[] list : userTaskList) {
		for (final String str : list) {
		    fileWriter.append(str + ",");

		}
		fileWriter.append("\n");

	    }
	    fileWriter.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return message;
    }

    public void editTask(String taskTitle, int read) {
	try {
	    Reader reader = Files.newBufferedReader(Paths.get(filePath));
	    CSVReader csvReader = new CSVReader(reader);
	    List<String[]> userTaskList = csvReader.readAll();
	    if (read == 1) {
		System.out.println("Enter the task Title");
		String title = input.nextLine();
		for (int i = 0; i < userTaskList.size(); i++) {
		    String[] strArray = userTaskList.get(i);
		    for (int j = 1; j < strArray.length; j++) {
			{
			    if (strArray[j].equalsIgnoreCase(taskTitle)) { // String to be replaced
				userTaskList.get(i)[j] = title; // Target replacement
			    }
			}
		    }
		}
	    }
	    if (read == 2) {
		System.out.println("Enter the task duedate");
		String dueDate = input.nextLine();
		for (int i = 0; i < userTaskList.size(); i++) {
		    String[] strArray = userTaskList.get(i);
		    for (int j = 1; j < strArray.length; j++) {
			{

			    if (strArray[j].equalsIgnoreCase(taskTitle)) { // String to be replaced
				userTaskList.get(i)[2] = dueDate; // Target replacement
			    }
			}
		    }
		}
	    }
	    reader.close();
	    FileWriter fileWriter = new FileWriter(filePath);
	    StringBuilder sb = new StringBuilder();
	    for (final String[] list : userTaskList) {
		for (final String str : list) {
		    fileWriter.append(str + ",");

		}
		fileWriter.append("\n");

	    }
	    fileWriter.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }
    public String taskStatusCount() {

	int doneTask = 0;
	int notDoneTask = 0;
	int i = 0;
	listOfTasks();
	for (i = 0; i < listOfTasks.size(); i++) {
	    UserTask userTask = listOfTasks.get(i);
	    if (userTask.getTaskStatus().equals("Done")) {
		doneTask++;
	    } else {
		notDoneTask++;
	    }
	}
	return "You have " + notDoneTask + " Not Done tasks and " + doneTask + " tasks are Done!";
    }
}
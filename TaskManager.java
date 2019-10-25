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
/**
 * Initialization of variables
 */
    private Path file_Path = new File("/Users/induyekkala/ToDoList.csv").toPath();
    private Charset charset = Charset.defaultCharset();
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private String filePath = "/Users/induyekkala/ToDoList.csv";
    private FileHandler fileHandler = new FileHandler();
    private ArrayList<UserTask> listOfTasks = new ArrayList<UserTask>();
    private File file_delete = new File(filePath);
    private Scanner input = new Scanner(System.in);
    private String message="";
/**
 * This function reads the data from a CSV file and sort the data in ascending order by project name
 * Comapartor class is used to sorting using compareTo method.
 * 
 * @return list of the tasks in sorted order
 */
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

    /**
     * This function reads the data from a CSV file and sort the data in ascending order by taskDueDate
     * Comapartor class is used to sorting using compareTo method
     * Date is parsed using DateFormat class in order to sort by day,month and year
     *
     * @return the list of task in sorted order by due date
     */
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
    /**
     * This function reads the data from a CSV file and check whether the user entered task is available in the list or not
     * If task is not in the it returns null value
     * @param taskTitle
     * @return the task
     */

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
    /**
     *  
     * @return the array list of task of the user
     */

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
/**
 * Function check for duplicate of task title 
 * If a task title is entered twice it pop user already the task is in the list
 * @param taskTitle
 * @return true or false
 */
    public boolean isDuplicateTask(String taskTitle) {

	listOfTasks();
	String message = "";
	boolean status = true;
	Iterator<UserTask> iterator = listOfTasks.iterator();
	while (iterator.hasNext()) {

	    if (iterator.next().getTaskTitle().equals(taskTitle)) {
		status = false;

	    } else {
		status = true;
	    }

	}
	return status;

    }
/**
 * Function read the data from a file and list all task and try removes a task from the list when the user enter
 * the task title to be removed
 * @param taskTitle
 * @return a message to where the task is removed or mot
 */
    public String removeTask(String taskTitle) {
	try {
	    Reader reader = Files.newBufferedReader(Paths.get(filePath));
	    CSVReader csvReader = new CSVReader(reader);
	    List<String[]> userTaskList = csvReader.readAll();
	    for (int i = 0; i < userTaskList.size(); i++) {
		String[] strArray = userTaskList.get(i);
		for (int j = 1; j < strArray.length; j++) {
		    {
			if (strArray[j].equalsIgnoreCase(taskTitle)) {
			    userTaskList.remove(i);
			    message = "Task removed from the list";
			    break;
			} else {
			    message = "No task in the TODO list";
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
/**
 * Function changes the status of the task from Not Done task to Done
 * @param taskTitle
 * @return a message if task status
 */
    public String editTaskStatus(String taskTitle) {

	try {
	    Reader reader = Files.newBufferedReader(Paths.get(filePath));

	    CSVReader csvReader = new CSVReader(reader);

	    List<String[]> userTaskList = csvReader.readAll();

	    for (int i = 0; i < userTaskList.size(); i++) {
		String[] strArray = userTaskList.get(i);
		for (int j = 1; j < strArray.length; j++) {
		    {
			if (strArray[j].equalsIgnoreCase(taskTitle)) {		 
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
    /**
     * Function edit the task title and task dueDate if the task title in the list
     * User have to enter into the switch case by selecting the option 1 for edit task title
     * and 2 for task due date
     * @param taskTitle
     * @param read
     */

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
    /**
     * Function holds the count of Not Done task and Done Tasks
     * @return
     */

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

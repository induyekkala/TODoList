package com.novare.IndividualProject;

import com.novare.IndividualProject.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.jackrutorial.User;

public class TaskManager {

    private Path file_Path = new File("/Users/induyekkala/ToDoList.csv").toPath();
    private Charset charset = Charset.defaultCharset();
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private String filePath = "/Users/induyekkala/ToDoList.csv";
    private FileHandler fileHandler = new FileHandler();
    private ArrayList<UserTask> listOfTasks = new ArrayList<UserTask>();
    private File file_delete = new File(filePath);
    private Scanner input=new Scanner(System.in);

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
	    if(taskList!=null)
	    {
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
	
	else
	{
	    userTaskArray = null;
	    throw new FileNotFoundException(); 
	}
	}
	catch (Exception e) {
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
    public boolean isDuplicateTask(String taskTitle){
	
	listOfTasks();
	String message = "";
	boolean status=true;
	Iterator<UserTask> iterator = listOfTasks.iterator();
	while (iterator.hasNext()) {
	    System.out.println(iterator.next().getTaskTitle());

	    if (iterator.next().getTaskTitle().equals(taskTitle)) {
		status=true;
		
	    }
	    else
	    {
		status=false;
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
	int i = 0;
	listOfTasks();
	for (i = 0; i < listOfTasks.size(); i++) {
	    UserTask userTask = listOfTasks.get(i);
	    if (userTask.getTaskTitle().equals(taskTitle)) {
		userTask.setTaskStatus("Done");
		listOfTasks.set(i, userTask);
		message = "task status marked as Done ";

	    } else {
		message = "difficulty in changing the task status  ";
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
   

    public void editTaskTitle(int id) {

	int i, j = 0;
	listOfTasks();
	boolean type = false;
	for (i = 0; i < listOfTasks.size(); i++) {
	    UserTask userTask = listOfTasks.get(i);
	    System.out.println(userTask.getTaskTitle());
	    if (id==userTask.getTaskId()) {
		System.out.println("Task title is in the TODO list you can proceed for updations ");
		System.out.println("Enter the task Title");
		String taskTitle=input.nextLine();
		userTask.setTaskTitle(taskTitle);	
		listOfTasks.set(i, userTask); 
		type = true;
		break;
	    }
	}

	if (type == false) {
	    System.out.println("Task title does'nt exist in the TODO list");
	}
	file_delete.delete();
	try {
	    fileHandler.writeCsv(filePath, listOfTasks);

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
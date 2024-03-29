package com.novare.IndividualProject;

import java.io.File;
import java.text.*;
import java.util.*;
import com.novare.IndividualProject.*;

public class Function_Task {

    private String filePath = "/Users/induyekkala/ToDoList.csv";
    private TaskManager taskManager = new TaskManager();
//	 Date format
    private DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private UserTask userTask = new UserTask();
    private Scanner input = new Scanner(System.in);
    private FileHandler fileHandler = new FileHandler();
    private ArrayList<UserTask> usersTask = new ArrayList<UserTask>();
    private File file_delete = new File(filePath);

    public void createTask() {
	fileHandler.createFile(filePath);
    }
    public boolean validateDate(String dateValue) {

	boolean status = false;
	try {
	    Date taskDate = null;
	    taskDate = (Date) formatter.parse(dateValue);

	} catch (ParseException e) {
	    status = false;
	}
	return status;

    }
    public void writeTask() {

	try {

	    System.out.println("Enter the Task Title");
	    String taskTitle = input.nextLine();
	    System.out.println("Enter the Date in this format DD-MM-YYYY");
	    String taskDate = input.next();
	    input.nextLine();
	    this.validateDate(taskDate);
	    Date taskDueDate = null;
	    taskDueDate = (Date) formatter.parse(taskDate);
	    String dueDate = formatter.format(taskDueDate);
	    userTask.setTaskStatus("Not Done");
	    String taskStatus = userTask.getTaskStatus();
	    System.out
		    .println("Enter a project name (Novare SDA Lund,Novare SDA Stockholm are the valid project names)");
	    String projectName = input.nextLine();
	    String list_projectName[] = { "Novare SDA Lund", "Novare SDA Stockholm" };
	    int line = fileHandler.findLineNumber();
	    if (list_projectName[0].equals(projectName) || list_projectName[1].equals(projectName)) {
		boolean status=taskManager.isDuplicateTask(taskTitle);	
		System.out.println(status);
		if(status==true)
		{	    
		userTask = new UserTask(line, taskTitle, dueDate, taskStatus, projectName);
		List<UserTask> usersTask = new ArrayList<UserTask>();
		usersTask.add(userTask);
		fileHandler.writeCsv(filePath, usersTask);
		}
		else
		{
		    System.out.println("Task title in the list please enter again");
		    writeTask();
		}
	    } else {
		System.out.println(
			"You have entered a wrong project name. Please enter a valid name (Novare SDA Lund, Novare SDA Stockholm)");
		System.out.println(
			"Enter a project name (Novare SDA Lund,Novare SDA Stockholm are the valid project names)");
		System.out.println("Re-enter the project name");
		projectName = input.nextLine();
		taskManager.isDuplicateTask(taskTitle);
		userTask = new UserTask(line, taskTitle, dueDate, taskStatus, projectName);
		List<UserTask> usersTask = new ArrayList<UserTask>();
		usersTask.add(userTask);
		fileHandler.writeCsv(filePath, usersTask);
	    }

	} catch (ParseException e) {
	    System.out.println("Enter the correct date format");

	}

    }

    public void readTask() {

	fileHandler.readCSV(filePath);

    }

    public void sortTaskByProject() {

	String[] userTask = taskManager.sortProjectName();
	if (userTask.length == 0) {
	    System.out.println("No Task in TODO list to display");
	} else {

	    System.out.println(
		    "****************************************************************************************************\n");
	    System.out.println("TaskId\t\t Task title\t\t   TaskDueDate\t           Status\t\\t         Project Name \n");
	    System.out.println(
		    "**************************************************************************************************** \n");
	    for (String sortedUserTask : userTask) {
		
		System.out.println(sortedUserTask.replace(",","\t\t"));
		//System.out.println(sortedUserTask + "\t");
		System.out.println("\n");
	    }  
	}

    }

    public void sortTaskByDueDate() {
	String[] userTask = taskManager.sortDueDate();
	if (userTask.length == 0) {
	    System.out.println("No Task in TODO list to display");
	} else {
	    if (userTask != null) {
		System.out.println(
			"************************************************************************************************\n");
		System.out.println("TaskId\t\t Task title\t\t  TaskDueDate\t         Status\t\t         Project Name \n");
		System.out.println(
			"************************************************************************************************ \n");
		for (String sortedUserTask : userTask) {
		    System.out.println(sortedUserTask.replace(",","\t\t"));
		    System.out.println("\n");
		}
	    }
	}

    }

    public boolean findATask() {
	System.out.println("Enter the task title to find");
	String taskTitle = input.nextLine();
	UserTask userTask = taskManager.findTask(taskTitle);
	boolean ret = false;
	if (userTask != null) {
	    System.out.println("Record in the list is" + "\nTask Id=" + userTask.getTaskId() + "\nTask Title="
		    + userTask.getTaskTitle() + "\nTask Due Date=" + userTask.getTaskDueDate() + "\nTask Status="
		    + userTask.getTaskStatus() + "\nProject Name=" + userTask.getProjectName());
	    ret = true;
	} else {
	    ret = false;
	}
	return ret;
    }

    public void removeATask() {

	System.out.println("Enter the Task to remove from the record");
	String taskTitle = input.next();
	taskManager.removeTask(taskTitle);
    }

    public void removeAProject() {
	System.out.println("Enter the Project to remove from the record");
	String projectName = input.next();
    }

    public void editATaskStatus() {
	System.out.println("Enter the which Task Title status to be changed ");
	String taskTitle = input.nextLine();
	taskManager.editTaskStatus(taskTitle);
    }

    public void editATask() {

	Scanner input = new Scanner(System.in);
	System.out.println("Enter the task id whose details to be updated");
	String taskTitle=input.next();
	System.out.println("Please select the option from the menu to edit");
	System.out.println("\n 1. Edit Title \n 2. Edit Due Date");
	System.out.println("Enter the choice");
	int readChoice = input.nextInt();
	switch (readChoice) {
	case 1:
	    taskManager.editTask(taskTitle,readChoice);   
	    break;   
	    
	case 2:    
	    taskManager.editTask(taskTitle,readChoice);
	    break;
	}

    }
    public String taskstatusProgress() {
	String message = taskManager.taskStatusCount();
	return message;

    }
}
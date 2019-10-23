package com.novare.IndividualProject;

import java.io.*;
import java.util.Random;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ToDoListTest {

    public static void main(String args[]) {

	Function_Task task = new Function_Task();
	FileHandler fileHandler = new FileHandler();
	String filePath = "/Users/induyekkala/ToDoList.csv";
	ArrayList<UserTask> usersTask = new ArrayList<UserTask>();
	Scanner input = new Scanner(System.in);
	int read = 0;
	boolean exit = false;
	task.createTask();
	while (!exit) {   
	    printCommand();  
	    System.out.println("Enter your choices");
	    read = input.nextInt();
	    input.nextLine(); 
	    switch (read) {    
	    case 1:
		int value;
		System.out.println("\nPress 1 to display by Due Date" + "\nPress 2 to display by Project");
		value = input.nextInt();
		switch (value) {
		case 1:
		    task.sortTaskByDueDate();
		    break;
		case 2:
		    task.sortTaskByProject();
		    break;
		default:
		    System.out.println("Please enter correct choice");
		    break;
		}
		break;
	    case 2:
		task.writeTask();
		break;
	    case 3:
		System.out.println("\nPress 1: Edit the Task in the TODO List"
			+ "\nPress 2: Update a Task Status as Done" + "\nPress 3: Remove the Task from the TODO List");
		read = input.nextInt();
		switch (read) {
		case 1:
		    task.editATask();
		    break;
		case 2:
		    task.editATaskStatus();
		    break;
		case 3:
		    task.removeATask();
		    break;
		case 4:
		    System.out.println("Please enter correct choice");
		    break;
		}
		break;
	    case 4:
		System.out.println("Thank you for selecting for TODO List");
		fileHandler.writeCsv(filePath, usersTask);
		System.exit(0);
	    default:
		System.out.println("You have entered wrong choice");
	    }
	}
    }
    
    public static void printCommand() {
	Function_Task task = new Function_Task();
	System.out.println("\nWelcome to ToDoList" + "\n"+task.taskstatusProgress() +"\n" + "\n Pick an option:"
		+ "\n (1) Show Task List (by Due Date or Project)" + "\n (2) Add new task"
		+ "\n (3) Edit Task (Update,Mark as done,Remove)" + "\n (4) Save and Quit");
    }

}

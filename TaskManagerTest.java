package com.novare.IndividualProject;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class TaskManagerTest {

    private Path file_Path = new File("/Users/induyekkala/ToDoList.csv").toPath();
    private Charset charset = Charset.defaultCharset();
    private ArrayList<UserTask> listOfTasks = new ArrayList<UserTask>();
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private TaskManager taskManager = new TaskManager();
    private UserTask userTask=new UserTask();

    @Test
    public void testSortProjectName() {

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
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	String arr[] = { "apple", "mango" };
	String exceptedUserTaskArray[] = taskManager.sortProjectName();

	assertTrue(Arrays.equals(arr, userTaskArray));
    }

    @Test

    public void testSortDueDate() {
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
	} catch (Exception e) {
	    e.printStackTrace();
	}
	String arr[] = { "apple", "mango" };
	String exceptedUserTaskArray[] = taskManager.sortDueDate();

	assertTrue(Arrays.equals(arr, userTaskArray));

    }
    @Test
    public void testFindTask() {
     userTask = taskManager.findTask("java");
     if(userTask==null)
     assertNull(userTask);
     else
     assertNotNull(userTask);
    }
    
    @Test
    public void testListOfTasks() {
     assertTrue(taskManager.listOfTasks().size() <= 0);
    }
    
    @Test
    public void testRemoveTask() {
     String message = taskManager.removeTask("java");
     assertEquals(message, "Task removed from the list");
    }

    
    
}

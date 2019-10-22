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

import org.junit.Before;
import org.junit.Test;

public class TaskManagerTest {

    private Path file_Path = new File("/Users/induyekkala/ToDoList.csv").toPath();
    private Charset charset = Charset.defaultCharset();
    private ArrayList<UserTask> listOfTasks = new ArrayList<UserTask>();
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private TaskManager taskManager = new TaskManager();
    private FileHandler fileHandler = new FileHandler();
    private UserTask userTask = new UserTask();
    private String filePath = "/Users/induyekkala/ToDoList.csv";

    @Before
    public void testCheckForFile() {
	FileHandler fileHandler = new FileHandler();
	boolean status = fileHandler.read(filePath);
	if (status == false)
	    fail("File not found in the record" + status);
	else
	    fileHandler.readCSV(filePath);
    }

    @Test
    public void testWriteTask() {
	userTask = new UserTask(1, "java", "23-10-2019", "Done", "Novare SDA Lund");
	listOfTasks.add(userTask);
	boolean actual = fileHandler.writeCsv(filePath, listOfTasks);
	if (actual == true) {
	    System.out.println("Task added in the list");
	} else {
	    fail("Task not added in the list");
	}

    }

    @Test
    public void testSortProjectName() {

	String[] exceptedUserTaskArray = { "" };
	try {

	    List<String> taskList = Files.readAllLines(file_Path, charset);
	    if (taskList != null) {
		taskList.remove(0);
		exceptedUserTaskArray = taskList.toArray(new String[] {});
		Arrays.sort(exceptedUserTaskArray, new Comparator<String>() {
		    public int compare(String firstProjectName, String secondProjectName) {
			return (firstProjectName.split(",")[4]).compareTo((secondProjectName.split(",")[4]));

		    }
		});
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	String arr[] = { "xxx", "yyy" };
	String actualUserTasksArray[] = taskManager.sortProjectName();
	assertTrue(Arrays.equals(arr, exceptedUserTaskArray));
	assertTrue(Arrays.equals(exceptedUserTaskArray, actualUserTasksArray));
    }

    @Test
    public void testSortDueDate() {
	String[] exceptedUserTaskArray = { "" };
	try {
	    List<String> taskList = Files.readAllLines(file_Path, charset);
	    if (taskList != null) {
		taskList.remove(0);
		exceptedUserTaskArray = taskList.toArray(new String[] {});
		Arrays.sort(exceptedUserTaskArray, new Comparator<String>() {
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
	String arr[] = { "xxx", "yyy" };
	String actualUserTasksArray[] = taskManager.sortDueDate();
	assertTrue(Arrays.equals(arr, exceptedUserTaskArray));
	assertTrue(Arrays.equals(exceptedUserTaskArray, actualUserTasksArray));
    }
    @Test
    public void testFindTask() {
	userTask = taskManager.findTask("java");
	if (userTask == null)
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

    @Test
    public void testEditTaskStatus() {

	String message = taskManager.editTaskStatus("java");
	assertEquals(message, "task status marked as Done");

    }

}

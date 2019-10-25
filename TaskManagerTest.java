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
    /**
     * Initialization of variables
     */

    private Path file_Path = new File("/Users/induyekkala/ToDoList.csv").toPath();
    private Charset charset = Charset.defaultCharset();
    private ArrayList<UserTask> listOfTasks = new ArrayList<UserTask>();
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private TaskManager taskManager = new TaskManager();
    private FileHandler fileHandler = new FileHandler();
    private UserTask userTask = new UserTask();
    private String filePath = "/Users/induyekkala/ToDoList.csv";

    /**
     * this test case helps to check whether the file is exist or not if file is not
     * exist the function will fail
     */
    @Before
    public void testCheckForFile() {
	FileHandler fileHandler = new FileHandler();
	boolean status = fileHandler.read(filePath);
	// a negative test case
	if (status == false)
	    fail("File not found in the record" + status);
	else
	    fileHandler.readCSV(filePath);
    }

    /**
     * check whether task writes into the file or not without an exception
     */
    @Test
    public void testWriteTask() {
	userTask = new UserTask(1, "java", "23-10-2019", "Done", "Novare SDA Lund");
	listOfTasks.add(userTask);
	boolean actual = fileHandler.writeCsv(filePath, listOfTasks);
	if (actual == true) {
	    System.out.println("Task added in the list");
	}
	// negative test case
	else {
	    fail("Task not added in the list");
	}
    }

    /**
     * check whether the tasks are sorted by project name or not Reads the content
     * from the file and sort the task by project name and store in an
     * exceptedUserTaskArray and compared with actualUserTaskArray variables
     */
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

    /**
     * check whether the tasks are sorted by due date or not Reads the content from
     * the file and sort the task by project name and store in an
     * exceptedUserTaskArray and compared with actualUserTaskArray variables
     */
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

    /**
     * check the whether the given string is in the list of task title or not the
     * test case fails if the task title is not in the list
     */

    @Test
    public void testFindTask() {
	userTask = taskManager.findTask("java");
	if (userTask == null)
	    // negative test case
	    assertNull(userTask);
	else
	    // positive test case
	    assertNotNull(userTask);
    }

    /**
     * check for the task in the list if the size is less then zero that means no
     * tasks in the list and test case fails
     */
    @Test
    public void testListOfTasks() {
	assertTrue(taskManager.listOfTasks().size() <= 0);
    }

    /**
     * check for whether the give string in the list or not if it is in the list
     * removes the task from the list or else the test case will fail if the task
     * title is not in the list
     */

    @Test
    public void testRemoveTask() {
	String message = taskManager.removeTask("java");
	assertEquals(message, "Task removed from the list");
    }

    /**
     * check for whether the give string is in the list or not if the string is in
     * the list changes the task status from Not Done to Done status or else the
     * task will fail if the task title is not in the list
     * 
     */

    @Test
    public void testEditTaskStatus() {

	String message = taskManager.editTaskStatus("java");
	assertEquals(message, "task status marked as Done");

    }

}

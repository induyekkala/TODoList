package com.novare.IndividualProject;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
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

import javax.swing.text.BadLocationException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.*;
import com.novare.IndividualProject.*;

public class TaskManagerTest {

    FileHandler handler = new FileHandler();
    TaskManager tes = new TaskManager();
    UserTask userTask = new UserTask();
    ArrayList<UserTask> userTaskList = new ArrayList<UserTask>();
    String filePath = "/Users/induyekkala/ToDoList.csv";
    private Path file_Path = new File("/Users/induyekkala/ToDoList.csv").toPath();
    private Charset charset = Charset.defaultCharset();
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    
      
      
    /*
     * @Test public void testDateIsInvalid() { boolean actualStatus=true;
     * System.out.println(taskManager.validateDate("23/12/2019"));
     * assertEquals(actualStatus,taskManager.validateDate("23/12/2019")); }
     * 
     * 
     * @Test public void testDateValid() {
     * assertTrue(taskManager.validateDate("23-12-2019", "DD/MM/YYYY")); }
     */

    @Test
    public void testSortProjectName() {

	List<String> taskList = new ArrayList<String>();
	try {
	    taskList = Files.readAllLines(file_Path, charset);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	taskList.remove(0);
	// passing into string array as array of strings
	String[] exceptedArray = taskList.toArray(new String[] {});
	/*
	 * Arrays.sort(exceptedArray, new Comparator<String>() { public int
	 * compare(String firstProjectName, String secondProjectName) {
	 * 
	 * return
	 * (firstProjectName.split(",")[4]).compareTo((secondProjectName.split(",")[4]))
	 * ;
	 * 
	 * } });
	 */

	String[] actualArray = tes.sortProjectName();
	   assertFalse(Arrays.equals(exceptedArray,actualArray));
	String arr[] = { "apple", "mango" };

//	assertArrayEquals(exceptedArray, actualArray);

    }

    @Test public void testSortDueDate() {
	  
	String[] exceptedTaskArray= {""};  
	try {
		    
		    List<String> taskList = Files.readAllLines(file_Path, charset);
		    taskList.remove(0);	   
		    exceptedTaskArray = taskList.toArray(new String[] {});
		    Arrays.sort(exceptedTaskArray, new Comparator<String>() {	
			public int compare(String firstTaskDate, String secondTaskDate) {

			    try {
				
				return dateFormat.parse(firstTaskDate.split(",")[2])
					.compareTo(dateFormat.parse(secondTaskDate.split(",")[2]));
			    }
			   
			    catch (ParseException e) {
				throw new IllegalArgumentException(e);
			    }
			
			}});    
	  }
	  catch(Exception e)
	  {
	      e.printStackTrace();
	  }
	  String[] actualTaskArray = tes.sortDueDate();
//	   assertFalse(Arrays.equals(exceptedTaskArray,actualTaskArray));
	String arr[] = { "apple", "mango" };

	assertArrayEquals(exceptedTaskArray, actualTaskArray);
	  
		   
      }

    @Test
    public void testFindTask() {

	userTask = tes.findTask("java");
	assertNull(userTask);
    }

    @Test
    public void testListOfTasks() {

	assertTrue(tes.listOfTasks().size() <= 0);
    }

    @Test
    public void testRemoveTask() {

	String message = tes.removeTask("java");
	assertEquals(message, "Task removed from the list");

    }

    @Test
    public void testEditTaskStatus() {

	String message = tes.editTaskStatus("java");
	assertEquals(message, "task status marked as Done");

    }

}

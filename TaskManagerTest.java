package com.novare.IndividualProject;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class TaskManagerTest {
    
    
    private Path file_Path = new File("/Users/induyekkala/ToDoList.csv").toPath();
    private Charset charset = Charset.defaultCharset();
    private ArrayList<UserTask> listOfTasks = new ArrayList<UserTask>();
    private TaskManager taskManager=new TaskManager();
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
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
	String arr[]= {"apple","mango"};
	String exceptedUserTaskArray[]=taskManager.sortProjectName();
	
	assertTrue(Arrays.equals(arr,userTaskArray));
    }
    

    
    

    

}

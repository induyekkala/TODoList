package com.novare.IndividualProject;



    import java.io.BufferedReader;
    import java.io.File;
    import java.io.FileReader;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.nio.charset.Charset;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.text.DateFormat;
    import java.text.SimpleDateFormat;
    import java.util.List;
    import java.util.stream.Stream;
    import com.novare.IndividualProject_TestCases.*;

    public class FileHandler {

        // private List<UserTask> usersTask = new ArrayList<UserTask>();

        // Creating File and FileWriter object for writing data into file
        public File file;
        public FileWriter fileWriter;

        // Line_Separator variable insert a new
        private static final String Line_Separator = "\n";
        TestBuilder test = new TestBuilder();

        // Reading the file path
        private Path file_Path = new File("/Users/induyekkala/ToDoList.csv").toPath();
        private Charset charset = Charset.defaultCharset();

        private String filePath = "/Users/induyekkala/ToDoList.csv"; // Creating File header

        private UserTask userTask = new UserTask();

        // Adding Task to ArrayList
        /*
         * public void addToDoList(UserTask user) {
         * 
         * usersTask.add(user); }
         */

        // Writing data to a CSV file
        
        public void createFile(String filePath)
        {
    	
    	try {
    	    file = new File(filePath);
    	    fileWriter = new FileWriter(file, true);
    	    
    		if(!file.exists())
    		{
    		    file.createNewFile();
    		    
    		}
    		
    	}
    		catch(Exception e)
    		{
    		    e.printStackTrace();
    		}
        }

        public void writeCsv(String filePath, List<UserTask> usersTask) {
    	String HEADER = "Id,  TaskTitle,  DueDate,  Status,  ProjectName"; 
    	try {
    	    file = new File(filePath);
    	    fileWriter = new FileWriter(file, true);
    	    
    	    
    	      if (file.length() == 0) { fileWriter.write(HEADER);
    	      fileWriter.append(Line_Separator); }
    	     
    		for (UserTask user : usersTask) {
    		    fileWriter.write(String.valueOf(+user.getTaskId()));

    		    fileWriter.write(",");
    		    fileWriter.write(user.getTaskTitle());

    		    fileWriter.write(",");
    		    fileWriter.write( user.getTaskDueDate());

    		    fileWriter.write(",");
    		    fileWriter.write(user.getTaskStatus());

    		    fileWriter.write(",");
    		    fileWriter.write(user.getProjectName());
    		    fileWriter.write(Line_Separator);
    		}
    		
    		
    	    
    	} catch (Exception e) {
    	    e.printStackTrace();
    	} finally {
    	    try {
    		fileWriter.close();
    	    } catch (IOException ie) {
    		
    		ie.printStackTrace();
    	    }
    	}
    	
        }
        // Reading data from a CSV file

        public boolean read(String filepath) {
    	File file = new File(filepath);
    	if (file.exists()) {

    	    return true;
    	} 
    	else {
    	    return false;
    	}

        }

        public void readCSV(String filePath) {
    	try {
    	    // read file into stream
    	    
    	    
    	    try (Stream<String> stream = Files.lines(Paths.get(filePath))) {

    		stream.forEach(System.out::println);
    	    }

    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
        }

        public int findLineNumber() {
    	BufferedReader reader = null;
    	int lines = 1;
    	try {

    	    reader = new BufferedReader(new FileReader(filePath));
    	    reader.readLine();
    	    while (reader.readLine() != null)
    		lines++;

    	} catch (IOException e) {
    	    e.printStackTrace();
    	}

    	finally {
    	    try {
    		reader.close();
    	    } catch (IOException e) {
    		e.printStackTrace();
    	    }
    	}

    	return lines;

        }
    }




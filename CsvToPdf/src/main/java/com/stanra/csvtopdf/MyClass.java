package com.stanra.csvtopdf;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyClass {
	
		/**
		 *  METHOD TO PRINT FILE DETAILS OF GOOGLE DRIVE 
		 * @param service
		 * @param fileId
		 * @return
		 * 
		 */
	  
	    public static ArrayList<String> printFile(Drive service, String fileId) {
	    	ArrayList <String> row = new ArrayList<String>();
 
	        try {
	          InputStream file = service.files().get(fileId).executeMediaAsInputStream();

	          InputStreamReader isReader = new InputStreamReader(file);
		        
		        BufferedReader reader = new BufferedReader(isReader);	//Creating a BufferedReader object
		        StringBuffer sb = new StringBuffer();
		        String str;
		        
		        while((str = reader.readLine())!= null){
		        	row.add(str);
		        	//System.out.println(str);
		        
		        }
	        } catch (IOException e) {
	          System.out.println("An error occurred: " + e);
	        }
			return row;
	        
	        
	      }

	  // ...
}

package com.BDtest.sudukoValidator;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;

public class fileValidator {
	
	private static void errorMessageHandler(final String message) {
	    System.out.println("-1");
	    System.out.println(message);
	    System.exit(-1);
	  }
	
	public int[][] create2DIntMatrixFromFile(String filename) throws Exception,NullPointerException,EOFException {
		int[][] matrix = null;
		
		try {
			@SuppressWarnings("unused")
			File tmpDir = new File(filename);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
        	errorMessageHandler("Validation error : File not Found");
		}
        @SuppressWarnings("resource")
        
        BufferedReader buffer = new BufferedReader(new FileReader(filename));
        
        String line;
        int row = 0;
        int colN = 0;
        int size = 0;

        while ((line = buffer.readLine()) != null) {
            try {
	        	String[] vals = line.trim().split(",|\\s,\\s|\\s,|,\\s");
	
	            // Lazy instantiation.
	            if (matrix == null) {
	                size = vals.length;
	                matrix = new int[size][size];
	            }
	
	            for (int col = 0; col < size; col++) {
	            	
	            	matrix[row][col] = Integer.parseInt(vals[col]);
	            }
	            row = row +1 ;
	            colN = colN + 1 ;
	            
            }
            catch (Exception e) {
        		System.out.println(e.getMessage());
        		errorMessageHandler("Validation error : Invalid Sudoku Grid");
            }
        }
        if(row < 9 || colN < 9 || row > 9 || colN > 9) {
    		throw new Exception ("Validation error : Invalid Grid");
        }
        return matrix;
    }
}

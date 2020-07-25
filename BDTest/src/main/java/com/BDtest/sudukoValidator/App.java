package com.BDtest.sudukoValidator;

import com.BDtest.sudukoValidator.fileValidator;
import com.BDtest.sudukoValidator.validateGrid;

public class App 
{
	private static void errorMessageHandler(final String message) {
		if(message==null) {
    		errorMessageHandler("Validation error : Invalid Sudoku Grid");
    	}
	    System.out.println("-1");
	    System.out.println(message);
	    System.exit(-1);
	  }
    
	public static void main( String[] args )
    {
		/*if (args.length != 1) {
	      errorMessageHandler("Error : the program must be called with 1 filename");
	    }*/
		
    	int[][] matrix = null;

        try {
            //matrix = new fileValidator().create2DIntMatrixFromFile(args[0]);
        	matrix = new fileValidator().create2DIntMatrixFromFile("validText.txt");
        } catch (Exception e) {
        	errorMessageHandler(e.getMessage());
        }
        
        new validateGrid().validateBoard(matrix);
    }
}


package com.BDtest.sudukoValidator;

import com.BDtest.sudukoValidator.validateRow;
import com.BDtest.sudukoValidator.validateColumn;
import com.BDtest.sudukoValidator.validateCell;

public class validateGrid {
	
	public static void printMatrix(int[][] matrix) {
        String str = "";
        int size = matrix.length;

        if (matrix != null) {
            for (int row = 0; row < size; row++) {
                str += " ";
                for (int col = 0; col < size; col++) {
                    str += String.format("%2d",  matrix[row][col]);
                    if (col < size - 1) {
                        str += " | ";
                    }
                }
                if (row < size - 1) {
                    str += "\n";
                    for (int col = 0; col < size; col++) {
                        for (int i = 0; i < 4; i++) {
                            str += "-";
                        }
                        if (col < size - 1) {
                            str += "+";
                        }
                    }
                    str += "\n";
                } else {
                    str += "\n";
                }
            }
        }

        System.out.println(str);
    }
	
	private static void errorMessageHandler(final String message) {
		if(message==null) {
    		errorMessageHandler("Validation error : Invalid Sudoku Grid");
    	}
	    System.out.println("Invalid");
	    System.out.println(message);
	    System.exit(-1);
	  }
	
	public void validateBoard(int[][] matrix) {
		
		int res1 = 0;
		int res2 = 0;
		int res3 = 0;
		
		printMatrix(matrix);
		int size = matrix.length;
		for(int i =0; i<size; i++) {
			try {
				res1 = new validateRow().validRow(i,matrix);
				res2 = new validateColumn().validColumn(i,matrix);
			}
			catch (Exception e) {
				errorMessageHandler(e.getMessage());
			}
		}
		try {
			res3 = new validateCell().subSquare(matrix);
			if(res3 == 1 && res2 == 1 && res1 == 1) {
				System.out.println("valid");
			}
		}
		catch (IllegalArgumentException e) {
			errorMessageHandler(e.getMessage());
		}
		
	}

}

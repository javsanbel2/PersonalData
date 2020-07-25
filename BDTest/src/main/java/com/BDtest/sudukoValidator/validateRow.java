package com.BDtest.sudukoValidator;

import java.util.HashSet;
import java.util.Set;

public class validateRow {
	public int validRow(int row, int[][] matrix)throws IllegalArgumentException {
		int size = matrix.length; 
		Set<Integer> s = new HashSet<Integer>();
		for(int i=0; i<size;i++) {
			if(matrix[row][i]<1 || matrix[row][i]>9) {
				row=row+1;
				i=i+1;
				throw new IllegalArgumentException("illegal value at row " + row + " column " +i );
			}
			else {
				if(s.add(new Integer(matrix[row][i]))==false) {
					row=row+1;
					i=i+1;
					throw new IllegalArgumentException("repeated value at row " + row + " column " +i );
					
				}
			}
		}
		return 1;
	}

}

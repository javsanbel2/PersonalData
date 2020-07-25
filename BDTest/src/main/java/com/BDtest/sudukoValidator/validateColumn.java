package com.BDtest.sudukoValidator;

import java.util.HashSet;
import java.util.Set;

public class validateColumn {
	
	public int validColumn(int col, int[][] matrix)throws IllegalArgumentException {
		int size = matrix.length; 
		Set<Integer> s = new HashSet<Integer>();
		for(int i=0; i<size;i++) {
			if(matrix[i][col]<1 || matrix[i][col]>9) {
				col=col+1;
				i=i+1;
				throw new IllegalArgumentException("illegal value at row " + i + " column " + col );
			}
			else {
				if(s.add(new Integer(matrix[i][col]))==false) {
					col=col+1;
					i=i+1;
					throw new IllegalArgumentException("repeated value at row " + i + " column " + col );
				}
			}
		}
		return 1;
	}
}

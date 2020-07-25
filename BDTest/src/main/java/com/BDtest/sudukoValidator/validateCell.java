package com.BDtest.sudukoValidator;

import java.util.HashSet;
import java.util.Set;

public class validateCell {
	
	public int subSquare(int[][] matrix) throws IllegalArgumentException {
		int size = matrix.length;
		for(int row =0 ; row<size; row=row+3) {
			for(int col =0; col<size; col=col+3) {
				Set<Integer> s = new HashSet<Integer>();
				for(int r=row; r<row+3; r++) {
					for(int c=col; c<col+3; c++) {
						if(matrix[r][c] < 0 || matrix[r][c] > 9) {
							r=r+1;
							c=c+1;
							throw new IllegalArgumentException("illegal value at row " + r + " column " + c );
						}
						else {
							if(s.add(new Integer(matrix[r][c]))==false) {
								r=r+1;
								c=c+1;
								throw new IllegalArgumentException("repeated value at row " + r + " column " +c );
								
							}
						}
					}
				}
			}
		}
		return 1;
	}

}

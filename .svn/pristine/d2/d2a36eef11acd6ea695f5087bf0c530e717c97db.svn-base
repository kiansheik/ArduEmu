package testcode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class TestCode {
	public static String stripNull(String s) {
		String out = "";
		for(int i = 0; i < s.length(); i++) {
			if(s.codePointAt(i) != 0) out += s.charAt(i);
		}
		return out;
	}
	
	public static void main(String[] args) throws IOException {
		File f = new File("ardemudumpv1.txt"); System.out.println(f.getAbsolutePath());
		BufferedWriter b = new BufferedWriter(new FileWriter(new File("ardemudumpv1filter.txt")));
		BufferedReader r = new BufferedReader(new FileReader(f)); 
		
		String sx = "";
		while(sx != null) {sx = r.readLine(); if(sx == null) break;
			sx = stripNull(sx);
			System.out.println(Arrays.toString(sx.toCharArray()));
			if(sx.contains(":") && !sx.contains("<") && !sx.contains("/") && !sx.contains("(") && !sx.contains("public")) b.append(sx + "\n");}
		b.flush(); b.close();
	}
}

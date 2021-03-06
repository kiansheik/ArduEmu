package arduio;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import poolsync.PoolInput;

public class HexReader extends PoolInput {
	private char[] data;
	private int i = 0; 
	
	public HexReader(String filePath) throws FileNotFoundException {
		File f = new File(filePath);
		Scanner s = new Scanner(f);
		String sx = ""; String nl = "";
		while((nl = s.nextLine()) != null) sx = sx.concat(nl.substring(9, nl.length()-3))) + "\n";
		data = sx.toCharArray();
	}
	
	@Override
	public Object nextInput() {
		if(i+8 > data.length-1) throw new IndexOutOfBoundsException("The index exceeds the array size!");
		for(int j = 0; j < 8; j++) {
			char c = data[i++];
			
		}
	}

	@Override
	public boolean hasNextInput() {
		
	}
}

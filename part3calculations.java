package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class part3calculations {

	public static void main(String[] args) throws InsufficientBitsLeftException, IOException {
	
		String inputFile = "compressed.dat";	
		ArrayList<Node> entries = new ArrayList<Node>();
		
		
		FileInputStream fis = new FileInputStream(inputFile);
		InputStreamBitSource bit_source = new InputStreamBitSource(fis);

	for (int i = 0; i < 256; i++) {
		int nextLen = bit_source.next(8);
		Node next = new Node(i, nextLen);
		entries.add(next);
	}
	
	fis.close();
	String input_file_name = "/users/garrettbolter/downloads/reuncompressed.txt";
	FileInputStream fis2 = new FileInputStream(input_file_name);
	InputStreamBitSource bit_source2 = new InputStreamBitSource(fis2);

	int[] symbol_counts = new int[256]; //changed to double
	int num_symbols = 0;
	
	// Read in each symbol (i.e. byte) of input file and 
	// update appropriate count value in symbol_counts
	// Should end up with total number of symbols 
	// (i.e., length of file) as num_symbols
	
	while (true) {
		int bit = 0;
		try {
			bit = bit_source2.next(8);
		} catch (InsufficientBitsLeftException e) {
			break;
		}
		if (bit < 0 || bit > 256) {
			break;
		}
		symbol_counts[bit]++;	
		num_symbols++;
		}

	
	// Close input file
	fis2.close();
	List<HuffNode> nodes = new ArrayList<HuffNode>();

	// Create array of symbol values
	
	int[] symbols = new int[256];
	for (int i=0; i<256; i++) {
		symbols[i] = i;
		nodes.add(i, (new HuffNode(i, (double) symbol_counts[i] / num_symbols)));
//		 System.out.println("Symbol " + i+ ": " + (char) i + "  Probability: " + nodes.get(i).count());
	}
	double entropy = 0;
	for (int i = 0; i < 256; i++) {
		double p = nodes.get(i).count();
		if (p != 0) {
//		double logp = (Math.log(p)/Math.log(2));
		
		double entropy1 = p*entries.get(i).getLen();
		entropy += entropy1; 
//				* entries.get(i).getLen();
	}
	}
	System.out.println(entropy);
		

	}
	
}

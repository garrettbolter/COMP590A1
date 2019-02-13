package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;


public class Decode {
	

	public static void main(String[] args) throws InsufficientBitsLeftException, IOException {
		
	String inputFile = "/users/garrettbolter/downloads/compressV3.txt";
	String outputFile = "/users/garrettbolter/downloads/reuncompressed.txt";

	
	ArrayList<Node> entries = new ArrayList<Node>();
	
	
		FileInputStream fis = new FileInputStream(inputFile);
		InputStreamBitSource bit_source = new InputStreamBitSource(fis);

	for (int i = 0; i < 256; i++) {
		int nextLen = bit_source.next(8);
		Node next = new Node(i, nextLen);
		entries.add(next);
	}
	
	Collections.sort(entries, Node.sortLen);
	
	int bodyLength = bit_source.next(32);
	HuffmanTree tree = new HuffmanTree();
	for (int i = 0; i < entries.size(); i++) {	
		tree.insert(entries.get(i));
	}
	
	Node decoder = tree.root;
	
	try {
		FileOutputStream fos = new FileOutputStream(outputFile);

		int i = 0;
		while(i<bodyLength) {
		int sym = bit_source.next(1);
		if(sym == 1) {
			decoder = decoder.right();
			if (decoder.getSymbol() != -10) {
				fos.write((char) decoder.getSymbol());
				i++;
				decoder = tree.root;
			}
		} else if(sym == 0) {
			decoder = decoder.left();
			if (decoder.getSymbol() != -10) {
				fos.write((char)decoder.getSymbol());
				i++;
				decoder = tree.root;
				
			}
		}
	}
	
	fos.flush();
	fos.close();
	fis.close();
	
	} catch (IOException e) {
		e.printStackTrace();
	}
	}

}

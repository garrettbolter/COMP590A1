package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.sun.javafx.collections.MappingChange.Map;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;
import io.OutputStreamBitSink;

import io.OutputStreamBitSink;

public class HuffEncodeT2 {
	
	public static void main(String[] args) throws IOException, InsufficientBitsLeftException {
		String input_file_name = "/users/garrettbolter/downloads/reuncompressed.txt";
		String output_file_name = "/users/garrettbolter/downloads/compressV3.txt";

		FileInputStream fis = new FileInputStream(input_file_name);
		InputStreamBitSource bit_source = new InputStreamBitSource(fis);

		int[] symbol_counts = new int[256]; //changed to double
		int num_symbols = 0;
		
		// Read in each symbol (i.e. byte) of input file and 
		// update appropriate count value in symbol_counts
		// Should end up with total number of symbols 
		// (i.e., length of file) as num_symbols
		
		while (true) {
			int bit = 0;
			try {
				bit = bit_source.next(8);
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
		fis.close();
		
		List<HuffNode> nodes = new ArrayList<HuffNode>();
		double[] problem = new double[256];
		// Create array of symbol values
		
		int[] symbols = new int[256];
		for (int i=0; i<256; i++) {
			symbols[i] = i;
			nodes.add(i, (new HuffNode(i, (double) symbol_counts[i] / num_symbols)));
			problem[i] = nodes.get(i).count();
//			 System.out.println("Symbol " + i+ ": " + (char) i + "  Probability: " + nodes.get(i).count());
		}
		double entropy = 0;
		for (int i = 0; i < 256; i++) {
			double p = nodes.get(i).count();
			if (p != 0) {
			double logp = (Math.log(p)/Math.log(2));
			
			entropy += p*logp;
		}
		}
//		System.out.println(entropy);
		
		Collections.sort(nodes, HuffNode.sortFreq);
		
		// Create encoder using symbols and their associated counts from file.

		
		// Given symbols and their associated counts, create initial
		// Huffman tree. Use that tree to get code lengths associated
		// with each symbol. Create canonical tree using code lengths.
		// Use canonical tree to form codes as strings of 0 and 1
		// characters that are inserted into _code_map.

		// Start with an empty list of nodes


		// While you still have more than one node in your list...
		while(nodes.size() > 1) {
			// Remove the two nodes associated with the smallest counts
			HuffNode temp1 = nodes.get(nodes.size() - 1);
			nodes.remove(nodes.size() - 1);
			HuffNode temp2 = nodes.get(nodes.size() - 1);
			nodes.remove(nodes.size() - 1);
			// Create a new internal node with those two nodes as children.
			HuffNode internal = new HuffNode(-10, -10.0);
			internal = internal.buildInternal(temp1, temp2);
			// Add the new internal node back into the list
			nodes.add(internal);
			// Resort
			Collections.sort(nodes, HuffNode.sortFreq);
		}

		// Create a temporary empty mapping between symbol values and their code strings
		HashMap<Integer, String> cmap = new HashMap<Integer, String>();
		// Start at root and walk down to each leaf, forming code string along the
		// way (0 means left, 1 means right). Insert mapping between symbol value and
		// code string into cmap when each leaf is reached.
		makeCode(nodes.get(0), "", cmap);
		// Create empty list of SymbolWithCodeLength objects
		
		List<Node> symLen = new ArrayList<Node>();

		// For each symbol value, find code string in cmap and create new SymbolWithCodeLength
		// object as appropriate (i.e., using the length of the code string you found in cmap).
		for (int i = 0; i < 256; i++) {
			int len = cmap.get(i).length();
			Node add = new Node(i, len);
			symLen.add(i, add);
		}
		// Sort sym_with_lenght
		Collections.sort(symLen, Node.sortLen);
		// Now construct the canonical tree as you did in HuffmanDecodeTree constructor
		HuffmanTree canon = new HuffmanTree();
		
		for (int i = 0; i < symLen.size(); i++) {
			canon.insert(symLen.get(i));
		}	
		
		// Create code map that encoder will use for encoding
		
		HashMap<Integer, String> codeMap = new HashMap<Integer, String>();
//		
//		// Walk down canonical tree forming code strings as you did before and
//		// insert into map.	
		Node decoder = canon.root;
		
		lastCode(decoder, "", codeMap);
		
		FileOutputStream fos = new FileOutputStream(output_file_name);
		OutputStreamBitSink bit_sink = new OutputStreamBitSink(fos);

		// Write out code lengths for each symbol as 8 bit value to output file.
		for (int i = 0; i < cmap.size(); i++) {
			bit_sink.write(cmap.get(i).length(), 8);
		}
		// Write out total number of symbols as 32 bit value.
		bit_sink.write(num_symbols, 32);
		// Reopen input file.
		fis = new FileInputStream(input_file_name);

		// Go through input file, read each symbol (i.e. byte),
		// look up code using encoder.getCode() and write code
        // out to output file.
		while (fis.available() > 0) {
			int bit = fis.read();
			String write = codeMap.get(bit);
			bit_sink.write(write);
		}
		// Pad output to next word.
		bit_sink.padToWord();

		// Close files.
		fis.close();
		fos.close();
		double ent = 0;
		for (int i = 0; i < 256; i++) {
			double prob = problem[i];
			if (prob != 0) {
//				double logp = (Math.log(p)/Math.log(2));
				
				double entropy1 = prob*cmap.get(i).length();
				ent += entropy1; 
//						* entries.get(i).getLen();
			}
		}
		System.out.println(ent);
		
		
//	public String getCode(int symbol) {
//		return _code_map.get(symbol);
//	}
//
//	public void encode(int symbol, OutputStreamBitSink bit_sink) throws IOException {
//		bit_sink.write(_code_map.get(symbol));
//	}
		

	}
	public static void makeCode(HuffNode root, String s, HashMap<Integer, String> h) { 
		  
        if (root.left() == null && root.right() == null) { 
            h.put(root.symbol(), s);
            return; 
        } 
  
        if (root.left() != null) { 
        makeCode(root.left(), s + "0", h);
        }
        if (root.right() != null) {
        makeCode(root.right(), s + "1", h); 
    }
	}
	public static void lastCode(Node root, String s, HashMap<Integer, String> h) { 
		  
        if (root.left() == null && root.right() == null) { 
            h.put(root.getSymbol(), s);
            return; 
        } 
  
        if (root.left() != null) { 
        lastCode(root.left(), s + "0", h);
        }
        if (root.right() != null) {
        lastCode(root.right(), s + "1", h); 
    }
	}
	
}

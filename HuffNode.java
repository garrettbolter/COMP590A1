package main;

import java.util.Comparator;

public class HuffNode {
	private int symbol;
	private double freq;
	private HuffNode left;
	private HuffNode right;
	private int height;
	private String s;
	
	public HuffNode(int symbol, double freq) {
		this.symbol = symbol;
		this.freq = freq;
		this.left = null;
		this.right = null;
	
	}
	
	public String getString() {
		return this.s;
	}
	
	public void setString(String s) {
		this.s = s;
	}
	
	public double count() {
		// TODO Auto-generated method stub
		return this.freq;
	}

	
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return false;
	}

	
	public int symbol() {
		// TODO Auto-generated method stub
		return this.symbol;
	}


	public int height() {
		return this.height;
	}

	
	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}


	public HuffNode buildInternal(HuffNode lc, HuffNode rc) {
		// TODO Auto-generated method stub
		HuffNode build = new HuffNode(-10, -10.0);
		build.freq = lc.freq + rc.freq;
		build.height = Math.max(lc.height, rc.height) + 1;
		build.left = lc;
		build.right = rc;
		build.symbol = -10;
		return build;
		// may need to return this Node
		
	}

	
	public HuffNode left() {
		// TODO Auto-generated method stub
		return this.left;
	}

	
	public HuffNode right() {
		// TODO Auto-generated method stub
		return this.right;
	}
	
	public static Comparator<HuffNode> sortFreq = new Comparator<HuffNode>() {
		public int compare(HuffNode n1, HuffNode n2) {
//		double freq1 = n1.count();
//		System.out.println(freq1);
//		double freq2 = n2.count();
		int dcheck = Double.compare(n2.count(), n1.count());	
		int h1 =  n1.height();
		int h2 =  n2.height();
			if (dcheck != 0) {
				return dcheck;
			} else {
				return h1 - h2;
			}


	}
	};
	

}

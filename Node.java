package main;

import java.util.Comparator;

public class Node {
	private int symbol;
	private int length;
	private Node left;
	private Node right;
	private boolean isFull;
	private String s;
	
	public Node(int symbol, int length) {
		this.symbol = symbol;
		this.length = length;
		this.left = null;
		this.right = null;
		if (symbol > 0) {
		this.isFull = true;
		} else {
			this.isFull = false;
		}
	}
	
	public boolean checkFull() {
		if (this.getSymbol() < 0) {
		if (this.left != null 
				&& this.right != null 
				&& this.left.checkFull() 
				&& this.right.checkFull()) { 
				return true;
			} else {
				return false;
			}
		
		} else {
			return true;
		}
	}

	public void setString(Node n, String s) {
		n.s = s;
	}
	
	public String getString() {
		return this.s;
	}
	
	public int getSymbol() {
		return this.symbol;
	}
	
	public int getLen() {
		return this.length;
	}
	
	public void setSymbol(int x) {
		this.symbol = x;
		if (x > 0) {
			this.isFull = true;
		}
	}
	 
	 public Node left() {
		 return this.left;
	 }
	 
	 public Node right() {
		 return this.right;
	 }
	 
	 public boolean isFull() {
		return this.isFull;
	 }
	 
	 public void setRight(Node n) {
		 this.right = n;
	 }
	 public void setLeft(Node n) {
		 this.left = n;
	 }
	 
	 public void setIsFull() {
		 this.isFull = true;
	 }

	public static Comparator<Node> sortLen = new Comparator<Node>() {

		public int compare(Node n1, Node n2) {

		   int len1 = n1.getLen();
		   int len2 = n2.getLen();

		   
		   return len1-len2;

		   
		   
	   }
	};

	
}

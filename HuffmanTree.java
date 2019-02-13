package main;

public class HuffmanTree {
	 Node root;

	 public HuffmanTree() {
		 root = new Node(-10,-5000);
	 }
	 
	 public void insert(Node node) {
		 Node search = root;
		 
		 for (int i = 0; i < node.getLen(); i++) {

			 if (search.getSymbol() == -10) {
				 if (search.left() == null) {
				   	search.setLeft(new Node(-10, -5000));
				   	search = search.left();
				   	
//				   	System.out.print("new left --> ");
				   	
			 	}
			 
			 	 else if (search.left().getSymbol() == -10 && !search.left().checkFull()) {
		
			 		 search = search.left();
			 		
//			 		System.out.print("left -->");
			 		
			 		
			 	} else if (search.right() == null) {
			 		search.setRight(new Node(-10, -5000));
			 		search = search.right();
			 		
//			 		System.out.print("new right -->");
			 		
				 
			 	} else if (search.right().getSymbol() == -10 && !search.right().checkFull()) {
			 		search = search.right();
			 		
			 		
			 		
//			 		System.out.print("right -->");
			 } 

 
			 } 	
		 }
		 search.setSymbol(node.getSymbol());
//		 System.out.println("set: " + search.getSymbol());
		 

		 
	 }
		 
}
	 

	


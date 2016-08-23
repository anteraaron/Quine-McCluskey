/**
 * Driver for Quine McCluskey which accepts an ArrayList of minterms
 */
package tabulationMethod;
import java.util.ArrayList;

public class QuineMcCluskeyDriver {

	public static void main(String[] args) {
		//Sample min terms for the Class QuineMcCluskey
		
		ArrayList<Integer> array = new ArrayList<Integer>();
		/*array.add(1);
		array.add(3);
		array.add(5);
		array.add(7);
		array.add(9);
		array.add(11);
		array.add(13);
		array.add(15);
		array.add(16);
		*/
		/*array.add(1);
		array.add(4);
		array.add(8);
		array.add(6);
		array.add(9);
		array.add(10);
		array.add(7);
		array.add(11);
		array.add(15);
		*/
		
		/*array.add(2);
		array.add(4);
		array.add(9);
		array.add(10);
		array.add(15);
		array.add(16);
		array.add(17);
		array.add(18);
		array.add(19);
		array.add(21);
		array.add(23);
		array.add(25);
		array.add(27);
		array.add(29);
		*/
		
		/*array.add(0);
		array.add(1);
		array.add(8);
		array.add(9);
		array.add(12);
		array.add(13);
		array.add(14);
		array.add(15);
		array.add(32);
		array.add(33);
		array.add(37);
		array.add(39);
		array.add(48);
		array.add(56);
		*/
		
		
		array.add(0);
		array.add(1);
		array.add(8);
		array.add(9);
		array.add(12);
		array.add(13);
		array.add(14);
		array.add(15);
		array.add(32);
		array.add(33);
		array.add(37);
		array.add(39);
		array.add(50);
		array.add(51);
		array.add(56);
		array.add(57);
		array.add(59);
		array.add(61);
		array.add(62);
		
		
		//array.add(67108863);  //This is the maximum number of minterms (26 Variables. all alphabet)
		//array.add(1);
		//array.add(62);
		
		
		/*array.add(0);
		array.add(1);
		array.add(2);
		array.add(3);
		array.add(4);
		array.add(5);
		array.add(6);
		array.add(7);
		*/
	/*
		array.add(2);
		array.add(3);
		array.add(5);
		array.add(7);
		array.add(8);
		array.add(9);
		array.add(10);
		array.add(11);
		array.add(12);
		array.add(16);
		array.add(17);
		array.add(21);
		array.add(23);
		array.add(27);
		array.add(30);
		array.add(31);
		array.add(33);
		array.add(34);
		array.add(35);
		array.add(36);
		array.add(38);
		array.add(39);
		array.add(40);
		array.add(43);
		array.add(46);
		array.add(47);
		array.add(48);
		array.add(52);
		array.add(55);
		array.add(57);
		array.add(58);
		array.add(59);
		array.add(60);
		array.add(62);	
		*/
	
		QuineMcCluskey tabulation = new QuineMcCluskey(array);
		
		//If you want to add your own variable uncomment the code below. Note that it should have the correct number of variable (e.g. 1-15 is 4 variables so add A-D)
		
		/*
		ArrayList<String> variables = new ArrayList<String>();
		variables.add("A");
		variables.add("B");
		variables.add("C");
		variables.add("D");
		variables.add("E");
		variables.add("F");
		QuineMcCluskey tabulation = new QuineMcCluskey(array, variables);
		*/
		
		/*
		for(int i = 0; i<tabulation.getMinTermsSize(); i++){
		//	System.out.println("[" + tabulation.getMinTerms(i) + "] = " + tabulation.getBinaryString(i)); //uncomment to view minterm to binary conversion
		}
		*/
		//System.out.println(tabulation.getLength()); //Returns the number of variable available
		
		System.out.println(tabulation.getAnswer());
		
		
	}

}

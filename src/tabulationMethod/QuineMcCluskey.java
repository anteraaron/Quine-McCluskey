/**
 * A program that simulates Quine-McCluskey method or also known as the "Tabulation Method".
 * 
 * @author Anter Aaron M. Custodio && Carl Zachery Viernes
 * U.P. Manila B.S. Computer Science
 * C.S. 130
 */
package tabulationMethod;
import java.util.*;


public class QuineMcCluskey {
	private int length; //number of variables
	private ArrayList<Integer> minTerms; //container for the inputted minTerms
	private ArrayList<String> binaryString, subGroup, subGroupIndex, fixedTerms, variables; //elements of the nested arraylist which contains binary converted minterms, grouped minterms, index of the grouped minterms, essential prime implicants, variable to be used, respectively
	private ArrayList<ArrayList<String>> group, groupIndex, groupFinalTerms; //Nested arraylist for the above arraylist
	private ArrayList<Boolean> checked; //An arrayList which tells whether a minTerm is checked or unchecked
	private ArrayList<ArrayList<Boolean>> checkedGroup; //nested arraylist for the above arraylist (unchecked)
	private int control;
	/**
	 * Default Constructor. No Length, No Minterms, No Final Terms and default variable
	 */
	public QuineMcCluskey(){
		setLength(0);
		setMinTerms(new ArrayList<Integer>());
		this.fixedTerms= new ArrayList<String>();
		this.groupFinalTerms = new ArrayList<ArrayList<String>>();
		this.variables = setVariables();
	}
	
	
	/** 
	 * A constructor which accepts a min terms only and uses pre-defined variables.
	 * @param minTerms an arraylist of minterms inputted by the user
	 */
	public QuineMcCluskey(ArrayList<Integer> minTerms){
		this.fixedTerms= new ArrayList<String>();
		this.groupFinalTerms = new ArrayList<ArrayList<String>>();
		this.variables = setVariables();
		begin(minTerms); //starts the algorithm
	}
	
	
	/**
	 * A constructor which accepts min terms and custom variables inputted by the user
	 * @param minTerms an arraylist of minterms inputted by the user
	 * @param variables an arraylist of variables inputted by the user
	 */
	public QuineMcCluskey(ArrayList<Integer> minTerms, ArrayList<String> variables){
		this.fixedTerms= new ArrayList<String>();
		this.groupFinalTerms = new ArrayList<ArrayList<String>>();
		Collections.reverse(variables);
		this.variables = new ArrayList<String>(variables);
		begin(minTerms); //starts the algorithm
	}
	
	
	/**
	 * A function that calls different method in Quine McCluskey
	 * @param minTerms minterms inputted by the user
	 */
	private void begin(ArrayList<Integer> minTerms){
		int i = 1;
		setMinTerms(minTerms); //Calls the method that Arranges the min terms in ascending order
		//determining the number of variables
		while(true){	
			if((int)Math.pow(2,i)-1 < getMinTerms(getMinTermsSize()-1)){
				i++;
			}else{
				setLength(i); //sets the number of variables
				break;
			}
		}	

		setBinaryString(); //Converts the minterms into binary form and populates the binaryString arrayList
		groupMinTerms(); //groups the minterms according to the number of 1's in their binary converted form
		removeGroupDuplicates();// removes duplicates from the Group
		essentialPrimeImplicant(); //simulate the prime implicant chart	
	}
	
	
	/**
	 * A method that finds the prime implicants of the inputed minterms.
	 */
	private void groupMinTerms(){
		int count, occurence, pos = 0;
		group = new ArrayList<ArrayList<String>>(); //ArrayList which contains an ArrayList of the binary form of the minterms with the same number of 1's.
		groupIndex = new ArrayList<ArrayList<String>>(); //ArrayList which contains an ArrayList of the minterms with the same number of 1's'
		checkedGroup = new ArrayList<ArrayList<Boolean>>(); //ArrayList which contains an ArrayList that determines whether the minterms are checked or not.
	
		//A loop that groups the minterms according to the number of their 1's.
		for(int i=0;i<getMaxOnes();i++){ 
			subGroup = new ArrayList<String>();
			subGroupIndex = new ArrayList<String>();
			checked = new ArrayList<Boolean>();
			
			if(getMinTermsSize()==1){ //If the user inputed only one minterm.
				subGroup.add(getBinaryString(0));
				subGroupIndex.add(getMinTerms(0)+"");
				checked.add(false);
			}
			else{

				for(int j=0;j<getMinTermsSize();j++){
				count = getBinaryString(j).length() - getBinaryString(j).replace("1", "").length(); //Determines the number of 1's of a minterm
					if(count == i){ //groups them according to the number of 1's
						subGroup.add(getBinaryString(j));
						subGroupIndex.add(getMinTerms(j)+"");
						checked.add(false);
					}
				}
			}
		
			if(!subGroup.isEmpty()){ //add only a subgroup(group that contains same number of 1's) that contains a minterm, otherwise ignore.
				group.add(subGroup);
				groupIndex.add(subGroupIndex);
				checkedGroup.add(checked);
			}
		}
		
		String temp1, temp2;//temporary container for strings
		//a loop that imitates the replacement of different binary digits by "-"
		for(int i=0;i<group.size()-1;i++){
			subGroup = new ArrayList<String>();
			subGroupIndex = new ArrayList<String>();
			checked = new ArrayList<Boolean>();
			
			for(int j=0; j<group.get(i).size(); j++){
				temp1 = group.get(i).get(j);
				
				for(int k=0; k<group.get(i+1).size(); k++){
					temp2 = group.get(i+1).get(k);
					occurence = 0; 
					//determines the number of occurences that the binary digits are not the same
					for(int l=0; l<getLength();l++){
						if(temp1.charAt(l)!=temp2.charAt(l)){
							occurence++;
							pos = l;
						}
						if(occurence>1)
							break;
					}
					if(occurence==1){ //if the two group of binary digits has only one different binary digit. replace the different digit by "-"
						StringBuilder replacement = new StringBuilder(temp1);
						replacement.setCharAt(pos, '-');
						subGroup.add(replacement.toString());
						subGroupIndex.add(groupIndex.get(i).get(j)+","+groupIndex.get(i+1).get(k));
						checked.add(false);
						checkedGroup.get(i).set(j, true); //assign true to the checked group which means it is now checked
						checkedGroup.get(i+1).set(k, true);
					}
					
				}
			}
			
			if(!subGroup.isEmpty()){ //adds the group only if it is not empty. otherwise ignore. It happens when all of the group of binary digits have differences in their binary digits greater than 1
				group.add(subGroup);
				groupIndex.add(subGroupIndex);
				checkedGroup.add(checked);
			}
		}
	}
	
	
	/**
	 * A method that removes duplicates in the resultant prime implicants.
	 */
	private void removeGroupDuplicates(){
		//loop that removes the duplicate terms in the group
		for(int i=0;i<group.size();i++){
			for(int j=0;j<group.get(i).size();j++){
				for(int k=0;k<group.get(i).size();k++){
					if(group.get(i).get(j).toString().equals(group.get(i).get(k).toString())&&k!=j){ //removes duplicate terms in the group
						group.get(i).remove(k);
						groupIndex.get(i).remove(k);
						checkedGroup.get(i).remove(k);

						k=0;
						i=0;
						break;
					}		
					
				}
			}	
		}
			
		String[] splitted;
		String temp = "";
		//removes the duplicated minterms in a term
		for(int i=0;i<groupIndex.size();i++){
			for(int j=0;j<groupIndex.get(i).size(); j++){
				splitted = groupIndex.get(i).get(j).split(",");
					
				for(int k=0;k<splitted.length;k++){
					for(int l=0;l<splitted.length;l++){
							
						if(splitted[k].equals(splitted[l])&&k!=l){
							splitted[k] = "";
							temp = temp.replace(",", "");
						}
					}
					temp+=splitted[k] + ",";
							
				}
				if(temp.charAt(0)==','){//trims trailing ,
					temp = temp.substring(1, temp.length());
				}	
				if(temp.endsWith(",")){
					temp = temp.substring(0, temp.length()-1);
				}
				
				groupIndex.get(i).set(j, temp);
				temp="";		
			}
		}
	}
	
	
	/**
	 * A method that searches for the prime implicants
	 */
	private void essentialPrimeImplicant(){
		ArrayList<String> temp1 = new ArrayList<String>(); //temporary container for the minterms
		ArrayList<String> temp2 = new ArrayList<String>(); //temporary container for the binary converted minterms
		ArrayList<String> temp3 = new ArrayList<String>(); //temporary container for the prime implicant
		ArrayList<String> rows = new ArrayList<String>(); //remaining rows after getting the prime implicant
		ArrayList<String> rowsBinary = new ArrayList<String>();//binary form of the rows
		ArrayList<String> columns = new ArrayList<String>();//remaining columns after getting the prime implicant
		int once = 0, ctr = 0;
		String[] splitted;
		
		//Loop that gets the minterms which are unchecked.
		for(int i=0;i<checkedGroup.size();i++){
			for(int j=0;j<checkedGroup.get(i).size();j++){
				if(checkedGroup.get(i).get(j)==false){
					temp1.add(groupIndex.get(i).get(j).toString());
					temp2.add(group.get(i).get(j).toString());
				}
			}
		}
		
		//Loop that searches for the prime implicants or the terms that only has one 'x' in a column
		for(int i=0; i<getMinTermsSize(); i++){
			for(int j=0;j<temp1.size();j++){
				splitted = temp1.get(j).toString().split(",");
							
				for(int k=0;k<splitted.length;k++){
					if(minTerms.get(i).toString().equals(splitted[k])){
						ctr++;
						once = j;
						if(ctr>1){
							break;
						}
					}
				}
			}
			if(ctr==1){	
				temp3.add(temp1.get(once));
				setFinalTerms(temp2.get(once));
			}
			ctr = 0;
		}
		
		//removes duplicate for temp3
		temp3 = new ArrayList<String>(removeDuplicates(temp3));
		
		//removes duplicate for prime implicants
		this.fixedTerms = new ArrayList<String>(removeDuplicates(this.fixedTerms));
		
		//searching for the remaining columns after removing the prime implicant
		for(int i=0;i<minTerms.size();i++){
			ctr = 0;
			for(int j=0;j<temp3.size();j++){
				splitted = temp3.get(j).toString().split(",");
				for(int k=0;k<splitted.length;k++){
					
					if(minTerms.get(i).toString().equals(splitted[k])){
						ctr++;
					}
				}
			}
			if(ctr==0){
				columns.add(minTerms.get(i).toString());
			}	
		}

		//Searching for the remaining rows after getting the prime implicants
		ctr = 0;
		for(int i=0;i<columns.size();i++){
			for(int j=0;j<temp1.size();j++){
				splitted = temp1.get(j).split(",");
				for(int k=0;k<splitted.length;k++){
					if(splitted[k].equals(columns.get(i))){
						rows.add(temp1.get(j));
						rowsBinary.add(temp2.get(j));
					}
				}

			}
		}
	
		//remove duplicate for the rows
		for(int i=0;i<rows.size();i++){
			for(int j=0;j<rows.size();j++){
				if(rows.get(i).equals(rows.get(j))&&i!=j){
					rows.remove(j);
					rowsBinary.remove(j);
					j--;
				}
			}
		}
		
		
		if(columns.isEmpty()){ //if the term is already simplified after getting the prime implicant
			groupFinalTerms.add(fixedTerms);
		}else{
			reducedImplicant(columns, rows, rowsBinary, this.fixedTerms); //perform the reduced prime implicant table
			removeFinalDuplicates();
		}
	}

	
	/**
	 * Method that simulates the reduced implicant table.
	 * @param columnsOrig remaining columns passed.
	 * @param rowsOrig remaining rows passed.
	 * @param rowsBinaryOrig remaining rows which contains the binary equivalent of the minterm.
	 * @param totalOrig an ArrayList of the terms of Essential Prime implicants and prime implicants
	 */
	private void reducedImplicant(ArrayList<String> columnsOrig, ArrayList<String> rowsOrig, ArrayList<String> rowsBinaryOrig, ArrayList<String> totalOrig){
	
		//Temporary container for the parameters because it will be manipulated
		ArrayList<String>columns;
		ArrayList<String>rows; 
		ArrayList<String>rowsBinary;
		ArrayList<String>total;
		ArrayList<Integer> temp = new ArrayList<Integer>(); //Container for the number of occurences below
		ArrayList<String> temp1;
		ArrayList<String> temp2;
	
		String[] splitted;
		
		//a loop that counts the number of occurences a row intersected the column. This determines what is the best row to choose in reducing the prime implicant table
		for(int i=0;i<columnsOrig.size();i++){
			for(int j=0;j<rowsOrig.size();j++){
				splitted = rowsOrig.get(j).split(",");
				for(int k=0;k<splitted.length;k++){
					if(columnsOrig.get(i).equals(splitted[k])){
						try{
							temp.set(j, temp.get(j)+1);
						}catch(IndexOutOfBoundsException e){
							temp.add(1);
						}	
					}
				}
			}
		}
		//A recursive loop that simulates the reduced prime implicant table.
		for(int i=0;i<rowsOrig.size();i++){
		if(control>100)
			break;
		
			if(temp.get(i)==getMax(temp)){// if the row has the highest number of occurences, therefore it is a candidate for a prime implicant
				
				//assign the original to the temporary arraylist
				columns = new ArrayList<String>(columnsOrig);
				rows = new ArrayList<String>(rowsOrig);
				rowsBinary = new ArrayList<String>(rowsBinaryOrig);
				total = new ArrayList<String>(totalOrig);
				total.add(rowsBinary.get(i)); //adds the selected prime implicant to the total terms
				splitted = rows.get(i).split(",");
			
				for(int j=0;j<columns.size();j++){
					for(int k=0;k<splitted.length;k++){
					
						if(columns.isEmpty())
							break;
				
						if(splitted[k].equals(columns.get(j))){
							columns.remove(j); //removes the column that has been intersected by the row to simplify the table
							j=0;
						}
	
					}
				}
				
				rows.remove(i); //remove the selected prime implicant
				rowsBinary.remove(i); //removes its binary counterpart
				temp1 = new ArrayList<String>(); //temporary container for the row
				temp2 = new ArrayList<String>(); //temporary container for the binary counterpart
				
				//Gets the rows which has the remaining minterms
				for(int j=0;j<columns.size();j++){
					for(int k=0;k<rows.size();k++){
						splitted = rows.get(k).split(",");
							for(int l=0;l<splitted.length;l++){
								if(splitted[l].equals(columns.get(j))){		
									temp1.add(rows.get(k));
									temp2.add(rowsBinary.get(k));
								}
							}
					}
				}
		
				//removes duplicate for the rows and also removes its duplicate binary counterpart
				for(int j=0;j<temp1.size();j++){
					for(int k=0;k<temp1.size();k++){
						if(temp1.get(j).equals(temp1.get(k))&&j!=k){
							temp1.remove(k);
							temp2.remove(k);
							k=0;
							j=0;
						}
					}
				}
	
				rows = new ArrayList<String>(temp1); //assign to the original row
				rowsBinary = new ArrayList<String>(temp2); //assign to the original binary counterpart
			
				reducedImplicant(columns, rows, rowsBinary, total); //call own function for recursion but the parameter is the reduced form. repeat until column is empty
				if(columns.isEmpty()){	//if column is empty, add the completed term to the arraylist of all possible answers
					groupFinalTerms.add(total); //if column is empty, add the completed term to the arraylist of all possible answers
					removeFinalDuplicates();
					control++;
				}
			}
		}
		
	}
	
	/**
	 * A method that removes the duplicate in an arraylist
	 * @param array array that has duplicates in it
	 * @return returns the array that the duplicates are removed
	 */
	private ArrayList<String> removeDuplicates(ArrayList<String> array){
		for(int i=0;i<array.size();i++){
			for(int j=0;j<array.size();j++){
				if(array.get(i).equals(array.get(j))&&i!=j){
					array.remove(j);
					j--;
				}
			}
		}
		return array;
	}
	/**
	 * Remove Duplicated Final answers or Answers that are the same but only has different order or answers that are longer than the best answer
	 */
	private void removeFinalDuplicates(){
		int ctr = 0;
		int shortest = groupFinalTerms.get(0).size();
		
		for(int i=0;i<groupFinalTerms.size();i++){
			
			if(groupFinalTerms.get(i).size() < shortest)
				shortest = groupFinalTerms.get(i).size();
		}
	
		for(int i=0;i<groupFinalTerms.size();i++){
			if(groupFinalTerms.get(i).size() != shortest){
				groupFinalTerms.remove(i);
				i=0;
			}
		}
		
		for(int i=0;i<groupFinalTerms.size();i++){
			if(groupFinalTerms.get(i).size() != shortest){
				groupFinalTerms.remove(i);
				i=0;
			}
		}
		
		for(int i=0;i<groupFinalTerms.size();i++){
			for(int j=0;j<groupFinalTerms.size();j++){
				for(int k=0;k<groupFinalTerms.get(i).size();k++){
					for(int l=0;l<groupFinalTerms.get(j).size();l++){
						if(groupFinalTerms.get(i).get(k).equals(groupFinalTerms.get(j).get(l))&&i!=j){
							ctr++;
						}
					}
				}
			
				if(ctr==groupFinalTerms.get(i).size()){
					groupFinalTerms.remove(i);
					i=0;
				}
				ctr=0;
			}
		}
	
	}
	
	// Getters and Setters------------------------------------------------------------------------------------------------------
	/**
	 * Returns the minterm
	 * @param index
	 * @return return the specific minterm
	 */
	public int getMinTerms(int index){
		return this.minTerms.get(index);
	}
	/**
	 * Returns the total number of minterms
	 * @return return the total number of minterms
	 */
	public int getMinTermsSize(){
		return this.minTerms.size();
	}
	/**
	 * Gets the total number of variables
	 * @return total number of variables
	 */
	public int getLength(){
		return this.length;
	}
	/**
	 * Returns the binary equivalent of the minterm
	 * @param index the specified index by the user
	 * @return binary string equivalent
	 */
	public String getBinaryString(int index){
		return this.binaryString.get(index);
	}
	/**
	 * Returns the maximum value in the arraylist
	 * @param list the list to be evaluated
	 * @return the maximum integer value in the arraylist
	 */
	private int getMax(ArrayList<Integer> list){
		int max=0;
		for(int i=0;i<list.size();i++){
			if(max<list.get(i))
				max = list.get(i);
		}
		return max;
	}
	/**
	 * Function that returns the highest count of ones
	 * @return int highest count of ones
	 */
	private int getMaxOnes(){
		
		int max = 0;
		for(int i =0;i<binaryString.size();i++){
			if((getBinaryString(i).length() - getBinaryString(i).replace("1", "").length() + 1) > max)
				max = getBinaryString(i).length() - getBinaryString(i).replace("1", "").length() + 1; 
		}
		return max;
	}
	/**
	 * Gets the variable to be used
	 * @param variable index of the variable to get
	 * @return equivalent variable of the inputted index
	 */
	private String getVariables(int variable){
		return this.variables.get(variable);
	}
	/**
	 * Gets the total concatenation of the final answer by adding "+" sign and "or"
	 * @return Answer
	 */
	public String getAnswer(){
		
		String finalTerm = "";
		String[] splitted;
		int pos = 0;
		String temp = groupFinalTerms.get(0).toString().replace("[", "").replace("]", "");//removes trailing braces
		
		if (temp.matches("[-]*")){ //if all are dashes
			finalTerm = "1";
		}else{
		
			for(int i=0;i<groupFinalTerms.size();i++){
				for(int j=0;j<groupFinalTerms.get(i).size();j++){
					
					splitted = groupFinalTerms.get(i).get(j).split("");
					
					for(int k=0;k<splitted.length;k++){
						pos = getLength()-k;
						if(splitted[k].equals("1")){
							finalTerm += getVariables(pos);
						}else if(splitted[k].equals("0")){
							finalTerm += getVariables(pos) + "'";
						}
						
					}
					
					if(j!=groupFinalTerms.get(i).size()-1)
						finalTerm += " + ";
				}
				
				if(i!=groupFinalTerms.size()-1)
					finalTerm += "\n or\n";
			}
				
		}
			return finalTerm;
			
			
		
	}
	/**
	 * Arranges the minterms ascending order
	 * @param minTerms minterm to be arranged
	 */
	private void setMinTerms(ArrayList<Integer> minTerms){
		this.minTerms = new ArrayList<Integer>(minTerms);
		Collections.sort(this.minTerms);
	}
	/**
	 * Sets the length or the number of variable
	 * @param length number of variable
	 */
	private void setLength(int length){
		this.length = length;
	}
	/**
	 * Sets the binary equivalent of the minterms
	 */
	private void setBinaryString(){
		String temp;
		binaryString = new ArrayList<String>();
		
		for(int i = 0; i<getMinTermsSize(); i++){
			temp = Integer.toBinaryString(getMinTerms(i)); //Convert to binary
			
			while(temp.length()!=getLength()){ //add zero padding to balance the terms
				if(temp.length()==0)
					break;
				temp = "0" + temp;
			}
			binaryString.add(temp);
		}
	}
	/**
	 * Sets the essential prime implicant's value
	 * @param value
	 */
	private void setFinalTerms(String value){
		this.fixedTerms.add(value);
	}
	/**
	 * sets a pre-defined variable if the user did not input any variables
	 * @return variable arraylist
	 */
	private ArrayList<String>setVariables(){
		return new ArrayList<String>(Arrays.asList("Z","Y","X","W","V","U","T","S","R","Q","P","O","N","M","L","K","J","I","H","G","F","E","D","C","B","A"));	
	}


}

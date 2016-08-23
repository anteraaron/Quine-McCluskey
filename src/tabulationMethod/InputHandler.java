/**
 * Handles the input of the user. Checks whether the input contains special characters.
 */
package tabulationMethod;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class InputHandler {
	
	private String input;
	private int element;
	private StringTokenizer tokenizer;
	private boolean canProceed = true;
	private boolean hasNonInt = false;
	private boolean hasRepeat = false;
	private boolean hasNegative = false;
	private char check;
	private ArrayList<Integer> tokens;
	public InputHandler(String input){
	this.input = input;
	
	removeSpace();//remove spaces from the input
	
	this.tokenizer = new StringTokenizer(this.input, ",");
	//checks for error in parsing of the input
	if(!checkIfEmpty(this.input)){
		if(!checkForNonInt(this.input)){
			if(!checkForRepeat(this.input)){
				if(!checkForNegatives(this.tokens)){
					setCanProceed(true);
				}
				else{
					setCanProceed(false);
				}
			}
			else{
				setCanProceed(false);	
			}
		}
		else{
			setCanProceed(false);
		}
	}else{
		setCanProceed(false);
	}
	
	}
	/**
	 * Checks if the user did not input anything.
	 * @param input Input from user
	 * @return boolean true or false
	 */
	public boolean checkIfEmpty(String input){
		if(input.equals("")){
			return true;
		}else
			return false;
	}
	/**
	 * Checks if the users inputs alphanumeric keys
	 * @param input from the user
	 * @return boolean true or false
	 */
	public boolean checkForNonInt(String input){
		for(int i = 0; i < input.length(); i++){
			check = input.charAt(i);
			if(Character.isLetter(check)){
			hasNonInt = true;
			}
			
		}
		return hasNonInt;
	}
	/**
	 * Checks if the user inputed duplicate minterms.
	 * @param input from the user
	 * @return boolean true or false
	 */
	public boolean checkForRepeat(String input){
		
		tokens = new ArrayList<Integer>();
		
		while(tokenizer.hasMoreTokens()){
			try{
				element = Integer.parseInt(tokenizer.nextToken());
				tokens.add(element);
			}catch(Exception e){
				return true;
			}
		}
		
		for(int i = 0; i < tokens.size(); i++){
			for(int j = 0; j < tokens.size(); j++){
				if(i != j){
					if(tokens.get(i) == tokens.get(j)){
						hasRepeat = true;
					}
				}
			}
		}
		
		return hasRepeat;
	}	
	/**
	 * Checks for negative minterms or if the minterm exceeds the maximum number of possible minterms
	 * @param tokns ArrayList of minterms
	 * @return boolean true or false
	 */
	public boolean checkForNegatives(ArrayList<Integer> tokns){
		for(int i = 0; i < tokns.size(); i++){
			if(tokns.get(i) < 0 || tokns.get(i) > 67108863){
				hasNegative = true;
			}
		}
		return hasNegative;
	}
	/**
	 * Removes spaces from the user's input
	 */
	public void removeSpace(){	
	this.input = this.input.replaceAll(" ", "");
	}
	/**
	 * Determines whether the user inputed a correct syntax
	 * @return boolean canProceed
	 */
	public boolean isCanProceed() {
		return canProceed;
	}
	/**
	 * If the user's input can be processed
	 * @param canProceed
	 */
	public void setCanProceed(boolean canProceed) {
		this.canProceed = canProceed;
	}
	/**
	 * Getter for the arraylist containing the correct minterms
	 * @return tokens
	 */
	public ArrayList<Integer> beginAlgo(){
		return tokens;
	}

}

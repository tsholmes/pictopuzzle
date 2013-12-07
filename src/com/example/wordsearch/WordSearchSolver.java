<<<<<<< HEAD
package com.example.wordsearch;

import java.util.HashSet;
import java.util.Vector;

public class WordSearchSolver extends PuzzleSolver<WordSearchResult> {
	
	private HashSet<String> found;
	private HashSet<String> dictionary;
	private Vector<WordSearchResult> results;
	private char[][] data;
    
    public WordSearchSolver(char[][] d, HashSet<String> dict){
    	found = new HashSet<String>();
    	results = new Vector<WordSearchResult>();
    	data = d;
    	dictionary = dict;
    }
    
    private void checkString(String word, Direction dir, int row, int col){
    	
    	WordSearchResult result = new WordSearchResult(word,dir,row,col);
    	
    	if(dictionary.contains(word)){
    		found.add(word);
    		results.add(result);
    	}
    }
    
    public void solve(){
    	
    	String check = "";
    	
    	for(int i = 0; i < data.length; i++){
    		for(int j = 0; j < data[i].length; j++){
    			
    			// Check down
    			check = "";
    			for(int a = i;a < data.length; a++){
    				check += data[a][j];
    				checkString(check, Direction.DOWN, i, j);
    			}
    			
    			
    			// Check up
    			check = "";
    			for(int a = i;a >= 0; a--){
    				check += data[a][j];
    				checkString(check, Direction.UP, i, j);
    			}
    			
    			// Check right
    			check = "";
    			for(int a = j;a < data[i].length; a++){
    				check += data[i][a];
    				checkString(check, Direction.RIGHT, i, j);
    			}
    			
    			// Check left
    			check = "";
    			for(int a = j;a >= 0; a--){
    				check += data[i][a];
    				checkString(check, Direction.LEFT, i, j);
    			}
    			
    			// Check down-right
    			check = "";
    			for(int a = 0; a+i < data.length && a+j < data[i].length; a++){
    				check += data[i+a][j+a];
    				checkString(check, Direction.DOWN_RIGHT, i, j);
    			}
    			
    			// Check down-left
    			check = "";
    			for(int a = 0; a+i < data.length && j-a >= 0; a++){
    				check += data[i+a][j-a];
    				checkString(check, Direction.DOWN_LEFT, i, j);
    			}
    			
    			// Check up-right
    			check = "";
    			for(int a = 0; i-a >= 0 && a+j < data[i].length; a++){
    				check += data[i-a][j+a];
    				checkString(check, Direction.UP_RIGHT, i, j);
    			}
    			
    			// Check up-left
    			check = "";
    			for(int a = 0; i-a >= 0 && j-a >= 0; a++){
    				check += data[i-a][j-a];
    				checkString(check, Direction.UP_LEFT, i, j);
    			}
    		}
    	}
    }
    
    public Vector<WordSearchResult> getResult(){
    	return results;
    }
    
    public void print(){
    	
    	System.out.println("Words found:");
    	System.out.println("------------");
    	
    	System.out.println(results);
    	
    	/*
    	for(WordSearchResult s: results){
    		System.out.println(s);
    	}
    	*/
    	
    	System.out.println("------------");
    }
	
	
	public static void main(String[] argv){
		
		char[][] testdata = {{'h', 'l', 'l', 'e', 'h'},
							 {'h', 'e', 'l', 'l', 'o'},
							 {'a', 'b', 'l', 'l', 'e'},
							 {'a', 'b', 'r', 'l', 'e'},
							 {'a', 'o', 'c', 'd', 'o'},
							 {'w', 'o', 'r', 'l', 'd'}};
		
		HashSet<String> testdict = new HashSet<String>();
		
		testdict.add("hello");
		testdict.add("world");
		
		WordSearchSolver word = new WordSearchSolver(testdata, testdict);
		word.solve();
		word.print();
		
		System.out.println("Done");
		
	}

}
=======
package com.example.wordsearch;

import java.util.HashSet;
import java.util.Vector;

public class WordSearchSolver extends PuzzleSolver<WordSearchResult> {
	
	private HashSet<String> found;
	private HashSet<String> dictionary;
	private Vector<WordSearchResult> results;
	private char[][] data;
    
    public WordSearchSolver(char[][] d, HashSet<String> dict){
    	
    	found = new HashSet<String>();
    	results = new Vector<WordSearchResult>();
    	data = d;
    	dictionary = dict;
    }
    
    private void checkString(String word, Direction dir, int row, int col){
    	
    	WordSearchResult result = new WordSearchResult(word,dir,row,col);
    	
    	if(dictionary.contains(word)){
    		found.add(word);
    		results.add(result);
    	}
    }
    
    public void solve(){
    	
    	String check = "";
    	
    	for(int i = 0; i < data.length; i++){
    		for(int j = 0; j < data[i].length; j++){
    			
    			// Check down
    			check = "";
    			for(int a = i;a < data.length; a++){
    				check += data[a][j];
    				checkString(check, Direction.DOWN, i, j);
    			}
    			
    			
    			// Check up
    			check = "";
    			for(int a = i;a >= 0; a--){
    				check += data[a][j];
    				checkString(check, Direction.UP, i, j);
    			}
    			
    			// Check right
    			check = "";
    			for(int a = j;a < data[i].length; a++){
    				check += data[i][a];
    				checkString(check, Direction.RIGHT, i, j);
    			}
    			
    			// Check left
    			check = "";
    			for(int a = j;a >= 0; a--){
    				check += data[i][a];
    				checkString(check, Direction.LEFT, i, j);
    			}
    			
    			// Check down-right
    			check = "";
    			for(int a = 0; a+i < data.length && a+j < data[i].length; a++){
    				check += data[i+a][j+a];
    				checkString(check, Direction.DOWN_RIGHT, i, j);
    			}
    			
    			// Check down-left
    			check = "";
    			for(int a = 0; a+i < data.length && j-a >= 0; a++){
    				check += data[i+a][j-a];
    				checkString(check, Direction.DOWN_LEFT, i, j);
    			}
    			
    			// Check up-right
    			check = "";
    			for(int a = 0; i-a >= 0 && a+j < data[i].length; a++){
    				check += data[i-a][j+a];
    				checkString(check, Direction.UP_RIGHT, i, j);
    			}
    			
    			// Check up-left
    			check = "";
    			for(int a = 0; i-a >= 0 && j-a >= 0; a++){
    				check += data[i-a][j-a];
    				checkString(check, Direction.UP_LEFT, i, j);
    			}
    		}
    	}
    }
    
    public Vector<WordSearchResult> getResult(){
    	return results;
    }
    
    public void print(){
    	
    	System.out.println("Words found:");
    	System.out.println("------------");
    	
    	System.out.println(results);
    	
    	/*
    	for(WordSearchResult s: results){
    		System.out.println(s);
    	}
    	*/
    	
    	System.out.println("------------");
    }
	
	
	public static void main(String[] argv){
		
		char[][] testdata = {{'h', 'l', 'l', 'e', 'h'},
							 {'h', 'e', 'l', 'l', 'o'},
							 {'a', 'b', 'l', 'l', 'e'},
							 {'a', 'b', 'r', 'l', 'e'},
							 {'a', 'o', 'c', 'd', 'o'},
							 {'w', 'o', 'r', 'l', 'd'}};
		
		HashSet<String> testdict = new HashSet<String>();
		
		testdict.add("hello");
		testdict.add("world");
		
		WordSearchSolver word = new WordSearchSolver(testdata, testdict);
		word.solve();
		word.print();
		
		System.out.println("Done");
		
	}

}
>>>>>>> f6a928c2b7da7141eaa55b2868516124abaa3e8d

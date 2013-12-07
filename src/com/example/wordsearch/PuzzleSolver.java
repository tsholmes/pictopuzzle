package com.example.wordsearch;

import java.util.Vector;


public abstract class PuzzleSolver<T> {
	
	abstract void solve();
	
	abstract Vector<T> getResult();

}

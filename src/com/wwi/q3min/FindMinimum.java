package com.wwi.q3min;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class FindMinimum {

	public static void main(String[] args) {

		//Random class is used to generate random number
		Random rand = new Random();

		//Arraylist is used to store 500 random numbers
		ArrayList<Integer> lst = new ArrayList<Integer>();
		for (int i = 0; i < 500; i++) {
			lst.add(rand.nextInt());
		}

		//Print the arraylist
		System.out.println("Random numbers generated :");
		System.out.println(lst);

		//Print the size of array list
		System.out.println("Number of Random Numbers generated :" + lst.size());

		//Sort the numbers in the arraylist in ascending order
		Collections.sort(lst);
		System.out.println("After sort :");

		//Print the sorted arraylist
		System.out.println(lst);

		//Retrieve the first value of arraylist to get minimum value
		System.out.println("Minimum of the random generated numbers :" + lst.get(0));
	}
}


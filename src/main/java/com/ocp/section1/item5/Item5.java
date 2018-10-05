package com.ocp.section1.item5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 *  Create and use singleton classes and immutable classes.
 * 
 * @author seberthe
 *
 */
public class Item5 {
	
	public static void main(String... args){
		// My singleton test
		MySingleSample1 instance1 = MySingleSample1.getInstance();
		MySingleSample1 instance2 = MySingleSample1.getInstance();
		System.out.println("instance1 & instance2 are equals? " + (instance1 == instance2));
		
		// Immutable Object Test
		List<String> acc = new ArrayList<>(Arrays.asList("a", "b", "c"));
		ImmutableTime mTime = new ImmutableTime(12, 0, acc);

		System.out.println("Initial Acc. " + acc);
		
		System.out.println("Acc size equals? " + (acc.size() == mTime.accumulatorSize()));
		
		acc.add("d");
		
		System.out.println("Modified Acc. " + acc);
		
		System.out.println("Acc size equals? " + (acc.size() == mTime.accumulatorSize()));
	}
}

/**
 *	Singleton Implementation 
 */
class MySingleSample1 {
	private static MySingleSample1 instance;
	
	private MySingleSample1(){}
	
	public static MySingleSample1 getInstance(){
		if(instance == null){
			synchronized (MySingleSample1.class) {
				if(instance == null){
					instance = new MySingleSample1();
				}
			}
		}
		return instance;
	}
}

class MySingleSample2 {
	private static MySingleSample2 instance = new MySingleSample2();
	
	private MySingleSample2(){}
	
	public static MySingleSample2 getInstance(){
		return instance;
	}
}

/**
 * 	Immutable class implementation
 */
class ImmutableTime{
	
	private final int hour;
	private final int minute;
	private final List<String> accumulator;
	
	public ImmutableTime(int h, int m, List<String> a){
		this.hour = h;
		this.minute = m;
		this.accumulator = new ArrayList<>(a);
	}
	public int getHour(){
		return hour;
	}
	public int getMinute(){
		return minute;
	}
	public List<String> getAccumulator(){
		return new ArrayList<>(accumulator);
	}
	
	public int accumulatorSize(){
		return this.accumulator.size();
	}
}

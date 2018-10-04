package com.ocp.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

public class TestGenerics {

	@Test
	public void testRawType(){
		Function<? extends Number, ? extends Number> additioner = (a) -> a.intValue() * 2;
		Function rawFunc = ref -> ref;
		
		List<String> mRawInGeneric = new ArrayList(); mRawInGeneric.add("String");
		List mGenericInRaw = new ArrayList<String>(); mGenericInRaw.add(2);
		
		// Assertion on Raw Inside Generic
		Assert.assertTrue(mRawInGeneric instanceof Object);
		Assert.assertTrue(mRawInGeneric instanceof List);
		Assert.assertTrue(mRawInGeneric instanceof ArrayList);
		Assert.assertTrue(mRawInGeneric.get(0) instanceof String);
		Assert.assertTrue(mRawInGeneric.get(0) instanceof Object);
		Assert.assertFalse(null instanceof Object);
		
		// Assertion on Generic Inside Raw
		Assert.assertTrue(mGenericInRaw instanceof List); // True
		Assert.assertTrue(mGenericInRaw instanceof ArrayList); 
		Assert.assertFalse(mGenericInRaw.get(0) instanceof String);
		Assert.assertTrue(mGenericInRaw.get(0) instanceof Number);
		Assert.assertTrue(mGenericInRaw.get(0) instanceof Integer);
		Assert.assertTrue(mGenericInRaw.get(0) instanceof Object);
		
		
		// List<Number> mNumbers = new ArrayList<Integer>(); // C.E
	}
	
	@Test(expected=ClassCastException.class)
	public void testGenericNonGenericCommunication(){
		// Generic Area
		List<String> mGenericStr = new ArrayList<>();
		mGenericStr.add("Seydou");
		mGenericStr.add("Salif");
		// mGenericStr.add(1); C.E: cause we're in generic area
		
		// Welcome String List to Generic Area
		welcomeToNonGenericArea(mGenericStr);
		
		System.out.println(mGenericStr);
		
		Assert.assertTrue(mGenericStr.size() == 5);
		
		for(String str: mGenericStr){ // A ClassCastException will be thrown cause mGenericStr has been in non 
			System.out.println(str);  // and have been added some numbers and boolean which can't be casted to String
		}
	}
	
	private void welcomeToNonGenericArea(List nonGenericList){
		nonGenericList.add(1);
		nonGenericList.add(1.4);
		nonGenericList.add(true);
	}
}

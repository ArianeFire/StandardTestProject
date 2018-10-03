package com.ocp.test;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBuncleDemo1 {

	public static void main(String... args){
		
		// Test Simple
		Locale fr = Locale.FRENCH;
		ResourceBundle bundle = ResourceBundle.getBundle("Labels", fr);
		System.out.println(bundle.getString("country"));
		
		
	}
	
}

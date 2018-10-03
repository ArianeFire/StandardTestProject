package com.ocp.test;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Test;

public class TestResourceBuncle {

	@Test
	public void testGettingBundlePropertyFile(){
		Locale fr = Locale.FRENCH;
		ResourceBundle bundle = ResourceBundle.getBundle("Labels", fr); // Check java folder to see Labels_fr.property file
		Assert.assertEquals("Pays", bundle.getString("country"));
	}
	
	@Test(expected = MissingResourceException.class)
	public void testGettingMissingResourceBundle(){
		ResourceBundle bundle = ResourceBundle.getBundle("Label"); // No Label...properties exist
		Assert.assertEquals("Pays", bundle.getString("country"));
	}
	
	@Test
	public void testDefaultResourceBundle(){
		ResourceBundle bundle = ResourceBundle.getBundle("Labels"); // The Default Resource Bundle will be loaded (In My case "fr")
		Assert.assertEquals("Pays", bundle.getString("country"));
	}
	
}

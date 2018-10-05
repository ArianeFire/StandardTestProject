package com.ocp.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class StringPatternMatcherAPITest {

	@Test
	@Ignore
	public void testCompareStringPortionMethods(){
		
		String mSentence = "I am going to school";
		
		Assert.assertTrue(mSentence.startsWith("I"));
		Assert.assertTrue(mSentence.startsWith("am", 2));
		
		Assert.assertTrue(mSentence.endsWith("l"));
		
		Assert.assertTrue(mSentence.substring(0,  1).equalsIgnoreCase("i"));
		
		Assert.assertTrue(mSentence.matches("I.+"));
		
		String findMe = "going";
		int findMeStartIndex = 5;
		boolean matches = mSentence.regionMatches(findMeStartIndex, findMe, 0, findMe.length());
		Assert.assertTrue(matches);
	}
	
	@Test
	@Ignore
	public void testPatternWithMatcherClass(){
		
		Pattern mPattern = Pattern.compile("ab");  
		Matcher mMatcher = mPattern.matcher("ababbaba");
		// We're looking for matches of "ab", in "ababbaba"
		
		int numberOfMatches = 0;
		while(mMatcher.find()){
			numberOfMatches++;
			
			// Note that the End Index it Count based (start at 1....)
			System.out.println(String.format("Start at %s, End at %s, Group matching %s", mMatcher.start(), mMatcher.end(), mMatcher.group()));
		}
		
		Assert.assertTrue(numberOfMatches == 3);
	}
	
	@Test
	@Ignore
	public void testCommonRegExp(){
		
		String mTarget = "a7b k@9";
		
		// Pattern for only space char. 
		// (Notice that we've escaped \ with a second one, without it the code will not compile)
		Pattern mPattern = Pattern.compile("\\s"); 
		Matcher mMatcher = mPattern.matcher(mTarget);
		int spaceCount = 0;
		while(mMatcher.find()){
			spaceCount++;
		}
		
		Assert.assertTrue(spaceCount == 1);
		
		/**
		 * Other Predefined Patterns: (Another \ shoud be used to escape those characters)
		 * 
		 * "\S" => Except Space  = [^ ]
		 * "\d" => Only Digits 	 = [0-9]
		 * "\D" => Except Digit  = [^0-9]
		 * "\w" => Only words	 = [a-bA-Z0-9]
		 * "\W" => Except Word   = [^a-bA-Z0-9]
		 */
	}
	
	@Test
	@Ignore
	public void testPatternSplitMethod(){
		String mTarget = "I am ggoing to school";
		Pattern mPattern = Pattern.compile("\\s"); // (Reg Exp to match Space Char.)
		
		String[] mSplitResult = mPattern.split(mTarget); // Split mTarget using space characters.
		Assert.assertTrue(mSplitResult.length == 5);
		
		mPattern = Pattern.compile("g"); 
		mSplitResult = mPattern.split(mTarget); // split using 'g' as separator
		Assert.assertTrue(mSplitResult.length == 4);
	}
	
	@Test
	public void testPatternQuote(){
		String mTarget = "www.seydouSoftLab.ml";
		
		Pattern mPattern = Pattern.compile(".");
		String[] mSplitResult = mPattern.split(mTarget); // This will return empty Array
														 // Cause of '.' matches every character
		Assert.assertTrue(mSplitResult.length == 0);	 // We have to escape '.' character
		
		// Using Escape Version
		mPattern = Pattern.compile("\\."); // Escaped '.' character pattern (Another Shortcut => '[.]' => . is treated as symbol)
		mSplitResult = mPattern.split(mTarget);
		Assert.assertTrue(mSplitResult.length == 3);
		
		// Using Pattern.quote() to escape (The Bast way)
		mPattern = Pattern.compile(Pattern.quote("."));
		mSplitResult = mPattern.split(mTarget);
		Assert.assertTrue(mSplitResult.length == 3);
 	}
}

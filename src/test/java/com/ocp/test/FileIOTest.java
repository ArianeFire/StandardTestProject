package com.ocp.test;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileIOTest {

	@BeforeClass
	public static void before(){
		// Delete Files They are create by previously running tests
		new File("filename.txt").delete();
		new File("tmpFile.txt").delete();
		new File("myDirectory").delete();
		new File("cwd_file.text").delete();
	}
	
	/**
	 * File file = new File(); => Doesn't create a physical file, only java File object
	 */
	@Test
	public void testConstructorDidNotCreateFile(){
		File file = new File("filename.txt");
		Assert.assertFalse(file.exists());
	}
	
	@Test
	public void testCreateFile() throws IOException{
		File file = new File("tmpFile.txt");
		
		Assert.assertFalse(file.exists());
		
		//Actually create the file if it doesn't exist
		file.createNewFile();
		
		Assert.assertTrue(file.exists());
		Assert.assertTrue(file.isFile());
	}
	
	@Test
	public void testCreateDirectory(){
		File file = new File("myDirectory");
		Assert.assertFalse(file.exists());
		
		file.mkdir();
		
		Assert.assertTrue(file.exists());
		Assert.assertTrue(file.isDirectory());
	}
	
	@Test
	public void testCreateFileConstrcutors() throws IOException{
		// Constructor creating file in the C.W.D
		File file = new File("cwd_file.text");
		Assert.assertFalse(file.exists());
		file.createNewFile();
		Assert.assertTrue(file.exists());
	}
}

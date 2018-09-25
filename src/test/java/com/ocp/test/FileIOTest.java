package com.ocp.test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
		new File("cwd_sub_dir", "myFileInCWD.txt").delete();
		new File("cwd_sub_dir", "myFileCreatedUsingDirReference.txt").delete();
		new File("cwd_sub_dir").delete();
		new File("important_directory", "ImportantFile.txt").delete();
		new File("important_directory").delete();
		new File("writeable.txt").delete();
		new File("readable.txt").delete();
	}
	
	/**
	 * File file = new File("text.txt"); => Doesn't create a physical file, only java File object
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
		
		// Constructor Creating file in C.W.D Subdirectory
		File directory = new File("cwd_sub_dir");
		Assert.assertFalse(directory.exists());
		directory.mkdir();
		
		File myNewfileToBeInCWD = new File("cwd_sub_dir", "myFileInCWD.txt");
		Assert.assertFalse(myNewfileToBeInCWD.exists());
		myNewfileToBeInCWD.createNewFile();
		
		// Constructor Creating file inside another Direcory Reference
		File myFileCreatedUsingDirReference = new File(directory, "myFileCreatedUsingDirReference.txt");
		Assert.assertFalse(myFileCreatedUsingDirReference.exists());
		
		myFileCreatedUsingDirReference.createNewFile();
		
		Assert.assertTrue(myFileCreatedUsingDirReference.exists());
	}
	
	@Test
	public void testFileImportantMethods() throws IOException{
		File directory = new File("important_directory");
		Assert.assertFalse(directory.exists());
		directory.mkdir();
		Assert.assertTrue(directory.exists());
		Assert.assertTrue(directory.isDirectory());
		
		File file = new File(directory, "ImportantFile.txt");
		Assert.assertFalse(file.exists());
		file.createNewFile();
		Assert.assertTrue(file.exists());
		Assert.assertTrue(file.isFile());
		
		Assert.assertTrue(directory.list().length == 1);
	}
	
	@Test
	public void testFileWriterConstructors() throws IOException{
		FileWriter mWriter = new FileWriter("writeable.txt");
		mWriter.write("Hello Writer World\n");
		mWriter.close();
		
		File writableFile = new File("writeable.txt");
		Assert.assertTrue(writableFile.exists()); // Assert the file already exist, cause was created by the code above
		
		// "true" in second parameter is used to specify hat we want to append to the file not override
		FileWriter mAppenderWriter = new FileWriter(writableFile, true); 
		mAppenderWriter.append("Hello Again Writer World");
		mAppenderWriter.write('\n');
		mAppenderWriter.write('m');
		mAppenderWriter.write('\n');
		mAppenderWriter.write(new char[]{'S', 'E', 'Y', 'D', 'O', 'U'});
		mAppenderWriter.close();
		
		boolean containsText = Files.readAllLines(Paths.get("writeable.txt")).contains("Hello Again Writer World");
		Assert.assertTrue(containsText);
	}
	
	@Test
	public void testFileReaderConstructors() throws IOException{
		createReadeableFile();
		FileReader mReader = new FileReader("readable.txt");
		int c = 0;
		StringBuilder mContent = new StringBuilder();
		while((c = mReader.read()) != -1)
			mContent.append((char)c);
		mReader.close();
		Assert.assertTrue(mContent.toString().contains("Hello Again Writer World"));
	}
	
	private static void createReadeableFile() throws IOException{
		FileWriter mWriter = new FileWriter("readable.txt");
		mWriter.write("Hello Writer World\n");
		mWriter.close();
		
		File writableFile = new File("readable.txt");
		
		FileWriter mAppenderWriter = new FileWriter(writableFile, true); 
		mAppenderWriter.append("Hello Again Writer World");
		mAppenderWriter.write('\n');
		mAppenderWriter.write('m');
		mAppenderWriter.write('\n');
		mAppenderWriter.write(new char[]{'S', 'E', 'Y', 'D', 'O', 'U'});
		mAppenderWriter.close();
	}
}

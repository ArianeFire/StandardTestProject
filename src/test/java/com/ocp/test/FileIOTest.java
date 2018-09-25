package com.ocp.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
		new File("buffereableWriter.txt").delete();
		new File("buffered-readable.txt").delete();
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
		String fileName = createReadeableFile("readable.txt");
		FileReader mReader = new FileReader(fileName);
		int c = 0;
		StringBuilder mContent = new StringBuilder();
		while((c = mReader.read()) != -1)
			mContent.append((char)c);
		mReader.close();
		Assert.assertTrue(mContent.toString().contains("Hello Again Writer World"));
	}
	
	@Test
	public void testBufferedWriterConstructors() throws IOException{
		// Difference with FileWriter is that it add writing newLine() method
		//& also cannot communicate directly with the File but only through Writer
		//(In the opposite of FileWriter which communicate directly with the file)
		BufferedWriter mBWriter = new BufferedWriter(new FileWriter("buffereableWriter.txt"));
		mBWriter.write("Hello BW Writer World");
		mBWriter.newLine();
		mBWriter.write("Hello BW Writer World Again");
		mBWriter.close();
		
		boolean hasWritten = Files.readAllLines(Paths.get("buffereableWriter.txt")).contains("Hello BW Writer World Again");
		Assert.assertTrue(hasWritten);
	}
	
	@Test
	public void testBufferedReaderConstructors() throws IOException{
		String fileName = createReadeableFile("buffered-readable.txt");
		// The Difference with a FileReader is that it allow reading line by line 
		// Also as BufferedWriter, it doesn't communicate directly with the file, but through a Reader
		//(in the opposite of a FileReader which communicate directly with the file)
		BufferedReader mBReader = new BufferedReader(new FileReader(fileName));
		boolean contains = mBReader.lines().anyMatch("Hello Again Writer World"::equals);
		Assert.assertTrue(contains);
		mBReader.close();
		
		mBReader = new BufferedReader(new FileReader(fileName));
		StringBuilder mFileContent = new StringBuilder();
		String singleLine = "";
		while((singleLine = mBReader.readLine()) != null)
			mFileContent.append(singleLine);
		
		Assert.assertTrue(mFileContent.toString().contains("Hello Again Writer World"));
		mBReader.close();
	}
	
	private static String createReadeableFile(String fileName) throws IOException{
		FileWriter mWriter = new FileWriter(fileName);
		mWriter.write("Hello Writer World\n");
		mWriter.close();
		
		File writableFile = new File(fileName);
		
		FileWriter mAppenderWriter = new FileWriter(writableFile, true); 
		mAppenderWriter.append("Hello Again Writer World");
		mAppenderWriter.write('\n');
		mAppenderWriter.write('m');
		mAppenderWriter.write('\n');
		mAppenderWriter.write(new char[]{'S', 'E', 'Y', 'D', 'O', 'U'});
		mAppenderWriter.close();
		
		return fileName;
	}
}

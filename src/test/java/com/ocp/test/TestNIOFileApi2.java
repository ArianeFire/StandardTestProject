package com.ocp.test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class TestNIOFileApi2 {
	
	@Test
	@Ignore
	public void testPathCreation(){
		
		Path mPath = Paths.get("NomenclatureImport.log");
		Path mFileNameAsPath = mPath.getFileName();
		Assert.assertTrue("NomenclatureImport.log".equals(mFileNameAsPath.toString()));
		
		Path mPath2 = FileSystems.getDefault().getPath("NomenclatureImport.log");
		Assert.assertTrue(mPath2.toString().equals("NomenclatureImport.log"));
		
		Path mHomeDirectory = Paths.get(System.getProperty("user.home"));
		System.out.println(mHomeDirectory);
		Assert.assertTrue(mHomeDirectory.isAbsolute()); // Home directory is an absolute path
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Ignore
	public void testPathOperations(){
		Path mHomeDir = Paths.get(System.getProperty("user.home"));
		
		// The Following Assertion are based on my work computer where (user.home=C:/Users/seberthe)
		Assert.assertTrue("seberthe".equals(mHomeDir.getFileName().toString())); // Return the Last element in the file hierarchie
		Assert.assertTrue("C:\\Users".equals(mHomeDir.getParent().toString()));
		Assert.assertTrue("C:\\".equals(mHomeDir.getRoot().toString()));
		Assert.assertTrue(mHomeDir.getNameCount() == 2); // Root of the Path is not considered as Name
		Assert.assertTrue(mHomeDir.getName(0).toString().equals("Users"));
		Assert.assertTrue(mHomeDir.subpath(0, 2).toString().contains("seberthe"));
		
		// Removing Duplicate in a Path (Normalize) & Joining to Paths && Create Path between to Path
		Path mNonNormalizedPath = mHomeDir.resolve(Paths.get("..")); // => C:\Users\seberthe\..
		Path mNormalizedPath = mNonNormalizedPath.normalize();		 // => C:\Users\
		Assert.assertTrue(mNormalizedPath.getFileName().toString().equals("Users"));
		
		// Create Path Between 2 Path
		Path mPathBetweenHomeAndItParent = mHomeDir.relativize(mNormalizedPath);
		Assert.assertTrue("..".equals(mPathBetweenHomeAndItParent.toString()));
		
		// Those two files locate the same file but they are not equals
		// As Paths class is syntactic, it doesn't operate on the file 
		// To check whether two paths point to the same file,
		// You will need to use Files.isSameFile(mNonNormalizedPath, mNormalizedPath));
		Assert.assertFalse(mNonNormalizedPath.equals(mNormalizedPath));
		
		// This will throw IllegalArgumentException cause there are only 2 names (Root is not counted as a name)
		Assert.assertTrue(mHomeDir.getName(2).toString().equals("Users")); 
	}
	
	@Test
	@Ignore
	public void testFilesCheckFileDirectory(){
		Path mNomencLogPath = Paths.get("NomenclatureImport.log");
		
		Assert.assertTrue(Files.exists(mNomencLogPath));
		Assert.assertFalse(Files.notExists(mNomencLogPath));
		
		Assert.assertTrue(Files.isRegularFile(mNomencLogPath));
		Assert.assertFalse(Files.isDirectory(mNomencLogPath));
		
		Assert.assertTrue(Files.isReadable(mNomencLogPath));
		Assert.assertTrue(Files.isWritable(mNomencLogPath));
		Assert.assertTrue(Files.isExecutable(mNomencLogPath));
	}
	
	@Test
	@Ignore
	public void testDeleteFileOrDir(){
		Path mTemporyFile = null;
		try {
			mTemporyFile = Files.createFile(Paths.get("file-to-delete.txt"));
		} catch (IOException e) {	
			// UnsupportedOperationException, FileAlreadyExistsException 
			Assert.assertFalse("Can't Create a file", false);
		}
		
		try {
			Files.delete(mTemporyFile);
		} catch (IOException e) { 		
			// NoSuchFileException, DirectoryNotEmptyException, IOException, SecurityException
			Assert.assertFalse("Can't delete the requested File", false);
		}
		
		try {
			Files.deleteIfExists(mTemporyFile);
		} catch (IOException e) {
			// DirectoryNotEmptyException, IOException, SecurityException
			Assert.assertFalse("Can't delete the requested File", false);
		}
	}
	
	@Test
	@Ignore
	public void testCopyFileOrDirectiory() throws IOException{
		Path mNomencLog = Paths.get("NomenclatureImport.log");
		Path outNomencLog = Paths.get("import");
		
		try { // Notice that the default behavior of this method is to copy the target of a symbolic link
			 	// That behavior can be customized by providing a CopyOption
			Files.copy(mNomencLog, outNomencLog.resolve(mNomencLog.getFileName()), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// UnsupportedOperationException, FileAlreadyExistsException, DirectoryNotEmptyException, SecurityException
			Assert.assertFalse("Can't Copy the specified file", false);
		}
		
		Assert.assertTrue(Files.exists(outNomencLog.resolve(mNomencLog.getFileName())));
	}
	
	@Test
	@Ignore
	public void testMoveFileOrDirectory() throws IOException{
		Path mFileToBeMoved = Files.createFile(Paths.get("FileToBeMoved.txt"));
		Path mDestionation = Paths.get("import").resolve(mFileToBeMoved.getFileName());
		
		// Notice that there is Declaration of IOException for this method
		Files.move(mFileToBeMoved, mDestionation, StandardCopyOption.REPLACE_EXISTING);
		
		Assert.assertTrue(Files.exists(mDestionation));
	}
	
	@Test/*(expected = UnsupportedOperationException.class)*/
	public void testReadFileOrDirectoryMetadata() throws IOException{
		
		Path mNomencLog = Paths.get("NomenclatureImport.log");
		
		long fileSize = Files.size(mNomencLog);	// Read File Size Metadata
		Assert.assertTrue(fileSize > 0); 
		
		FileTime mFileTime = Files.getLastModifiedTime(mNomencLog); // Read the last modified time of that file
		System.out.println(mFileTime);
		Assert.assertTrue(mFileTime.compareTo(FileTime.from(Instant.now())) < 0);
		
		Assert.assertFalse(Files.isHidden(mNomencLog));
		
		String fileOwner = Files.getOwner(mNomencLog).getName();
		System.out.println(fileOwner);
		Assert.assertNotNull(fileOwner);
		
		// The above method read a single Attributes, 
		// which can lead to performance issue when you want to read multiple Attributes
		// This can be solve using Files.readAttributes(...) methods
		Map<String, Object> attributes = Files.readAttributes(mNomencLog, "size,lastModifiedTime,lastAccessTime");
		Assert.assertTrue(attributes.size() == 3);
		Assert.assertTrue(((Long)attributes.getOrDefault("size", 0)).compareTo(0L) > 0);
		
		// Or more cooler form
		BasicFileAttributes basicAttrs = Files.readAttributes(mNomencLog, BasicFileAttributes.class);
		DosFileAttributes dosAttrs = Files.readAttributes(mNomencLog, DosFileAttributes.class);
		
		Assert.assertTrue(basicAttrs.size() == dosAttrs.size());
		
		// This may throw UnsupportedOperationException as not every OS support (Not Window)
		//PosixFileAttributes posixAttrs = Files.readAttributes(mNomencLog, PosixFileAttributes.class);
	}
}

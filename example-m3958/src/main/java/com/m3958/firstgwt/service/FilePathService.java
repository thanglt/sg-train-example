package com.m3958.firstgwt.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.dao.WebSiteDao;
import com.m3958.firstgwt.exception.BadZipEntryNameException;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.utils.StringUtils;


@Singleton
public class FilePathService {
	
	@Inject
	private SiteConfigService scs;
	
	public File getSiteDir(String siteId){
		File siteDir = new File(scs.getSiteRoot(), siteId);
		return siteDir;
	}
	
	public File getFile(String siteId,String dirPath){
		File siteDir = getSiteDir(siteId);
		File tf = new File(siteDir,dirPath);
		try {
			if(tf.getCanonicalPath().startsWith(siteDir.getCanonicalPath())){
				return tf;
			}else{
				return null;
			}
		} catch (IOException e) {}
		return null;
	}
	
	public boolean isSafe(File siteDir,File fileDir){
		try {
			if(fileDir.getCanonicalPath().startsWith(siteDir.getCanonicalPath())){
				return true;
			}else{
				return false;
			}
		} catch (IOException e) {}
		return false;
	}
	
	private String webinfDir;
	
	public String getWebinfDir(){
		if(webinfDir == null){
			File f = new File(this.getClass().getResource("/").getFile());
			File warf = f.getParentFile();
			webinfDir = warf.getAbsolutePath(); 
		}
		return webinfDir;
	}
	
	  
	@SuppressWarnings("unchecked")
	public boolean unzipFile(String siteId,File toUnZipFile) throws IOException,BadZipEntryNameException{
	    Enumeration entries;
	    ZipFile zipFile;
	    zipFile = new ZipFile(toUnZipFile);
	    entries = zipFile.entries();
	    
	    File destDir = toUnZipFile.getParentFile();
	    File siteDir = getSiteDir(siteId);
	    /*
	     * 第一个条目必须是目录，比如abc，第二个条目的路径必须是abc/xxx，这样才是合法的。
	     */
	    String firstEntryName = null;
	    int entryNum = 0;
	    
	    while(entries.hasMoreElements()){
	        ZipEntry entry = (ZipEntry)entries.nextElement();
	        if(entryNum == 1){
	        	if(!entry.getName().startsWith(firstEntryName)){
	        		throw new BadZipEntryNameException("zip文件里面的文件名不要包含中文和其他非法字符！可以用：字母，下划线_，单划线-，点.");
	        	}
	        }
	        if(entry.isDirectory()) {
	        	  if(entryNum == 0){
	        		  firstEntryName = entry.getName();
	        	  }
		          File df = new File(destDir,entry.getName());
		          if(!isSafe(siteDir, df)){
		        	  throw new BadZipEntryNameException("zip文件包含不安全的內容！");
		          }
		          if(!entry.getName().matches("(\\w|\\.|[-/:])+")){
		        	  throw new BadZipEntryNameException("zip文件里面的文件名不要包含中文和其他非法字符！可以用：字母，下划线_，单划线-，点.");
		          }
		          df.mkdir();
	        }else{
		        if(entryNum == 0){
		        	throw new BadZipEntryNameException("zip文件里面的第一级必须只有一个目录");
		        }
		        File of = new File(destDir,entry.getName());
		        if(!isSafe(siteDir, of)){
					throw new BadZipEntryNameException("zip文件包含不安全的內容！");
				}
		        if(!entry.getName().matches("(\\w|\\.|[-/:])+")){
		        	throw new BadZipEntryNameException("zip文件里面的文件名不要包含中文和其他非法字符！可以用：字母，下划线_，单划线-，点.");
		        }
		        copyInputStream(zipFile.getInputStream(entry),
		           new BufferedOutputStream(new FileOutputStream(of)));
	        }
	        entryNum++;
	    }
	    zipFile.close();
	    return true;
	  }
	  
	  public void zip(File dir2zip,File dstzipfile) throws IOException{
		    ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dstzipfile));
		    zipDir(dir2zip.getAbsolutePath(),dir2zip.getAbsolutePath().length(),zos,dstzipfile);
		    zos.close();
	  }
		  
	  public void zipDir(String dir2zip, int baselength,ZipOutputStream zos,File dstzipfile){ 
	      try{ 
	          //create a new File object based on the directory we          have to zip 
	    	  File  zipDir = new File(dir2zip); 
	          //get a listing of the directory content 
	          String[] dirList = zipDir.list(); 
	          byte[] readBuffer = new byte[2156]; 
	          int bytesIn = 0; 
	          //loop through dirList, and zip the files 
	          for(int i=0; i<dirList.length; i++){ 
	              File f = new File(zipDir, dirList[i]); 
		          if(f.isDirectory()) 
		          { 
		                  //if the File object is a directory, call this 
		                  //function again to add its content recursively 
		              String filePath = f.getPath(); 
		              zipDir(filePath,baselength,zos,dstzipfile); 
		                  //loop again 
		              continue; 
		          } 
	              //if we reached here, the File object f was not  a directory 
	              //create a FileInputStream on top of f
		          if(!f.equals(dstzipfile)){
		              FileInputStream fis = new FileInputStream(f); 
		              //create a new zip entry 
			          ZipEntry anEntry = new ZipEntry(f.getPath().substring(baselength)); 
			              //place the zip entry in the ZipOutputStream object 
			          zos.putNextEntry(anEntry); 
		              //now write the content of the file to the ZipOutputStream 
		              while((bytesIn = fis.read(readBuffer)) != -1) 
		              { 
		                  zos.write(readBuffer, 0, bytesIn); 
		              } 
		             //close the Stream 
		             fis.close();
		         }
	          } 
		  }catch(Exception e){ 
		      //handle exception 
		  }
	  }
	  
	  
	  public boolean deleteDirectory(File path) {
		    if( path.exists() ) {
		      File[] files = path.listFiles();
		      for(int i=0; i<files.length; i++) {
		         if(files[i].isDirectory()) {
		           deleteDirectory(files[i]);
		         }
		         else {
		           files[i].delete();
		         }
		      }
		    }
		    return( path.delete() );
		  }
	  
		public void copyFile(String sourceFilePath, String destFilePath) {
			try {
				File sourceFile = new File(sourceFilePath);
				if (sourceFile.exists()) {
					FileInputStream fis = new FileInputStream(sourceFilePath);
					FileOutputStream fos = new FileOutputStream(destFilePath);
					byte[] b = new byte[1024];
					while (true) {
						int i = fis.read(b);
						if (i == -1)
							break;
						fos.write(b, 0, i);
					}
					fis.close();
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void copyFile(File sourceFile, File destFile) {
			try {
				if (sourceFile.exists()) {
					FileInputStream fis = new FileInputStream(sourceFile);
					FileOutputStream fos = new FileOutputStream(destFile);
					byte[] b = new byte[1024];
					while (true) {
						int i = fis.read(b);
						if (i == -1)
							break;
						fos.write(b, 0, i);
					}
					fis.close();
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void deleteFile(String path) {
			try {
				File file = new File(path);
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void deleteFile(File file) {
			try {
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		public boolean deletePictureFile(File f){
			String fileExpEnt = StringUtils.basenameWithoutDot(f.getAbsolutePath());
			String ent = StringUtils.getFileExtension(f);
			File f_sq = new File(fileExpEnt + "_sq" + ent);
			File f_s = new File(fileExpEnt + "_s" + ent);
			File f_m = new File(fileExpEnt + "_m" + ent);
			File f_l = new File(fileExpEnt + "_l" + ent);
			File f_t = new File(fileExpEnt + "_t" + ent);
			if(f.delete() && f_sq.delete() && f_s.delete() && f_m.delete() && f_l.delete() && f_t.delete()){
				return true;
			}
			return false;
		}
		
		
		  /**
		  * Fetch the entire contents of a text file, and return it in a String.
		  * This style of implementation does not throw Exceptions to the caller.
		  *
		  * @param aFile is a file which already exists and can be read.
		  */
		  public String getContents(File aFile,String charsetName) {
		    //...checks on aFile are elided
		    StringBuilder contents = new StringBuilder();
		    try {
		      //use buffering, reading one line at a time
		      //FileReader always assumes default encoding is OK!
		      BufferedReader input =  new BufferedReader(new InputStreamReader(new FileInputStream(aFile),charsetName));
		      try {
		        String line = null; //not declared within while loop
		        /*
		        * readLine is a bit quirky :
		        * it returns the content of a line MINUS the newline.
		        * it returns null only for the END of the stream.
		        * it returns an empty String if two newlines appear in a row.
		        */
		        while (( line = input.readLine()) != null){
		          contents.append(line);
		          contents.append(System.getProperty("line.separator"));
		        }
		      }
		      finally {
		        input.close();
		      }
		    }
		    catch (IOException ex){
		      ex.printStackTrace();
		    }
		    
		    return contents.toString();
		  }

		  /**
		  * Change the contents of text file in its entirety, overwriting any
		  * existing text.
		  *
		  * This style of implementation throws all exceptions to the caller.
		  *
		  * @param aFile is an existing file which can be written to.
		  * @throws IllegalArgumentException if param does not comply.
		  * @throws FileNotFoundException if the file does not exist.
		  * @throws IOException if problem encountered during write.
		  */
		  public void setContents(File aFile, String aContents,String charsetName)
		                                 throws FileNotFoundException, IOException {
		    if (aFile == null) {
		      throw new IllegalArgumentException("File should not be null.");
		    }
//		    if (!aFile.exists()) {
//		      throw new FileNotFoundException ("File does not exist: " + aFile);
//		    }
//		    if (!aFile.isFile()) {
//		      throw new IllegalArgumentException("Should not be a directory: " + aFile);
//		    }
//		    if (!aFile.canWrite()) {
//		      throw new IllegalArgumentException("File cannot be written: " + aFile);
//		    }

		    //use buffering
		    Writer output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(aFile),charsetName));
		    try {
		      //FileWriter always assumes default encoding is OK!
		      output.write( aContents );
		    }
		    finally {
		      output.close();
		    }
		  }

		  public static void deleteDir(File dir){
			  String[] ls = dir.list();
			  if(ls == null)return;
			  for (int i = 0; i < ls.length; i++) {
				File f = new File(dir,ls[i]);
				if(f.isDirectory()){
					deleteDir(f);
				}
				f.delete();
			  }
			  dir.delete();
		  }

		  public void copyInputStream(InputStream in, OutputStream out)  throws IOException
		  {
		    byte[] buffer = new byte[1024];
		    int len;

		    while((len = in.read(buffer)) >= 0)
		      out.write(buffer, 0, len);

		    in.close();
		    out.close();
		  }
		  
			private SimpleDateFormat sdf = new SimpleDateFormat("_yyyy_MM_dd_hh_mm_ss");
			
			public File getNextBakFile(File f) {
				Calendar c = Calendar.getInstance();
				String fn = StringUtils.getFileNameWithoutExt(f);
				String ext = StringUtils.getFileExtension(f);
				File nf = null;
				while(true){
					String newName;
					if(ext.isEmpty()){
						newName = fn + sdf.format(c.getTime());
					}else{
						newName = fn + sdf.format(c.getTime()) + "." + ext;
					}
					nf = new File(f.getParent(),newName);
					if(!nf.exists()){
						return nf;
					}
					c.add(Calendar.SECOND, 1);
				}
			}

}

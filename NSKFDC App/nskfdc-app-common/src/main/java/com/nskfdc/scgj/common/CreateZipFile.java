package com.nskfdc.scgj.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateZipFile {

	public  void ZipFile(ArrayList<String>files) {
		final Logger LOGGER = LoggerFactory.getLogger(CreateZipFile.class);
        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        FileInputStream fis = null;
		
	        try {
	        	LOGGER.debug("In try block of ZipFIle");
	            fos = new FileOutputStream("C:/Users/Bhawana Sharma/Desktop/ziptest.zip");
	            zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
	            for(String filePath:files){
	                File input = new File(filePath);
	                fis = new FileInputStream(input);
	                ZipEntry ze = new ZipEntry(input.getName());
	                LOGGER.debug("Zipping the file: "+input.getName());
	                zipOut.putNextEntry(ze);
	                byte[] tmp = new byte[4*1024];
	                int size = 0;
	                while((size = fis.read(tmp)) != -1){
	                    zipOut.write(tmp, 0, size);
	                }
	                zipOut.flush();
	                fis.close();
	              
	            }
	            zipOut.closeEntry();
	            zipOut.close();
	            LOGGER.debug("Done... Zipped the files...");
	        } catch (FileNotFoundException e) {
	        	LOGGER.error("File not found exception while zipping file: " + e);

	        } catch (IOException e) {
	            LOGGER.error("" +  e);
	            //e.printStackTrace();
	        } finally{
	                 try{
	                  if(fos != null) fos.close();
	                 } catch(Exception ex){
	                 
	              }
	          }
	        
}
}
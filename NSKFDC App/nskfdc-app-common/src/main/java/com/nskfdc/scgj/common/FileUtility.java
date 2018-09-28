package com.nskfdc.scgj.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileUtility {

	private static final Logger LOGGER= LoggerFactory.getLogger(FileUtility.class);

	@Autowired
	private ReadApplicationConstants readApplicationConstants;

	/**
	 * Method to create a zip file based on the list of file paths
	 * @param filesForZip
	 * @param batchId
	 * @return
	 */
	public String createZip(Collection<String> filesForZip, String batchId, String zipLocationRead)
	{
		int folderExists = 1;
		
		String zipFileLink = "";
		

		LOGGER.debug("Path to save zip file is " + zipLocationRead);
		if(filesForZip.size() > 0)
		{
			LOGGER.error("Array of files "+filesForZip +"to create zip is not null");

			File folder = new File(zipLocationRead);

			if(!folder.exists())
			{
				LOGGER.debug("Folder to save temp files does not exists. Trying to create folder to store temp zip file");
				folderExists = 0;
			}
			LOGGER.debug("Checking for the permissions to create a folder");

			if(folder.mkdirs() || folder.canWrite())
			{
				folderExists = 1;
			}
			if(folderExists == 1)
			{
				LOGGER.debug("Folder to save temp zip file exists");
				zipFileLink = zipLocationRead +"//" +batchId + ".zip";
				LOGGER.debug("Path of zip file" + zipFileLink);
				FileOutputStream fileOutputStream = null;
				ZipOutputStream zipOut = null;
				FileInputStream fileInputStream = null;
				try {
					LOGGER.debug("Trying to create zip file");
					fileOutputStream = new FileOutputStream(zipFileLink);
					zipOut = new ZipOutputStream(new BufferedOutputStream(fileOutputStream));
					for(String filePath:filesForZip)
					{
						File input = new File(filePath);
						fileInputStream = new FileInputStream(input);
						ZipEntry zipEntry = new ZipEntry(input.getName());
						LOGGER.debug("Zipping the file: "+input.getName());
						zipOut.putNextEntry(zipEntry);
						byte[] tmp = new byte[4*1024];
						int size = 0;
						while((size = fileInputStream.read(tmp)) != -1){
							zipOut.write(tmp, 0, size);
						}
						zipOut.flush();
						fileInputStream.close();
					}

					zipOut.close();
					LOGGER.debug("Zipped the files successfully");

				}

				catch (FileNotFoundException e)
				{

					LOGGER.error("File not found exception :" +e);
				} 
				catch (IOException e) {

					LOGGER.error("An Input output exception occured :" +e);
				} 
				finally
				{
					try{

						LOGGER.debug(" Closing the fileOutputStream");
						if(fileOutputStream != null) fileOutputStream.close();
					} 
					catch(Exception ex){

						LOGGER.error("Exception is :" +ex);
					}
				}
			}
			else
			{
				LOGGER.debug("File array"+filesForZip +" is empty : ");
			}
		}
		return zipFileLink;
	}

	/**
	 * Method to delete file based on a filePath given
	 * @param filePath
	 * @return status of file deleted operation
	 */
	public boolean deleteFile(String filePath)
	{
		boolean isDeleted = false;
		if(!(filePath.isEmpty()) && filePath != null)
		{

			File file = new File(filePath);
			if(file.exists())
			{
				LOGGER.debug("file to be deleted at path " + filePath + " exists");
				try
				{
					file.delete();
					isDeleted = true;
				}
				catch(Exception e)
				{
					LOGGER.error("An exception occured while deleting file " +filePath + " exception is :"+e);
				}

			}
			else
			{
				LOGGER.debug("file to be deleted at path " + filePath + " does not exist");
			}			
		}
		else
		{
			LOGGER.debug("Path for file to be deleted is empty");
			isDeleted = false;
		}
		return isDeleted;
	}
}





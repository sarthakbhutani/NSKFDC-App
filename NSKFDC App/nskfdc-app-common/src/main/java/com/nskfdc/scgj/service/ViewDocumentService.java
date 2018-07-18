package com.nskfdc.scgj.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nskfdc.scgj.dao.ViewDocumentDao;
import com.nskfdc.scgj.dto.ViewDocumentDto;

@Service
public class ViewDocumentService {

private static final Logger LOGGER = LoggerFactory.getLogger(ViewDocumentService.class);
	
	@Autowired
	private ViewDocumentDao viewDocumentDao;
	
public Collection<ViewDocumentDto> getViewTrainingPartnerDetailForBatchId(String tpName, String batchId){
			LOGGER.debug("Request received from Control");
		LOGGER.debug("In POC training Partner Service, to get training Partner details for search");
		
		try {
			LOGGER.debug("In try block to get training partner details");
			return viewDocumentDao.getViewTrainingPartnerDetailForBatchId(tpName,batchId);
		} catch (Exception e) {
		
			LOGGER.debug("An error occurred while getting the training partner details for Search"+ e);
			return null;
		}
	}

public Collection<ViewDocumentDto> getViewTrainingPartnerDetailForscgjBtNumber(String tpName ,String scgjBtNumber){
	LOGGER.debug("Request received from Control");
LOGGER.debug("In POC training Partner Service, to get training Partner details for search");

try {
	LOGGER.debug("In try block to get training partner details");
	return viewDocumentDao.getViewTrainingPartnerDetailForSearchscgjBtNumber( tpName, scgjBtNumber);
} catch (Exception e) {

	LOGGER.debug("An error occurred while getting the training partner details for Search"+ e);
	return null;
}
}


//			public ArrayList<String> CreateZipFile() {
//				 Collection<ViewDocumentDto> a;
//				try {
//					 LOGGER.debug("In try block of Service to get the manipulated data for Search Document Functionality");
//					 //open collection & manipulate data
////                  
//					 Iterator itr= a.iterator();
//					 
//					 Integer batchId = ((ViewDocumentDto) a).getBatchId();
//					 String trainingPartnerName= ((ViewDocumentDto) a).getTrainingPartnerName();
//					 String uplodedOn= ((ViewDocumentDto) a).getUplodedOn();
//					 String finalBatchReportPath = ((ViewDocumentDto) a).getFinalBatchReportPath();
//                     String occupationCertificatePath = ((ViewDocumentDto) a).getOccupatioCertificatePath();
//                     String minuteOfSelectionCommitteePath = ((ViewDocumentDto) a).getMinuteOfSelectionCommitteePath();
//                     String dataSheetForSDMSPath = ((ViewDocumentDto) a).getDataSheetForSDMSPath();
//                     String dataSheetForNSKFCPath = ((ViewDocumentDto) a).getDataSheetForNSKFCPath();
//                     String attendanceSheetPath = ((ViewDocumentDto) a).getAttendanceSheetPath();
//                    while(itr.hasNext()){
//          
//          
//                   }
//					FileOutputStream fos = new FileOutputStream("C:/Users/Bhawana Sharma/Desktop/ziptest.zip");
//					ZipOutputStream zos = new ZipOutputStream(fos);
//
//					String file1Name = "D:/ziptest/tex2.txt";
//					String file2Name = "D:/ziptest/txt1.txt";
//					String file3Name = "D:/ziptest/BHAWANASYNOPSIS.pdf";
//					String file4Name = "D:/ziptest/viewDocumentCSS.css";
//		
//					ArrayList<String> filespath=new ArrayList<String>();					 
//					 
//					filespath.add(addToZipFile(file1Name, zos));
//		
//					filespath.add(addToZipFile(file2Name, zos));
//		
//					filespath.add(addToZipFile(file3Name, zos));
//		
//					filespath.add(addToZipFile(file4Name, zos));
//		
// 
//					zos.close();
//					fos.close();
//					
//
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				
//			}
//
//				public static void addToZipFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {
//
//					System.out.println("Writing '" + fileName + "' to zip file");
//					System.out.println("hi this is method");
//					File file = new File(fileName);
//					FileInputStream fis = new FileInputStream(file);
//					ZipEntry zipEntry = new ZipEntry(fileName);
//					zos.putNextEntry(zipEntry);
//
//					byte[] bytes = new byte[1024];
//					int length;
//					while ((length = fis.read(bytes)) >= 0) {
//						zos.write(bytes, 0, length);
//					}
//
//					zos.closeEntry();
//					fis.close();
//					return null;
//					
//				}	

}

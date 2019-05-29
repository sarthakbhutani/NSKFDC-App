package com.nskfdc.scgj.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.ViewDocumentConfig;
import com.nskfdc.scgj.dto.UploadDocumentsDto;
import com.nskfdc.scgj.dto.ViewDocumentDto;

@Repository
public class ViewDocumentDao extends AbstractTransactionalDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ViewDocumentDao.class);

	private static final ViewDocumentRowmapper viewDocument_RowMapper = new ViewDocumentRowmapper();
	

	@Autowired
	private ViewDocumentConfig viewDocumentConfig;

	/**
	 * Method to get details of documents uploaded given a batch Id and training partner
	 * @param tpName
	 * @param batchId
	 * @return Collection of View document object 
	 */
	public Collection<ViewDocumentDto> getViewTrainingPartnerDetailForBatchId(String tpName, String batchId) {

		LOGGER.debug("Request received from Service to ViewDocumentsDao");
		LOGGER.debug("In getViewTrainingPartnerDetailForBatchId");
		LOGGER.debug("To get Document details of TP for entered BatchId");

		try {
			LOGGER.debug("TRYING -- To get the TP Document Details for entered BatchId");
			Map<String, Object> parameters = new HashMap<>();
			LOGGER.debug("Inserting the trainingPartnerName & scgjBatchNumber in parameters");
			parameters.put("trainingPartnerName", tpName);
			parameters.put("batchId", batchId);
			LOGGER.debug("Execute query to get training partner documents for Search");
			LOGGER.debug("For enterd BatchId" + batchId);
			return getJdbcTemplate().query(viewDocumentConfig.getShowTrainingPartnerDetailsForDownloadusingBatchId(),
					parameters, viewDocument_RowMapper);

		} catch (Exception e) {

			LOGGER.error("CATCHING -- Exception handled while getting the TP documents for entered batchId ");
			LOGGER.error("In ViewDocumentDao - getViewTrainingPartnerDetailForBatchId");
			LOGGER.error("Exception is " + e);
			LOGGER.error("Returning null");
			return null;

		}

	}

	/**
	 * Method to get Details of uploaded documents based on training partner and batch number
	 * @param tpName
	 * @param scgjBtNumber
	 * @return Collection of viewDocumnet object
	 */
	public Collection<ViewDocumentDto> getViewTrainingPartnerDetailForSearchscgjBtNumber(String tpName,
			String scgjBtNumber) {

		LOGGER.debug("Request received from Service to ViewDocumentsDao");
		LOGGER.debug("In getViewTrainingPartnerDetailForSearchscgjBtNumber");
		LOGGER.debug("To get Document details of TP for entered SCGJBatchNumber");

		try {
			LOGGER.debug("TRYING -- To get the TP Document Details for entered SCGJBatchNumber");
			Map<String, Object> parameters = new HashMap<>();
			LOGGER.debug("Inserting the trainingPartnerName & scgjBatchNumber in parameters");
			parameters.put("trainingPartnerName", tpName);
			parameters.put("scgjBatchNumber", scgjBtNumber);
			LOGGER.debug("Execute query to get training partner documents for Search");
			LOGGER.debug("For enterd SCGJBatchNumber" + scgjBtNumber);
			return getJdbcTemplate().query(
					viewDocumentConfig.getShowTrainingPartnerDetailsForDownloadusingBatchNumber(), parameters,
					viewDocument_RowMapper);

		} catch (Exception e) {

			LOGGER.error("CATCHING -- Exception handled while getting the TP documents for entered SCGJBatchNumber ");
			LOGGER.error("In ViewDocumentDao - getViewTrainingPartnerDetailForSearchscgjBtNumber");
			LOGGER.error("Exception is " + e);
			LOGGER.error("Returning null");
			return null;

		}

	}

	

	/**
	 * Row mapper for view documents object
	 * @author Ruchi
	 *
	 */
	private static class ViewDocumentRowmapper implements RowMapper<ViewDocumentDto> {
		@Override
		public ViewDocumentDto mapRow(ResultSet rs, int rownum) throws SQLException {

			String batchId = rs.getString("batchId");
			String trainingPartnerName = rs.getString("trainingPartnerName");
			String uplodedOn = rs.getString("dateUploaded");
			Integer finalBatchReport = rs.getInt("finalBatchReport");
			Integer occupationCertificate = rs.getInt("occupationCertificate");
			Integer minuteOfSelectionCommittee = rs.getInt("minuteOfSelectionCommittee");
			Integer dataSheetForSDDMS = rs.getInt("dataSheetForSDDMS");
			Integer dataSheetForNSKFC = rs.getInt("dataSheetForNSKFC");
			Integer attendanceSheet = rs.getInt("attendanceSheet");
			String finalBatchReportPath = rs.getString("finalBatchReportPath");
			String occupationCertificatePath = rs.getString("occupatioCertificatePath");
			String minuteOfSelectionCommitteePath = rs.getString("minuteOfSelectionCommitteePath");
			String dataSheetForSDMSPath = rs.getString("dataSheetForSDMSPath");
			String dataSheetForNSKFCPath = rs.getString("dataSheetForNSKFCPath");
			String attendanceSheetPath = rs.getString("attendanceSheetPath");
			

			
			return new ViewDocumentDto( batchId,  trainingPartnerName,  uplodedOn,  finalBatchReport,
					 occupationCertificate,  minuteOfSelectionCommittee,  dataSheetForSDDMS,
					 dataSheetForNSKFC,  attendanceSheet,  finalBatchReportPath,
					 occupationCertificatePath,  minuteOfSelectionCommitteePath,  dataSheetForSDMSPath,
					 dataSheetForNSKFCPath,  attendanceSheetPath);
		}
		
	}
	
	
	
}
	


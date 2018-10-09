package com.nskfdc.scgj.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StringUtility {
		
	private static final Logger LOGGER = LoggerFactory.getLogger(StringUtility.class);

	
	public String constructedBatchId(String trainingPartnerName ,Integer batchCount,String municipality, Integer nsdcRegNumber) {
		
		String batchId="";
		LOGGER.debug("In the Utility Class - constructedBatchId() to get the name of training partner"); 
		
		LOGGER.debug("The name of training partner is : " +trainingPartnerName);
		
		if(trainingPartnerName==null)
		{
			LOGGER.error("Could not get name of training partner");
			return null;
		}
		

		String[] splittedTrainingPartnerName = splitBySpace(trainingPartnerName);
		LOGGER.debug("Splitted the name of training parter");
		LOGGER.debug("Sending the splitted name to getFirstLetter() to get the initials of training partner with name : " + trainingPartnerName);
		String uniqueTrainingPartnerName = getTrainingPartnerInitials(splittedTrainingPartnerName, nsdcRegNumber);
		LOGGER.debug("The unique name of the training partner is " +uniqueTrainingPartnerName);
		LOGGER.debug("Calling the method -> splitbySpace to get the string array of municipality name : " +municipality);
		String[] splittedMunicipalityName = splitBySpace(municipality);
		LOGGER.debug("Splitted the name of the municipality by space ");
		LOGGER.debug("Sending splitted municipality name to getUniqueMunicipalityName() method to get the unique name of municipality");
		String uniqueMunicipalityName = getUniqueMunicipalityName(splittedMunicipalityName);
		LOGGER.debug("The unique name for municipality is : " +uniqueMunicipalityName);
		LOGGER.debug("Constructing the unique batch id using name of training partner, municipality and number of batches");
		
		batchId = uniqueTrainingPartnerName+uniqueMunicipalityName+batchCount;
		return batchId;
	}


	/**
	 * Method to split string by a space
	 * @param stringToSplit
	 * @return
	 */
	  public String[] splitBySpace(String stringToSplit)
	  {
		  String[] splittedString = null;
		  if(stringToSplit !=null)
		  {
			  splittedString = stringToSplit.split("\\s+");
		  }
		  return splittedString;
	  }
	  
	  /**
	   * Method to split the string by passed string value
	   * @param stringToSplit
	   * @param splitBy
	   * @return the splitted array
	   */
	  public String[] splitByChar(String stringToSplit, String splitBy)
	  {
		  String[] splittedArray = null;
		  if(stringToSplit!=null && splitBy!=null)
		  {
			  splittedArray = stringToSplit.split(splitBy);
		  }	 
		  return splittedArray;
	  }
	  
	  /**
	   * Method to compare two strings
	   * @param wordOne
	   * @param wordTwo
	   * @return true if strings match and false if they don't
	   */
	  public boolean stringCompare(String wordOne, String wordTwo)
	  {
		  boolean result=false;
		  if(wordOne !=null && wordTwo !=null)
		  {
			  if(wordOne.equals(wordTwo))
			  {
				  result= true;
			  }		  
		  }
	  return result;
	  }
	  /**
	 * Method to get initials of an string array
	 * @param splittedString
	 * @param nsdcRegNumber
	 * @return
	 */
	  public String getTrainingPartnerInitials(String[] splittedString,Integer nsdcRegNumber)
	  {
		  String initials="";
		  int lengthOfArray = splittedString.length;
		  
		  for(int i = 0;i<lengthOfArray;i++)
		  {
			  char firstCharacter;
			  firstCharacter = splittedString[i].charAt(0);
			  LOGGER.debug("Character in String utility - getFirstLetter() is " +firstCharacter); 
			  
			  initials =  initials+firstCharacter;
		  }
		  
		  String splitBySlash = initials+"_"+nsdcRegNumber+"_";
		  
		  return splitBySlash;
	  }
	
	  /**
	   * Get the name of Municipality
	   * @param municipalityName
	   * @return
	   */
	  public String getUniqueMunicipalityName(String[] municipalityName)
	  {
		  
		  int municipalityArrayLength = municipalityName.length;
		  String municipalityUniqueName = "";
		  
		  //getting the first names of the municipality
		  for(int i = 0; i<(municipalityArrayLength-2); i++)
		  {
			  municipalityUniqueName = municipalityUniqueName+municipalityName[i].toString().substring(0,1).toUpperCase()+municipalityName[i].substring(1).toLowerCase()+" ";
			  LOGGER.debug("The municipality unique name is : " + municipalityUniqueName);
		  }
		  
		  //Getting the initials of the last 2 words
		  
		  char secondLastChar = Character.toUpperCase(municipalityName[(municipalityName.length)-2].charAt(0));
		  char lastChar = Character.toUpperCase(municipalityName[(municipalityName.length)-1].charAt(0));
		  
		  
		  municipalityUniqueName = municipalityUniqueName+" "+secondLastChar+lastChar+"_";
		  LOGGER.debug("The municipality Unique name is : " + municipalityUniqueName);
		  return municipalityUniqueName;
	  }

}

package com.nskfdc.scgj.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nskfdc.scgj.dao.DataImportDao;

public class StringUtility {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StringUtility.class);

	//To split with spaces and return splitted array
	
	  public String[] splitBySpace(String stringToSplit)
	  {
		  String[] splittedString = stringToSplit.split("\\s+");
		 
		  return splittedString;
				  
				  
				// getFirstLetter(splittedString);
		  
	  }
	  
	  
	//To find first letter of every word of array and return
	
	  public String getFirstLetter(String[] splittedString )
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
		  
		  String splitBySlash = initials.concat("/");
		  
		  return splitBySlash;
	  }
	
	  public String getUniqueMunicipalityName(String[] municipalityName)
	  {
//		  
		  int municipalityArrayLength = municipalityName.length;
		  String municipalityUniqueName = "";
		  
		  //getting the first names of the municipality
		  for(int i = 0 ; i<(municipalityArrayLength-2); i++)
		  {
			  municipalityUniqueName = municipalityUniqueName+municipalityName[i].toString();
			  LOGGER.debug("The municipality unique name is : " + municipalityUniqueName);
		  }
		  
		  //Getting the initials of the last 2 words
		  
		  char secondLastChar = municipalityName[(municipalityName.length)-2].charAt(0);
		  char lastChar = municipalityName[(municipalityName.length)-1].charAt(0);
		  
		  
		  municipalityUniqueName = municipalityUniqueName+secondLastChar+lastChar+"/";
		  LOGGER.debug("The municipality Unique name is : " + municipalityUniqueName);
		  return municipalityUniqueName;
	  }
	
	  
	
}

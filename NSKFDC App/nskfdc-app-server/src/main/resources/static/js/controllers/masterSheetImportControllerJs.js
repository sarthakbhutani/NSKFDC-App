function checkforward() {
	
    var location = document.getElementById('Loca');
    var invalid = location.value == "none";
    if (invalid) {
        location.className = 'state';
    } else {
        location.className = 'state';
    }
   return !invalid;
}


function checkforstate() {
   var location = document.getElementById('State');
  var invalid = location.value == "State";
 if (invalid) {
      location.className = 'state';
  } else {
     location.className = 'state';
 }
   return !invalid;
}


var app = angular.module('app');
app.controller('importController', function($scope, $http, $rootScope, fileUpload, $timeout) {
	
	document.getElementById("rollinguploadCandidateSheetGif").style.display="none";
	
	$scope.submitMsg=false;
	$scope.dateError="";
    $scope.dateErrorFlag =false;
    $scope.confirmbox = false;
	$scope.batch = {};
	$scope.generateCandidateSheetError=false;
	$scope.centreIdErrorFlag=false;
	$scope.employerNumberErrorFlag=false;
	$scope.emptyEmployerNameCheck=false;
	$scope.masterSheet = {};
	$scope.rollingGenerateCandidateSheet= false;
	$scope.generatingCandidateSheet="";
	$http.get('/getNameOfUser').then(function(response){
		$rootScope.nameOfuser=response.data.trainingPartnerName;
	});
	
	
	$http.get('/getFinancialYear')
    .then(function (response) {
    	let year = response.data;
    	let first = year%10000;
    	let second = year/10000;
    	
    	$scope.financialyear = Math.floor(second) + " - " + first;
    })
   
	
	$http.get('/getTotalTargets')
    .then(function (response) {
    	if(response.data == null)
    		{
    	$scope.totaltargets = 0;
    		}
    	else{
    		$scope.totaltargets = response.data;
    	}
    });

	$http.get('/getTargetAchieved')
    .then(function (response) {
    	if(response.data == null)
		{
	$scope.targetachieved = 0;
		}
	else{
		$scope.targetachieved = response.data;
	}

    });
	
	$http.get('/getRemainingTargets')
    .then(function (response) {
    	
    	if(response.data == null)
		{
	$scope.remainingtargets = 0;
		}
	else{
		$scope.remainingtargets = response.data;
	}

    });
	
	
var url = '/getBatchIdfortrainer';
    $scope.ids = [];
    $http.get(url)
	    .then(function(response) {
	        let dt = response.data;
	        for (i in dt) {
	            $scope.ids.push(response.data[i].batchId);
	        }
	    });

    $scope.batch.myVar = 'none';
    $scope.enable = function() {
        $scope.isDisabled = false;
        $scope.isDisabled1 = false;
        $scope.isDisabled2 = false;
        $scope.isDisabled3 = false;
        $scope.isDisabled4 = false;
        $scope.isDisabled5 = false;
        $scope.isDisabled6 = false;
        $scope.isDisabled7 = false;
        $scope.isDisabled8 = false;
        $scope.isDisabled9 = false;
        $scope.isDisabled10 = false;
        $scope.isDisabled11 = false;
        $scope.isDisabled12 = false;
        $scope.isDisabled13 = false;
       
        /*----------------- To get the existing details of Batch Id--------------*/
        $http.get("/get-batch-details?batchId="+$scope.batchDetails.value)
        .then(function(response){        
        	$scope.batch = response;
        	$scope.batch.centreId= response.data.centreId;
        	$scope.batch.state= response.data.state;
        	$scope.batch.centreCity=response.data.centreCity;
        	$scope.batch.municipality=response.data.municipality;
        	$scope.batch.selectionCommitteeDate=response.data.selectionCommitteeDate;
        	$scope.batch.principalTrainerName=response.data.principalTrainerName;
        	$scope.batch.batchStartDate=response.data.batchStartDate;
        	$scope.batch.batchEndDate=response.data.batchEndDate;
        	$scope.batch.assessmentDate=response.data.assessmentDate;
        	$scope.batch.medicalExamDate=response.data.medicalExamDate;
        	$scope.batch.employerName=response.data.employerName;
        	$scope.batch.employerContactNumber=response.data.employerContactNumber;
        	$scope.batch.wardType = response.data.wardType;
        	$scope.batch.wardNumber = response.data.wardNumber;
        });
    };
    
    
    
    
    $scope.generateBatchId = function() {
    	$scope.rolling = true;
    	$scope.confirmbox = false;
	    $scope.generating = "Please wait while we generate batch sheet for you.";
	    //To check if remaining targets is zero or negative	    
	    $http.get('/getRemainingTargets').then(
	    function(response)
	    {
	    	if(response.data <= 0 )
			{
	    		$scope.errorMsg = true;
	    		$scope.errorMessage="Please contact SCGJ for more targets";
	    		$scope.rolling = false;
	    		$timeout(function() {
    	        	$scope.rolling = false;
    	            $scope.success = false;
    	            $scope.successMessage = "";
    	            $scope.errorMessage="";
    	            $scope.errorMsg=false;
    	         }, 3000);
			}
		else
		{
			$http.get("/generateBatch/"+$scope.generateBatch.municipality)
            .then(function(response) {
            	$scope.success = true;
            	if(response.data == null)
            		{
            		  $scope.success = false;
            		  $scope.errorMessage="Batch cannot be generated";
            		}
            	$scope.successMessage = "Batch " + response.data.batchId +" generated successfully!";
                $scope.data = response.data;
                $scope.ids = [];
                $http.get('/getBatchIdfortrainer')
            	    .then(function(response) {
            	    	$scope.rolling = false;
            		    $scope.generating = "";
            	        let dt = response.data;
            	        for (i in dt) {
            	            $scope.ids.push(response.data[i].batchId);
            	        }
            	        
            	        $timeout(function() {
            	        	$scope.rolling = false;
            	            $scope.success = false;
            	            $scope.successMessage = "";
            	            $scope.errorMessage="";
            	            $scope.errorMsg=false;
            	         }, 3000);
            	    });
         }, function(errorResponse){
        	 $scope.errorMessage="Batch cannot be generated";
        	 $timeout(function() {
 	        	$scope.rolling = false;
 	            $scope.success = false;
 	            $scope.successMessage = "";
 	           $scope.errorMessage="";
 	            $scope.errorMsg=false;
 	         }, 3000);
         });
		}	    	
	    });   
    };
    
    
 
    
    
        
    $scope.setFile = function(element) {
        $scope.$apply(function($scope) {
            $scope.theFile = element.files[0];
            $scope.FileMessage = '';
            var filename = $scope.theFile.name;
            var index = filename.lastIndexOf(".");
            var strsubstring = filename.substring(index, filename.length);
            if ( strsubstring == '.xlsx') {
            } else {
                $scope.theFile = '';
                $scope.FileMessage = 'Please upload correct File ,File extension should be .xlsx';
            }
        });
    };
    
    
    $scope.checkEndDate=function(){
    	$scope.dateErrorFlag = false;
    	if( ($scope.batch.batchStartDate == null || $scope.batch.batchStartDate == undefined) && $scope.batch.batchEndDate != null){
    		$scope.dateErrorFlag = true;
    		$scope.dateError = "Please fill Batch Start date first";
    	}
    	if($scope.batch.batchEndDate< $scope.batch.batchStartDate)
    	{
    		$scope.dateErrorFlag = true;
    		$scope.dateError = "Batch End Date cannot be less than Batch Start Date";
    	}
    	
    	
    	if($scope.dateErrorFlag == false)
     	{
    	var startDate = new Date($scope.batch.batchStartDate);
    	var endDate = new Date($scope.batch.batchEndDate);
    	var timeDiff = Math.abs(endDate.getTime() - startDate.getTime());
        var dayDifference = Math.ceil(timeDiff / (1000 * 3600 * 24));
    	
    	if( dayDifference > 4)
    		{
    			$scope.dateErrorFlag = true;
    			$scope.dateError = "Batch duration cannot exceed 5 days";
    		}

     	}
    	
    	$timeout(function() {
            $scope.dateError="";
            $scope.dateErrorFlag =false;
            $scope.errorMsg="";
            $scope.success = false;
            $scope.successMessage = false;
            $scope.errorMsg=false;
         }, 6000);
    	return $scope.dateErrorFlag ;
     };
    

    /*------------Submit the Data in Database------------*/
    
    $scope.submitMasterSheet=function(){
    	
    	//Check if center Id is number > 0
    	if($scope.batch.centreId<=0){
    		$scope.employerNumberErrorFlag=false;
    		$scope.centreIdErrorFlag=true;
    	}	
    	//Check if end start date is not null
    	else if($scope.checkEndDate())
    	{
    		$scope.centreIdErrorFlag=false;
    		$scope.employerNumberErrorFlag=false;
    		$scope.batch.batchEndDate = null;
    	}
    	else if($scope.batch.employerContactNumber<0){
    		$scope.centreIdErrorFlag=false;
    		$scope.employerNumberErrorFlag=true;
    	}
    	
    	else
    	{
    		if($scope.batch.employerContactNumber>0 && ($scope.batch.employerName==undefined || $scope.batch.employerName==null || $scope.batch.employerName=="" )){
    			$scope.emptyEmployerNameCheck=true;
    		}
    		else{
    		$scope.emptyEmployerNameCheck=false;
    		$scope.centreIdErrorFlag=false;
    		$scope.employerNumberErrorFlag=false;
    		$scope.sumbitBatchDetails={
        			batchId : $scope.batchDetails.value,
        			wardType : $scope.batch.wardType,
        			wardNumber : $scope.batch.wardNumber,
        			centreId : $scope.batch.centreId,
        			state : $scope.batch.state,
        			city : $scope.batch.centreCity,
        			municipality : $scope.batch.municipality,
        			selectionCommitteeDate : $scope.batch.selectionCommitteeDate,
        			trainerName : $scope.batch.principalTrainerName,
        			batchStartDate : $scope.batch.batchStartDate,
        			batchEndDate : $scope.batch.batchEndDate,
        			assessmentDate : $scope.batch.assessmentDate,
        			medicalExamDate : $scope.batch.medicalExamDate,
        			employerName : $scope.batch.employerName,
        			employerNumber : $scope.batch.employerContactNumber}
        			$http({
        	    		url : '/submitBatchDetails',
        	    		method :"POST",
        	    		data : angular.toJson($scope.sumbitBatchDetails),
        	    		headers : {
        	    			'Content-type' : 'application/json'
        	    		}
        	    	}).then(function(response){
        	    		$scope.status= response.data;
        				if($scope.status==1){
        					$scope.submitMsg=true;
        					$scope.errorMsg=false;
        					$scope.SuccessMessage="Batch details inserted successfully";
        					
        				}
        			
        			
        				else{
        					
        					$scope.submitMsg=false;
        					$scope.errorMessage="Could not insert details of batch";
        					$scope.errorMsg=true;
        					
        				}          
        	    	},function(errorResponse){
        	    		$scope.submitMsg=false;
        				$scope.errorMessage="Could not insert details of batch";
        				$scope.errorMsg=true;
        	    	});
        	    	
        	    	$timeout(function() {
        	            $scope.submitMsg="";
        	            $scope.errorMsg="";
        	            $scope.success = false;
        	            $scope.successMessage = false;
        	            $scope.errorMsg=false;       	            
        	         }, 3000);
        			
        	}
    	}
  
    	
    	$timeout(function() {
            $scope.centreIdErrorFlag=false;
    		$scope.employerNumberErrorFlag=false;
    		$scope.emptyEmployerNameCheck=false;
            
         }, 3000);
    	};
    	    	
    
    
     /* for Confirmation box */
    	 $scope.genarateBatchConfirmation = function(){
    	    	if($scope.confirmbox== true)
    	    	$scope.confirmbox= false;
    	    	else
    	    		$scope.confirmbox= true;
    	  }
    
    	 $scope.uploadCandidateSheet= function(){
    		 
    		 var file = $scope.masterSheet.import;
		      	
		      	var batchId = $scope.uploadCandidateDetails.value;
		      	
		          var importUrl = "/importMasterSheet";
		        var fileuploaded = fileUpload.uploadFileToUrl(file, importUrl, batchId);
    	 }
    	 
    	 $scope.generateCandidateSheet= function(){
    		 
    		 $scope.rollingGenerateCandidateSheet= true;
    		 $scope.generatingCandidateSheet="Please wait generating Candidate Sheet";
    		 
    		 var url='/downloadFinalMasterSheet?batchId='+$scope.generatebatchDetails.value;  	  
         	$http.get(url, { responseType : 'arraybuffer' }).then(function(response)
         	{	
         		if(response.data.byteLength!=0)
        		{   
         			 $scope.rollingGenerateCandidateSheet= false;
            		 $scope.generatingCandidateSheet="";
         			var excelFile = new Blob([response.data], { type : 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
         			var downloadURL = URL.createObjectURL(excelFile);
         			var link = document.createElement('a');
         			link.href = downloadURL;
         			link.download = "Candidate Sheet_"+$scope.generatebatchDetails.value+".xlsx"
         			document.body.appendChild(link);
         			link.click();
         			
         			$scope.generateCandidateSheetError = true;
                	$scope.generateCandidateSheetMessage = "Candidate sheet generated successfully!";
                	document.getElementById("successId").style.color = "green";
                	
        		}
         		else{
         			 $scope.rollingGenerateCandidateSheet= false;
            		 $scope.generatingCandidateSheet="";
         			$scope.generateCandidateSheetError = true;
                	$scope.generateCandidateSheetMessage = "Candidate sheet cannot be generated!";
                	document.getElementById("successId").style.color = "red";
                	
         		}
         			
         	}); 
         	
        	
        	$timeout(function() {
	            $scope.generateCandidateSheetMessage="";
	            $scope.rollingGenerateCandidateSheet= false;
	    		 $scope.generatingCandidateSheet="";
	            $scope.generateCandidateSheetError = false;
	         }, 3000);
      
    	 }
});
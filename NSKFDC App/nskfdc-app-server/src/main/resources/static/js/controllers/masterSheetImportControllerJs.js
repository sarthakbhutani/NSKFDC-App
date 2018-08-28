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
	$scope.submitMsg=false;
	$scope.dateError="";
    $scope.dateErrorFlag =false;
    $scope.confirmbox = false;
	$scope.batch = {};
	$scope.generateCandidateSheetError=false;
	$scope.masterSheet = {};
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
			$http.get("/generateBatch")
            .then(function(response) {
            	$scope.success = true;
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
        	 $scope.$scope.errorMessage="Batch cannot be generated";
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
    		$scope.dateErrorFlag =true;
    		$scope.dateError = "Batch End Date cannot be less than Batch Start Date";
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
    	//Check if end start date is not null
    	if($scope.checkEndDate())
    	{
    		$scope.batch.batchEndDate = null;
    	}
    	else
    	{
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
		      	
		      	var batchId = $scope.batchDetails.value;
		      	
		          var importUrl = "/importMasterSheet";
		        var fileuploaded = fileUpload.uploadFileToUrl(file, importUrl, batchId);
    	 }
    	 
    	 $scope.generateCandidateSheet= function(){
    		 var url='/downloadFinalMasterSheet?batchId='+$scope.batchDetails.value;  	  
         	$http.get(url, { responseType : 'arraybuffer' }).then(function(response)
         	{	
         		
         			var excelFile = new Blob([response.data], { type : 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
         			var downloadURL = URL.createObjectURL(excelFile);
         			var link = document.createElement('a');
         			link.href = downloadURL;
         			link.download = "Candidate Sheet_"+$scope.batchDetails.value+".xlsx"
         			document.body.appendChild(link);
         			link.click();
         			
         			$scope.generateCandidateSheetError = true;
                	$scope.generateCandidateSheetMessage = "Batch sheet generated successfully!";
         			
         	}); 
         	
        	
        	$timeout(function() {
	            $scope.generateCandidateSheetMessage="";
	            $scope.generateCandidateSheetError = false;
	         }, 3000);
      
    	 }
});
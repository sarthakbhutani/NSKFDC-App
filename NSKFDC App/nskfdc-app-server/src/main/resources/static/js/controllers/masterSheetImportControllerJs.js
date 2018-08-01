function checkforward() {
	
    var location = document.getElementById('Loca');
    var invalid = location.value == "none";
    if (invalid) {
       alert('First Choose Ward Type');
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
       alert('choose State');
      location.className = 'state';
  } else {
     location.className = 'state';
 }
   return !invalid;
}







var app = angular.module('app');
app.controller('importController', function($scope, $http, $rootScope) {
	
	
	$http.get('/getNameOfUser').then(function(response){
		$rootScope.nameOfuser=response.data.trainingPartnerName;
	});
	
	
	$http.get('/getFinancialYear')
    .then(function (response) {
    	console.log("the data for financial year is " + response.data);
    	let year = response.data;
    	let first = year%10000;
    	let second = year/10000;
    	
    	console.log(year);
    	$scope.financialyear = Math.floor(second) + " - " + first;
    })
    .catch((response) => console.log("error in getting the value " + response + " " + response.status + " "  + response.data));
	
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

    $scope.myVar = 'none';
    //	 $scope.value = 'select';
    $scope.enable = function() {
        console.log("inside function " + $scope.batchDetails.value);
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
        $http.get("/BatchDetails?batchId="+$scope.batchDetails.value)
        .then(function(response){
        
        	$scope.batch= response;
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
        });
    };
    
    
    
    
    $scope.generateBatchId = function() {
        $http.get("/generateBatch")
            .then(function(response) {
                $scope.data = response.data;
                $scope.ids = [];
                $http.get('/getBatchIdfortrainer')
            	    .then(function(response) {
            	        let dt = response.data;
            	        for (i in dt) {
            	            $scope.ids.push(response.data[i].batchId);
            	        }
            	    });
         });
    
    };
    
    
    $scope.downloadMasterSheet = function(){
    	var url='/downloadFinalMasterSheet';  	  
    	$http.get(url, { responseType : 'arraybuffer' }).then(function(response)
    	{	
    		
    			var excelFile = new Blob([response.data], { type : 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
    			var downloadURL = URL.createObjectURL(excelFile);
    			var link = document.createElement('a');
    			link.href = downloadURL;
    			link.download = "Batch Master Sheet.xlsx"
    			document.body.appendChild(link);
    			link.click();
    			
    	}); 
    };
    
    
        
    $scope.setFile = function(element) {
        $scope.$apply(function($scope) {
            $scope.theFile = element.files[0];
            $scope.FileMessage = '';
            var filename = $scope.theFile.name;
            console.log(filename.length)
            var index = filename.lastIndexOf(".");
            var strsubstring = filename.substring(index, filename.length);
            if ( strsubstring == '.xlsx') {
                console.log('File Uploaded sucessfully');
            } else {
                $scope.theFile = '';
                $scope.FileMessage = 'please upload correct File ,File extension should be .xlsx';
            }
        });
    };
    
    
    
    

    /*------------Submit the Data in Database------------*/
    
    $scope.submitMasterSheet=function(){
    	
    	$scope.sumbitBatchDetails={
    			batchId : $scope.batchDetails.value,
    			wardType : $scope.myVar,
    			wardNumber : $scope.wardNumber,
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
    			employerNumber : $scope.batch.employerContactNumber
    			
    			
    	};
    	
    	
    	
    	$http({
    		url : '/submitBatchDetails',
    		method :"POST",
    		data : angular.toJson($scope.sumbitBatchDetails),
    		headers : {
    			'Content-type' : 'application/json'
    		}
    	}).then(function(response){
    		
    	});
    }
    
    
 
    
});
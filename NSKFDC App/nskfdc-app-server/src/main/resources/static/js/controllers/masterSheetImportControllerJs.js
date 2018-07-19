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
app.controller('importController', function($scope, $http) {	
	$http.get('/getFinancialYear')
    .then(function (response) {
    	console.log("the data for financial year is " + response.data);
    	let year = response.data;
    	let first = year%10000;
    	let second = year/10000;
    	
//    	str.substr(4,1)
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
        console.log("inside function " + $scope.value);
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
    };
    $scope.generateBatchId = function() {
        $http.get("/generateBatch")
            .then(function(response) {
                console.log("Inside controller");
                $scope.data = response.data;
            });
        if ($scope.data == -1) {
            console.log("Inside if");
            console.log("Batch not generated");
        } else {
            console.log("Batch successfully generated");
        }
    };
    
    
    $scope.downloadMasterSheet=function(){
    	var url='/downloadFinalMasterSheet';  	  
    	$http.get(url, { responseType : 'arraybuffer' }).then(function(response)
    	{
    		
    			var pdfFile = new Blob([response.data], { type : 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
    			var downloadURL = URL.createObjectURL(pdfFile);
    			var link = document.createElement('a');
    			link.href = downloadURL;
    			link.download = "Final Master Sheet.xls"
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

});
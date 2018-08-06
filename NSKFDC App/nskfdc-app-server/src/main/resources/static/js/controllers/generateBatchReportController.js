var tp = angular.module("app");

tp.directive('ngFiles', ['$parse', function ($parse) {

    function fn_link(scope, element, attrs) {
        var onChange = $parse(attrs.ngFiles);
        element.on('change', function (event) {
            onChange(scope, { $files: event.target.files });
        });
    };

    return {
        link: fn_link
    }
} ]);

tp.controller("generateBatchReportController" , function($scope, $http,$timeout){
	
	
	var formdata = new FormData();
	var fd = new FormData();
	var numberPic;
	
  
     
     
   /*-----Method to upload pictures -----*/  
   $scope.uploadFile = function($files) {
	   
	   angular.forEach($files, function (value, key) {
		   fd.append("file", value);
       }); 
     }
     
     
     
     $scope.generateBatchReportwithFile = function(){
    	 $scope.rolling = true;
    	 $scope.generating = "Please wait, while we generate your final batch report";
    	 fd.append("batchId",$scope.batchId);
    	 fd.append("batchnumber",$scope.batchnumber);
    	 /* fd.append("file11",$scope.generateReport.day1Pic1);
    	 fd.append("file12",$scope.generateReport.day1Pic2);
    	 fd.append("file13",$scope.generateReport.day2Pic1);
    	 fd.append("file14",$scope.generateReport.day2Pic2);
    	 fd.append("file15",$scope.generateReport.day3Pic1);
    	 fd.append("file16",$scope.generateReport.day3Pic2);
    	 fd.append("file17",$scope.generateReport.day4Pic1);
    	 fd.append("file18",$scope.generateReport.day4Pic2);
    	 fd.append("file19",$scope.generateReport.day5Pic1);
    	 fd.append("file20",$scope.generateReport.day5Pic2);
    	 fd.append("file21",$scope.generateReport.day6Pic1);
    	 fd.append("file22",$scope.generateReport.day6Pic2);
    	 fd.append("file23",$scope.generateReport.day7Pic1);
    	 fd.append("file24",$scope.generateReport.day7Pic2);*/
    	 //Remove before committing
    	/* var res = Array.from(fd.entries(), ([key, prop]) => (
    	            {[key]: {
    	              "ContentLength": 
    	              typeof prop === "string" 
    	              ? prop.length 
    	              : prop.size
    	            }
    	          }));*/

    	//console.log(res);
    	 
    	$http({
    		url: '/generateBatchReport',
			 method: "POST",
			 data: fd,
/*			 responseType : 'arraybuffer',*/
			 headers: {'Content-Type': undefined},
			 transformRequest: angular.identity,
             transformResponse: [function (data) {
             	thisIsResponse=data;
             	return data;
           
             }]
    	}).then(function(response){
    	   		
    		/*if(response.data.byteLength!=0)
    		{
    			$scope.rolling=false;
    			$scope.generating = "";
    			document.getElementById("Success").innerHTML="<center> Batch Report Successfully generated ! </center>";
    			var pdfFile = new Blob([response.data], { type : 'application/pdf' })
    			var downloadURL = URL.createObjectURL(pdfFile);
    			var link = document.createElement('a');
    			link.href = downloadURL;
    			link.download = "Final Batch Report.pdf"
    			document.body.appendChild(link);
    			link.click();
    		}*/
    		if(response.data.length > 0)
    			{
    			$scope.rolling=false;
    			$scope.generating = "";
    			document.getElementById("Success").innerHTML="<center> Batch Report Successfully generated ! </center>";
    			var pdfFile = new Blob([response.data], { type : 'application/pdf' })
    			var downloadURL = URL.createObjectURL(pdfFile);
    			var link = document.createElement('a');
    			link.href = downloadURL;
    			link.download = "Final Batch Report.pdf"
    			document.body.appendChild(link);
    			link.click();
    			}
    		else{
    			$scope.rolling=false;
    			$scope.generating = "";
    			document.getElementById("Error").innerHTML="<center>Please insert valid data to generate Batch Report </center>";
    		}
    		
    		$timeout(function() {
    			document.getElementById("Success").innerHTML="";
    			$scope.generating = "";
    			document.getElementById("Error").innerHTML="";
             }, 4000);
    	}, function(errorResponse){
    		$scope.rolling=false;
			$scope.generating = "";
			document.getElementById("Error").innerHTML="<center>" + errorResponse.data +"</center>";
    		/*document.getElementById("Error").innerHTML="<center>An exception occured while generating report. Try again later</center>";*/
    		
    		$timeout(function() {
    			document.getElementById("Success").innerHTML="";
    			$scope.generating = "";
    			document.getElementById("Error").innerHTML="";
             }, 4000);
    	});
    	 
     }	    
	
	
 		   var url='/getBatchIdDetails';
      	 
          	
          	$scope.ids = [];
          	
                  $http.get(url)
                  .then(function (response) {
                	  
                  	let dt = response.data;	
                  	for(i in dt){
                  		$scope.ids.push(response.data[i].batchId); 
                  	}
                  	let length=$scope.ids.length;/*
                  	console.log(length);*/
              	});
              
          
          
});
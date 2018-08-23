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
	
	$scope.day = "";
     
     
   /*-----Method to upload pictures -----*/  
   $scope.uploadFile = function($files) {
	   
	   angular.forEach($files, function (value, key) {
		   console.log("This is the day"+$scope.day);
		   fd.set("file"+$scope.day, value);
       }); 
     }
     
     
     
     $scope.generateBatchReportwithFile = function(){
    	 $scope.rolling = true;
    	 $scope.generating = "Please wait, while we generate your final batch report";
    	 fd.append("batchId",$scope.batchId);
    	 fd.append("batchnumber",$scope.batchnumber);
    	
    	

    	console.log(res);
    	 
    	$http({
    		url: '/generateBatchReport',
			 method: "POST",
			 data: fd,
			 responseType : 'arraybuffer',
			 headers: {'Content-Type': undefined},
			 transformRequest: angular.identity
            /* transformResponse: [function (data) {
             	thisIsResponse=data;
             	return data;
           
             }]*/
    	}).then(function(response){
    	   		
    		if(response.data.byteLength!=0)
    		{
    			$scope.rolling=false;
    			$scope.generating = "";
    			document.getElementById("Success").innerHTML="<center> Batch Report Successfully generated ! </center>";
    			var pdfFile = new Blob([response.data], { type : 'application/pdf' })
    			var downloadURL = URL.createObjectURL(pdfFile);
    			var link = document.createElement('a');
    			link.href = downloadURL;
    			link.download = "Final Batch Report" + "-" + $scope.batchId + ".pdf"
    			document.body.appendChild(link);
    			link.click();
    		}
    		/*if(response.data.length > 0)
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
    		else{
    			$scope.rolling=false;
    			$scope.generating = "";
    			document.getElementById("Error").innerHTML="<center>Either candidate's or training center's information is not present</center>";
    		}
    		
    		$timeout(function() {
    			document.getElementById("Success").innerHTML="";
    			$scope.generating = "";
    			document.getElementById("Error").innerHTML="";
             }, 6000);
    	}, function(errorResponse){
    		$scope.rolling=false;
			$scope.generating = "";
			document.getElementById("Error").innerHTML="<center>" + errorResponse.data +"</center>";
    		/*document.getElementById("Error").innerHTML="<center>An exception occured while generating report. Try again later</center>";*/
    		
    		$timeout(function() {
    			document.getElementById("Success").innerHTML="";
    			$scope.generating = "";
    			document.getElementById("Error").innerHTML="";
             }, 6000);
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
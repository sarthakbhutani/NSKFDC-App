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
	$scope.batchNumberError= false; 
	$scope.day = "";
     
     
   /*-----Method to upload pictures -----*/  
   $scope.uploadFile = function($files) {
	   
	   angular.forEach($files, function (value, key) {
		   fd.set("file"+$scope.day, value);
       }); 
     }
     
     
     
     $scope.generateBatchReportafterDateCheck = function(){
    	 $scope.rolling = true;
    	 $scope.generating = "Please wait, while we generate your final batch report";
    	 fd.append("batchId",$scope.batchId);
    	 
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
              
                 
                  $scope.getSCGJBatchNumber = function(){
                	  console.log("SCGJ batchNumber");
                	 
                  var url1 = '/showBatchNumber?batchId='+$scope.batchId;
                  $http.get(url1)
                  .then(function(response){
                   	  if(response.data == null || response.data == ""){
                		 $scope.batchNumberError= true; 
                		 $scope.batchnumber= response.data;
                	  }
                	  else{
                		  $scope.batchNumberError= false; 
                		  $scope.batchnumber= response.data;
                	  }
                  })
                  }
              
                  
                  /*   Method to Generate report  */
          $scope.generateBatchReportwithFile = function(){
        	  var checkDateUrl='/checkBatchEndDate?batchId='+$scope.batchId;
        	  if($scope.batchnumber == null || $scope.batchnumber== undefined || $scope.batchnumber== ""){
        		  $scope.batchNumberError= true; 
        		  document.getElementById("Success").innerHTML="";
      			  document.getElementById("Error").innerHTML="<center>SCGJ Batch Number is mandatory</center>";
        	  }
        	  else{
        	  $scope.batchNumberError= false; 
        	  $http.get(checkDateUrl)
        	  .then(function(response){
        		  
        		  if(response.data == 1){
        			  document.getElementById("Error").innerHTML="";
        			 $scope.generateBatchReportafterDateCheck();
        		  }
	        	  else{
	        		  document.getElementById("Success").innerHTML="";
	      			  document.getElementById("Error").innerHTML="<center>Batch has not ended</center>";
	        	  }
        		  
        	  }
        	  );
        	  }
        	  
        	  $timeout(function() {
      			document.getElementById("Success").innerHTML="";
      			document.getElementById("Error").innerHTML="";
               }, 6000);
          }
          
});
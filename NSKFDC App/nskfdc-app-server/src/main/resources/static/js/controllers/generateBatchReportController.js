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
	
	document.getElementsByClassName("filesizeError").innerHTML="";
	var formdata = new FormData();
	var fd = new FormData();
	var countValuesToTransfer=0;
	$scope.batchNumberError= false; 
	$scope.day = "";
     
     
   /*-----Method to upload pictures -----*/  
   $scope.uploadFile = function($files) {
	   var fizesizemessage = "error"+$scope.day;
	   var firstCharDay=($scope.day).charAt(0);
	   var secondCharDay=($scope.day).charAt(1);
	   var daysPic=("day"+firstCharDay+"Pic"+secondCharDay);
	   
	   if($files[0].size>1000000){
		   document.getElementById(daysPic).value=null;
		   document.getElementById(fizesizemessage).innerHTML="Image size exceeding 1MB";
	   }
	   
	   else{
		   document.getElementById(fizesizemessage).innerHTML="";
		   angular.forEach($files, function (value, key) {
		   fd.set("file"+$scope.day, value);
       }); 
	   }
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
    		
    		else{
    			$scope.rolling=false;
    			$scope.generating = "";
    			document.getElementById("Error").innerHTML="<center>Either candidate's or training center's information is not present</center>";
    		}    		
    		
    		/*To reset the formData*/
    		 document.getElementById("myForm").reset();
    		fd.delete("file11");
    		fd.delete("file12");
    		fd.delete("file21");
    		fd.delete("file22");
    		fd.delete("file31");
    		fd.delete("file32");
    		fd.delete("file41");
    		fd.delete("file42");
    		fd.delete("file51");
    		fd.delete("file52");
    		fd.delete("file61");
    		fd.delete("file62");
    		fd.delete("file71");
    		fd.delete("file72");
    		fd.delete("batchId");		
    		$scope.batchnumber="";
    		delete $scope.batchId;
    		
    		
    		$timeout(function() {
			document.getElementById("Success").innerHTML="";
			$scope.generating = "";
			document.getElementById("Error").innerHTML="";
             }, 6000);
    	}, function(errorResponse){
    		$scope.rolling=false;
			$scope.generating = "";
			document.getElementById("Error").innerHTML="<center>" + errorResponse.data +"</center>";
    		
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
                  	let length=$scope.ids.length;
              	});
              
                  
                  
                  
                  
                 
               $scope.getSCGJBatchNumber=function(){                	 
                  var url1 = '/showBatchNumber?batchId='+$scope.batchId;
                  $http.get(url1)
                  .then(function(response){
                	  var scgjBatchNumber = JSON.stringify(response.data.scgjBatchNumber);
                   	  if(JSON.parse(scgjBatchNumber) == null || JSON.parse(scgjBatchNumber) == ""){
                		 $scope.batchNumberError= true;
                		 $scope.batchnumber= "";
                	  }
                	  else{
                		  $scope.batchNumberError= false; 
                		  $scope.batchnumber = JSON.parse(scgjBatchNumber);
                	  }
                  });
                  }
              
                  
/*   Method to Generate report  */
          $scope.generateBatchReportwithFile = function(){
        	  countValuesToTransfer=0;
        	  for (var value of fd.values()) {
         		 countValuesToTransfer++;
         		}
        	  
         	 
         	 if(countValuesToTransfer==14){
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
        		  
        	  });
        	  }
        	  
        	  $timeout(function() {
      			document.getElementById("Success").innerHTML="";
      			document.getElementById("Error").innerHTML="";
               }, 6000);
        	  
        	  
        	 
        	
          }else{
        	  document.getElementById("Error").innerHTML="Please enter all the valid values";
     		 $timeout(function() {
      			document.getElementById("Success").innerHTML="";
      			$scope.generating = "";
      			document.getElementById("Error").innerHTML="";
               }, 6000);
          }
         	 
          }
          
});
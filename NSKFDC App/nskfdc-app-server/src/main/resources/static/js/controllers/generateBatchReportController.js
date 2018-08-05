var tp = angular.module("app");

tp .directive('ngFiles', ['$parse', function ($parse) {

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
     $scope.getTheFiles = function ($files) {
         angular.forEach($files, function (value, key) {
             formdata.append("file", value);
         });
     };
     
     $scope.generateBatchReportwithFile = function(){
    	 $scope.rolling = true;
    	    $scope.generating = "Please wait, while we generate your final batch report"
    	 formdata.append("batchId",$scope.batchId);
    	 formdata.append("batchnumber",$scope.batchnumber);
    	$http({
    		url: '/generateBatchReport',
			 method: "POST",
			 data: formdata,
			 responseType : 'arraybuffer',
			 headers: {
                 'Content-Type': undefined
             }
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
    			link.download = "Final Batch Report.pdf"
    			document.body.appendChild(link);
    			link.click();
    		}
    		else{
    			$scope.rolling=false;
    			$scope.generating = "";
    			document.getElementById("Error").innerHTML="<center> Batch Report cannot be generated ! </center>";
    		}
    		
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
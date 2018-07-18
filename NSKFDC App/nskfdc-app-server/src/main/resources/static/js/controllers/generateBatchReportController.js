var tp = angular.module("app");

tp.controller("generateBatchReportController" , function($scope, $http){
	
	$scope.details = {
 	        enableGridMenus: false,
 	        enableSorting: false,
 	        enableFiltering: false,
 	        enableCellEdit: false,
 	        enableColumnMenus: false,
 	        enableHorizontalScrollbar: 0,
 	        enableVerticalScrollbar: 0,
 	        paginationPageSizes: [5, 10, 20, 30],
 	        paginationPageSize: 10,
 	        useExternalPagination: true,
 	        
 	       columnDefs: [
 	            {
 	            	name: 'Id', 
 	                displayName: 'Batch Id'
 	                	
 	            },
 	            {
 	            	name: 'Date',
 	            	displayName: 'Uploaded On'
 	            },
 	            {
 	                name: 'Name',
 	                displayName: 'Uploaded By'
 	            },
 	            {
 	                name: 'Download',
 	                displayName: 'Download Zip File.'
 	            }
 	            
 	           
 	        ]
 	    
     };
 	    
	    
 		   var url='/getBatchIdDetails';
      	 
           //$scope.ngBlur = function () {
          	
          	$scope.ids = [];
          	
                  $http.get(url)
                  .then(function (response) {
                	  
                  	let dt = response.data;	
                  	for(i in dt){
                  		$scope.ids.push(response.data[i].batchId); 
                  	}
                  	let length=$scope.ids.length;
                  	console.log(length);
              	});
              
           $scope.generateBatchReport = function(batchId,batchnumber){
           
           var url2='/generateBatchReport?batchId=';
           
           $http.get(url2+$scope.batchId+'&batchnumber='+$scope.batchnumber, { responseType : 'arraybuffer' })
           .then(function (response){
           let data1=response.data;
           console.log(data1);
           if(response.data.byteLength!=0)
   			{
	   			
	   			var pdfFile = new Blob([response.data], { type : 'application/pdf' })
	   			var downloadURL = URL.createObjectURL(pdfFile);
	   			var link = document.createElement('a');
	   			link.href = downloadURL;
	   			link.download = "Occupation Certificate.pdf"
	   			document.body.appendChild(link);
	   			link.click();
   			}
   		else{
   			$window.alert("Invalid");
   			}
           });
          
           };
           
          
});
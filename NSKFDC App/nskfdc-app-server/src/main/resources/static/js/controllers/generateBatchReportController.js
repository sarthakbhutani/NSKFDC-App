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
 	    
	    
 		   var url='/getBatchIdDetails?batchnumber=';
      	 
           $scope.ngBlur = function (batchnumber) {
          	
          	$scope.ids = [];
          	
                  $http.get(url+$scope.batchnumber)
                  .then(function (response) {
                	  
                  	let dt = response.data;	
                  	for(i in dt){
                  		$scope.ids.push(response.data[i].batchId); 
                  	}
              	});
          	
          	 }

});
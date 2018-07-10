var uploadDocument = angular.module("app",['ngRoute','ui.grid',
	                                      'ui.grid.edit',
	                                      'ui.grid.cellNav',
	                                      'ui.grid.autoResize',
	                                      'ui.bootstrap',
	                                      'ui.grid.pagination']);

uploadDocument.controller("uploadDocumentController" , function($scope, $http){
	console.log("working");
	  $scope.submitForm = function () {
		  console.log("inside submit form"+$scope.batchID);
	    alert("Send a request to the server: "+$scope.batchID);
	    $http.get('/SearchService?batchId='+$scope.batchID)
	    .then(function (response) {
	    	 $scope.details.data= response.data;
	    	console.log("working after grid");

	    });
	    
	    
	  };
	  
	  console.log("working bef grid");
	  
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
		            	name: 'batchId', 
		                displayName: 'Batch ID'
		            },
		            {
		            	name: 'uploadedOnDate',
		            	displayName: 'Uploaded On'
		            },
		            {
		                name: 'documentsUploaded',
		                displayName: 'Documents Uploaded'
		            },
		             {
		            	 name: 'tpDocUrl', 
		            	 displayName: 'Download Zip File', 
		            	 cellTemplate: '<a ng-href="{{row.entity.tpDocUrl}}" target="_blank" download><img src="images/rar_icon.png" alt="abc" class="pointer"></a>'
		             }
		        ]
		    };
	  
	});
	

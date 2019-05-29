var app = angular.module('app');
app.controller('updateBatchNumberController', function($scope, $http, $timeout) {
	
		$scope.insertBNErrorMessage="";
		$scope.insertBNSuccessMessage="";
		 $scope.updateBNSuccessMessage="";
		 $scope.updateBNErrorMessage="";
	
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
             
             
        /* Insert SCGJ Batch Number*/
             $scope.insertBatchNumberFunc = function(){
            	
            	 var insertUrl = '/insertBatchNumber?batchId='+$scope.batchId+'&scgjBatchNumber='+$scope.insertbatchnumber;
            	 $http.get(insertUrl)
            	 .then(function(response){
            		 $scope.batchId="";
            		 $scope.insertbatchnumber="";
            		 if(response.data == 1){
            			 $scope.insertBNErrorMessage="";
            			 $scope.insertBNSuccessMessage="Batch Number inserted successfully";
            			 
            		 }
            		 else if(response.data==-299){
            			 $scope.insertBNSuccessMessage="";
            			 $scope.insertBNErrorMessage="Batch Number already present , Please update your batch number";
            		 }
            		 else if(response.data == -825)
            			 {
            			 $scope.insertBNSuccessMessage="";
            			 $scope.insertBNErrorMessage="Batch Number already exists, Please enter your unique SDMS Batch Number";
            			 }
            		 
            	 });
             
            	 
            	 $timeout(function() {
            		 $scope.insertBNSuccessMessage="";
        			 $scope.insertBNErrorMessage="";
                  }, 4000);
             }
        
       /* Update SCGJ Batch Number*/
             $scope.updateBatchNumberFunc = function(){

             	
            	 var updateUrl = '/updateBatchNumber?batchId='+$scope.batchIdforUpdate+'&scgjBatchNumber='+$scope.updatebatchnumber;
            	 $http.get(updateUrl)
            	 .then(function(response){
            		 $scope.batchIdforUpdate="";
            		 $scope.updatebatchnumber="";
            		 if(response.data == 1){
            			 $scope.updateBNErrorMessage="";
            			 $scope.updateBNSuccessMessage="Batch Number updated successfully";
            			 
            		 }
            		 else if(response.data==-699){
            			 $scope.updateBNSuccessMessage="";
            			 $scope.updateBNErrorMessage="Batch Number not present, Please insert batch number";
            		 }
            		 
            		 else if(response.data == -677)
           		  {
           			 $scope.updateBNSuccessMessage="";
           			 $scope.updateBNErrorMessage="Batch Number already exists, Please enter your unique SDMS Batch Number";
           			 
           		 }
           		 
           		 else if(response.data == -585)
           			 {
	            			 $scope.updateBNSuccessMessage="";
	            			 $scope.updateBNErrorMessage="SDMS Batch Number cannot be updated";
           			 }
            	 });
             
            	 
            	 $timeout(function() {
            		 $scope.updateBNSuccessMessage="";
        			 $scope.updateBNErrorMessage="";
                  }, 4000);
             
             }
             
       /* Get Updated Batch Number */
             $scope.getUpdatedBatchNumberFunc = function(){
            	 
            	 var searchUrl = '/showDetails?batchId='+$scope.batchIdforSearch;
            	 $http.get(searchUrl)
            	 .then(function(response){
            		 $scope.batchIdforSearch="";
            		 if(response.data == null || response.data == ""){
            			 $scope.searchBNErrorMessage="No Records Found";
            			 $scope.searchBatchNumber.data = [];
            			 
            		 }
            		 else{
            			 $scope.searchBatchNumber.data = response.data;
            		 }
            		
            	 });
             
            	 $timeout(function() {
            		 $scope.searchBNErrorMessage="";
                  }, 4000);
             
             
            	 
            	  
             }
             
       /* Updated batch Numbers Table */    
             $scope.searchBatchNumber ={

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
             				displayName: 'Batch Id'
             			},
             			{
             				name: 'scgjBatchNumber',
             				displayName: 'SDMS Batch Number'
             			},
             			{
             				name: 'updatedOn',
             				displayName: 'Updated On'
             			},
             			{
             				name: 'trainingPartnerName',
             				displayName: 'Updated By'
             			}
             			]
             	
             };
             
             
      
});
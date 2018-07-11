var report = angular.module('app');
	
report.controller('reportController',function($scope, $http) {
	
	var url1='/generateOccupationCertificateReport?trainingPartnerEmail=abc@gmail.com&batchId=';
    console.log("Entered in controller");
    $scope.ngSubmit1=function(batchId){
    console.log("Entered in ngSubmit()");	
	console.log($scope.batchId);
	
	$http.get(url1+$scope.batchId)
	.then(function (response){
		$scope.status= response.data;
		if($scope.status==1 && $scope.batchId!=null){
			
			console.log("Occupation Certificate generated successfully");
		}else{
			console.log("Occupation Certificate not generated");
			
		}	
	});
    }
   
    var url2='/generateAttendanceSheet?trainingPartnerEmail=abc@gmail.com&batchId=';
    console.log("Entered in controller");
    $scope.ngSubmit2=function(batchId){

    console.log("Entered in ngSubmit()");	
	console.log($scope.batchId);
	
	$http.get(url2+$scope.batchId)
	.then(function (response){
		$scope.status= response.data;
		
		console.log($scope.status);
		
		if($scope.status==1 && $scope.batchId!=null){
			
			console.log("Attendance Sheet generated successfully");
		}else{
			console.log("Attendance Sheet not generated");
		
		}
	});
    }
	
    
    
    $scope.onSearch = function (batchId1) {
	var batch=batchId1;
	$http.get('/getReports?trainingPartnerEmail=abc@gmail.com & batchId='+batch)
	.then(function (response) {
	$scope.searchReport.data= response.data;
	 
	  		});
	  	}
    
    var app = angular.module('application', ['ngRoute','ui.grid',
        'ui.grid.edit',
        'ui.grid.cellNav',
        'ui.grid.autoResize',
        'ui.bootstrap',
        'ui.grid.pagination']);



    $scope.searchReport = {
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
    				name: 'batchid', 
    				displayName: 'Batch Id'
    			},
    			{
    				name: 'type',
    				displayName: 'Report Type'
    			},
    			{
    				name: 'generatedOn',
    				displayName: 'Generated On'
    			},
    			{
    				name: 'generatedBy',
    				displayName: 'Generated By'
    			},
    			{
    				name: 'viewReport',	
    				displayName: 'View Report'
    			}

    			]
    			};
    
});
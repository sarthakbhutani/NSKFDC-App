var scgj = angular.module("app");

scgj.controller("updateTargetController" , function($scope, $http){
	
	$scope.errorMessage=false;
	
	 $scope.updatedData = {
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
		            	name: 'nsdcRegNumber', 
		                displayName: 'NSDC Reg. Number',
		             
		            },
		            {
		            	name: 'trainingPartnerName',
		            	displayName: 'Training Partner'
		            },
		            {
		                name: 'targets',
		                displayName: 'Target'
		            },
		            {
		                name: 'targetApprovalDate',
		                displayName: 'Updated On'
		             },
		             
		        ]
		    };
	 
	 $scope.update=function()
	 {
		
		 $http.get("/updateTargets?nsdcRegNumber="+$scope.data.nsdcRegNumber+"&targets="+$scope.data.targets)
			.then(function (response) {	
			console.dir(response.data);
			console.log(response.status);
			if(response.data==1)
			{
			$scope.errorMessage=false;
			$scope.successMessage=true;
			}
		else
			{
			$scope.errorMessage=true;
			$scope.successMessage=false;
			}
			});

	 }
	 
	 $scope.search=function(nsdcRegNumber) {
		 $http.get("/getUpdatedTargets?nsdcRegNumber="+$scope.nsdcRegNumber)
			.then(function (response) {	
				 
				 if(response.data.length==0){
					 $scope.searchError='No Data Found';
				 }
				 else{
				 $scope.updatedData.data=response.data;
				 $scope.searchError='';
				 }
			});
	 }
	
	 /*----------- Grid Height -------------*/
	 $scope.getTableHeight=function(){
		 var rowHeight=30;
		 var headerHeight=30;
		
		 return{
			 height:($scope.updatedData.data.length * rowHeight + headerHeight)+"px"
		 };
	
	 };
});
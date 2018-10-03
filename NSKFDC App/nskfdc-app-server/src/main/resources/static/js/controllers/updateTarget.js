var scgj = angular.module("app");

scgj.controller("updateTargetController" , function($scope, $http, $timeout){

	
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

	 $scope.update=function(nsdcRegNumber,targets)
	 {
		 if($scope.data.nsdcRegNumber==null && $scope.data.targets==null)
			 {
			 $scope.dataundefined='Please enter NSDC Reg. Number and New Target';
			 }
		 else if($scope.data.targets<=0){
			 $scope.dataundefined='Please enter valid target to allocate';
		 }
		 else if($scope.data.targets==undefined)
			{
			$scope.dataundefined='Please enter new target to be allocated';
			}
		 else if($scope.data.nsdcRegNumber==undefined)
			{
			$scope.dataundefined='Please enter NSDC Reg. Number';
			}
		else{
		 $http.get("/updateTargets?nsdcRegNumber="+$scope.data.nsdcRegNumber+"&targets="+$scope.data.targets)
			.then(function (response) {
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
		
		 document.getElementById("FormUpdateTarget").reset();
		}
		 
		 
		 
		 $timeout(function() {
             $scope.errorMessage="";
             $scope.successMessage="";
            $scope.dataundefined="";
          }, 4000);
	 }
	 
	 $scope.search=function(nsdcRegNumber) {
		 if($scope.nsdcRegNumber==undefined)
			{
			$scope.searchundefined='Please enter NSDC Reg. Number';
			}
	 else{
		 $http.get("/getUpdatedTargets?nsdcRegNumber="+$scope.nsdcRegNumber)
			.then(function (response) {
				 
				 if(response.data.length==0){
					 $scope.searchError='No Data Found';
					 $scope.updatedData.data=[];
					 
				 }
				 else{
				 $scope.updatedData.data=response.data;
				 $scope.searchError='';
				 document.getElementById("FormSearchTarget").reset();
				 }
				 
			});
		 
	 }
		 $timeout(function() {
             $scope.searchError="";
             $scope.searchundefined="";
             
          }, 4000);
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
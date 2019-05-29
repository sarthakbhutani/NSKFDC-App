var scgj = angular.module("app");

scgj.controller("generateCredentialsController" , function($scope, $http, $timeout){
	
	$scope.credentialTable= false;
	
	
	/*---------------------------- EYE TOGGLE --------------------------------*/
	$scope.inputTypePassword="password";
	$scope.eyeToggleGenerateCredential=function(){
		if($scope.inputTypePassword=="password"){
			$scope.inputTypePassword="text";
		}
		else{
			$scope.inputTypePassword="password";
		}
	}
	
	
	
	$scope.credentialErrorMessage=false;
	 $scope.detailsData = {
		        enableGridMenus: false,
		        enableSorting: false,
		        enableFiltering: false,
		        enableCellEdit: false,
		        enableColumnMenus: false,
		        enableHorizontalScrollbar: true,
		        enableVerticalScrollbar: 0,
		        paginationPageSizes: [5, 10, 20, 30],
		        paginationPageSize: 10,
		        useExternalPagination: true,

		        columnDefs: [
		            {
		            	name: 'nsdcRegNumber', 
		                displayName: 'NSDC Reg. Number',
		                width: '14%'
		             
		            },
		            {
		            	name: 'trainingPartner',
		            	displayName: 'Training Partner',
		                width: '40%'
		            },
		            {
		                name: 'userEmail',
		                displayName: 'Email ID',
		                width: '40%'
		            },
		            {
		                name: 'password',
		                displayName: 'Password',
		                width: '20%'
		            },
		            {
		                name: 'generatedOn',
		                displayName: 'Generated On',
		                width: '16%'
		             },
		             
		        ]
		    };
	 
	 
	 $scope.check=function()
	 {
		 var generatecredential="/getGenerateCredential";
		 $http({
			 url: generatecredential,
			 method: "POST",
			 data: angular.toJson($scope.data),
			 headers: {
				 'Content-Type': 'application/json'
			 }
		 }).then(function(response){
			if(response.data==-25)
				{
				$scope.credentialErrorMessage=true;
				$scope.credentialSuccessMessage=false;
				}
			else
				{
				$scope.credentialErrorMessage=false;
				$scope.credentialSuccessMessage=true;
				$scope.data=null;
				}
		 });
		 $timeout(function() {
             $scope.credentialErrorMessage="";
             $scope.credentialSuccessMessage="";
          }, 4000);

	 }
	 
	 $scope.search=function(nsdcRegNumber) {
		 $http.get("/SearchService?nsdcRegNumber="+$scope.nsdcRegNumber)
			.then(function (response) {	
				 
				 if(response.data.length==0){
					 $scope.searchError='No Data Found';
					 $scope.detailsData.data=[];
					 }
				 else{
					 $scope.detailsData.data=response.data;
					 $scope.searchError='';
				 }
			});
		 $timeout(function() {
             $scope.searchError="";
          }, 4000);
	 }
	 
	
	 
	 /*----------- Grid Height -------------*/
	 $scope.getTableHeight=function(){
		 var rowHeight=50;
		 var headerHeight=30;
		
		 return{
			 height:($scope.detailsData.data.length * rowHeight + headerHeight)+"px"
		 };
	
	 };

});
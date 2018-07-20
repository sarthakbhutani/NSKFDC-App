var scgj = angular.module("app");

scgj.controller("generateCredentialsController" , function($scope, $http){
	
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
		            	name: 'trainingPartner',
		            	displayName: 'Training Partner'
		            },
		            {
		                name: 'userEmail',
		                displayName: 'Email ID'
		            },
		            {
		                name: 'password',
		                displayName: 'Password',
		                	width:"13%",
		                	cellclass:"passwordfont"
		            },
		            {
		                name: 'generatedOn',
		                displayName: 'Generated On',
		                	width:"13%"
		             },
		             
		        ]
		    };
	 
	 
	 $scope.check=function()
	 {
		 var generatecredential="/getGenerateCredential";
		 console.log("Our credentials"+angular.toJson($scope.data));
		 $http({
			 url: generatecredential,
			 method: "POST",
			 data: angular.toJson($scope.data),
			 headers: {
				 'Content-Type': 'application/json'
			 }
		 }).then(function(response){
			 console.log(response);
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

	 }
	 
	 
	 $http.get('')
	 .then(function (response) {
	 	 $scope.detailsData.data= response.data;

	 });
		   
		    

	 $scope.search=function(nsdcRegNumber) {
		 console.log($scope.nsdcRegNumber);
		 $http.get("/SearchService?nsdcRegNumber="+$scope.nsdcRegNumber)
			.then(function (response) {	
				 
				 if(response.data.length==0){
					 $scope.searchError='No Data Found';
				 }
				 else{
				 $scope.detailsData.data=response.data;
				 $scope.searchError='';
				 }
			});
	 }
	 
	
	 
	 /*----------- Grid Height -------------*/
	 $scope.getTableHeight=function(){
		 var rowHeight=30;
		 var headerHeight=30;
		
		 return{
			 height:($scope.detailsData.data.length * rowHeight + headerHeight)+"px"
		 };
	
	 };

});
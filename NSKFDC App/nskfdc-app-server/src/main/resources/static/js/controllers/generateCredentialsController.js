var scgj = angular.module("app");

scgj.controller("generateCredentialsController" , function($scope, $http){
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
		                displayName: 'Password'
		            },
		            {
		                name: 'generatedOn',
		                displayName: 'Generated On'
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
				}
		 });

	 }
	 
	 
	 $http.get('')
	 .then(function (response) {
	 	 $scope.detailsData.data= response.data;

	 });
		   
		    
	var pwd = document.getElementById('password')
	var eye = document.getElementById('eye')


	 eye.addEventListener('click',togglePass);

	 function togglePass()
	 {
        eye.classList.toggle('active');	    
	     pwd.type == ('password') ? pwd.type = 'text' :
	        pwd.type = 'password';
	 }
	 
//	 $scope.emailadd = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;

	 $scope.search=function(nsdcRegNumber) {
		 console.log($scope.nsdcRegNumber);
		 $http.get("/SearchService?nsdcRegNumber="+$scope.nsdcRegNumber)
			.then(function (response) {	
				 console.log(response.data);
		 $scope.detailsData.data=response.data;
			});
	 }
	 

});
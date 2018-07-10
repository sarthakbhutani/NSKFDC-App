var scgj = angular.module("app");

scgj.controller("generateCredentialsController" , function($scope, $http){
	
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
		            	name: 'NSDC Registration Number', 
		                displayName: 'NSDC Registration Number',
		             
		            },
		            {
		            	name: 'Training Partner',
		            	displayName: 'Training Partner'
		            },
		            {
		                name: 'Email ID',
		                displayName: 'Email ID'
		            },
		            {
		                name: 'Password',
		                displayName: 'Password'
		            },
		            {
		                name: 'Generated on',
		                displayName: 'Generated on'
		             },
		             
		        ]
		    };
		   
		    
	var pwd = document.getElementById('password')
	 var eye = document.getElementById('eye')


	 eye.addEventListener('click',togglePass);

	 function togglePass()
	 {
	        eye.classList.toggle('active');
	        
	        (
	     pwd.type == 'password') ? pwd.type = 'text' :
	        pwd.type = 'password';
	 }

	 $scope.emailadd = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;


});
//Angular module initialization
var app = angular.module('app', ['ngRoute',
								'ui.grid',
							    'ui.grid.edit',
							    'ui.grid.cellNav',
							    'ui.grid.autoResize',
							    'ui.bootstrap',
							    'ui.grid.pagination']);



// Application Configuration
app.config(function($routeProvider, $httpProvider){
	
	$routeProvider.when('/scgj', {
	    templateUrl : 'SCGJ_admin.html',
	    controller : 'SCGJUserController'

	})
	.when('/trainingPartner', {
        templateUrl : 'trainingPartner.html',
        controller : 'trainingPartnerController'

    })
    .otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});


// main controller of the App
app.controller('mainController', function($scope, $http) {
		
//	var pwd = document.getElementById('password')
//	var eye = document.getElementById('eye')
//	
//	eye.addEventListener('click',togglePass);
//	function togglePass()
//	{
//		eye.classList.toggle('active');
//		
//		(pwd.type == 'password') ? pwd.type = 'text' :
//		pwd.type = 'password';
//	}
//	

	
});


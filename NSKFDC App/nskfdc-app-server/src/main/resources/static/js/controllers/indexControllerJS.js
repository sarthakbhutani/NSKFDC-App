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
	
	$routeProvider.when('/adminUser', {
	    templateUrl : 'adminUser.html',
	    controller : 'scgjController'

	})
	.when('/trainingPartner', {
        templateUrl : 'trainingPartner.html',
        controller : 'trainingPartner'

    })
    .otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});


app.controller('mainController',

  function($rootScope, $scope, $http, $location) {
	var self = this;
	console.log("Working");
	
	$scope.credential={username: '',password:''}
	
	var authenticate = function(credentials){
		console.log(credentials);
		var headers = credentials? { authorization : "Basic" + btoa(credentials.username + ":" + credentials.password)}:{};
		
		$http.get('/user', {headers : headers}).then(function(data){
			console.log(data);
//			if(data.name){
//				$rootScope.authenticated = true;
//				$location.path("/adminUser");
//			}
//			else{
//				$rootScope.authenticated= false;
//	//			$location.path("/");
//				console.log("false result");
//			}
//			
		});
	}
	
	authenticate();
	
	
	
	$scope.login=function(){
		console.log("It is working");
		authenticate($scope.credential);
	}
	
});


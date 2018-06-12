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
	    controller : 'mainController'

	})
	.when('/trainingPartner', {
        templateUrl : 'trainingPartner.html',
        controller : 'trainingPartner'

    })
    .otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});

// main controller of the App

app.controller('mainController', function($rootScope, $scope, $http, $location, $route) {
	
	$scope.credential={username: '',password:''}
	
	$scope.
	
	// authenticate function to get user authentication
	
	var authenticate = function(credentials){
		
		var headers = credentials? { authorization : "Basic " + btoa(credentials.username + ":" + credentials.password)}:{};
	
		$http.get('/user', {headers : headers}).then(function(response){
			
			$rootScope.userRole = JSON.stringify(response.data.authorities[0].authority);			
			$rootScope.authenticated= true;
			
			// Routing between templates based on user-role
			
			if($rootScope.userRole == '"admin"'){
				$location.path("/adminUser");
			}
			else if($rootScope.userRole == '"TP"'){
				$location.path("/trainingPartner");
			}
			else
				$location.path("/");
			
			
		}, function(data){
			// function which executes when the user is not authenticated
			$rootScope.authenticated= false;
		});
	}
	
	// Calling authenticate function, if user is already logged in and gave the reload command
	authenticate();
	
	// function for login button
	$scope.login=function(){
		authenticate($scope.credential);
	}
	
	// function for logout action
	$scope.logout = function($route) {
        $rootScope.type = "logout"; 	
        $http.post('logout', {}).finally(function() {
            $rootScope.authenticated= false;
           $location.path("/");
           
        });
	}
	           
});


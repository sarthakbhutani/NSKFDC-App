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
app.controller('mainController', function($rootScope, $scope, $http, $location, $route,$window)  {
		
	$rootScope.mainTemplate=true;
	$scope.invalidErrorMessage="";
	$scope.credential={userEmail: '',password:''}
	
	var nameOfUser = function()
	{
		$http.get('/getNameOfUser')
		.then(function(response){
			
		});
	}
	
	$rootScope.nameOfuser=$window.localStorage.getItem('nameOfUser');
	
	var authenticate = function(credentials){
		
		var headers = credentials? { authorization : "Basic " + btoa(credentials.userEmail + ":" + credentials.password)}:{};
	
		$http.get('/user', {headers : headers}).then(function(response){
		
			
			
			
			$rootScope.userRole = JSON.stringify(response.data.authorities[0].authority);	
			$rootScope.username = response.data.principal.username;	
			$rootScope.mainTemplate=false;
			
			if(credentials){
				
				$http.get('/getNameOfUser').then(function(nameOfUser){
					$rootScope.nameofUser=nameOfUser.data.trainingPartnerName;
					$window.localStorage.setItem('nameOfUser',$rootScope.nameofUser);
					
				},function(error){
					
				});
				
			}

	// Routing between templates based on user-role
			
			if($rootScope.userRole == '"scgj"'){
				$rootScope.mainTemplate=false;
				$location.path("/scgj");
			}
			else if($rootScope.userRole == '"TP"'){
				$rootScope.mainTemplate=false;
				$location.path("/trainingPartner");
			}
			else
				$location.path("/");

		
			
		}, function(data){
			// function which executes when the user is not authenticated
			$rootScope.mainTemplate= true;
			if(credentials){
				$scope.invalidErrorMessage="Invalid Username/ Password";
			}
			else{
				$scope.invalidErrorMessage="";
			}
		});
	}
	
	// Calling authenticate function, if user is already logged in and gave the reload command
	authenticate();
	
	$scope.login= function(){
		authenticate($scope.credential);
		
	}
	
	
	
	// function for logout action
	$scope.logout = function($route) {
        $rootScope.type = "logout"; 	
        $http.post('logout', {}).finally(function() {
        $rootScope.mainTemplate= true;
        $location.path("/");
        	
        });
	}
	
	
	
	
});





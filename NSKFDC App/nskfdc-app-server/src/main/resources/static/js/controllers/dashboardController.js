var scgj = angular.module("app");

scgj.controller("dashboardController" , function($scope, $http){
	
	$http.get('/getNumberOfOngoingTrainings')
    .then(function (response) {
    	$scope.ongoingTrainings = response.data;

    });

	$http.get('/getNumberOfCandidatesTrained')
    .then(function (response) {
    	$scope.candidatesTrained = 2500;//response.data;

    });
	
	$http.get('/getNumberOfTrainingPartners')
    .then(function (response) {
    	 $scope.trainingPartners = response.data;

    });
	
	$http.get('/getNumberOfUpcomingAssessments')
    .then(function (response) {
    	$scope.upcomingAssesments = response.data;

    });

});
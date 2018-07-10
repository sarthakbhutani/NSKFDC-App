var scgj = angular.module("app");


scgj.controller("dashboardController" , function($scope, $http){
		
	$http.get('/getNumberOfOngoingTrainings')
    .then(function (response) {

    	$scope.ongoingTrainings = response.data;
    });

	$http.get('/getNumberOfCandidatesTrained')
    .then(function (response) {
    	$scope.candidatesTrained = response.data;

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

      /* $(document).ready(function() {
         var processed_json = new Array();   
         $.getJSON('/getStateDetails', function(data) {
             // Populate series
             for (i = 0; i < data.length; i++){
                 processed_json.push([data[i].states, data[i].centers]);
             }
          
             // draw chart
             $('#container').highcharts({
             chart: {
                 type: "column",
                 	width: 500  
             },
             title: {
                 text: "Top 5 States with training centers"
             },
             colors:["#927571"],
             xAxis: {
                 type: 'category',
                 allowDecimals: false,
                 title: {
                     text: "States"
                 }
             },
             yAxis: {
                 title: {
                     text: "Centers"
                 }
             },
             series: [{
                 name: 'Centers',
                 data: processed_json
             }]
         }); 
     });
 });

       $(document).ready(function() {
         var processed_json1 = new Array();   
         $.getJSON('/getTotalNumberOfCandidatesTrainedInLast6Months', function(data) {
             // Populate series
             console.log(data.length);
             for (i = 0; i<data.length; i++){
                 processed_json1.push([data[i].month, data[i].count]);
             }
             console.log(processed_json1);
          
             // draw chart
             $('#container1').highcharts({
             chart: {
                 type: "column"
             },
             title: {
                 text: "Candidates Trained"
             },
             xAxis: {
                 type: 'category',
                 allowDecimals: false,
                 title: {
                     text: "Months"
                 }
             },
             yAxis: {
                 title: {
                     text: "Number of Candidates"
                 }
             },
             series: [{
                 name: 'Candidates',
                 data: processed_json1
             }]
         }); 
     });
 });
*/


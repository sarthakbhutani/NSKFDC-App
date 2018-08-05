var scgj = angular.module("app");


scgj.controller("dashboardController" , function($scope, $http){
		
	$http.get('/getNumberOfOngoingTrainings')
    .then(function (response) {
        if(response.data==null)
        	{
        	$scope.ongoingTrainings = 0;
        	}
        
        else{
    	$scope.ongoingTrainings = response.data;
        }
        });

	$http.get('/getNumberOfCandidatesTrained')
    .then(function (response) {
    	if(response.data==null)
    	{
    		$scope.candidatesTrained = 0;
    	}
    
    else{
	   	$scope.candidatesTrained = response.data;
    }
    });
	
	$http.get('/getNumberOfTrainingPartners')
    .then(function (response) {
    	if(response.data==null)
    	{
    		$scope.trainingPartners = 0;
    	}
    
    else{
	   	$scope.trainingPartners = response.data;
    }
    });
	
	$http.get('/getNumberOfUpcomingAssessments')
    .then(function (response) {
    	if(response.data==null)
    	{
    		$scope.upcomingAssesments = 0;
    	}
    
    else{
	
    	$scope.upcomingAssesments = response.data;
    }
    });
	



       $(document).ready(function() {
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
                   
             },
             credits : {
            	 enabled : false
             },
             title: {
                 text: "Top 5 States with maximum training centers"
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
             for (i = 0; i<data.length; i++){
                 processed_json1.push([data[i].month, data[i].count]);
             };
          
             // draw chart
             $('#container1').highcharts({
             chart: {
                 type: "column"
             },
             
             title: {
            	 
                 text: "Candidates Trained In Last 6 Months"
             },
             credits : {
            	 enabled : false
             },
             colors:["#FFA500"],
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
       
       
       $(document).ready(function () {
           var processed_json2 = new Array();  
           $.getJSON('/getshowStateDetailsForMapChart', function (data) {
                    for (i = 0; i < data.length; i++){
                        if(data[i].states=='Puducherry'.toLowerCase()||data[i].states=='Puducherry'){
                            processed_json2.push(['in-py', data[i].centers]);
                        }
                        if(data[i].states=='Lakshadweep'.toLowerCase()||data[i].states=='Lakshadweep'){
                            processed_json2.push(['in-ld', data[i].centers]);
                        }
                        if(data[i].states=='West Bengal'.toLowerCase()||data[i].states=='West Bengal'){
                            processed_json2.push(['in-wb', data[i].centers]);
                        }
                        if(data[i].states=='Odisha'.toLowerCase()||data[i].states=='Odisha'){
                            processed_json2.push(['in-or', data[i].centers]);
                        }
                        if(data[i].states=='Bihar'.toLowerCase()||data[i].states=='Bihar'){
                            processed_json2.push(['in-br', data[i].centers]);
                        }
                        if(data[i].states=='Sikkim'.toLowerCase()||data[i].states=='Sikkim'){
                            processed_json2.push(['in-sk', data[i].centers]);
                        }
                        if(data[i].states=='Chhattisgarh'.toLowerCase()||data[i].states=='Chhattisgarh'){
                            processed_json2.push(['in-ct', data[i].centers]);
                        }
                        if(data[i].states=='Tamil Nadu'.toLowerCase()||data[i].states=='Tamil Nadu'){
                            processed_json2.push(['in-tn', data[i].centers]);
                        }
                        if(data[i].states=='Madhya Pradesh'.toLowerCase()||data[i].states=='Madhya Pradesh'){
                            processed_json2.push(['in-mp', data[i].centers]);
                        }
                        if(data[i].states=='Gujarat'.toLowerCase()||data[i].states=='Gujarat'){
                            processed_json2.push(['in-gj', data[i].centers]);
                        }
                        if(data[i].states=='Goa'.toLowerCase()||data[i].states=='Goa'){
                            processed_json2.push(['in-ga', data[i].centers]);
                        }
                        if(data[i].states=='Nagaland'.toLowerCase()||data[i].states=='Nagaland'){
                            processed_json2.push(['in-nl', data[i].centers]);
                        }
                        if(data[i].states=='Manipur'.toLowerCase()||data[i].states=='Manipur'){
                            processed_json2.push(['in-mn', data[i].centers]);
                        }
                        if(data[i].states=='Arunachal Pradesh'.toLowerCase()||data[i].states=='Arunachal Pradesh'){
                            processed_json2.push(['in-ar', data[i].centers]);
                        }
                        if(data[i].states=='Mizoram'.toLowerCase()||data[i].states=='Mizoram'){
                            processed_json2.push(['in-mz', data[i].centers]);
                        }
                        if(data[i].states=='Tripura'.toLowerCase()||data[i].states=='Tripura'){
                            processed_json2.push(['in-tr', data[i].centers]);
                        }
                        if(data[i].states=='Daman and Diu'.toLowerCase()||data[i].states=='Daman and Diu'){
                            processed_json2.push(['in-dd', data[i].centers]);
                        }
                        if(data[i].states=='Delhi'.toLowerCase()||data[i].states=='Delhi'){
                            processed_json2.push(['in-dl', data[i].centers]);
                        }
                        if(data[i].states=='Haryana'.toLowerCase()||data[i].states=='Haryana'){
                            processed_json2.push(['in-hr', data[i].centers]);
                        }
                        if(data[i].states=='Himachal Pradesh'.toLowerCase()||data[i].states=='Himachal Pradesh'){
                            processed_json2.push(['in-hp', data[i].centers]);
                        }
                        if(data[i].states=='Jammu and Kashmir'.toLowerCase()||data[i].states=='Jammu and Kashmir'){
                            processed_json2.push(['in-jk', data[i].centers]);
                        }
                        if(data[i].states=='Kerala'.toLowerCase()||data[i].states=='Kerala'){
                            processed_json2.push(['in-kl', data[i].centers]);
                        }
                        if(data[i].states=='Karnataka'.toLowerCase()||data[i].states=='Karnataka'){
                            processed_json2.push(['in-ka', data[i].centers]);
                        }
                        if(data[i].states=='Maharashtra'.toLowerCase()||data[i].states=='Maharashtra'){
                            processed_json2.push(['in-mh', data[i].centers]);
                        }
                        if(data[i].states=='Assam'.toLowerCase()||data[i].states=='Assam'){
                            processed_json2.push(['in-as', data[i].centers]);
                        }
                        if(data[i].states=='Andhra Pradesh'.toLowerCase()||data[i].states=='Andhra Pradesh'){
                            processed_json2.push(['in-ap', data[i].centers]);
                        }
                        if(data[i].states=='Meghalaya'.toLowerCase()||data[i].states=='Meghalaya'){
                            processed_json2.push(['in-ml', data[i].centers]);
                        }
                        if(data[i].states=='Punjab'.toLowerCase()||data[i].states=='Punjab'){
                            processed_json2.push(['in-pb', data[i].centers]);
                        }
                        if(data[i].states=='Rajasthan'.toLowerCase()||data[i].states=='Rajasthan'){
                            processed_json2.push(['in-rj', data[i].centers]);
                        }
                        if(data[i].states=='Uttar Pradesh'.toLowerCase()||data[i].states=='Uttar Pradesh'){
                        	
                            processed_json2.push(['in-up', data[i].centers]);
                        }
                        if(data[i].states=='Uttarakhand'.toLowerCase()||data[i].states=='Uttarakhand'){
                            processed_json2.push(['in-ut', data[i].centers]);
                        }
                        if(data[i].states=='Jharkhand'.toLowerCase()||data[i].states=='Jharkhand'){
                            processed_json2.push(['in-jh', data[i].centers]);
                        }
                       if(data[i].states=='Chandigarh'.toLowerCase()||data[i].states=='Chandigarh'){
                            processed_json2.push(['in-ch', data[i].centers]);
                        }
                        if(data[i].states=='Dadra and Nagar Haveli'.toLowerCase()||data[i].states=='Dadra and Nagar Haveli'){
                            processed_json2.push(['in-dn', data[i].centers]);
                        }
                        if(data[i].states=='Andaman and Nicobar Islands'.toLowerCase()||data[i].states=='Andaman and Nicobar Islands'){
                            processed_json2.push(['in-an', data[i].centers]);
                        }
                        if(data[i].states=='Telangana'.toLowerCase()||data[i].states=='Telangana'){
                            processed_json2.push(['in-tg', data[i].centers]);
                        }
                        
                        
                    }

                    var mapData = Highcharts.geojson(Highcharts.maps["countries/in/custom/in-all-disputed"]);

                    $('#container2').highcharts('Map', {
                    chart : {
                        borderWidth : 0,
                        map: 'countries/in/custom/in-all-disputed'
                    },
                    credits : {
                   	 enabled : false
                    },
                    title: {
                        text: 'Number of Training Centers in Each State'
                    },

                    mapNavigation: {
                        enabled: true
                    },
                        legend: {
                        enabled: true,
                         
                            },
                    series : [{
                        name: 'Number of Centers',
                        mapData: mapData,
                        type:'map',
                        data: processed_json2,
                          states: {
                                   hover: {
                                           color: '#BADA55'
                                          }
                                  },
                          dataLabels: {
                                        enabled: true,
                                        format: '{point.name}'
                                      }
                    }]
                });

            })
        });

});


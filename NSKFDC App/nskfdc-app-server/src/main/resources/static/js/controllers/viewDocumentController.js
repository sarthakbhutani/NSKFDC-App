var scgj = angular.module("app");

scgj.controller("viewDocumentController" , function($scope, $http){
	
	$scope.errorMessage="";
	
    $scope.uploadedDocument = {
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
        	name: 'batchId', 
            displayName: 'batchId'
            	
        },
        {
        	name: 'trainingPartnerName',
        	displayName: 'Training Partner'
        },
        {
            name: 'uplodedOn',
            displayName: 'Uploaded On'
        },
        
        {
       	 name: 'tpDocUrl', 
       	 displayName: 'Download Zip File', 
       	 cellTemplate: '<a ng-href="{{row.entity.tpDocUrl}}" target="_blank" download><img src="images/zipImage.png" class="pointer"></a>'
        }
        
        
        ]
};





$scope.searchDocuments=function(){
	console.log("working");
    if($scope.batchId!=null){
	console.log($scope.tpName);
	
	$http.get('/getTrainingPartnerDetailForSearchbatchId?tpName='+$scope.tpName+'&batchId='+$scope.batchId)
		.then(function (response) {	
		

		 $scope.uploadedDocument.data= response.data;
		 console.log("batchid");

	});
    }
    else{
	$http.get('/getTrainingPartnerDetailForSearchscgjBtNumber?tpName='+$scope.tpName+'&scgjBtNumber='+$scope.scgjBtNumber)
	.then(function (response) {	
		

		 $scope.uploadedDocument.data= response.data;
		 console.log("scgj");

	});
    }
	if($scope.tpName==null){
		$scope.errorMessage="Please enter Training Partner Name";
		
	}
	else{
		$scope.errorMessage="";
		if(($scope.batchId==null)&&($scope.scgjBtNumber==null)){
			console.log("working");
    		$scope.errorMessage="Please enter Batch Id or SCGJ Batch Number";
    		}
    	else{
    		$scope.errorMessage="";
    		
    	}
	}
	
	
}

});
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
       	 cellTemplate: '<img src="images/zipImage.png" alt="abc" ng-click="grid.appScope.DownloadZipFileForBatchId()" ng-click="grid.appScope.DownloadZipFileForSearchscgjBtNumber()"class="pointer"></a>'
        }
        
        
        ]
};
  //Zip download start FileForBatchId
	  $scope.DownloadZipFileForBatchId = function(){
		  console.log("working in batch id");
		  var url='/downloadZipFileForBatchId?tpName='+$scope.tpName+'&batchId='+$scope.batchId;
		  console.log("This is downloading")
		  $http.get(url, { responseType : 'arraybuffer' })
		  .then(function(response){
			  console.log("done"+$scope.batchID + response.data);
			  var zipFile = new Blob([response.data], { type : 'application/zip' })
			  var downloadURL = URL.createObjectURL(zipFile);
			  var link = document.createElement('a');
			  //from here:: move the rest from up
			  link.href = downloadURL;
			  link.download =$scope.tpName +$scope.batchID + '.zip';
			  document.body.appendChild(link);
			  link.click();
		  });
	  };
	  //zip download end

	  //zip download start ForSearchscgjBtNumber
	  $scope.DownloadZipFileForSearchscgjBtNumber = function(){
		  console.log("working working in SCGJbatch number");
		  var url='/downloadZipFileForSearchscgjBtNumber?tpName='+$scope.tpName+'&scgjBtNumber='+$scope.scgjBtNumber;
		  console.log("This is downloading")
		  $http.get(url, { responseType : 'arraybuffer' })
		  .then(function(response){
			  console.log("done"+$scope.batchID + response.data);
			  var zipFile = new Blob([response.data], { type : 'application/zip' })
			  var downloadURL = URL.createObjectURL(zipFile);
			  var link = document.createElement('a');
			  //from here:: move the rest from up
			  link.href = downloadURL;
			  link.download =$scope.tpname +$scope.scgjBtNumber + '.zip';
			  document.body.appendChild(link);
			  link.click();
		  });
	  };
	  /*zip download end*/


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
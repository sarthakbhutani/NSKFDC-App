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
            displayName: 'Batch Id'
            	
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
       	 name: 'zipFileLink', 
       	 displayName: 'Download Zip File' ,
       	 cellTemplate: '<img src="images/rar_icon_noBackground.png" alt="Zip Icon" ng-click="grid.appScope.DownloadZipFileForBatchId()" class="pointer">'
        }
        
        
        ]
};
  //Zip download start FileForBatchId
	  $scope.DownloadZipFileForBatchId = function(){
		  if($scope.scgjBtNumber==null){
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
			  
			  link.download =$scope.tpName +$scope.batchId + '.zip';
			  document.body.appendChild(link);
			  link.click();
			  
			  if(response.data.length==0){
					 $scope.zipError='File not found';
				 }
				 else{
				 $scope.uploadedDocument.data=response.data;
				 $scope.zipSuccess='File downloaded successfully';
				 }
		  });
	  }
		  else{
			  console.log("working working in SCGJbatch number");
			  var url='/downloadZipFileForSearchscgjBtNumber?tpName='+$scope.tpName+'&scgjBtNumber='+$scope.scgjBtNumber;
			  console.log("This is downloading")
			  $http.get(url, { responseType : 'arraybuffer' })
			  .then(function(response){
				  
				  if(response.data.length==0){
						 $scope.zipError='File not found';
					 }
					 else{
					 $scope.uploadedDocument.data=response.data;
					 $scope.zipSuccess='File downloaded successfully';
					 }
				  
				  console.log("done"+$scope.scgjBtNumber + response.data);
				  var zipFile = new Blob([response.data], { type : 'application/zip' })
				  var downloadURL = URL.createObjectURL(zipFile);
				  var link = document.createElement('a');
				  //from here:: move the rest from up
				  link.href = downloadURL;
				  link.download =$scope.tpName +$scope.scgjBtNumber + '.zip';
				  document.body.appendChild(link);
				  link.click();
			  });
		  }
	  };
	  //zip download end

	 


$scope.searchDocuments=function(){
	//console.log("working");
    if($scope.batchId!=null){
	console.log($scope.tpName);
	
	$http.get('/getTrainingPartnerDetailForSearchbatchId?tpName='+$scope.tpName+'&batchId='+$scope.batchId)
		.then(function (response) {	
			console.log(response);
			if(response.data.length==0){
				 $scope.searchError='No Data Found';
			 }
			 else{
			 $scope.uploadedDocument.data=response.data;
			 $scope.searchError='';
			 }

		

		 $scope.uploadedDocument.data= response.data;
		 console.log("batchid");

	});
    }
    else{
    	console.log($scope.scgjBtNumber);
	$http.get('/getTrainingPartnerDetailForSearchscgjBtNumber?tpName='+$scope.tpName+'&scgjBtNumber='+$scope.scgjBtNumber)
	.then(function (response) {
		if(response.data.length==0){
			 $scope.searchError='No Data Found';
		 }
		 else{
		 $scope.uploadedDocument.data=response.data;
		 $scope.searchError='';
		 }

		
		

		 $scope.uploadedDocument.data= response.data;
		 console.log("scgj");
		 

	});
    }
	if($scope.tpName==null){
		document.getElementById("errorMessage").innerHTML="Please enter Training Partner Name";
		
	}
	else{
		
		if(($scope.batchId==null)&&($scope.scgjBtNumber==null)){
			
			document.getElementById("errorMessageBatch").innerHTML="Please enter BatchId or SCGJ Batch Number";
    		}

	}
		
}

/*----------- Grid Height -------------*/
$scope.getTableHeight=function(){
	 var rowHeight=30;
	 var headerHeight=30;
	
	 return{
		 height:($scope.uploadedDocument.data.length * rowHeight + headerHeight)+"px"
	 };

};
});
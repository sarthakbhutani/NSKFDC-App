var scgj = angular.module("app");

scgj.controller("viewDocumentController" , function($scope, $http, $timeout){
	
	$scope.errorMessage="";
	
//	Search Document functionality
	
	$scope.searchDocuments=function(){
	    if($scope.batchId!=null && $scope.batchId!="" && $scope.batchId!=undefined && $scope.tpName!=null && $scope.tpName!="" && $scope.tpName!=undefined){
		
		$http.get('/getTrainingPartnerDetailForSearchbatchId?tpName='+$scope.tpName+'&batchId='+$scope.batchId)
			.then(function (response) {
				if(response.data.length==0){
					 $scope.searchError='No Data Found';
				 }
				 else{
				 $scope.uploadedDocument.data=response.data;
				 $scope.searchError='';
				 }

		});
	    }
	    else if($scope.scgjBtNumber!=null && $scope.scgjBtNumber!="" && $scope.scgjBtNumber!=undefined && $scope.tpName!=null && $scope.tpName!="" && $scope.tpName!=undefined){
		$http.get('/getTrainingPartnerDetailForSearchscgjBtNumber?tpName='+$scope.tpName+'&scgjBtNumber='+$scope.scgjBtNumber)
		.then(function (response) {
			if(response.data.length==0){
				 $scope.searchError='No Data Found';
				 $scope.uploadedDocument.data=[];
			 }
			 else{
			 $scope.uploadedDocument.data=response.data;
			 $scope.searchError='';
			 }

			 

		});
	    }
		else{
			
			if(($scope.batchId==null || $scope.batchId=="" || $scope.batchId==undefined)&&($scope.scgjBtNumber==null || $scope.scgjBtNumber=="" || $scope.scgjBtNumber==undefined)){
				
				$scope.errorMessageBatch="Please enter BatchId or SCGJ Batch Number";
	    		}
			else{
				$scope.errorMessageBatch="Please enter valid details";
			}

		}
	    $timeout(function() {
	        $scope.searchError="";
	        $scope.errorMessageBatch="";
	     }, 3000);
	}
	
//	Uploaded Document table code starts
	
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
//	Uploaded Document table code ends
    
  //Zip download start FileForBatchId and SCGJ Batch Number
	  $scope.DownloadZipFileForBatchId = function(){
		  if($scope.batchId!=null && $scope.batchId!=""  && $scope.batchId!=undefined && $scope.tpName!=null && $scope.tpName!="" && $scope.tpName!=undefined){
		  var url='/downloadZipFileForBatchId?tpName='+$scope.tpName+'&batchId='+$scope.batchId;
		  $http.get(url, { responseType : 'arraybuffer' })
		  .then(function(response){
			  
			  
			  if(response.data.byteLength!=0){
				  var zipFile = new Blob([response.data], { type : 'application/zip' })
				  var downloadURL = URL.createObjectURL(zipFile);
				  var link = document.createElement('a');
				  link.href = downloadURL;
				  link.download =$scope.tpName +$scope.batchId + '.zip';
				  document.body.appendChild(link);
				  link.click();
				 $scope.uploadedDocument.data=response.data;
				 $scope.zipSuccess='File downloaded successfully';
					
				 }
				 else{
				
					 $scope.zipError='File not found';	
				 }
		  });
	  }
		  else if($scope.scgjBtNumber!=null && $scope.scgjBtNumber!="" && $scope.scgjBtNumber!=undefined && $scope.tpName!=null && $scope.tpName!="" && $scope.tpName!=undefined){
			  var url='/downloadZipFileForSearchscgjBtNumber?tpName='+$scope.tpName+'&scgjBtNumber='+$scope.scgjBtNumber;
			  $http.get(url, { responseType : 'arraybuffer' })
			  .then(function(response){
				  
				  if(response.data.byteLength!=0){
						  var zipFile = new Blob([response.data], { type : 'application/zip' })
						  var downloadURL = URL.createObjectURL(zipFile);
						  var link = document.createElement('a');
						  //from here:: move the rest from up
						  link.href = downloadURL;
						  link.download =$scope.tpName +$scope.scgjBtNumber + '.zip';
						  document.body.appendChild(link);
						  link.click();
						  $scope.uploadedDocument.data=response.data;
						  $scope.zipSuccess='File downloaded successfully';
						
					 }
					 else{
						 $scope.zipError='File not found';
					 }
				  
				
			  });
		  }
		  else{
			  $scope.zipError='Please fill all the details required';  
		  }
		  $timeout(function() {
		        $scope.zipError="";
		        $scope.zipSuccess=""
		     }, 3000);
	  };
	  //zip download end


/*----------- Grid Height -------------*/
$scope.getTableHeight=function(){
	 var rowHeight=30;
	 var headerHeight=30;
	
	 return{
		 height:($scope.uploadedDocument.data.length * rowHeight + headerHeight)+"px"
	 };

};
});
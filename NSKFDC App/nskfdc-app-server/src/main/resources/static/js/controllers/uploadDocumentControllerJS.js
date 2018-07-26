var uploadDocument = angular.module('app');

uploadDocument.controller("uploadDocumentController" , function($scope, $http){
	
	   $scope.ids = [];
	    $http.get('/getBatchIdDet')
		    .then(function(response) {
		        let dt = response.data;
		        console.log(dt);
		        for (i in dt) {
		            $scope.ids.push(response.data[i].batchId);
		            console.log(dt[i]);
		        }
		    });
	
	 $scope.submitForm = function () {
		  
		  if($scope.batchID==null)
			  {
			    
			    document.getElementById("searchBox").innerHTML="Please enter the Batch Id";
			    
			  }
		  
		  else if($scope.batchID!=null)
			  {
				  
			    $http.get('/searchDocument?batchId='+$scope.batchID)
			    .then(function (response) {
			    	 if(response.data.length==0){
						 $scope.searchdetailError='No Data Found';
					 }
					 else{
						 $scope.details.data= response.data;
					 $scope.searchdetailError='';
					 }
			    	
			    	 
			    	 
			    	 
	    });
	    
	  }
			
			 
	    
	  };

	  

	  $scope.details = {
		        enableGridMenus: false,
		        enableSorting: false,
		        enableFiltering: false,
		        enableCellEdit: false,
		        enableColumnMenus: false,
		        enableVerticalScrollbar: 0,
		        paginationPageSizes: [5, 10, 20, 30],
		        paginationPageSize: 10,
		        useExternalPagination: true,
//		        rowHeight:70,
//		        enableColumnResizing: true,
		        columnDefs: [
		            {
		            	name: 'batchId',  width: '17%', 
		                displayName: 'Batch ID'
		            },
		            {
		            	name: 'dateUploaded', width: '17%', 
		            	displayName: 'Uploaded On'
		            },
		            {
		                name: 'documentsUploaded', width: '117%', 
		                displayName: 'Documents Uploaded'
		            },
		             {
		            	 name: 'zipFileLink',  width: '19%', 
		            	 displayName: 'Download Zip File', 
//		            	 cellTemplate: '<a ng-href="file:///D:/sarthak/testZIp/test1.zip" target="_blank" download="test1.zip"><img src="images/rar_icon.png" alt="abc" class="pointer"></a>'
		            	 cellTemplate: '<img src="images/rar_icon_noBackground.png" alt="Zip Icon" ng-click="grid.appScope.downloadZip()" class="pointer">'
				             
		            }
		        ]
//	  ,enableColumnResizing: true
		    };
	  	  
	  //zip download start
	  $scope.downloadZip = function(){
		  var url='/downloadZipFileService?batchId='+$scope.batchID;
		  console.log("This is downloading")
		  $http.get(url, { responseType : 'arraybuffer' })
		  .then(function(response){
			  console.log("done"+$scope.batchID + response.data);
			  var zipFile = new Blob([response.data], { type : 'application/zip' })
			  var downloadURL = URL.createObjectURL(zipFile);
			  var link = document.createElement('a');
			  //from here:: move the rest from up
			  link.href = downloadURL;
			  link.download = $scope.batchID + '.zip';
			  document.body.appendChild(link);
			  link.click();
		  });
	  };

				 

	  
	  $scope.getTableHeight = function(){
			var rowHeight=30;
			var headerHeight =30;
			return{
				height:($scope.details.data.length* rowHeight + headerHeight) +"px"
			};
		  };
		  
		  var content = 'file content for example';
		  var blob = new Blob([ content ], { type : 'text/plain' });
		  $scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
		  
		  
		  $scope.submitForm1=function(){
				if($scope.myVar=='fbr'){
					$http.get("/getScgjDetails?batchId="+$scope.myVar2+"&scgjBatchNumber="+$scope.myVar3)
					.then(function (response){
						console.log("working5");
						$scope.status= response.data;
						if($scope.status==1){
							document.getElementById("successful").innerHTML="Done";	
							document.getElementById("error_msg1").innerHTML="";	
							
							
						}
					
					
						else{
							console.log("working10");
							 document.getElementById("error_msg1").innerHTML="Please Enter the Valid Details";
							 //document.getElementById("error_msg2").innerHTML="Invalid ScgjID";
							 document.getElementById("successful").innerHTML="";
							
						}          
						
					
					});
				}
				else{
					if($scope.myVar!=null){
					$http.get("/getBatchDetails?batchId="+$scope.myVar2)
					.then(function (response){
						//console.log("working5");
						$scope.status= response.data;
						if($scope.status==1){
							document.getElementById("error_msg1").innerHTML="";	
							document.getElementById("successful").innerHTML="Done";
							
						}
						else{
							//console.log("working10");
							 document.getElementById("error_msg1").innerHTML="Select Valid BatchId";
							 document.getElementById("successful").innerHTML="";
							
				               
						}
					
					});
				}
				}
			};
			/* $scope.enable = function() {
			        console.log("inside function " + $scope.value);
			        $scope.isDisabled = false;
			 };*/
			 
			 
			
				  


});


//	});
	
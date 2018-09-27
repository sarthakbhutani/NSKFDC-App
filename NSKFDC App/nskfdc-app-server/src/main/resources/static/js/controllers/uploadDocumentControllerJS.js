var uploadDocument = angular.module('app');

uploadDocument.controller("uploadDocumentController" , function($scope, $http, uploadFile, $timeout){

	$scope.ids = [];
	$scope.batch = {};
	$http.get('/getBatchIdDet')
	.then(function(response) {
		let dt = response.data;
		for (i in dt) {
			$scope.ids.push(response.data[i].batchId);

		}
	});

	$scope.submitForm = function () {
		$http.get('/searchDocument?batchId='+$scope.batchID)
		.then(function (response) {
			if(response.data.length==0){
				$scope.searchdetailError='No Data Found';
				$scope.details.data=[];
			}
			else{
				$scope.details.data= response.data;
				$scope.searchdetailError='';
			}	 

		}, function(error){
			$scope.searchdetailError = 'An error occured while searching for batch' +$scope.batchID 
		});
		
		$timeout(function(){
			$scope.searchdetailError = "";
		},6000)
	};

	$scope.uploadFile = function(){

		var file = $scope.dataImport.upload;
		var fileType = $scope.fileType;
		var batchId = $scope.batchId;
		var scgjBatchNumber = $scope.batch.scgjBatchNumber;
		console.log(scgjBatchNumber);
		var uploadUrl = "/uploadFile";
		var uploadedFile = uploadFile.uploadFileToUrl(file, uploadUrl, fileType, batchId, scgjBatchNumber);


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
			columnDefs: [
			             {
			            	 name: 'batchId',  width: '11%', 
			            	 displayName: 'Batch ID'
			             },
			             {
			            	 name: 'dateUploaded', width: '12%', 
			            	 displayName: 'Uploaded On'
			             },
			             {
			            	 name: 'documentsUploaded', width: '65%', 
			            	 displayName: 'Documents Uploaded'
			             },
			             {
			            	 name: 'zipFileLink',  width: '12%', 
			            	 displayName: 'Download Zip File', 
			            	 cellTemplate: '<img src="images/rar_icon_noBackground.png" alt="Zip Icon" ng-click="grid.appScope.downloadZip()" class="pointer">'

			             }
			             ]

	};

	//zip download start
	$scope.downloadZip = function(){
		var url='/downloadZipFileService?batchId='+$scope.batchID;

		$http.get(url, { responseType : 'arraybuffer' })
		.then(function(response)
				{
			if(response.data.byteLength!=0)
			{
				var zipFile = new Blob([response.data], { type : 'application/zip' })
				var downloadURL = URL.createObjectURL(zipFile);
				var link = document.createElement('a');
				link.href = downloadURL;
				link.download = $scope.batchID + '.zip';
				document.body.appendChild(link);
				link.click();
			}
			else
			{
				$scope.downloadError = "File generated is not a valid zip file"
			}

				}, function(error)
				{
					$scope.downloadError = "An error occured while downloading file."

				});
		$timeout(function(){
			$scope.downloadError = "";
		},6000)
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
		if($scope.fileType=='fbr'){
			$http.get("/getScgjDetails?batchId="+$scope.batchId+"&scgjBatchNumber="+$scope.scgjBatchNumber)
			.then(function (response){
				$scope.status= response.data;
				if($scope.status==1){
					document.getElementById("successful").innerHTML="Done";	
					document.getElementById("error_msg1").innerHTML="";	


				}


				else{
					document.getElementById("error_msg1").innerHTML="Please Enter the Valid Details";

					document.getElementById("successful").innerHTML="";

				}          


			});
		}
		else{
			if($scope.fileType!=null){
				$http.get("/getBatchDetails?batchId="+$scope.batchId)
				.then(function (response){
					$scope.status= response.data;
					if($scope.status==1){
						document.getElementById("error_msg1").innerHTML="";	
						document.getElementById("successful").innerHTML="Done";

					}
					else{
						document.getElementById("error_msg1").innerHTML="Select Valid BatchId";
						document.getElementById("successful").innerHTML="";


					}

				});
			}
		}
	};







});




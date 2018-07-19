var tp = angular.module("app");

tp.directive('demofileModel', ['$parse', function ($parse) {
    return {
       restrict: 'A',
       link: function(scope, element, attrs) {
          var model = $parse(attrs.fileModel);
          var modelSetter = model.assign;
          
          element.bind('change', function(){
             scope.$apply(function(){
                modelSetter(scope, element[0].files[0]);
             });
          });
       }
    };
 }]);

tp.service('fileUploadService', function ($http, $q) {
	this.uploadFileToUrl = function (file, uploadUrl) {
        //FormData, object of key/value pair for form fields and values
        var fileFormData = new FormData();
        fileFormData.append('file', file);

        var deffered = $q.defer();
        $http.post(uploadUrl, fileFormData, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}

        }).success(function (response) {
            deffered.resolve(response);

        }).error(function (response) {
            deffered.reject(response);
        });

        return deffered.promise;
    } 
});
    
    
    this.uploadFileWithKey = function(file, uploadUrl,keyFile, keyData, keyDataValue){
        var fd = new FormData();
            
            fd.append(keyFile, file);
            fd.append(keyData,keyDataValue);
       
    var method = "POST";
       $http.post(uploadUrl, fd, {
          transformRequest: angular.identity,
          headers: {'Content-Type': undefined}
       })
       .then(function(response){
            //console.log("The file was uploaded successfully");
            //console.log(response);
       },function errorCallback(response){
            //console.log(JSON.stringify(response.data));
       });
        }

    
 }]);


tp.controller("generateBatchReportController",[ '$scope','$http','fileUpload' , function($scope, $http, fileUpload){
	
	
	$scope.uploadFile = function(){
		function () {
            var file = $scope.myFile;
            var uploadUrl = "/generateBatchReport", //Url of webservice/api/server
                promise = fileUploadService.uploadFileToUrl(file, uploadUrl);
 
            promise.then(function (response) {
                $scope.serverResponse = response;
            }, function () {
                $scope.serverResponse = 'An error has occurred';
            })
        }
     };
	
	
	
	
	$scope.details = {
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
 	            	name: 'Id', 
 	                displayName: 'Batch Id'
 	                	
 	            },
 	            {
 	            	name: 'Date',
 	            	displayName: 'Uploaded On'
 	            },
 	            {
 	                name: 'Name',
 	                displayName: 'Uploaded By'
 	            },
 	            {
 	                name: 'Download',
 	                displayName: 'Download Zip File.'
 	            }
 	            
 	           
 	        ]
 	    
     };
 	    
	    
 		   var url='/getBatchIdDetails';
      	 
           //$scope.ngBlur = function () {
          	
          	$scope.ids = [];
          	
                  $http.get(url)
                  .then(function (response) {
                	  
                  	let dt = response.data;	
                  	for(i in dt){
                  		$scope.ids.push(response.data[i].batchId); 
                  	}
                  	let length=$scope.ids.length;
                  	console.log(length);
              	});
                  
              
              
           $scope.generateBatchReport = function(batchId,batchnumber){
           
           var url2='/
';
           
           $http.get(url2+$scope.batchId+'&batchnumber='+$scope.batchnumber, { responseType : 'arraybuffer' })
           .then(function (response){
           let data1=response.data;
           console.log(data1);
           if(response.data.byteLength!=0)
   			{
	   			
	   			var pdfFile = new Blob([response.data], { type : 'application/pdf' })
	   			var downloadURL = URL.createObjectURL(pdfFile);
	   			var link = document.createElement('a');
	   			link.href = downloadURL;
	   			link.download = "Final Batch Report.pdf"
	   			document.body.appendChild(link);
	   			link.click();
   			}
   		else{
   			$window.alert("Invalid");
   			}
           });
          
           };
           
          
           
          
}]);
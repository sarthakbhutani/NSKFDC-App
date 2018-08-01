uploadDocument.service('uploadFile', ['$http', function ($http) {
    this.uploadFileToUrl = function (file, uploadUrl,fileType, batchId, scgjBatchNumber ) {
        var fd = new FormData();
        console.log('File is :'+file);
        var file = document.getElementById('file').files[0];
        fd.append('fileType', fileType);
        fd.append('file', file);
        fd.append('batchId', batchId);
        fd.append('scgjBatchNumber', scgjBatchNumber);
        //fd.append('file',  document.getElementById('csvFile').files[0]);
        console.log('fileType:---'+fileType);
        console.log('batchId:---'+batchId);
        console.log('scgjBatchNumber:---'+scgjBatchNumber);
        console.log('FILE-----'+file);
                
        $http({
        	method: 'POST',
        	url: uploadUrl,
            data: fd,
            headers: {'Content-Type': undefined},
            
            transformRequest: angular.identity,
            transformResponse: function (data) {
            	//console.log(data);
            	thisIsResponse=data;
            	//console.log(thisIsResponse);
            	return data;
            }
        }).then(function(response) {
            
//        	//console.log("response of success -----");
//        	//console.log(thisIsResponse);
//        	responseOfUpload(thisIsResponse);
//        	 
//            return response.data;
//           
//            
//        }, function errorCallback(response) {
//        	//console.log("Error in receiving response from backend------" +response);
//            //console.log('Error: '+response);
            return response.data;
         });
        
    }
}]);
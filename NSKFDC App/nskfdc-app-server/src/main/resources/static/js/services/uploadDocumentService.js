uploadDocument.service('uploadFile', ['$http', function ($http, $timeout) {
    this.uploadFileToUrl = function (file, uploadUrl,fileType, batchId, scgjBatchNumber ) {
        var fd = new FormData();
//        console.log('File is :'+file);
        var file = document.getElementById('file').files[0];
        fd.append('fileType', fileType);
        fd.append('file', file);
        fd.append('batchId', batchId);
        fd.append('scgjBatchNumber', scgjBatchNumber);
        //fd.append('file',  document.getElementById('csvFile').files[0]);
//        console.log('fileType:---'+fileType);
//        console.log('batchId:---'+batchId);
//        console.log('scgjBatchNumber:---'+scgjBatchNumber);
//        console.log('FILE-----'+file);
                
        $http({
        	method: 'POST',
        	url: uploadUrl,
            data: fd,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity,
            transformResponse: [function (data) {
            	thisIsResponse=data;
            	return data;
          
            }]
		}).then(function(response) {
			if(thisIsResponse=="File Uploaded Successfully")
				{
				document.getElementById("Success").innerHTML=thisIsResponse;
				}
			else{
        	document.getElementById("Error").innerHTML=thisIsResponse;
			}
			setTimeout(function(){
				document.getElementById("Success").innerHTML="";
				document.getElementById("Error").innerHTML
			},4000);
			
            return response.data;
            
        }, function errorCallback(response) {
        	alert('Unable to upload try again later');
         });
        
        
    }

}]);
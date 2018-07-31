uploadDocument.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function (file, uploadUrl,myVar, myVar2, myVar3 ) {
        var fd = new FormData();
        console.log('File is :'+file);
        var file = document.getElementById('file').files[0];
        fd.append('fileType', myVar);
        fd.append('file', file);
        fd.append('batchId', myVar2);
        fd.append('batchNo', myVar3);
        //fd.append('file',  document.getElementById('csvFile').files[0]);
        console.log('myVar:---'+myVar);
        console.log('myVar2:---'+myVar2);
        console.log('myVar3:---'+myVar3);
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
            
        	//console.log("response of success -----");
        	//console.log(thisIsResponse);
        	responseOfUpload(thisIsResponse);
        	 
            return response.data;
           
            
        }, function errorCallback(response) {
        	//console.log("Error in receiving response from backend------" +response);
            //console.log('Error: '+response);
            return response.data;
         });
        
    }
}]);
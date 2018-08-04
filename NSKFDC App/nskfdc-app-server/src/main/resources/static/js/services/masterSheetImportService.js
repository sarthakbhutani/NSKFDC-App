app.service('fileUpload', ['$http', function ($http) {
	this.uploadFileToUrl = function (file, importUrl, batchId ) {
		var fd = new FormData();

        fd.append('file', file);
        fd.append('batchId', batchId);

        
        $http({
        	method: 'POST',
        	url: importUrl,
            data: fd,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity,
            transformResponse: [function (data) {
            	thisIsResponse=data;
            	return data;
          
            }]
            
        }).then(function(response) {
        	var errorMessage = thisIsResponse;
			if(thisIsResponse=="File Uploaded Successfully")
				{
				document.getElementById("importExcelMessage").innerHTML=errorMessage;
				document.getElementById("importExcelMessage").style.color = "green";
				}
			else{
	        	document.getElementById("importExcelMessage").innerHTML=errorMessage;
	        	document.getElementById("importExcelMessage").style.color = "red";
			}
			setTimeout(function(){
				document.getElementById("importExcelMessage").innerHTML=""
					},4000);
            return response.data;
           
        
        	
	});

}
}]);
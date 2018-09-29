app.service('fileUpload', ['$http', function ($http) {
	this.uploadFileToUrl = function (file, importUrl, batchId ) {
		var fd = new FormData();
        fd.append('file', file);
        fd.append('batchId', batchId);
        document.getElementById("rollinguploadCandidateSheetGif").style.display="block";
        
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
        	document.getElementById("rollinguploadCandidateSheetGif").style.display="none";
        	var errorMessage = thisIsResponse;
			if(thisIsResponse=="File Uploaded Successfully")
				{
				document.getElementById("rollinguploadCandidateSheetGif").style.display="none";
				document.getElementById("importExcelMessage").innerHTML=errorMessage;
				document.getElementById("importExcelMessage").style.color = "green";
				}
			else{
				document.getElementById("rollinguploadCandidateSheetGif").style.display="none";
	        	document.getElementById("importExcelMessage").innerHTML=errorMessage;
	        	document.getElementById("importExcelMessage").style.color = "red";
			}
			setTimeout(function(){
				document.getElementById("importExcelMessage").innerHTML="";
				document.getElementById("rollinguploadCandidateSheetGif").style.display="none";
					},7000);
            return response.data;
           
        
        	
	});

}
}]);
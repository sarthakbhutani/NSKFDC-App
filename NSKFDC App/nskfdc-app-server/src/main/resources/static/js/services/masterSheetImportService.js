app.service('fileUpload', ['$http', function ($http) {
	this.uploadFileToUrl = function (file, importUrl, batchId ) {
		var fd = new FormData();
//        console.log('File is :'+file);
        fd.append('file', file);
        fd.append('batchId', batchId);
//        console.log('batchId:---'+batchId);
//        console.log('FILE-----'+file);
        
        $http({
        	method: 'POST',
        	url: importUrl,
            data: fd,
            headers: {'Content-Type': undefined},
            
        }).then(function(response) {
	});

}
}]);
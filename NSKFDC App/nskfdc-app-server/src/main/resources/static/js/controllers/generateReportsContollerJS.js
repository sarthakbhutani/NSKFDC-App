var report = angular.module('app');
	
report.controller('reportController',function($scope, $http, $timeout) {
	
	
    $scope.Submit1=function(batchId){
    	if($scope.batchId==null)
    		{
    		document.getElementById("error_msg").innerHTML="Please enter Batch ID";
    		}
    	else{
    	var url='/generateOccupationCertificateReport?batchId='+$scope.batchId;	  
    	$http.get(url, { responseType : 'arraybuffer' }).then(function(response)
    	{
    		if(response.data.byteLength!=0)
    		{
    			document.getElementById("success_msg").innerHTML="Report successfully generated!";
    			var pdfFile = new Blob([response.data], { type : 'application/pdf' })
    			var downloadURL = URL.createObjectURL(pdfFile);
    			var link = document.createElement('a');
    			link.href = downloadURL;
    			link.download = "Occupation Certificate.pdf"
    			document.body.appendChild(link);
    			link.click();
    		}
    		else{
    			document.getElementById("error_msg").innerHTML="Data Not Found, Report cannot be generated";
    		}
    		$scope.onLoad();
    		
    	}); 
    	}
    	
    	$timeout(function() {
			document.getElementById("success_msg").innerHTML="";
			document.getElementById("error_msg").innerHTML="";
         }, 4000);
    };
    
    $scope.Submit2=function(batchId){
    	if($scope.batchId==null)
		{
		document.getElementById("error_msg").innerHTML="Please enter Batch ID";
		}
	else{
    	var url='/generateAttendanceSheet?batchId='+$scope.batchId; 	  
    	$http.get(url, { responseType : 'arraybuffer' }).then(function(response)
    	{
    		if(response.data.byteLength!=0)
    		{
    			document.getElementById("success_msg").innerHTML="Report successfully generated!";	
    			var pdfFile = new Blob([response.data], { type : 'application/pdf' })
    			var downloadURL = URL.createObjectURL(pdfFile);
    			var link = document.createElement('a');
    			link.href = downloadURL;
    			link.download = "Attendance Sheet.pdf"
    			document.body.appendChild(link);
    			link.click();
    		}
    		else{
    			document.getElementById("error_msg").innerHTML="Data Not Found, Report cannot be generated";
    		}
	$scope.onLoad();
    		
    	}); 
    	}
    	
    	$timeout(function() {
			document.getElementById("success_msg").innerHTML="";
			document.getElementById("error_msg").innerHTML="";
         }, 4000);
    };
    
    $scope.Submit3=function(batchId){
    	if($scope.batchId==null)
		{
		document.getElementById("error_msg").innerHTML="Please enter Batch ID";
		}
	else{
    	var url='/generateNSKFDCSheet?batchId='+$scope.batchId;  	  
    	$http.get(url, { responseType : 'arraybuffer' }).then(function(response)
    	{
    		if(response.data.byteLength!=0)
    		{
    			document.getElementById("success_msg").innerHTML="Report successfully generated!";
    			var pdfFile = new Blob([response.data], { type : 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
    			var downloadURL = URL.createObjectURL(pdfFile);
    			var link = document.createElement('a');
    			link.href = downloadURL;
    			link.download = "NSKFDC Sheet.xls"
    			document.body.appendChild(link);
    			link.click();
    		}
    		else{
    			document.getElementById("error_msg").innerHTML="Data Not Found, Report cannot be generated";
    		}
	$scope.onLoad();
    		
    	}); 
    	}
    	
    	$timeout(function() {
			document.getElementById("success_msg").innerHTML="";
			document.getElementById("error_msg").innerHTML="";
         }, 4000);
    };
    
    $scope.Submit4=function(batchId){
    	if($scope.batchId==null)
		{
		document.getElementById("error_msg").innerHTML="Please enter Batch ID";
		}
	else{
    	var url='/generateSDMSSheet?batchId='+$scope.batchId; 	  
    	$http.get(url, { responseType : 'arraybuffer' }).then(function(response)
    	{	
    		if(response.data.byteLength!=0)
    		{
    			document.getElementById("success_msg").innerHTML="Report successfully generated!";	
    			var pdfFile = new Blob([response.data], { type : 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
    			var downloadURL = URL.createObjectURL(pdfFile);
    			var link = document.createElement('a');
    			link.href = downloadURL;
    			link.download = "SDMS Sheet.xls"
    			document.body.appendChild(link);
    			link.click();
    		}
    	else{
				document.getElementById("error_msg").innerHTML="Data Not Found, Report cannot be generated";
			}
	$scope.onLoad();
    		
    	}); 
    	}
    	
    	$timeout(function() {
			document.getElementById("success_msg").innerHTML="";
			document.getElementById("error_msg").innerHTML="";
         }, 4000);
    };
    
    $scope.submitMinutes = function(batchId){
    	if($scope.batchId==null)
		{
		document.getElementById("error_msg").innerHTML="Please enter Batch ID";
		}
	else{
    	var urlReq='/generateMinutesOfSelectionCommitteeDetails?batchId='+$scope.batchId; 	  
    	$http.get(urlReq, { responseType : 'arraybuffer' }).then(function(response)
    	{	
    		if(response.data.byteLength!=0)
    		{
    			/*console.log("the length of response is " + response.data.byteLength)*/
    			document.getElementById("success_msg").innerHTML="Report successfully generated!";	
    			var pdfFile = new Blob([response.data], { type : 'application/pdf' })
    			var downloadURL = URL.createObjectURL(pdfFile);
    			var link = document.createElement('a');
    			link.href = downloadURL;
    			link.download = "Minutes of Selection Committee.pdf"
    			document.body.appendChild(link);
    			link.click();
    		}
    	else{
    			/*console.log("the length of response is " + response.data.byteLength)*/
				document.getElementById("error_msg").innerHTML="Data Not Found, Report cannot be generated";
			}
	$scope.onLoad();
    		
    	}); 
    	}
    	
    	$timeout(function() {
			document.getElementById("success_msg").innerHTML="";
			document.getElementById("error_msg").innerHTML="";
         }, 4000);
    };

    
    $scope.auditTable = {
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
    				name: 'type',
    				displayName: 'Report Type'
    			},
    			{
    				name: 'generatedOn',
    				displayName: 'Generated On'
    			},
    			{
    				name: 'generatedBy',
    				displayName: 'Generated By'
    			}
    			]
    	};
    
		$scope.onLoad = function () {	
			$http.get('/getAuditTableRecords')
			.then(function (response) {	
				$scope.auditTable.data= response.data;
	  		});
	  	}

	  	$scope.onLoad();
    
	    var url1='/getBatchIdDetailsForGenerateReport';       	
       	$scope.ids = [];
               $http.get(url1)
               .then(function (response) {
             	  
               	let dt = response.data;	
               	for(i in dt){
               		$scope.ids.push(response.data[i].batchId); 
               	}
               	let length=$scope.ids.length;
               	/*console.log(length);*/
           	});
               
               /*----------- Grid Height -------------*/
               $scope.getTableHeight=function(){
               	 var rowHeight=30;
               	 var headerHeight=30;
               	
               	 return{
               		 height:($scope.auditTable.data.length * rowHeight + headerHeight)+"px"
               	 };

               };
});
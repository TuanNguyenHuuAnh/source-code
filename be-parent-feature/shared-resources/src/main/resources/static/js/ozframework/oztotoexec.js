/**
 * @author PHAM
 * @param $http
 * @param $log
 * @param $filter
 * @param $location
 * @param $window
 * @param $timeout
 * @param cfpLoadingBar
 * @param toaster
 * @returns {___anonymous127_5153}
 */
function oztotoService($http, $log, $filter, $location, $window, $timeout, $state, $uibModal, $rootScope, cfpLoadingBar, toaster) {
	
	var timeout = 3000;
	var methods = {
		initOZTotoViewer: function($scope) {
			$log.debug("oztotoService - initOZTotoViewer");
			
			var ozviewer = new OZTotoFramework.OZViewer();
						
			ozviewer.addEventListener("OZProgressCommand", function(event){	
				if(event.step == 4 && event.state == 2) { // Progress is completed

				}
			});
			
			//sung 180119 ocr scan  
			ozviewer.addEventListener("OZUserEvent", function(event) {
			     //event.param1, event.param2, event.param3, event.result
				
				 if(event.param1 == "ocr_scan"){
					 //oz report (OnClick): _TriggerOCXUserEvent("ocr_scan", "btnOCR","3$$fName^fCompany^fDept");
					//alert("OZUserEvent1:"+event.param1);
					OZTotoFramework.dispatchEvent("_callOCR_",  event);
				 }
				 
				 //180627
				 if(event.param1 == "smapri"){
						//var url = "smapri:/Format/Print";
			      		//location.href = url;
					 
					 	var p1 = "__format_archive_url=http://ozdemo.forcs.jpn.com/eFormDevTemplate/resources/smapri/OZ_PaperBox_Label.spfmtz";
						                               
			      		var p2 = "__format_id_number=1"
			      		//alert("event.param1:1");
			      		
			      		var arrInfo = event.param2.split("$$");
			      		//alert("event.param1:2");
			      		var p3 = encodeURIComponent("温度") + "=" 	+ encodeURIComponent(arrInfo[0])
			      		+ "&"+encodeURIComponent("月") + "=" + encodeURIComponent(arrInfo[1])
			      		+ "&"+encodeURIComponent("日") + "=" + encodeURIComponent(arrInfo[2])
			      		+ "&"+encodeURIComponent("時") + "=" + encodeURIComponent(arrInfo[3])
			      		+ "&"+encodeURIComponent("商品名") + "=" + encodeURIComponent(arrInfo[4])
			      		+ "&"+encodeURIComponent("備考") + "=" + encodeURIComponent(arrInfo[5])
			      		+ "&"+encodeURIComponent("月２") + "=" + encodeURIComponent(arrInfo[6])
			      		+ "&"+encodeURIComponent("日２") + "=" + encodeURIComponent(arrInfo[7])
			      		+ "&"+encodeURIComponent("時２") + "=" + encodeURIComponent(arrInfo[8])
			      		+ "&"+encodeURIComponent("(発行枚数)") + "=" + encodeURIComponent(event.param3);
			      		
			      		
					 	//OZTotoFramework.dispatchEvent("openAppURL", {Applink : "smapri:/Format/Print"});
			      		OZTotoFramework.dispatchEvent("openAppURL", {Applink : "smapri:/Format/Print?" + p1 + "&" + p2 + "&" + p3, received : false});
			      	//alert("event.param1:4");
			      		
			      		
			      		
			      }

			  });
			//sung 180119 ocr scan 
			OZTotoFramework.addEventListener("_callTriggerExternalEvent_", function(event){
			    //OZTotoFramework.util.trace("enter eventListener15+");
				setTimeout(function(){
					ozviewer.document.triggerExternalEvent(event.param1, event.param2, event.param3, "");
				}, 100);
				
			});
			
			ozviewer.addEventListener("OZExportMemoryStreamCallBack", function(event){

				//OZTotoFramework.util.alert("addEventListener");
				
				$log.debug("oztotoService - OZExportMemoryStreamCallBack");
				cfpLoadingBar.start();	// Loading Bar Start

				
				if(event.outputdata == "{}" ){
					toaster.pop({
						type: 'error',
						//title: 'Export',
						body: UM_MEMORYSTREAM_EXPORT_FAILED,
						showCloseButton: true,
						timeout: timeout
					});
				}else{		
					//OZTotoFramework.util.alert("outputdata::"+event.outputdata);
					
					var obj = eval('(' + event.outputdata + ')');
					var value = null;
					var formdata = new FormData();
					var index = 1;
					for(var key in obj){
						value = obj[key];
						formdata.append("file_name_"+index, key.replace("/sdcard/",""));
						formdata.append("file_stream_"+index, value);
						index++;
					}
				
				//	OZTotoFramework.util.alert("$scope.approverFlag : "+$scope.approverFlag);
					if($scope.approverFlag !== null && $scope.approverFlag !== "undefined" && $scope.approverFlag == 'Y'){

						//OZTotoFramework.util.alert("addEventListener 33");
						
						formdata.append("ozdFileName", 		$scope.formName);
						
						// create submit request
						var req = {
							method: 'POST',
							url: './api/v1/doc/report/update',
							dataType: 'json',
							transformRequest : angular.identity,
							headers: { 
								'Content-Type': undefined	// case attachment
							},
							data : formdata
						};
						
						// execute submit request
						$http(req).success(function(data, status, headers, config) {
							$log.debug($filter('json')(data));
/*							toaster.pop({
								type: 'success',
								title: "Submit",
								body: UM_PROCESSING_COMPLETED,
								showCloseButton: true,
								timeout: 3000
							});*/
							
							//$scope.formFileName = data.file_name_1;

							$scope.docStatus = "Y";
							methods.updateDoc($scope);
							
							//$state.go('roomboard');
							
						}).error(function(data, status, headers, config) {
							toaster.pop({
								type: 'error',
								title: data.code,
								body: data.msg,
								showCloseButton: true,
								timeout: 3000
							});	
							
						}).then(function(data) {
							cfpLoadingBar.complete();	// end progress bar
							
							// stop OZ indicator
							OZTotoFramework.indicator.stop(100);
							
							// remove OZ listener event
							ozviewer.removeEventListener("OZProgressCommand");
							ozviewer.removeEventListener("OZExportMemoryStreamCallBack");
							
							// dispose ozviewer
							ozviewer.dispose();
							
							// navigate to m-sitesurvey page
							//$state.go('mobile_sitesurvey');
						});							
					}
					else if($scope.finish !== null && $scope.finish !== "undefined" && $scope.finish) 
					{ 
						//OZTotoFramework.util.alert("addEventListener 11");
						
						formdata.append("ozdFileName", 		"");
						
						var req = {
								method: 'POST',
								url: './api/v1/doc/report/update',
								dataType: 'json',
								transformRequest : angular.identity,
								headers: { 
									'Content-Type': undefined	// case attachment
								},
								data : formdata
							};
						
						// execute submit request
						$http(req).success(function(data, status, headers, config) {
							/*$log.debug($filter('json')(data));
							toaster.pop({
								type: 'success',
								title: "Submit",
								body: UM_PROCESSING_COMPLETED,
								showCloseButton: true,
								timeout: 3000
							});*/
							
							$scope.formFileName = data.file_name_1;
							
							var modalInstance = $uibModal.open({
								templateUrl: 'statics/views/document/modal.html?v=' + new Date().getTime(),
								controller: 'docModalCtrl',
								size: 'md',		// sm, md, lg
								backdrop: 'static', 
								scope: $scope
							});
							
							// Draggable
							$timeout(function(){
								$('.modal-content').draggable({ handle: ".modal-window" });
							},100);	
							
							//$state.go('document.my_doc');
							
						}).error(function(data, status, headers, config) {
							toaster.pop({
								type: 'error',
								title: data.code,
								body: data.msg,
								showCloseButton: true,
								timeout: 3000
							});
							
						}).then(function(data) {
							cfpLoadingBar.complete();	// end progress bar
							
							// stop OZ indicator
							OZTotoFramework.indicator.stop(100);
							
							// remove OZ listener event
							ozviewer.removeEventListener("OZProgressCommand");
							ozviewer.removeEventListener("OZExportMemoryStreamCallBack");
							
							// dispose ozviewer
							//ozviewer.dispose();
							$rootScope.$broadcast('setVisible');
							
							// navigate to m-sitesurvey page
							//$state.go('mobile_sitesurvey');
						});
					} 
					else if($scope.finish !== null && $scope.finish !== "undefined" && !$scope.finish)	{

						//OZTotoFramework.util.alert("addEventListener 22");
						
						formdata.append("ozdFileName", 		"");
					
						var req = {
								method: 'POST',
								url: './api/v1/doc/report/update',
								dataType: 'json',
								transformRequest : angular.identity,
								headers: { 
									'Content-Type': undefined	// case attachment
								},
								data : formdata
							};
						
						// execute submit request
						$http(req).success(function(data, status, headers, config) {
							/*$log.debug($filter('json')(data));
							toaster.pop({
								type: 'success',
								title: "Submit",
								body: UM_PROCESSING_COMPLETED,
								showCloseButton: true,
								timeout: 3000
							});*/
							
							$scope.formFileName = data.file_name_1;
							
							var modalInstance = $uibModal.open({
								templateUrl: 'statics/views/document/modal.html?v=' + new Date().getTime(),
								controller: 'docModalCtrl',
								size: 'md',		// sm, md, lg
								backdrop: 'static', 
								scope: $scope
							});
							
							// Draggable
							$timeout(function(){
								$('.modal-content').draggable({ handle: ".modal-window" });
							},100);	
							
							//$state.go('document.my_doc');
							
						}).error(function(data, status, headers, config) {
							toaster.pop({
								type: 'error',
								title: data.code,
								body: data.msg,
								showCloseButton: true,
								timeout: 3000
							});
							
						}).then(function(data) {
							cfpLoadingBar.complete();	// end progress bar
							
							// stop OZ indicator
							OZTotoFramework.indicator.stop(100);
							
							// remove OZ listener event
							ozviewer.removeEventListener("OZProgressCommand");
							ozviewer.removeEventListener("OZExportMemoryStreamCallBack");
							
							// dispose ozviewer
							//ozviewer.dispose();
							$rootScope.$broadcast('setVisible');
							
							//$state.go('mobile_sitesurvey');
						});
					}
				}
			});
		
			return ozviewer;
		},		
		updateDoc: function($scope) {
			
			cfpLoadingBar.start();	// Loading Bar Start
			
			$log.debug("ozrViewService - update::updateRoomStatus>>"+$scope.docId);
			//OZTotoFramework.util.alert("ozrViewService - update::updateRoomStatus>>"+$scope.updateRoomStatus);
			
			var formdata = new FormData();
			formdata.append("docId",	$scope.docId);
			formdata.append("docStatus",	$scope.docStatus);
			formdata.append("docApprover",	$scope.docApprover);
			formdata.append("docApproverCount",	$scope.docApproverCount);
			formdata.append("docComment",	$scope.docComment);
			formdata.append("inputJson",	$scope.inputJson);
			
			// create submit request
			var req = {
				method: 'POST',
				url: './api/v1/doc/update',
				dataType: 'json',
				transformRequest : angular.identity,
				headers: { 
					'Content-Type': undefined	// case attachment
				},
				data : formdata
			};
			
			// execute submit request
			$http(req).success(function(data, status, headers, config) {
				$log.debug($filter('json')(data));
/*				toaster.pop({
					type: 'success',
					title: "Submit",
					//body: UM_PROCESSING_COMPLETED,
					body: '처리되었습니다.',
					showCloseButton: true,
					timeout: 3000
				});*/
			
				//$scope.$emit("disposeOzviewer");
				
				//cfpLoadingBar.complete();	// end progress barcfpLoadingBar.complete();	// end progress bar

				//$state.go('document.approval_doc');
				
				if( $scope.docStatus == "N" )
				{
					methods.sendNoticeMail(  $scope.createdUserId , $scope.createdUserId , "DECLINE" , $scope.enSubject );
				}
				else
				{
					methods.getNextApprover($scope);
				}
				
			}).error(function(data, status, headers, config) {
				toaster.pop({
					type: 'error',
					title: data.code,
					body: data.msg,
					showCloseButton: true,
					timeout: 3000
				});
				
			}).then(function(data) {
				// refresh parent data
				//$parent.$emit('GetListEvent');
				cfpLoadingBar.complete();	// end progress bar
				
			});
			
		},
		runOZViewer: function($scope, ozviewer, ozparam_splitter) {
			$log.debug("oztotoService - runOZViewer");
			
			var reportparam = $scope.servletconnection_url;
			
			$.each($scope.ozparams, function(key, value){
				reportparam = reportparam + ozparam_splitter + value;
			});
			
			//$log.debug("oztotoService - runOZViewer >> " + reportparam);
			ozviewer.createViewer($scope.ozviewer_div, reportparam, ozparam_splitter);
			ozviewer.setVisible(true);
		},
		createReport: function($scope, ozviewer, ozparam_splitter) {
			$log.debug("oztotoService - createReport");
			
			var reportparam = $scope.servletconnection_url;
			
			$.each($scope.ozparams, function(key, value){
				reportparam = reportparam + ozparam_splitter + value;
			});
			
			//$log.debug("oztotoService - runOZViewer >> " + reportparam);
			ozviewer.createReport(reportparam, ozparam_splitter);
		},
		getNextApprover: function($scope)
		{
			$scope.params = {};
			$scope.params.docId = $scope.docId;
			var req = {
				method: 'GET',
				url: './api/v1/doc/getnextapp',
				dataType: 'json',
				headers: { 
					'Content-Type': 'application/json; charset=utf-8'
				},
				params: $scope.params
			};
			$http(req).success(function(data, status, headers, config) {
				if(data.nextApproverId != "" && data.nextApproverId != null )
				{
					methods.sendNoticeMail( data.nextApproverId , $scope.createdUserId , "APPROVE" , $scope.enSubject );
				}
				else if( data.nextApproverId == "" || data.nextApproverId == null )
				{
					methods.sendNoticeMail( data.nextApproverId , $scope.createdUserId , "COMPLETE" , $scope.enSubject );
				}
			}).error(function(data, status, headers, config) {
				toaster.pop({
					type: 'error',
					title: data.code,
					body: data.msg,
					showCloseButton: true,
					timeout: 3000
				});
				
			}).then(function(data) {
				cfpLoadingBar.complete();	// end progress bar
			});
			
		},
		sendNoticeMail: function( email , createdUserId , type , title )
		{
			
			//alert($scope.emailparam.email);
			var req = {
				method: 'POST',
				url: './api/v1/doc/sendNoticeMail',
				headers: { 
					'Content-Type': undefined
				},
				params : { email : email , lang : $window.localStorage.getItem('setLang') , createdUserId : createdUserId , type : type , title : title }
			}
			
			$http(req).success(function(data, status, headers, config) {
				cfpLoadingBar.complete();	// end progress bar
				$state.go('document.approval_doc');
				
			}).error(function(data, status, headers, config) {
				toaster.pop({
					type: 'error',
					title: data.code,
					body: data.msg,
					showCloseButton: true,
					timeout: 3000
				});
				
			}).then(function(data) {
				cfpLoadingBar.complete();	// end progress bar
			});
		}	
	};
	
	return methods;
}
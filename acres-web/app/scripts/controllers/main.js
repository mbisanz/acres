'use strict';

angular.module('acres')

  .controller('MainCtrl', function($scope, $location, version, showcaseService, loginService) {

    $scope.$path = $location.path.bind($location);
    $scope.version = version;
    
    $scope.resetShowcase = function() {
    	showcaseService.save(
    		function(value, responseHeaders) {
			}, function(httpResponse) {
				alert("Failed to load showcase data!\n"+httpResponse.data);
			});
    };
  });

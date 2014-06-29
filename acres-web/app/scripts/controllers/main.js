'use strict';

angular.module('acres')

  .controller('MainCtrl', function($scope, $location, version, showcaseService) {

    $scope.$path = $location.path.bind($location);
    $scope.version = version;
    
    $scope.resetShowcase = function() {
    	showcaseService.save();
    };

  });

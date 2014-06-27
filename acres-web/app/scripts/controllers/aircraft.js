'use strict';

angular.module('acres')

.controller('AircraftCtrl', function($scope, aircraftService) {

	updateAircraftList();

	function updateAircraftList() {
		aircraftService.query(function(value, responseHeaders) {
			$scope.aircrafts = value;
		}, function(httpHeaders) {
			alert("Failed to load aircraft list!");
		});
	};

});

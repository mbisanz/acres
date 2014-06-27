'use strict';

angular.module('acres')
	.controller('AircraftCtrl',
		function($scope, aircraftService, reservationService) {

			updateAircraftList();

			function updateAircraftList() {
				aircraftService.query(function(value, responseHeaders) {
					$scope.aircrafts = value;
				}, function(httpHeaders) {
					alert("Failed to load aircraft list!");
				});
			}
			;

			$scope.loadAircraftReservations = function(aircraft) {
				console.log("loadAircraftReservations");
				reservationService.query({
					registration : aircraft.registration
				}, function(value, responseHeaders) {
					$scope.reservations = value;
					$scope.aircrafts = null;
				}, function(httpHeaders) {
					alert("Failed to load aircraft list!");
				});
			};

		});

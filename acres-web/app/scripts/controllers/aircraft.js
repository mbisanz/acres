'use strict';

angular.module('acres')
	.controller('AircraftCtrl',
		function($scope, aircraftService, reservationService) {

			$scope.updateAircraftList = function() {
				aircraftService.query(function(value, responseHeaders) {
					$scope.aircrafts = value;
					$scope.reservations = null;
					$scope.showReservations = false;
				}, function(httpHeaders) {
					alert("Failed to load aircraft list!");
				});
			}
			;

			$scope.loadAircraftReservations = function(aircraft) {
				reservationService.query({
					registration : aircraft.registration
				}, function(value, responseHeaders) {
					$scope.reservations = value;
					$scope.showReservations = true;
				}, function(httpHeaders) {
					alert("Failed to load bookings list!");
				});
			};
			
			$scope.updateAircraftList();
		});

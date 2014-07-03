'use strict';

angular.module('acres')
	.controller('AircraftCtrl',
		function($scope, aircraftService, reservationService) {

			$scope.updateAircraftList = function() {
				aircraftService.query(function(value, responseHeaders) {
					$scope.aircrafts = value;
					$scope.reservations = null;
					$scope.showReservations = false;
				}, function(httpResponse) {
					alert("Failed to load aircraft list!\n"+httpResponse.data);
				});
			}
			;

			$scope.loadAircraftReservations = function(aircraft) {
				reservationService.query({
					registration : aircraft.registration
				}, function(value, responseHeaders) {
					$scope.reservations = value;
					$scope.showReservations = true;
				}, function(httpResponse) {
					alert("Failed to load bookings list!\n"+httpResponse.data);
				});
			};
			
			$scope.updateAircraftList();
		});

'use strict';

angular.module('acres')
	.controller('BookingCtrl',
		function($scope, reservationWorkflowService) {

			$scope.loadReservationList = function() {
				reservationWorkflowService.query(function(value, responseHeaders) {
					$scope.reservations = value;
				}, function(httpHeaders) {
					alert("Failed to load bookings list!");
				});
			};

			$scope.cancelReservation = function(reservation) {
				reservationWorkflowService.remove({
					resId : reservation.id
				}, function(value, responseHeaders) {
					$scope.loadReservationList();
				}, function(httpHeaders) {
					alert("Failed to cancel booking!");
				});
			};
			
			$scope.updateReservation = function(reservation) {
				reservationWorkflowService.update({
					resId : reservation.id
				}, function(value, responseHeaders) {
					$scope.loadReservationList();
				}, function(httpHeaders) {
					alert("Failed to update booking!");
				});
			};
			
			$scope.loadReservationList();
		});

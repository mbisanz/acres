'use strict';

angular.module('acres').controller(
		'BookingCtrl',
		function($scope, reservationWorkflowService, aircraftService) {

			$scope.loadReservationList = function() {
				reservationWorkflowService.query(function(value,
						responseHeaders) {
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
			$scope.showEditReservation = false;

			$scope.reservation = {};
			$scope.aircrafts = aircraftService.query();

			$scope.status = {
				isopen : false
			};

			$scope.aircraftSelected = function(aircraft) {
				console.log(aircraft);
				$scope.reservation.aircraft = aircraft;
				$scope.status.isopen = !$scope.status.isopen;
			};
			
			$scope.editReservation = function(reservation) {
				if (reservation === null) {
					$scope.reservation = {};
				} else {
					$scope.reservation = reservation;
				}
				$scope.showEditReservation = true;
			}

			$scope.cancelEdit = function(reservation) {
				$scope.reservation = {};
				$scope.showEditReservation = false;
			}

			$scope.saveReservation = function() {
				var reservation = $scope.reservation;
				console.log($scope.reservation);
				reservationWorkflowService.save(reservation, function(value, responseHeaders) {
					$scope.loadReservationList();
					$scope.reservation = {};
					$scope.showEditReservation = false;
				}, function(httpHeaders) {
					alert("Failed to save reservation!");
				});
			};
		});

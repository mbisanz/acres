'use strict';

angular.module('acres').controller(
		'BookingCtrl',
		function($scope, userService, aircraftService, reservationWorkflowService, reservationWorkflowStepService) {

			$scope.loadReservationList = function() {
				reservationWorkflowService.query(function(value,
						responseHeaders) {
					$scope.reservations = value;
				}, function(httpResponse) {
					alert("Failed to load bookings list!\n"+httpResponse.data);
				});
			};

			$scope.cancelReservation = function(reservation) {
				reservationWorkflowService.remove({
					resId : reservation.id
				}, function(value, responseHeaders) {
					$scope.loadReservationList();
				}, function(httpResponse) {
					alert("Failed to cancel booking!\n"+httpResponse.data);
				});
			};

			$scope.step = function(reservation) {
				reservationWorkflowStepService.step(reservation.id).
					success(function (value, status){
						$scope.loadReservationList();
					}).
			        error(function(data, status) {
			        	alert("Failed to update booking!\n"+data);
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
			};

			$scope.cancelEdit = function(reservation) {
				$scope.reservation = {};
				$scope.showEditReservation = false;
			};

			$scope.saveReservation = function() {
				var reservation = $scope.reservation;
				console.log(reservation);
				if (reservation.id) {
					reservationWorkflowService.update("",reservation,function(value, responseHeaders){
						$scope.loadReservationList();
						$scope.reservation = {};
						$scope.showEditReservation = false;
					}, function(httpResponse) {
						console.log(httpResponse);
						alert("Failed to update reservation!\n"+httpResponse.data);
					});
				} else {
					reservation.user = {};
					reservationWorkflowService.save("",reservation,function(value, responseHeaders){
						$scope.loadReservationList();
						$scope.reservation = {};
						$scope.showEditReservation = false;
					}, function(httpResponse) {
						console.log(httpResponse);
						alert("Failed to create reservation!\n"+httpResponse.data);
					});
				}
			};
		});

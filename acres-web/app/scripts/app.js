'use strict';

angular.module('acres', ['ngAnimate', 'ngResource', 'ngRoute'])

  .constant('version', 'v0.1.0')

  .config(function($locationProvider, $routeProvider, $httpProvider) {

	// default: always use guest authorization
    //$httpProvider.defaults.headers.common['Authorization'] = 'Basic Z3Vlc3Q6Z3Vlc3Q=';

    $locationProvider.html5Mode(false);

    $routeProvider
      .when('/', {
        templateUrl: 'views/home.html'
      })
      .when('/aircrafts', {
        templateUrl: 'views/aircrafts.html',
        controller: 'AircraftCtrl'
      })
      .when('/bookings', {
        templateUrl: 'views/bookings.html',
        controller: 'BookingCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });


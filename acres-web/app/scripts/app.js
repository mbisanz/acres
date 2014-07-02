'use strict';

angular.module('acres', ['ngAnimate', 'ngResource', 'ngRoute', 'ngCookies', 'ui.bootstrap', 'ui.bootstrap.datetimepicker', 'http-auth-interceptor'])

  .constant('version', 'v0.1.0')

  .config(function($locationProvider, $routeProvider) {

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


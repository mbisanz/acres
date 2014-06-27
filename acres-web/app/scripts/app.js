'use strict';

angular.module('acres', ['ngAnimate', 'ngResource', 'ngRoute'])

  .constant('version', 'v0.1.0')

  .config(function($locationProvider, $routeProvider, $httpProvider) {

    $locationProvider.html5Mode(false);

    $routeProvider
      .when('/', {
        templateUrl: 'views/home.html'
      })
      .when('/aircrafts', {
        templateUrl: 'views/aircrafts.html',
        controller: 'AircraftCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });


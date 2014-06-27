'use strict';

var baseUrl = '/acres-rest/rest';

angular.module('acres')
	.factory('aircraftService',
		[ '$resource', function($resource) {
			return $resource(baseUrl + '/ac/:acId', {acId:'@acId'}, {
				update : {method:'PUT'}
			});
		} ]
	)
	.factory('reservationService',
		[ '$resource', function($resource) {
			return $resource(baseUrl + '/res/:resId', {resId:'@resId'}, {
				update : {method:'PUT'},
				query : {method:'GET', url: baseUrl + '/res/search', isArray: true}
			});
		} ]
	)
	.factory('reservationWorkflowService',
		[ '$resource', function($resource) {
			return $resource(baseUrl + '/reswf/:acId', {acId:'@acId'}, {
				update : {method:'PUT'},
			});
		} ]
	)
;

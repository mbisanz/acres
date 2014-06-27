'use strict';

var baseUrl = '/acres-rest/rest';

angular.module('acres')
	.factory('aircraftService',
		[ '$resource', function($resource) {
			return $resource(baseUrl + "/ac/:acId", {acId:'@acId'}, {
				update : {method:'PUT'}
			});
		} ]
	);
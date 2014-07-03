'use strict';

var baseUrl = '/acres-rest/rest';

angular.module('acres')
	.factory('userService',
		[ '$resource', function($resource) {
			return $resource(baseUrl + '/user/:userId', {userId:'@id'}, {
				update : {method:'PUT'}
			});
		} ]
	)
	.factory('aircraftService',
		[ '$resource', function($resource) {
			return $resource(baseUrl + '/ac/:acId', {acId:'@id'}, {
				update : {method:'PUT'}
			});
		} ]
	)
	.factory('reservationService',
		[ '$resource', function($resource) {
			return $resource(baseUrl + '/res/:resId', {resId:'@id'}, {
				update : {method:'PUT'},
				query : {method:'GET', url: baseUrl + '/res/search', isArray: true}
			});
		} ]
	)
	.factory('reservationWorkflowService',
		[ '$resource', function($resource) {
			return $resource(baseUrl + '/reswf/:resId', {resId:'@id'}, {
				update : {method:'PUT'}
			});
		} ]
	)
	.factory('reservationWorkflowStepService',
		[ '$http', function($http) {
			return {
				step: function(resId) {
					return $http.put(baseUrl + "/reswf/step/" + resId);
				}
			};
		} ]
	)
	.factory('showcaseService',
		[ '$resource', function($resource) {
			return $resource(baseUrl + '/showcase');
		} ]
	)
	.factory('Base64', function() {
	    var keyStr = 'ABCDEFGHIJKLMNOP' +
	        'QRSTUVWXYZabcdef' +
	        'ghijklmnopqrstuv' +
	        'wxyz0123456789+/' +
	        '=';
	    return {
	        encode: function (input) {
	            var output = "";
	            var chr1, chr2, chr3 = "";
	            var enc1, enc2, enc3, enc4 = "";
	            var i = 0;
	 
	            do {
	                chr1 = input.charCodeAt(i++);
	                chr2 = input.charCodeAt(i++);
	                chr3 = input.charCodeAt(i++);
	 
	                enc1 = chr1 >> 2;
	                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
	                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
	                enc4 = chr3 & 63;
	 
	                if (isNaN(chr2)) {
	                    enc3 = enc4 = 64;
	                } else if (isNaN(chr3)) {
	                    enc4 = 64;
	                }
	 
	                output = output +
	                    keyStr.charAt(enc1) +
	                    keyStr.charAt(enc2) +
	                    keyStr.charAt(enc3) +
	                    keyStr.charAt(enc4);
	                chr1 = chr2 = chr3 = "";
	                enc1 = enc2 = enc3 = enc4 = "";
	            } while (i < input.length);
	 
	            return output;
	        },
	 
	        decode: function (input) {
	            var output = "";
	            var chr1, chr2, chr3 = "";
	            var enc1, enc2, enc3, enc4 = "";
	            var i = 0;
	 
	            // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
	            var base64test = /[^A-Za-z0-9\+\/\=]/g;
	            if (base64test.exec(input)) {
	                console.log("There were invalid base64 characters in the input text.\n" +
	                    "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
	                    "Expect errors in decoding.");
	            }
	            input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
	 
	            do {
	                enc1 = keyStr.indexOf(input.charAt(i++));
	                enc2 = keyStr.indexOf(input.charAt(i++));
	                enc3 = keyStr.indexOf(input.charAt(i++));
	                enc4 = keyStr.indexOf(input.charAt(i++));
	 
	                chr1 = (enc1 << 2) | (enc2 >> 4);
	                chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
	                chr3 = ((enc3 & 3) << 6) | enc4;
	 
	                output = output + String.fromCharCode(chr1);
	 
	                if (enc3 != 64) {
	                    output = output + String.fromCharCode(chr2);
	                }
	                if (enc4 != 64) {
	                    output = output + String.fromCharCode(chr3);
	                }
	 
	                chr1 = chr2 = chr3 = "";
	                enc1 = enc2 = enc3 = enc4 = "";
	 
	            } while (i < input.length);
	 
	            return output;
	        }
	    };
	})	
	.service('loginService', function($http, $cookieStore, authService, Base64) {
	    var basicCredentials = $cookieStore.get('basicCredentials');
	    console.log("Initializing authentication from " + basicCredentials);
	    if (basicCredentials) {
	        $http.defaults.headers.common['Authorization'] = 'Basic ' + basicCredentials;
            delete $http.defaults.headers.common['Acres-Default-Authorization'];
	    } else {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + Base64.encode('guest:guest');
            $http.defaults.headers.common['Acres-Default-Authorization'] = 'true';
	    }
	
	    return {
	        setUserNameAndPassword: function(userName, password) {
	            var encodedUserNameAndPassword = Base64.encode(userName + ':' + password);
	            $cookieStore.put('basicCredentials', encodedUserNameAndPassword);
	            $http.defaults.headers.common['Authorization'] = 'Basic ' + encodedUserNameAndPassword;
	            delete $http.defaults.headers.common['Acres-Default-Authorization'];
	            authService.loginConfirmed();
	        },
	        isLoggedIn: function() {
	            return $cookieStore.get('basicCredentials') !== undefined;
	        },
	        getLogin: function() {
	        	if (!this.isLoggedIn()) {
	        		return this.getDefaultLogin();
	        	}
	        	var basicCredentials = $cookieStore.get('basicCredentials');
	        	var basicCredentialsDecoded = Base64.decode(basicCredentials);
	        	var login = basicCredentialsDecoded.split(':')[0];
	        	return login;
	        },
	        getDefaultLogin: function() {
	        	return 'guest';
	        },
	        logOut: function() {
	            $cookieStore.remove('basicCredentials');
	            $http.defaults.headers.common['Authorization'] = 'Basic ' + Base64.encode('guest:guest');
	            $http.defaults.headers.common['Acres-Default-Authorization'] = 'true';
	        }
	    };
	})
;

'use strict';

function LoginCtrl($scope, $rootScope, $location, loginService) {

    $scope.credentials = { userName: '', password: '' };
    $rootScope.login = loginService.getLogin();
    
    $scope.submit = function() {
        loginService.setUserNameAndPassword($scope.credentials.userName, $scope.credentials.password);
        $rootScope.login = loginService.getLogin();
    };

    $scope.isLoggedIn = function() {
        return loginService.isLoggedIn();
    };
    
    $scope.logOut = function() {
        loginService.logOut();
        $rootScope.login = loginService.getLogin();
        $location.path('/');
    };
};

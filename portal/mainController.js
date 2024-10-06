var app = angular.module("myApp", ["ngRoute", 'ngWebSocket']);

app.controller("MainController", function($scope, $location) {

});

app.config(function ($routeProvider) {
    $routeProvider
    .when("/",  {
        templateUrl: 'dashboard.html',
        controller: 'DashboardController'
    })
    .otherwise({
        redirect: "/"
    });
})
'use strict';

/**
 * @ngdoc overview
 * @name toneAnalysis
 * @description
 * # toneAnalysis
 *
 * Main module of the application.
 */
angular
  .module('toneAnalysis', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/tone-main.html',
        controller: 'ToneMainCtrl'
      })
      .when('/results', {
        templateUrl: 'views/tone-results.html',
        controller: 'ToneResultsCtrl'
      })  
      .when('/addResult', {
        templateUrl: 'views/tone-add-result.html',
      }) 
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .when('/back', {
          templateUrl: 'views/tone-main.html',
          controller: 'ToneMainCtrl'
      })      
      .otherwise({
        redirectTo: '/'
      });
  })
;

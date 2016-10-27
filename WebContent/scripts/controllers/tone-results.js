'use strict';

angular.module('toneAnalysis')
  .controller('ToneResultsCtrl', function ($scope, $http, $rootScope, $location) {

    // $http.get('/db/employees.json' ).
    //$http.get('/api/essayList' ).
    //  success(function(data) {
    //   $scope.employees = data;
    //   console.log($scope.employees);
    //  });

    $scope.getAllResults = function() {
        console.log('Adding getAllResults...');

        $http({
          url: "/api/essayList",
          // dataType: "json",
          method: "GET",
          //data: $scope.newEssay,
          headers: {
              "Content-Type": "text/plain"
          }
        }).success(function(response){
            console.log('SUCCESS:', response);
            $rootScope.essayInput=response;
            $('#essayInput').html($rootScope.essayInput);
            $location.path( '/results' );
        }).error(function(error){
            console.log('ERROR:', error);
        });
    }})
    ;



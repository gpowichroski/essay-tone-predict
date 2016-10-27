'use strict';

angular.module('toneAnalysis')
  .controller('ToneMainCtrl', function ($scope, $http, $rootScope, $location) {

    $scope.newEssay = {};
    $scope.newEssay.teacher = '';
    $scope.newEssay.classs = '';
    $scope.newEssay.grade = '';
    $scope.newEssay.essay = '';

    $scope.addCandidate = function() {
      console.log('Adding Candidate...');
      console.log('NEW ESSAY', $scope.newEssay);
      // $scope.newEssayString = JSON.stringify($scope.newEssay);
      // console.log('STRING', $scope.newEssayString);

      $http({
        url: "/api/add",
        method: "POST",
        data: $scope.newEssay,
        headers: {
            "Content-Type": "text/plain"
        }
      }).success(function(response){
          $rootScope.essayInput=response;
          console.log('ToneMainCtrl - SUCCESS:', $rootScope.essayInput);
          console.log('ToneMainCtrl - $scope.teacher:', $scope.newEssay.teacher);
          console.log('ToneMainCtrl - $scope.classs:', $scope.newEssay.classs);
          console.log('ToneMainCtrl - $scope.grade:', $scope.newEssay.grade);
          
          $rootScope.essayTeacher=$scope.newEssay.teacher;
          $rootScope.essayClasss=$scope.newEssay.classs;
          $rootScope.essayGrade=$scope.newEssay.grade;
          
          $('#essayInput').html($rootScope.essayInput);
          $('#essayTeacher').html($rootScope.essayTeacher);
          $('#essayClasss').html($rootScope.essayClasss);
          $('#essayGrade').html($rootScope.essayGrade);      
                
          //alert("Essay alert ");
          //alert($scope.newEssayString);
          $location.path( '/addResult' );
          //$location.path( '/addResult' );
      }).error(function(error){
          console.log('ERROR:', error);
      });

    };

    
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
    }    
    
    
  });

const ipcRenderer = require('electron').ipcRenderer
const remote = require('electron').remote;
const Hammer = require("hammerjs")
var anglar = require("angular")
var jquery = require("jquery")
var Speech = require('electron-speech')

var recog = Speech({
  lang: 'en-US',
  continuous: true
})

recog.on('text', function (text) {
  console.log(text)
});

recog.on('error', function (err) {
  console.log("error")
});


recog.listen()

angular.module('hannahBot', [])

  .controller('toteCtrl', function($scope){
    $scope.toolName = "";
    $scope.teamName = "";
    $scope.relationalList = ipcRenderer.sendSync('ToteManager', 'ready')
    $scope.BLlist = ipcRenderer.sendSync('BLManager', 'ready')
    $scope.data = Object.keys($scope.relationalList)
    $scope.data2 = Object.keys($scope.BLlist)

    $scope.borrowTool = function(){
      $('#borrowModal').openModal()
    }
    $scope.borrowComplete = function(){
      $scope.needed = ipcRenderer.sendSync('BLSave', {'teamName':$scope.teamName, 'toolName':$scope.toolName, 'borrowOrLent':"Borrowing"})
      $scope.BLlist = ipcRenderer.sendSync('BLManager', 'ready')
      $scope.data2 = Object.keys($scope.BLlist)
      $('#borrowModal').closeModal()
    }

    $scope.lendTool = function(){
      $('#lendModal').openModal()
    }
    $scope.lendComplete = function(){
      $scope.needed = ipcRenderer.sendSync('BLSave', {'teamName':$scope.teamName, 'toolName':$scope.toolName, 'borrowOrLent':"Lent"})
      $scope.BLlist = ipcRenderer.sendSync('BLManager', 'ready')
      $scope.data2 = Object.keys($scope.BLlist)
      $('#lendModal').closeModal()
    }

  })

  .filter('searchFor', function(){
  return function(arr, searchString){
      if(!searchString){
          return arr;
      }
      /*
      you can add special cases here with just one if statement
      */
      var result = [];
      searchString = searchString.toLowerCase();
      angular.forEach(arr, function(item){
          if(item.toLowerCase().indexOf(searchString) !== -1){
          result.push(item);
      }
      });
      return result;
  };
  });

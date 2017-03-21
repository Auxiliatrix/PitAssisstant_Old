const ipcRenderer = require('electron').ipcRenderer
const remote = require('electron').remote;
const Hammer = require("hammerjs")
var anglar = require("angular")
var jquery = require("jquery")

angular.module('BotContainer', [])

  .controller('MainControl', function($scope){
    $(".button-collapse").sideNav();
  })

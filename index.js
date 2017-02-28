'use strict'
const path = require('path')
const fs = require('fs')
const parse = require('csv-parse');
const LineByLineReader = require('line-by-line');
const Regex = require("regex");
const loki = require("lokijs")
const electron = require('electron')
const app = electron.app
const BrowserWindow = electron.BrowserWindow
const Menu = electron.Menu
const crashReporter = electron.crashReporter
const shell = electron.shell
const ipcMain = require('electron').ipcMain
let menu
let template
let mainWindow = null

var GoogleSpreadsheet = require('google-spreadsheet');
var async = require('async');

// spreadsheet key is the long id in the sheets URL
var doc = new GoogleSpreadsheet('1bmFP84BnL4GCf8XxO_rOF8yxscJ1Sf6Hh0qIgLyTeAs');
var sheet;

async.series([
  function setAuth(step) {
    // see notes below for authentication instructions!
    // OR, if you cannot save the file locally (like on heroku)

    doc.useServiceAccountAuth('HannahBot-08d28bc93ea4.json', step);
  },
  function getInfoAndWorksheets(step) {
    doc.getInfo(function(err, info) {
      console.log("2")
      console.log('Loaded doc: '+info.title+' by '+info.author.email);
      sheet = info.worksheets[0];
      console.log('sheet 1: '+sheet.title+' '+sheet.rowCount+'x'+sheet.colCount);
      step();
    });
  }
]);


console.log(app.getPath('userData'))

const borrowLendDB = new loki(app.getPath('userData') + '/borrowLend.hpdb',
  {
    autoload: true,
    autoloadCallback : loadHandler,
    autosave: true,
    autosaveInterval: 1000,
  });

  function loadHandler() {
    var coll = borrowLendDB.getCollection('bl');
    if (coll === null) {
      coll = borrowLendDB.addCollection('bl');
    }
  }

function blIndex(datab){
  var blList = {}
  var bl = datab.getCollection("bl").data;
  var blitem, teamName, toolName, borrowOrLent
  for (blitem in bl) {
    teamName = bl[blitem].team
    toolName = bl[blitem].tool
    borrowOrLent = bl[blitem].blkey
    blList[toolName] = borrowOrLent + " : " + teamName
  }

  console.log(blList)
  return blList;

}

function addblToDB(datab, teamName, toolName, borrowOrLent){
      var bl = datab.getCollection("bl")
    	bl.insert({
    		team: teamName,
    		tool: toolName,
    		blkey: borrowOrLent
    	})
    	datab.saveDatabase()

}

function deleteBL(datab, toolName){
    var bl = datab.getCollection("bl");
    bl.removeWhere({'tool': {'$eq': toolName}})
}



var stuff = {};


app.on('ready', () => {
  mainWindow = new BrowserWindow()
  mainWindow.setMenu(null);
  mainWindow.loadURL(`file://${__dirname}/html/index.html`)
  mainWindow.on('closed', () => {
    mainWindow = null
  })

  mainWindow.openDevTools()

  csvToDB("inventory2.csv")

    function cleanArray(actual) {
        var newArray = new Array();
        for (var i = 0; i < actual.length; i++) {
          if (actual[i]) {
            newArray.push(actual[i]);
          }
        }
        return newArray;
    }

    function makeList(lineRead){
      var keys =  lineRead.splice(0, 1);
      var items
      var semi = [];
      var finished = [];
      var num = 0;
      var specItem;
      while (num < lineRead[0].length){
        for (items in lineRead){
            specItem = lineRead[items]
            specItem = specItem[num]
            semi.push(specItem);
        }
        if(semi != []){
        finished.push(cleanArray(semi))
        }
        semi = []
        num = num + 1;
      }
      return [keys[0], finished];
    }

    function csvToDB(pathToCsvFile){
      var lineRead = [];
      var finishedLine;
      var finishedTotes;
      var toteNames;
      var finalRelational = {};
      var lr = new LineByLineReader(pathToCsvFile);

      lr.on('error', function (err) {
        console.log("error")
      });
      lr.on('line', function (line) {
        lineRead.push(line.split(","))
      });
      lr.on('end', function () {
        var item;
        var item1;
        finishedLine = makeList(lineRead)
        finishedTotes = finishedLine[1]
        toteNames = finishedLine[0]
        for (item in finishedTotes){
          for(item1 in finishedTotes[item]){
            finalRelational[finishedTotes[item][item1]] = toteNames[item];
          }
        }
        stuff = finalRelational;
      });
    }


    ipcMain.on('ToteManager', function (event, arg) {
      event.returnValue = stuff
    })

    ipcMain.on('BLManager', function (event, arg) {
      var dataJSON = blIndex(borrowLendDB)
      event.returnValue = dataJSON
    })

    ipcMain.on('BLSave', function (event, arg) {
      addblToDB(borrowLendDB, arg.teamName, arg.toolName, arg.borrowOrLent);
      event.returnValue = "blank"
    })

    ipcMain.on('BLRemove', function (event, arg) {
      deleteApp(borrowLendDB, arg.toolName)
      event.returnValue = "blank"
    })


});

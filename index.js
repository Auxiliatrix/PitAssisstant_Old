'use strict'
const path = require('path')
const fs = require('fs')
const Regex = require("regex");
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

console.log(app.getPath('userData'))


app.on('ready', () => {
  mainWindow = new BrowserWindow()
  mainWindow.setMenu(null);
  mainWindow.loadURL(`file://${__dirname}/html/index.html`)
  mainWindow.on('closed', () => {
    mainWindow = null
  })

  mainWindow.openDevTools()

    ipcMain.on('ToteManager', function (event, arg) {
      event.returnValue = stuff
    })

});

'use strict';
$(document).ready(function () {
	var $draggables = $('.draggable').draggabilly({
		// contain to parent element
		containment: true
	});
});

var app = angular.module("totes", []);

app.controller("containerController", function ($scope) {
	//tote object: String: name, boolean: rotation (true = horizontal), array: contents
	$scope.totes = [{
		"name": "tote1",
		"horizontal": true,
		"contents": ["Stuff", "lol"],
		"glow": false
	}, {
		"name": "tote2",
		"horizontal": true,
		"contents": ["Rivets", "Rivet Gun", "Staples"],
		"glow": false
	}];

	$scope.onClick = function (x) {
		x[1] = !x[1]; //flips boolean, true = hori, false = verti
	};

	$scope.getStyle2 = function (b) {
		if (b) {
			return { //horizontal
				width: '10em',
				height: '6em'
			};
		} else { //vertical
			return {
				width: '6em',
				height: '10em'
			};
		}
	};
	
	$scope.getStyle = function (b, g) {
		var s = {
			"width": '10em',
			"height": '6em',
		};
		if (!b) {
			s.width = '6em';
			s.height = '10em';
		}
		if (g) {
			Object.defineProperty(s, 'box-shadow', '0px 0px 5px 8px rgba(255,245,54,1)');
		}
		return s;
	};

	$scope.showContents = function (x) {
		$scope.modalTote = x;
		$scope.contentUIShow = true;
	};

	$scope.searchText = "";
	$scope.results = [];
	$scope.$watch('searchText', function () {
		$scope.search();
	});

	$scope.search = function () {
		if ($scope.searchText === '') {return; }
		$scope.results = [];
		var i, j;
		for (i = 0; i < $scope.totes.length; i++) {
			for (j = 0; j < $scope.totes[i].contents.length; j++) {
				if ($scope.totes[i].contents[j].indexOf($scope.searchText) !== -1) {
					console.log('result found: ' + $scope.totes[i].contents[j]);
					$scope.results.push([$scope.totes[i].contents[j], $scope.totes[i]]);
				}
			}
		}
	};



	$scope.modalTote = null;
	$scope.contentUIShow = false;
	$scope.newItem = "";

	$scope.newTote = function () {
		$scope.totes.push({
			"name": "New Tote",
			"horizontal": true,
			"contents": ["new", "tote"],
			"glow": false
		});
		var draggie = new Draggabilly('.draggable', {
			// options...
		});
	};
});
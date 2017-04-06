Application.$controller('MainPageController', ['$scope', 'DialogService', function($scope, DialogService) {
    'use strict';
    $scope.onPageVariablesReady = function() {};
    $scope.onPageReady = function() {};

    $scope.calendar1Eventclick = function($event, $data, $view) {
        $scope.Variables.eventId.dataSet.dataValue = $data.id;
        $scope.Variables.readEvent.setFilter('id', $data.id);
        $scope.Variables.readEvent.update();
        DialogService.open('dialog2', $scope);
    };
    $scope.calendar1Eventresize = function($event, $newData, $oldData, $delta, $revertFunc, $ui, $view) {
        $scope.Variables.updateEvent.setInput('id', $newData.id);
        $scope.Variables.updateEvent.setInput('title', $newData.title);
        $scope.Variables.updateEvent.setInput('start', $newData.start.format());
        $scope.Variables.updateEvent.setInput('end', $newData.end.format());
        $scope.Variables.updateEvent.setInput('allDay', $newData.allDay);
        $scope.Variables.updateEvent.setInput('description', $newData.description);
        $scope.Variables.updateEvent.setInput('color', $newData.color);
        $scope.Variables.updateEvent.updateRecord();
        $scope.Variables.readEvent.update();
    };
    $scope.calendar1Eventdrop = function($event, $newData, $oldData, $delta, $revertFunc, $ui, $view) {
        $newData.end = $newData.end || WM.copy($newData.start).add(2, 'hours');
        $scope.Variables.updateEvent.setInput('id', $newData.id);
        $scope.Variables.updateEvent.setInput('title', $newData.title);
        $scope.Variables.updateEvent.setInput('start', $newData.start.format());
        $scope.Variables.updateEvent.setInput('end', $newData.end.format());
        $scope.Variables.updateEvent.setInput('allDay', $newData.allDay);
        $scope.Variables.updateEvent.setInput('description', $newData.description);
        $scope.Variables.updateEvent.setInput('color', $newData.color);
        $scope.Variables.updateEvent.updateRecord();
        $scope.Variables.readEvent.update();
    };
    $scope.calendar1Select = function($start, $end, $view) {
        var myvar = $view.name;
        DialogService.open('dialog1', $scope, {});
    };


}]);

Application.$controller('dialog1Controller', ['$scope',
    function($scope) {
        'use strict';
        $scope.ctrlScope = $scope;
    }
]);

Application.$controller('dialog2Controller', ['$scope',
    function($scope) {
        'use strict';
        $scope.ctrlScope = $scope;

    }
]);



Application.$controller("dialog1Controller", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;
    }
]);

Application.$controller("dialog2Controller", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;
    }
]);
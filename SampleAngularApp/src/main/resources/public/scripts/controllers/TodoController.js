app.controller('TodoController', function($scope, $rootScope, $location, $http) {
    function initialize() {
        $scope.todos = [];
        $scope.keyword = '';
        $scope.page = 0;
        $scope.selectedIds = [];
        $scope.newId = -1;
        $scope.searchTodos(0);
        $scope.switchToMode($location.path());
    }
    
    $scope.switchToMode = function(mode) {
    	if (mode === '/edit') {
            $http.get('rest/security/user')
                .success(function(user) {
                    $scope.editMode = user;
                    $location.path($scope.editMode? '/edit' : '/view');
                });
        } else {
            $scope.editMode = false;
            $location.path('/view');
        }
    }
    
    // watch selected todos
    $scope.$watch('todos|filter:{selected:true}', function (results) {
            $scope.selectedIds = results.map(function(todo) {
                todo.selected = true;
                return todo.id;
            });
        }, true)
    
    $scope.searchTodos = function(page) {
        if ($scope.isLoading) {
            return;
        }
        
        if ($scope.selectedIds.length > 0) {
            if (!confirm("The selected todos are not handled. Forget them and continue to search?")) {
                return;
            }
        }
        
        if (!page) {
            // reset todos list if search again
            $scope.todos = [];
            page = 0;
        }
        
        var PAGE_SIZE = 24;
        $scope.page = page;
        $scope.isLoading = true;
        $http.get('rest/todos?keyword=' + $scope.keyword + '&page=' + $scope.page + '&pageSize=' + PAGE_SIZE)
             .success(function(items) {
                 $scope.hasMoreTodos = (items.length >= PAGE_SIZE);
                 for (var i=0; i<items.length; i++) {
                     $scope.todos.push(items[i]);
                 }
             })
             .finally(function() {
                 $scope.isLoading = false;
             });
    }
    
    $scope.newTodo = function(type) {
        $scope.keyword = '';
        $scope.todos.unshift({
            id : $scope.newId--,
            type : type
        });
    };

    $scope.saveTodo = function(todo) {
        if (!confirm('Your changes will be saved. Are you sure?')) {
            return;
        }
        
        delete todo.errors;
        
        var action = (todo.id < 0) ? $http.post : $http.put;
        var uri = (todo.id < 0) ? 'rest/todos/' : 'rest/todos/' + todo.id;
        action(uri, todo).then(function(response) {
            todo.id = response.data.id;
            todo.errors = {};
            document.getElementById('filter').focus();
        }, function(response) {
            if (response.status == 400) {
                todo.errors = response.data;
            }
        });
    };

    $scope.deleteTodos = function() {
        if (!confirm('Are you sure you want to delete selected todo(s)?')) {
            return;
        }
        
        var ids = $scope.selectedIds;
        $http.delete('rest/todos?ids=' + ids).then(function(response) {
            for (var i = 0; i < ids.length; i++) {
                for (var j = $scope.todos.length - 1; j >= 0; j--) {
                    if ($scope.todos[j].id === ids[i]) {
                        $scope.todos.splice(j, 1);
                        break;
                    }
                }
            }
        });
    };
    
    initialize();
});
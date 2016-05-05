package com.newt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.newt.entity.Todo;
import com.newt.service.TodoService;

@RestController
@RequestMapping(value = "/rest/todos")
public class TodoController {

	@Autowired
	TodoService todoService;

	@RequestMapping(method = GET)
	public List<Todo> searchTodos(@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
		return todoService.searchTodos(keyword, page, pageSize);
	}

	@RequestMapping(method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Todo createTodo(@RequestBody Todo todo) throws Exception {
		todo.setId(null);
		return todoService.saveTodo(todo);
	}

	@RequestMapping(value = "/{id}", method = PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Todo updateTodo(@PathVariable("id") String id, @RequestBody Todo todo) {
		todo.setId(id);
		return todoService.saveTodo(todo);
	}

	@RequestMapping(method = DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTodos(@RequestParam String[] ids) {
		todoService.deleteTodos(ids);
	}
}

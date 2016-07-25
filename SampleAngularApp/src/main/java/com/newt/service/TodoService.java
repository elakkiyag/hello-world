package com.newt.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newt.entity.Todo;
import com.newt.repository.TodoRepository;

@Service
@Transactional(readOnly = true)
public class TodoService {
	
	private final AtomicInteger idGeneration = new AtomicInteger(1000);

	@Autowired
	TodoRepository todoRepository;

	@Transactional
	public long loadTodos(String filePath) throws IOException {
		long totalRecords = 0;
		try (InputStream input = new FileInputStream(filePath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
			totalRecords = reader.lines().map(Todo::parseTodo).peek(this::saveTodo).count();
		}
		return totalRecords;
	}

	public List<Todo> searchTodos(String keyword, int page, int pageSize) {
		keyword = (keyword == null) ? "" : keyword.toLowerCase();
		return todoRepository.searchTodos(keyword, new PageRequest(page, pageSize));
	}

	public Todo getTodo(String id) {
		return todoRepository.findOne(id);
	}

	@Transactional
	public Todo saveTodo(Todo todo) {
		if (todo.getId() == null)
			todo.setId(String.valueOf(idGeneration.incrementAndGet()));
		return todoRepository.save(todo);
	}

	@Transactional
	public void deleteTodos(String... ids) {
		todoRepository.deleteTodos(ids);
	}

	@Transactional
	public void deleteAllTodos() {
		todoRepository.deleteAllInBatch();
	}

	public static Todo parseTodo(String todoLine) {
        String[] items = todoLine.split("\\|");
        if (items.length < 2) {
            throw new IllegalArgumentException("Invalid todo-line format: " + todoLine);
        }
        
        Todo todo = new Todo();
        todo.setId(items[0]);
        todo.setName(items[1]);
        if (items.length > 2) {
        	todo.setCreatedon(items[2]);
        }
       
        return todo;
    }
	
}

package com.newt.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "todo")
public class Todo {
	@Id
	private String id;

	@NotEmpty(message = "{validation.not-empty.message}")
	private String name;

	private String createdon;

	public Todo() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatedon() {
		return createdon;
	}

	public void setCreatedon(String createdon) {
		this.createdon = createdon;
	}

	@Override
	public boolean equals(Object obj) {
		if (id != null && obj instanceof Todo) {
			return id.equals(((Todo) obj).name);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return (id == null) ? 0 : id.hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s|%s|%s", id, name, createdon);
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

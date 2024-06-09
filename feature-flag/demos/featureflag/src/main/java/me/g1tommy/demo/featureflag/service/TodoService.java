package me.g1tommy.demo.featureflag.service;

import java.util.List;

import me.g1tommy.demo.featureflag.domain.Todo;

public interface TodoService {

	Todo createTodo(String description);

	List<Todo> getTodos();

	Todo getTodo(long index);

	Todo updateTodo(long index, String description);

	void removeTodo(long index);
}

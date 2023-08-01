package me.g1tommy.demo.featureflag.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import me.g1tommy.demo.featureflag.domain.Todo;
import me.g1tommy.demo.featureflag.domain.TodoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {


	private final TodoRepository todoRepository;

	@Override
	@Transactional
	public Todo createTodo(String description) {
		final Todo newTodo = Todo.builder()
				.description(description)
				.build();

		return todoRepository.save(newTodo);
	}

	@Override
	public List<Todo> getTodos() {
		return todoRepository.findAll();
	}

	@Override
	public Todo getTodo(long index) {
		return todoRepository.getReferenceById(index);
	}

	@Override
	@Transactional
	public Todo updateTodo(long index, String description) {
		final Todo oldTodo = todoRepository.getReferenceById(index);
		oldTodo.updateDescription(description);

		return todoRepository.save(oldTodo);
	}

	@Override
	@Transactional
	public void removeTodo(long index) {
		todoRepository.deleteById(index);
	}
}

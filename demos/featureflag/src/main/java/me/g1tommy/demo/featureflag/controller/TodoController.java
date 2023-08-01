package me.g1tommy.demo.featureflag.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.g1tommy.demo.featureflag.aop.BooleanFlag;
import me.g1tommy.demo.featureflag.domain.Todo;
import me.g1tommy.demo.featureflag.service.TodoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@RestController
@RequestMapping("/v1/todos")
@RequiredArgsConstructor
public class TodoController {

	private static final String FLAG_NAME_TEST_FLAG = "test-flag";

	private final TodoService service;

	@GetMapping
	@BooleanFlag(FLAG_NAME_TEST_FLAG)
	public List<Todo> todos() {
		if (log.isDebugEnabled()) {
			log.debug("If Feature Flag is enabled, then you can see this log.");
		}
		return service.getTodos();
	}

	@GetMapping("/{index}")
	@BooleanFlag(FLAG_NAME_TEST_FLAG)
	public Todo getTodo(@PathVariable long index) {
		if (log.isDebugEnabled()) {
			log.debug("If Feature Flag is enabled, then you can see this log.");
		}
		return service.getTodo(index);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public Todo createTodo(@RequestBody String description) {
		return service.createTodo(description);
	}

	@PutMapping("/{index}")
	public Todo updateTodo(@PathVariable long index, @RequestBody String description) {
		return service.updateTodo(index, description);
	}

	@DeleteMapping("/{index}")
	@ResponseStatus(NO_CONTENT)
	public void deleteTodo(@PathVariable long index) {
		service.removeTodo(index);
	}
}

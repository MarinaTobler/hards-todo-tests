package com.todo.requests;

import com.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import java.util.List;

// ?? .asserThat() в реквестах не надо, т.к. здесь это не проверки? проверяем в тестах?
// ?? ContentType не надо здесь прописывать? т.к. это устанавливается в RequestSpec?
public class ValidatedTodoRequest extends Request implements CrudInterface<Todo>, SearchInterface {
    private TodoRequest todoRequest;

    // NB! сразу здесь создаём новый todoRequest, чтобы потом не повторяться в каждом запросе:
    public ValidatedTodoRequest(RequestSpecification reqSpec) {
        super(reqSpec);
        todoRequest = new TodoRequest(reqSpec);
    }

    // Как и где проверять, что response body is emptyOrNullString? Ответ: т.к. по конвенции create должен возвращать 201 и
    // пустую строку, то прямо здесь десерелизуем в строку и потом в тесте проверяем, что она пустая:

    @Override
    public String create(Todo entity) {
        return todoRequest.create(entity)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().asString();
    }

    // сразу десерелизуем в класс
    @Override
    public Todo update(long id, Todo entity) {
        return todoRequest.update(id, entity)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Todo.class);
    }

    // т.к. ответ должен быть пустой, то десерелизуем в строку, и проверяем, что она пустая
    @Override
    public String delete(long id) {
        return todoRequest.delete(id)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract()
                .body()
                .asString();
    }

    // Возвращаем сразу List, а не массив, т.к. с ним удобнее потом работать: м. использовать .stream, .add и т.д.
    @Override
    public List<Todo> readAll(int offset, int limit) {
        Todo[] todos = todoRequest.readAll()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Todo[].class);
        return List.of(todos);
    }

    public List<Todo> readAll() {
        Todo[] todos = todoRequest.readAll()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Todo[].class);
        return List.of(todos);
    }


}

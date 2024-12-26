package com.todo.requests;

import com.todo.models.Todo;
import com.todo.storages.TestDataStorage;
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
    // пустую строку, то прямо здесь десерелизуем в строку и считаем, что она пустая:
    // "Create ToDo. Status code 201 and empty/null string has been received."
    @Override
    public String create(Todo entity) {
        var response = todoRequest.create(entity)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().asString();
        TestDataStorage.getInstance().addData(entity);
        return response;
    }

    // сразу десерелизуем в класс
    // "Update ToDo. Status code 200 and updated ToDo has been received."
    @Override
    public Todo update(long id, Todo entity) {
        return todoRequest.update(id, entity)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Todo.class);
    }

    // т.к. ответ должен быть пустой, то десерелизуем в строку, и проверяем, что она пустая
    // "Delete ToDo. Status code 200 and empty/null string has been received."
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
    // "Get ToDos with offset {0}, limit {1}. Status code 200 and list of todos has been received."
    @Override
    public List<Todo> readAll(int offset, int limit) {
        Todo[] todos = todoRequest.readAll()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Todo[].class);
        return List.of(todos);
    }

    // "Get All ToDos. Status code 200 and list of todos has been received."
    public List<Todo> readAll() {
        Todo[] todos = todoRequest.readAll()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Todo[].class);
        return List.of(todos);
    }


}

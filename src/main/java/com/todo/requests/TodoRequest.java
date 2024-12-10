package com.todo.requests;

import com.todo.models.Todo;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class TodoRequest extends Request implements CrudInterface<Todo>, SearchInterface {
    private static final String TODO_ENDPOINT = "/todos";

    public TodoRequest(RequestSpecification reqSpec) {
        super(reqSpec);
    }

    @Override
    public Response create(Todo entity) {
        return given()
                .spec(reqSpec)
                .body(entity)
                .when()
                .post(TODO_ENDPOINT);
    }

    @Override
    public Response update(long id, Todo entity) {
        return given()
                .spec(reqSpec)
                .body(entity)
                .when()
                .put(TODO_ENDPOINT + id);
    }

    @Override
    public Response delete(long id) {
        return given()
                .spec(reqSpec)
                .delete(TODO_ENDPOINT + id);
    }

    @Override
    public Response readAll() {
        return given()
                .spec(reqSpec)
                .when()
                .get(TODO_ENDPOINT);
    }

    @Override
    public Response readAll(int offset, int limit) {
        return given()
                .spec(reqSpec)
                .when()
                .get(TODO_ENDPOINT + "?offset=" + offset + "limit=" + limit);
    }
}

package com.todo.requests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
/*
этот класс не нужен, т.к. в api тестах мы не проверяем контрактное тестирование
 */
public class UnvalidatedTodoRequest extends Request {
    private static final String TODO_ENDPOINT = "/todos";

    public UnvalidatedTodoRequest(RequestSpecification reqSpec) {
        super(reqSpec);
    }

    public Response create(String json) {
        return given()
                .spec(reqSpec)
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(TODO_ENDPOINT);
    }

    public Response update(long id, String json) {
        return given()
                .spec(reqSpec)
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .put(TODO_ENDPOINT + id);
    }

    public Response delete(long id) {
        return given()
                .spec(reqSpec)
                .delete(TODO_ENDPOINT + id);
    }


}

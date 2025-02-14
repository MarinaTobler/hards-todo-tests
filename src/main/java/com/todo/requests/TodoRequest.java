package com.todo.requests;

import com.todo.models.Todo;
import com.todo.requests.interfaces.CrudInterface;
import com.todo.requests.interfaces.SearchInterface;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class TodoRequest extends Request implements CrudInterface<Todo>, SearchInterface {
    private static final String TODO_ENDPOINT = "/todos";

    public TodoRequest(RequestSpecification reqSpec) {
        super(reqSpec);
    }

    @Override
    @Step("Create {entity}")
    public Response create(Todo entity) {
        return given()
                .spec(reqSpec)
                .body(entity)
                .when()
                .post(new Endpoint(TODO_ENDPOINT).build());
    }

    @Override
    @Step("Update {entity}")
    public Response update(long id, Todo entity) {
        return given()
                .spec(reqSpec)
                .body(entity)
                .when()
                .put(new Endpoint(TODO_ENDPOINT).build() + id);
    }

    @Override
    @Step("Delete {id}")
    public Response delete(long id) {
        return given()
                .spec(reqSpec)
                .delete(new Endpoint(TODO_ENDPOINT).build() + id);
    }

//    @Override
//    public Response readAll(int offset, int limit) {
//        return given()
//                .spec(reqSpec)
//                .when()
//                .get(TODO_ENDPOINT + "?offset=" + offset + "limit=" + limit);
//    }
// лучше переписать как ниже:

    @Override
    @Step("Read all by {offset, limit}")
    public Response readAll(int offset, int limit) {
        return given()
                .spec(reqSpec)
                .queryParam("offset", offset)
                .queryParam("limit", limit)
                .when()
                .get(new Endpoint(TODO_ENDPOINT).build());
    }

    @Override
    @Step("Read all")
    public Response readAll() {
        return given()
                .spec(reqSpec)
                .when()
                .get(new Endpoint(TODO_ENDPOINT).build());
    }
}

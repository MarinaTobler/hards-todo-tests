package com.todo;

import com.todo.requests.TodoRequest;
import com.todo.requests.TodoRequester;
import com.todo.specs.request.RequestSpec;
import com.todo.storages.TestDataStorage;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import com.todo.models.Todo;
import org.junit.jupiter.api.BeforeEach;

import static io.restassured.RestAssured.given;

public class BaseTest {
    // чтобы была доступна всем детям (protected):
    protected TodoRequester todoRequester;
    protected SoftAssertions softly;

    @BeforeAll
    public static void setup() {
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @BeforeEach
    public void setupTest() {
        todoRequester = new TodoRequester(RequestSpec.authSpecAsAdmin());
        softly = new SoftAssertions();
    }

    // не нужно:
//    protected void createTodo(Todo todo) {
//        given()
//                .contentType("application/json")
//                .body(todo)
//                .when()
//                .post("/todos")
//                .then()
//                .statusCode(201);
//    }
//
//    protected void deleteAllTodos() {
//
//        Todo[] todos = given()
//                .when()
//                .get("/todos")
//                .then()
//                .statusCode(200)
//                .extract()
//                .as(Todo[].class);
//
//        for (Todo todo : todos) {
//            given()
//                    .auth()
//                    .preemptive()
//                    .basic("admin", "admin")
//                    .when()
//                    .delete("/todos/" + todo.getId())
//                    .then()
//                    .statusCode(204);
//        }
//    }

    @AfterEach
    public void clean() {
        TestDataStorage.getInstance().getStorage()
                .forEach((k, v) ->
                        new TodoRequest(RequestSpec.authSpecAsAdmin())
                                .delete(k));

        TestDataStorage.getInstance().clean();
    }

    // Вызываем явное падение, когда мы уже накопили результат по каждой из ошибок
    @AfterEach
    public void assertAll() {
        softly.assertAll();
    }
}

package com.todo.get;


import com.todo.BaseTest;
import com.todo.annotations.DataPreparationExtension;
import com.todo.annotations.Mobile;
import com.todo.annotations.MobileExecutionExtension;
import com.todo.annotations.MobilePlusDataPreparationExtension;
import com.todo.annotations.PrepareTodo;
import com.todo.requests.TodoRequest;
import com.todo.requests.ValidatedTodoRequest;
import com.todo.specs.request.RequestSpec;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;

import com.todo.models.Todo;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Random;

@Epic("TODO Management")
@Feature("Get Todos API")
//@ExtendWith(DataPreparationExtension.class)
//@ExtendWith(MobileExecutionExtension.class)
@ExtendWith(MobilePlusDataPreparationExtension.class)
public class GetTodosTests extends BaseTest {

    @BeforeEach
    public void setupEach() {
        deleteAllTodos();
    }

    @Test
    @Description("Получение пустого списка TODO, когда база данных пуста")
    public void testGetTodosWhenDatabaseIsEmpty() {
//        TodoRequest todoRequest = new TodoRequest(RequestSpec.unauthSpec());
//        todoRequest.readAll()
        todoRequester.getRequest().readAll()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("", hasSize(0));
    }

    public void userCanCreateTodoWithArabicText() {
        // генерируем todo
        // проставляем text=arabic
        // создаем
        // проверяем успех
    }


    @Test
//    @Mobile
    @Description("Получение списка TODO с существующими записями")
    public void testGetTodosWithExistingEntries() {
        // Предварительно создать несколько TODO
//        Todo todo1 = new Todo(1, "Task 1", false);
        // если только буквы:
        Todo todo1 = new Todo(
                Integer.valueOf(RandomStringUtils.randomNumeric(3)),
                RandomStringUtils.randomAlphabetic(10),
//                new Random().nextBoolean());
                Math.random() < 0.5);
        Todo todo2 = new Todo(2, "Task 2", true);

//        ValidatedTodoRequest validatedTodoRequest = new ValidatedTodoRequest(RequestSpec.unauthSpec());
//        validatedTodoRequest.create(todo1);
//        validatedTodoRequest.create(todo2);
        todoRequester.getValidatedRequest().create(todo1);
        todoRequester.getValidatedRequest().create(todo2);
//        createTodo(todo1);
//        createTodo(todo2);

//        Response response =
//                given()
//                        .filter(new AllureRestAssured())
//                        .when()
//                        .get("/todos")
//                        .then()
//                        .statusCode(200)
//                        .contentType("application/json")
//                        .body("", hasSize(2))
//                        .extract().response();

//        Response response = new TodoRequest(RequestSpec.unauthSpec()).readAll()
        // ?? д.б. validated request?
        Response response = todoRequester.getRequest().readAll()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("", hasSize(2))
                .extract().response();

        // Дополнительная проверка содержимого
        Todo[] todos = response.getBody().as(Todo[].class);

        Assertions.assertEquals(1, todos[0].getId());
        Assertions.assertEquals("Task 1", todos[0].getText());
        Assertions.assertFalse(todos[0].isCompleted());

        Assertions.assertEquals(2, todos[1].getId());
        Assertions.assertEquals("Task 2", todos[1].getText());
        Assertions.assertTrue(todos[1].isCompleted());
    }

    @Test
    @Mobile // extension тут -> 1 вариант: properties (config), 2 вариант: storage request
    @PrepareTodo(5)
    @Description("Использование параметров offset и limit для пагинации")
    public void testGetTodosWithOffsetAndLimit() {
//        ValidatedTodoRequest validatedTodoRequest = new ValidatedTodoRequest(RequestSpec.unauthSpec());

        // Создаем 5 TODO
//        for (int i = 1; i <= 5; i++) {
//            validatedTodoRequest.create(new Todo(i, "Task " + i, i % 2 == 0));
//        }

        // Проверяем, что получили задачи с id 3 и 4
//        List<Todo> todos = validatedTodoRequest.readAll(2, 2);
        List<Todo> todos = todoRequester.getValidatedRequest().readAll(2, 2);

        Assertions.assertEquals(todos.size(), 2);
    }

    @Test
    @DisplayName("Передача некорректных значений в offset и limit")
    public void testGetTodosWithInvalidOffsetAndLimit() {
        // Тест с отрицательным offset
        given()
                .filter(new AllureRestAssured())
                .queryParam("offset", -1)
                .queryParam("limit", 2)
                .when()
                .get("/todos")
                .then()
                .statusCode(400)
                .contentType("text/plain")
                .body(containsString("Invalid query string"));

        // Тест с нечисловым limit
        given()
                .filter(new AllureRestAssured())
                .queryParam("offset", 0)
                .queryParam("limit", "abc")
                .when()
                .get("/todos")
                .then()
                .statusCode(400)
                .contentType("text/plain")
                .body(containsString("Invalid query string"));

        // Тест с отсутствующим значением offset
        given()
                .filter(new AllureRestAssured())
                .queryParam("offset", "")
                .queryParam("limit", 2)
                .when()
                .get("/todos")
                .then()
                .statusCode(400)
                .contentType("text/plain")
                .body(containsString("Invalid query string"));
    }

    @Test
    @Tag("Negative")
    @DisplayName("Set negative offset")
    public void testSetNegativeOffset() {
        TodoRequest todoRequest = new TodoRequest(RequestSpec.unauthSpec());
        todoRequest.readAll(-1, 2)
                .then()
                .statusCode(400)
                .contentType("text/plain")
                .body(containsString("Invalid query string"));
    }

    @Test
    @Tag("Negative")
    @Description("Set limit only, offset is empty")
    @DisplayName("Set limit only, offset is empty")
    public void testSetOnlyLimit() {
        ValidatedTodoRequest validatedTodoRequest = new ValidatedTodoRequest(RequestSpec.unauthSpec());
        validatedTodoRequest.readAll(0, 2);
    }

    @Test
    @DisplayName("Проверка ответа при превышении максимально допустимого значения limit")
    public void testGetTodosWithExcessiveLimit() {
//        ValidatedTodoRequest validatedTodoRequest = new ValidatedTodoRequest(RequestSpec.unauthSpec());
        // Создаем 10 TODO
        for (int i = 1; i <= 10; i++) {
//            createTodo(new Todo(i, "Task " + i, i % 2 == 0));
//            validatedTodoRequest.create(new Todo(i, "Task " + i, i % 2 == 0));
            todoRequester.getValidatedRequest().create(new Todo(i, "Task " + i, i % 2 == 0));
        }
//        Response response =
//                given()
//                        .filter(new AllureRestAssured())
//                        .queryParam("limit", 1000)
//                        .when()
//                        .get("/todos")
//                        .then()
//                        .statusCode(200)
//                        .contentType("application/json")
//                        .extract().response();
//
//        Todo[] todos = response.getBody().as(Todo[].class);

//        List<Todo> todos = validatedTodoRequest.readAll(0, 1000);
        List<Todo> todos = todoRequester.getValidatedRequest().readAll(0, 1000);
        // Проверяем, что вернулось 10 задач
//        Assertions.assertEquals(10, todos.length);
        Assertions.assertEquals(10, todos.size());
    }
}

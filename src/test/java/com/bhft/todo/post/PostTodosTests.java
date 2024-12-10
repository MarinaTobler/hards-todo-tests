package com.bhft.todo.post;

import com.bhft.todo.BaseTest;
import com.todo.models.Todo;
import com.todo.requests.TodoRequest;
import com.todo.requests.UnvalidatedTodoRequest;
import com.todo.requests.ValidatedTodoRequest;
import com.todo.specs.RequestSpec;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostTodosTests extends BaseTest {

    @BeforeEach
    public void setupEach() {
        deleteAllTodos();
    }

    @Test
    public void testCreateTodoWithValidData() {
//        Todo newTodo = new Todo(1, "New Task", false);
//
//        // Отправляем POST запрос для создания нового TODO
//        given()
//                .filter(new AllureRestAssured())
//                .contentType(ContentType.JSON)
//                .body(newTodo)
//                .when()
//                .post("/todos")
//                .then()
//                .statusCode(201)
//                .body(is(emptyOrNullString())); // Проверяем, что тело ответа пустое
//
//        // Проверяем, что TODO было успешно создано
//        Todo[] todos = given()
//                .when()
//                .get("/todos")
//                .then()
//                .statusCode(200)
//                .extract()
//                .as(Todo[].class);
//
//        // Ищем созданную задачу в списке
//        boolean found = false;
//        for (Todo todo : todos) {
//            if (todo.getId() == newTodo.getId()) {
//                Assertions.assertEquals(newTodo.getText(), todo.getText());
//                Assertions.assertEquals(newTodo.isCompleted(), todo.isCompleted());
//                found = true;
//                break;
//            }
//        }
//        Assertions.assertTrue(found, "Созданная задача не найдена в списке TODO");

// Updated version after Lesson-1:

        Todo newTodo = new Todo(1, "New Task", false);

        // Отправляем POST запрос для создания нового TODO
//Version 1:
//        String actualResponseBodyAsSting = new ValidatedTodoRequest(RequestSpec.authSpec())
//                .create(newTodo);
//        Assertions.assertEquals(emptyOrNullString(), actualResponseBodyAsSting);
//Version 2:
        Object actualResponseBody = new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .create(newTodo);
        Assertions.assertEquals(emptyOrNullString(), actualResponseBody);

        // Проверяем, что TODO было успешно создано
        Todo[] todos = new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .readAll();

        // Ищем созданную задачу в списке
        boolean found = false;
        for (Todo todo : todos) {
            if (todo.getId() == newTodo.getId()) {
                Assertions.assertEquals(newTodo.getText(), todo.getText());
                Assertions.assertEquals(newTodo.isCompleted(), todo.isCompleted());
                found = true;
                break;
            }
        }
        Assertions.assertTrue(found, "Созданная задача не найдена в списке TODO");
    }

    /**
     * TC2: Попытка создания TODO с отсутствующими обязательными полями.
     */
    @Test
    public void testCreateTodoWithMissingFields() {
//        // Создаем JSON без обязательного поля 'text'
//        String invalidTodoJson = "{ \"id\": 2, \"completed\": true }";
//
//        given()
//                .filter(new AllureRestAssured())
//                .contentType(ContentType.JSON)
//                .body(invalidTodoJson)
//                .when()
//                .post("/todos")
//                .then()
//                .statusCode(400)
//                .contentType(ContentType.TEXT)
//                .body(notNullValue()); // Проверяем, что есть сообщение об ошибке

// Updated version after Lesson-1:

        // Создаем JSON без обязательного поля 'text'
        String invalidTodoJson = "{ \"id\": 2, \"completed\": true }";

// Version 1:
//        Todo todoWithoutText = new Todo(1, null, true);
//        Object actualResponse = new TodoRequest(RequestSpec.authSpec())
//                .create(todoWithoutText)
//                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
//                .body(notNullValue());
//Version 2:
// ?? как вставить json вместо Todo?
// мы не можем передать некорректный тип данных, если в TodoRequest в create запрашивается Todo,
// поэтому сделала UnvalidatedTodoRequest класс, в нём create принимает json и не имплементирует crud
        new UnvalidatedTodoRequest(RequestSpec.unauthSpec())
                .create(invalidTodoJson)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .contentType(ContentType.TEXT)
                .body(notNullValue());
    }

    /**
     * TC3: Создание TODO с максимально допустимой длиной поля 'text'.
     */
    @Test
    public void testCreateTodoWithMaxLengthText() {
//        // Предполагаем, что максимальная длина поля 'text' составляет 255 символов
//        String maxLengthText = "A".repeat(255);
//        Todo newTodo = new Todo(3, maxLengthText, false);
//
//        // Отправляем POST запрос для создания нового TODO
//        given()
//                .filter(new AllureRestAssured())
//                .contentType(ContentType.JSON)
//                .body(newTodo)
//                .when()
//                .post("/todos")
//                .then()
//                .statusCode(201)
//                .body(is(emptyOrNullString())); // Проверяем, что тело ответа пустое
//
//        // Проверяем, что TODO было успешно создано
//        Todo[] todos = given()
//                .when()
//                .get("/todos")
//                .then()
//                .statusCode(200)
//                .extract()
//                .as(Todo[].class);
//
//        // Ищем созданную задачу в списке
//        boolean found = false;
//        for (Todo todo : todos) {
//            if (todo.getId() == newTodo.getId()) {
//                Assertions.assertEquals(newTodo.getText(), todo.getText());
//                Assertions.assertEquals(newTodo.isCompleted(), todo.isCompleted());
//                found = true;
//                break;
//            }
//        }
//        Assertions.assertTrue(found, "Созданная задача не найдена в списке TODO");

//Updated version after Lesson-1:

        // Предполагаем, что максимальная длина поля 'text' составляет 255 символов
        String maxLengthText = "A".repeat(255);
        Todo newTodo = new Todo(3, maxLengthText, false);

        // Отправляем POST запрос для создания нового TODO
        Object actualResponseBody = new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .create(newTodo);
        Assertions.assertEquals(emptyOrNullString(), actualResponseBody);

        // Проверяем, что TODO было успешно создано
        Todo[] todos = new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .readAll();

        // Ищем созданную задачу в списке
        boolean found = false;
        for (Todo todo : todos) {
            if (todo.getId() == newTodo.getId()) {
                Assertions.assertEquals(newTodo.getText(), todo.getText());
                Assertions.assertEquals(newTodo.isCompleted(), todo.isCompleted());
                found = true;
                break;
            }
        }
        Assertions.assertTrue(found, "Созданная задача не найдена в списке TODO");
    }

    /**
     * TC4: Передача некорректных типов данных в полях.
     */
    @Test
    public void testCreateTodoWithInvalidDataTypes() {
//        // Поле 'completed' содержит строку вместо булевого значения
//        Todo newTodo = new Todo(3, "djjdjd", false);
//
//        todoRequest.create(newTodo)
//                .then()
//                .statusCode(400)
//                .contentType(ContentType.TEXT)
//                .body(notNullValue()); // Проверяем, что есть сообщение об ошибке

//Updated version after Lesson-1:

        // Поле 'completed' содержит строку вместо булевого значения
        Todo newTodo = new Todo(3, "djjdjd", false);


        TodoRequest todoRequest = new TodoRequest(RequestSpec.unauthSpec());

// ?? как вставить json вместо Todo?
// мы не можем передать некорректный тип данных, если в TodoRequest в create запрашивается Todo,
// поэтому сделала UnvalidatedTodoRequest класс, в нём create принимает json и не имплементирует crud
        String invalidTodoJson = "{ \"id\": 3, \"text\": \"djjdjd\", \"completed\": \"String\" }";
        new UnvalidatedTodoRequest(RequestSpec.unauthSpec())
                .create(invalidTodoJson)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .contentType(ContentType.TEXT)
                .body(notNullValue());
    }

    /**
     * TC5: Создание TODO с уже существующим 'id' (если 'id' задается клиентом).
     */
    @Test
    public void testCreateTodoWithExistingId() {
//        // Сначала создаем TODO с id = 5
//        Todo firstTodo = new Todo(5, "First Task", false);
//        createTodo(firstTodo);
//
//        // Пытаемся создать другую TODO с тем же id
//        Todo duplicateTodo = new Todo(5, "Duplicate Task", true);
//
//        given()
//                .filter(new AllureRestAssured())
//                .contentType(ContentType.JSON)
//                .body(duplicateTodo)
//                .when()
//                .post("/todos")
//                .then()
//                .statusCode(400) // Конфликт при дублировании 'id'
//                //.contentType(ContentType.TEXT)
//                .body(is(notNullValue())); // Проверяем, что есть сообщение об ошибке

//Updated version after Lesson-1:
        // Сначала создаем TODO с id = 5
        Todo firstTodo = new Todo(5, "First Task", false);
        createTodo(firstTodo);

        // Пытаемся создать другую TODO с тем же id
        Todo duplicateTodo = new Todo(5, "Duplicate Task", true);

        new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .create(firstTodo);
        new TodoRequest(RequestSpec.unauthSpec())
                .create(duplicateTodo)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .contentType(ContentType.TEXT)
                .body(is(notNullValue()));
    }

}

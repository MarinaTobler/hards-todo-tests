package com.bhft.todo.put;

import com.bhft.todo.BaseTest;
import com.todo.requests.TodoRequest;
import com.todo.requests.UnvalidatedTodoRequest;
import com.todo.requests.ValidatedTodoRequest;
import com.todo.specs.RequestSpec;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import com.todo.models.Todo;

public class PutTodosTests extends BaseTest {

    @BeforeEach
    public void setupEach() {
        deleteAllTodos();
    }

    /**
     * TC1: Обновление существующего TODO корректными данными.
     */
    @Test
    public void testUpdateExistingTodoWithValidData() {
//        // Создаем TODO для обновления
//        Todo originalTodo = new Todo(1, "Original Task", false);
//        createTodo(originalTodo);
//
//        // Обновленные данные
//        Todo updatedTodo = new Todo(1, "Updated Task", true);
//
//        // Отправляем PUT запрос для обновления
//        given()
//                .filter(new ResponseLoggingFilter())
//                .contentType(ContentType.JSON)
//                .body(updatedTodo)
//                .when()
//                .put("/todos/" + updatedTodo.getId())
//                .then()
//                .statusCode(200);
//        //.contentType(ContentType.JSON)
////                .body("id", equalTo(1))
////                .body("text", equalTo("Updated Task"))
////                .body("completed", equalTo(true));
//
//        // Проверяем, что данные были обновлены
//        Todo[] todos = given()
//                .when()
//                .get("/todos")
//                .then()
//                .statusCode(200)
//                .extract()
//                .as(Todo[].class);
//
//        Assertions.assertEquals(1, todos.length);
//        Assertions.assertEquals("Updated Task", todos[0].getText());
//        Assertions.assertTrue(todos[0].isCompleted());

//Updated version after Lesson-1:

        // Создаем TODO для обновления
        Todo originalTodo = new Todo(1, "Original Task", false);
        new ValidatedTodoRequest(RequestSpec.authSpec())
                .create(originalTodo);

        // Обновленные данные
        Todo updatedTodo = new Todo(1, "Updated Task", true);

        // Отправляем PUT запрос для обновления
        Todo actualUpdatedTodo = new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .update(1, updatedTodo);
        Assertions.assertEquals(updatedTodo, actualUpdatedTodo);

        // Проверяем, что данные были обновлены
        Todo[] todos = given()
                .when()
                .get("/todos")
                .then()
                .statusCode(200)
                .extract()
                .as(Todo[].class);

        Assertions.assertEquals(todos[0], updatedTodo);


    }

    /**
     * TC2: Попытка обновления TODO с несуществующим id.
     */
    @Test
    public void testUpdateNonExistentTodo() {
//        // Обновленные данные для несуществующего TODO
//        Todo updatedTodo = new Todo(999, "Non-existent Task", true);
//
//        given()
//                .filter(new AllureRestAssured())
//                .contentType(ContentType.JSON)
//                .body(updatedTodo)
//                .when()
//                .put("/todos/" + updatedTodo.getId())
//                .then()
//                .statusCode(404)
//                //.contentType(ContentType.TEXT)
//                .body(is(notNullValue()));

//Updated version after Lesson-1:
        // Обновленные данные для несуществующего TODO
        Todo updatedTodo = new Todo(999, "Non-existent Task", true);

        new TodoRequest(RequestSpec.unauthSpec())
                .update(999, updatedTodo)
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .contentType(ContentType.TEXT)
                .body(is(notNullValue()));
    }

    /**
     * TC3: Обновление TODO с отсутствием обязательных полей.
     */
    @Test
    public void testUpdateTodoWithMissingFields() {
//        // Создаем TODO для обновления
//        Todo originalTodo = new Todo(2, "Task to Update", false);
//        createTodo(originalTodo);
//
//        // Обновленные данные с отсутствующим полем 'text'
//        String invalidTodoJson = "{ \"id\": 2, \"completed\": true }";
//
//        given()
//                .filter(new AllureRestAssured())
//                .contentType(ContentType.JSON)
//                .body(invalidTodoJson)
//                .when()
//                .put("/todos/2")
//                .then()
//  // Почему 401 Unauthorized? должен же быть 400 BadRequest?
//                .statusCode(401);
//        //.contentType(ContentType.JSON)
//        //.body("error", containsString("Missing required field 'text'"));

//Updated version after Lesson-1:
        // Создаем TODO для обновления
        Todo originalTodo = new Todo(2, "Task to Update", false);
        new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .create(originalTodo);

        // Обновленные данные с отсутствующим полем 'text'
        String invalidTodoJson = "{ \"id\": 2, \"completed\": true }";

        // Почему 401 Unauthorized? должен же быть 400 BadRequest?
        new UnvalidatedTodoRequest(RequestSpec.unauthSpec())
                .update(2, invalidTodoJson)
                .then().assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("error", containsString("Missing required field 'text'"));
    }

    /**
     * TC4: Передача некорректных типов данных при обновлении.
     */
    @Test
    public void testUpdateTodoWithInvalidDataTypes() {
//        // Создаем TODO для обновления
//        Todo originalTodo = new Todo(3, "Another Task", false);
//        createTodo(originalTodo);
//
//        // Обновленные данные с некорректным типом поля 'completed'
//        String invalidTodoJson = "{ \"id\": 3, \"text\": \"Updated Task\", \"completed\": \"notBoolean\" }";
//
//        given()
//                .filter(new AllureRestAssured())
//                .contentType(ContentType.JSON)
//                .body(invalidTodoJson)
//                .when()
//                .put("/todos/3")
//                .then()
//  // Почему 401 Unauthorized? должен же быть 400 BadRequest?
//                .statusCode(401);

//Updated version after Lesson-1:
        // Создаем TODO для обновления
        Todo originalTodo = new Todo(3, "Another Task", false);
        new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .create(originalTodo);
        // Обновленные данные с некорректным типом поля 'completed'
        String invalidTodoJson = "{ \"id\": 3, \"text\": \"Updated Task\", \"completed\": \"notBoolean\" }";

        new UnvalidatedTodoRequest(RequestSpec.unauthSpec())
                .update(3, invalidTodoJson)
                .then().assertThat()
// Почему 401 Unauthorized? должен же быть 400 BadRequest?
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    /**
     * TC5: Обновление TODO без изменения данных (передача тех же значений).
     */
    @Test
    public void testUpdateTodoWithoutChangingData() {
//        // Создаем TODO для обновления
//        Todo originalTodo = new Todo(4, "Task without Changes", false);
//        createTodo(originalTodo);
//
//        // Отправляем PUT запрос с теми же данными
//        given()
//                .filter(new AllureRestAssured())
//                .contentType(ContentType.JSON)
//                .body(originalTodo)
//                .when()
//                .put("/todos/4")
//                .then()
//                .statusCode(200);
//
//
//        // Проверяем, что данные не изменились
//        Todo[] todo = given()
//                .when()
//                .get("/todos")
//                .then()
//                .statusCode(200)
//                .extract()
//                .as(Todo[].class);
//
//        Assertions.assertEquals("Task without Changes", todo[0].getText());
//        Assertions.assertFalse(todo[0].isCompleted());

//Updated version after Lesson-1:
        // Создаем TODO для обновления
        Todo originalTodo = new Todo(4, "Task without Changes", false);
        new ValidatedTodoRequest(RequestSpec.authSpec())
                .create(originalTodo);

        // Отправляем PUT запрос с теми же данными

        Todo actualTodo = new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .update(4, originalTodo);

        // Проверяем, что данные не изменились
        Todo[] todo = given()
                .when()
                .get("/todos")
                .then()
                .statusCode(200)
                .extract()
                .as(Todo[].class);

        Assertions.assertEquals("Task without Changes", todo[0].getText());
        Assertions.assertFalse(todo[0].isCompleted());
        Assertions.assertEquals(originalTodo, todo[0]);
        Assertions.assertEquals(originalTodo, actualTodo);
    }
}

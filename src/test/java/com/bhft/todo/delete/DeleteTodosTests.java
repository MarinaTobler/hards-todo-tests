package com.bhft.todo.delete;

import com.bhft.todo.BaseTest;

import com.todo.models.User;
import com.todo.requests.TodoRequest;
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

import com.todo.models.Todo;

public class DeleteTodosTests extends BaseTest {

    @BeforeEach
    public void setupEach() {
        deleteAllTodos();
    }

    /**
     * TC1: Успешное удаление существующего TODO с корректной авторизацией.
     */
    @Test
    public void testDeleteExistingTodoWithValidAuth() {
//        // Создаем TODO для удаления
//        Todo todo = new Todo(1, "Task to Delete", false);
//        createTodo(todo);
//
//        // Отправляем DELETE запрос с корректной авторизацией
//        given()
//                .filter(new AllureRestAssured())
//                .auth()
//                .preemptive()
//                .basic("admin", "admin")
//                .when()
//                .delete("/todos/" + todo.getId())
//                .then()
//                .statusCode(204)
//                .body(is(emptyOrNullString())); // Проверяем, что тело ответа пустое
//
//        // Получаем список всех TODO и проверяем, что удаленная задача отсутствует
//        Todo[] todos = given()
//                .when()
//                .get("/todos")
//                .then()
//                .statusCode(200)
//                .extract()
//                .as(Todo[].class);
//
//        // Проверяем, что удаленная задача отсутствует в списке
//        boolean found = false;
//        for (Todo t : todos) {
//            if (t.getId() == todo.getId()) {
//                found = true;
//                break;
//            }
//        }
//        Assertions.assertFalse(found, "Удаленная задача все еще присутствует в списке TODO");

//Updated version after Lesson-1:
        // Создаем TODO для удаления
        Todo todo = new Todo(1, "Task to Delete", false);
        new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .create(todo);

        // Отправляем DELETE запрос с корректной авторизацией
        new TodoRequest(RequestSpec.authSpecForAdmin())
                .delete(1)
                .then().assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .body(is(emptyOrNullString())); // Проверяем, что тело ответа пустое

        // Получаем список всех TODO и проверяем, что удаленная задача отсутствует
        Todo[] todos = new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .readAll();

        // Проверяем, что удаленная задача отсутствует в списке
        boolean found = false;
        for (Todo t : todos) {
            if (t.getId() == todo.getId()) {
                found = true;
                break;
            }
        }
        Assertions.assertFalse(found, "Удаленная задача все еще присутствует в списке TODO");
    }

    /**
     * TC2: Попытка удаления TODO без заголовка Authorization.
     */
    @Test
    public void testDeleteTodoWithoutAuthHeader() {
//        // Создаем TODO для удаления
//        Todo todo = new Todo(2, "Task to Delete", false);
//        createTodo(todo);
//
//        // Отправляем DELETE запрос без заголовка Authorization
//        given()
//                .filter(new AllureRestAssured())
//                .when()
//                .delete("/todos/" + todo.getId())
//                .then()
//                .statusCode(401);
//                //.contentType(ContentType.JSON)
//                //.body("error", notNullValue()); // Проверяем наличие сообщения об ошибке
//
//        // Проверяем, что TODO не было удалено
//        Todo[] todos = given()
//                .when()
//                .get("/todos")
//                .then()
//                .statusCode(200)
//                .extract()
//                .as(Todo[].class);
//
//        // Проверяем, что задача все еще присутствует в списке
//        boolean found = false;
//        for (Todo t : todos) {
//            if (t.getId() == todo.getId()) {
//                found = true;
//                break;
//            }
//        }
//        Assertions.assertTrue(found, "Задача отсутствует в списке TODO, хотя не должна была быть удалена");

//Updated version after Lesson-1:
        // Создаем TODO для удаления
        Todo todo = new Todo(2, "Task to Delete", false);
        new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .create(todo);

//??? Отправляем DELETE запрос без заголовка Authorization ??? Как переписать, когда есть спецификация?
        given()
                .filter(new AllureRestAssured())
                .when()
                .delete("/todos/" + todo.getId())
                .then()
                .statusCode(401);
        //.contentType(ContentType.JSON)
        //.body("error", notNullValue()); // Проверяем наличие сообщения об ошибке

        // Проверяем, что TODO не было удалено
        Todo[] todos = new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .readAll();

        // Проверяем, что задача все еще присутствует в списке
        boolean found = false;
        for (Todo t : todos) {
            if (t.getId() == todo.getId()) {
                found = true;
                break;
            }
        }
        Assertions.assertTrue(found, "Задача отсутствует в списке TODO, хотя не должна была быть удалена");
    }

    /**
     * TC3: Попытка удаления TODO с некорректными учетными данными.
     */
    @Test
    public void testDeleteTodoWithInvalidAuth() {
//        // Создаем TODO для удаления
//        Todo todo = new Todo(3, "Task to Delete", false);
//        createTodo(todo);
//
//        // Отправляем DELETE запрос с некорректной авторизацией
//        given()
//                .filter(new AllureRestAssured())
//                .auth()
//                .preemptive()
//                .basic("invalidUser", "invalidPass")
//                .when()
//                .delete("/todos/" + todo.getId())
//                .then()
//                .statusCode(401);
////                .contentType(ContentType.JSON)
////                .body("error", notNullValue());
//
//        // Проверяем, что TODO не было удалено
//        Todo[] todos = given()
//                .when()
//                .get("/todos")
//                .then()
//                .statusCode(200)
//                .extract()
//                .as(Todo[].class);
//
//        // Проверяем, что задача все еще присутствует в списке
//        boolean found = false;
//        for (Todo t : todos) {
//            if (t.getId() == todo.getId()) {
//                found = true;
//                break;
//            }
//        }
//        Assertions.assertTrue(found, "Задача отсутствует в списке TODO, хотя не должна была быть удалена");

//Updated version after Lesson-1:
        // Создаем TODO для удаления
        Todo todo = new Todo(3, "Task to Delete", false);
        new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .create(todo);

        // Отправляем DELETE запрос с некорректной авторизацией
        User user = new User("invalidUser", "invalidPass");
        new TodoRequest(RequestSpec.authSpec(user))
                .delete(3)
                .then().assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .contentType(ContentType.JSON)
                .body("error", notNullValue());

        // Проверяем, что TODO не было удалено
        Todo[] todos = new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .readAll();

        // Проверяем, что задача все еще присутствует в списке
        boolean found = false;
        for (Todo t : todos) {
            if (t.getId() == todo.getId()) {
                found = true;
                break;
            }
        }
        Assertions.assertTrue(found, "Задача отсутствует в списке TODO, хотя не должна была быть удалена");
    }

    /**
     * TC4: Удаление TODO с несуществующим id.
     */
    @Test
    public void testDeleteNonExistentTodo() {
        // Отправляем DELETE запрос для несуществующего TODO с корректной авторизацией

        new TodoRequest(RequestSpec.authSpecForAdmin())
                .delete(999)
                .then().assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .contentType(ContentType.JSON)
                .body("error", notNullValue());

        // Дополнительно можем проверить, что список TODO не изменился
        Todo[] todos = new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .readAll();

        // В данном случае, поскольку мы не добавляли задач с id 999, список должен быть пуст или содержать только ранее добавленные задачи
    }

    /**
     * TC5: Попытка удаления с некорректным форматом id (например, строка вместо числа).
     */
    @Test
    public void testDeleteTodoWithInvalidIdFormat() {
        // Отправляем DELETE запрос с некорректным id
//??? Как в этом случае посылать String вместо long в TodoRequest delete требует long id?
        given()
                .filter(new AllureRestAssured())
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .when()
                .delete("/todos/invalidId")
                .then()
                .statusCode(404);
//                .contentType(ContentType.JSON)
//                .body("error", notNullValue());
    }
}

package com.todo.put;

import com.todo.BaseTest;
import com.todo.annotations.BeforeEachExtension;
import com.todo.annotations.PrepareTodo;
import com.todo.models.Todo;
import com.todo.requests.UnvalidatedTodoRequest;
import com.todo.requests.ValidatedTodoRequest;
import com.todo.specs.request.RequestSpec;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Epic("TODO Management")
@Feature("Put Todos API")
@ExtendWith(BeforeEachExtension.class)
public class PutTodosTests extends BaseTest {

//    @BeforeEach
//    public void setupEach() {
//        deleteAllTodos();
//    }

    /**
     * TC1: Обновление существующего TODO корректными данными.
     */
    @Test
    @Tag("Positive")
    @Description("Authorized user can update todo with valid data")
//    public void testUpdateExistingTodoWithValidData() {
    public void userCanUpdateExistingTodoWithValidData() {


        // Создаем TODO для обновления
        Todo originalTodo = new Todo(1, "Original Task", false);
//        createTodo(originalTodo);
//        ValidatedTodoRequest authValReq = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());
//        authValReq.create(originalTodo);
        todoRequester.getValidatedRequest().create(originalTodo);

        // Обновленные данные
        Todo updatedTodo = new Todo(1, "Updated Task", true);
        // Отправляем PUT запрос для обновления
        // !! Здесь отправляем ValidatedTodoRequest!, кот. возвращает десерелизованный объект
//        Todo actualUpdatedTodo = authValReq.update(1, updatedTodo);
//        Todo actualUpdatedTodo = authValReq.update(originalTodo.getId(), updatedTodo);
        Todo actualUpdatedTodo = todoRequester.getValidatedRequest().update(originalTodo.getId(), updatedTodo);

        // Проверяем, что данные были обновлены
        Assertions.assertEquals(updatedTodo, actualUpdatedTodo);
//        List<Todo> todos = authValReq.readAll();
        List<Todo> todos = todoRequester.getValidatedRequest().readAll();
        Assertions.assertEquals(todos.getFirst(), updatedTodo);
        Assertions.assertEquals(1, todos.size());
        Assertions.assertEquals("Updated Task", todos.getFirst().getText());
        Assertions.assertTrue(todos.getFirst().isCompleted());
    }

    /**
     * TC2: Попытка обновления TODO с несуществующим id.
     */
    @Test
    @Tag("Negative")
    public void testUpdateNonExistentTodo() {
//Updated version after Lesson-1:

        // Обновленные данные для несуществующего TODO
        Todo updatedTodo = new Todo(999, "Non-existent Task", true);

//        given()
//                .filter(new AllureRestAssured())
//                .contentType(ContentType.JSON)
//                .body(updatedTodo)
//                .when()
//                .put("/todos/" + updatedTodo.getId())
//                .then()
//                .statusCode(404)
//                .contentType(ContentType.TEXT)
//                .body(is(notNullValue()));

//        TodoRequest todoReq = new TodoRequest(RequestSpec.unauthSpec());
//        todoReq.update(updatedTodo.getId(), updatedTodo)
        todoRequester.getRequest().update(updatedTodo.getId(), updatedTodo)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(is(notNullValue())); // Проверяем, что есть сообщение об ошибке
    }


//    /**
//     * TC3: Обновление TODO с отсутствием обязательных полей.
//     */
//    @Disabled("NB! Этот тест не нужен, т.к. этo контрактное тестирование!, t.k. у нас все поля обязательные")
//    @Test
//    public void testUpdateTodoWithMissingFields() {
////Updated version after Lesson-1:
//        // Создаем TODO для обновления
//        Todo originalTodo = new Todo(2, "Task to Update", false);
//        new ValidatedTodoRequest(RequestSpec.unauthSpec())
//                .create(originalTodo);
//
//        // Обновленные данные с отсутствующим полем 'text'
//        String invalidTodoJson = "{ \"id\": 2, \"completed\": true }";
//
//        // Почему 401 Unauthorized? должен же быть 400 BadRequest?
//        new UnvalidatedTodoRequest(RequestSpec.unauthSpec())
//                .update(2, invalidTodoJson)
//                .then().assertThat()
//                .statusCode(HttpStatus.SC_BAD_REQUEST)
//                .contentType(ContentType.JSON)
//                .body("error", containsString("Missing required field 'text'"));
//    }

//    /**
//     * TC4: Передача некорректных типов данных при обновлении.
//     */
//    @Disabled("NB! Этот тест не нужен, т.к. этo контрактное тестирование!")
//    @Test
//    public void testUpdateTodoWithInvalidDataTypes() {
//
////Updated version after Lesson-1:
//        // Создаем TODO для обновления
//        Todo originalTodo = new Todo(3, "Another Task", false);
//        new ValidatedTodoRequest(RequestSpec.unauthSpec())
//                .create(originalTodo);
//        // Обновленные данные с некорректным типом поля 'completed'
//        String invalidTodoJson = "{ \"id\": 3, \"text\": \"Updated Task\", \"completed\": \"notBoolean\" }";
//
//        new UnvalidatedTodoRequest(RequestSpec.unauthSpec())
//                .update(3, invalidTodoJson)
//                .then().assertThat()
//// Почему 401 Unauthorized? должен же быть 400 BadRequest?
//                .statusCode(HttpStatus.SC_BAD_REQUEST);
//    }

    /**
     * TC5: Обновление TODO без изменения данных (передача тех же значений).
     */
    @Test
    @Tag("Positive")
    public void testUpdateTodoWithoutChangingData() {

        // Создаем TODO для обновления
        Todo originalTodo = new Todo(4, "Task without Changes", false);
//        createTodo(originalTodo);
//        ValidatedTodoRequest valAuthReq = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());
//        valAuthReq.create(originalTodo);
        todoRequester.getValidatedRequest().create(originalTodo);

        // Отправляем PUT запрос с теми же данными
//        Todo actualTodo = valAuthReq.update(4, originalTodo);
        Todo actualTodo = todoRequester.getValidatedRequest().update(4, originalTodo);

        // Проверяем, что данные не изменились
//        List<Todo> todos = valAuthReq.readAll();
        List<Todo> todos = todoRequester.getValidatedRequest().readAll();
        Assertions.assertEquals(originalTodo, actualTodo);
        Assertions.assertEquals(originalTodo, todos.getFirst());
        Assertions.assertEquals("Task without Changes", todos.getFirst().getText());
        Assertions.assertFalse(todos.getFirst().isCompleted());
    }
}

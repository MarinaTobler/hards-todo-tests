package com.bhft.todo.put;

import com.bhft.todo.BaseTest;
import com.todo.models.Todo;
import com.todo.requests.TodoRequest;
import com.todo.requests.UnvalidatedTodoRequest;
import com.todo.requests.ValidatedTodoRequest;
import com.todo.specs.RequestSpec;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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
//Updated version after Lesson-1:

        ValidatedTodoRequest authValReq = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());

        // Создаем TODO для обновления
        Todo originalTodo = new Todo(1, "Original Task", false);
        authValReq.create(originalTodo);

        // Обновленные данные
        Todo updatedTodo = new Todo(1, "Updated Task", true);
        // Отправляем PUT запрос для обновления
        // !! Здесь отправляем ValidatedTodoRequest!, кот. возвращает десерелизованный объект
//        Todo actualUpdatedTodo = authValReq.update(1, updatedTodo);
        Todo actualUpdatedTodo = authValReq.update(originalTodo.getId(), updatedTodo);

        // Проверяем, что данные были обновлены
        Assertions.assertEquals(updatedTodo, actualUpdatedTodo);
        List<Todo> todos = authValReq.readAll();
        Assertions.assertEquals(todos.getFirst(), updatedTodo);
        Assertions.assertEquals(1, todos.size());
        Assertions.assertEquals("Updated Task", todos.getFirst().getText());
        Assertions.assertTrue(todos.getFirst().isCompleted());
    }

    /**
     * TC2: Попытка обновления TODO с несуществующим id.
     */
    @Test
    public void testUpdateNonExistentTodo() {
//Updated version after Lesson-1:

        // Обновленные данные для несуществующего TODO
        Todo updatedTodo = new Todo(999, "Non-existent Task", true);

        TodoRequest todoReq = new TodoRequest(RequestSpec.unauthSpec());
        todoReq.update(updatedTodo.getId(), updatedTodo)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(is(notNullValue())); // Проверяем, что есть сообщение об ошибке
    }


    /// NB! Этот тест не нужен, т.к. этo контрактное тестирование!
    /**
     * TC3: Обновление TODO с отсутствием обязательных полей.
     */
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

    /// NB! Этот тест не нужен, т.к. этo контрактное тестирование!
    /**
     * TC4: Передача некорректных типов данных при обновлении.
     */
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
    public void testUpdateTodoWithoutChangingData() {
//Updated version after Lesson-1:

        // Создаем TODO для обновления
        Todo originalTodo = new Todo(4, "Task without Changes", false);
        ValidatedTodoRequest valAuthReq = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());
        valAuthReq.create(originalTodo);

        // Отправляем PUT запрос с теми же данными
        Todo actualTodo = valAuthReq.update(4, originalTodo);

        // Проверяем, что данные не изменились
        List<Todo> todos = valAuthReq.readAll();
        Assertions.assertEquals(originalTodo, actualTodo);
        Assertions.assertEquals(originalTodo, todos.getFirst());
        Assertions.assertEquals("Task without Changes", todos.getFirst().getText());
        Assertions.assertFalse(todos.getFirst().isCompleted());
    }
}

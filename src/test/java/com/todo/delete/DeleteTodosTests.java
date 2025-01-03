package com.todo.delete;

import com.todo.BaseTest;
import com.todo.models.Todo;
import com.todo.requests.ValidatedTodoRequest;
import com.todo.specs.request.RequestSpec;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;

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
        //Updated version after Lesson-1:
        // Создаем TODO для удаления
        Todo todo = new Todo(1, "Task to Delete", false);

//        ValidatedTodoRequest valAuthReq = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());
//        valAuthReq.create(todo);

        todoRequester.getValidatedRequest().create(todo);


        // Отправляем DELETE запрос с корректной авторизацией
//        valAuthReq.delete(todo.getId());
        todoRequester.getValidatedRequest().delete(todo.getId());
        // не надо здесь проверять, что тело ответа пустое, тк уже должно быть проверено в ValidatedTodoRequest
//        String responseBody = valAuthReq.delete(todo.getId());
//      // Assertions.assertEquals(emptyOrNullString(),responseBody);
//        assertThat(responseBody).isNullOrEmpty(); // Проверяем, что тело ответа пустое

        // Получаем список всех TODO и проверяем, что удаленная задача отсутствует
//        List<Todo> todos = new ValidatedTodoRequest(RequestSpec.unauthSpec())
//                .readAll();
        List<Todo> todos = todoRequester.getValidatedRequest().readAll();

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

    /// NB! Этот тест не нужен, т.к. этo контрактное тестирование!
    /**
     * TC2: Попытка удаления TODO без заголовка Authorization.
     */
//    @Test
//    public void testDeleteTodoWithoutAuthHeader() {
////Updated version after Lesson-1:
//        // Создаем TODO для удаления
//        Todo todo = new Todo(2, "Task to Delete", false);
//        new ValidatedTodoRequest(RequestSpec.unauthSpec())
//                .create(todo);
//
////??? Отправляем DELETE запрос без заголовка Authorization ??? Как переписать, когда есть спецификация?
//        given()
//                .filter(new AllureRestAssured())
//                .when()
//                .delete("/todos/" + todo.getId())
//                .then()
//                .statusCode(401);
//        //.contentType(ContentType.JSON)
//        //.body("error", notNullValue()); // Проверяем наличие сообщения об ошибке
//
//        // Проверяем, что TODO не было удалено
//        List<Todo> todos = new ValidatedTodoRequest(RequestSpec.unauthSpec())
//                .readAll();
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
//    }

    /**
     * TC3: Попытка удаления TODO с некорректными учетными данными.
     */
    //"Not-Authorized user. Delete ToDo."
    @Test
    public void testDeleteTodoWithInvalidAuth() {
//Updated version after Lesson-1:
        // Создаем TODO для удаления
        Todo todo = new Todo(3, "Task to Delete", false);
        ValidatedTodoRequest valAuthReq = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());
        valAuthReq.create(todo);



        // Отправляем DELETE запрос с некорректной авторизацией
//        User user = new User("invalidUser", "invalidPass");
//        TodoRequest todoReq = new TodoRequest(RequestSpec.authSpec(user));
        // or !! с неавтроризованным юзером
//        TodoRequest todoReq = new TodoRequest(RequestSpec.unauthSpec());
//        todoReq
        todoRequester.getRequest()
                .delete(3)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("error", notNullValue()); // проверяем, что есть сообщение об ошибке

        // Проверяем, что TODO не было удалено
//        List<Todo> todos = valAuthReq.readAll();
        List<Todo> todos = todoRequester.getValidatedRequest().readAll();

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
        long id = 999;
//        TodoRequest authReq = new TodoRequest(RequestSpec.authSpecForAdmin());
//        authReq.delete(id)
        todoRequester.getRequest().delete(id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("error", notNullValue()); // проверяем, что есть сообщение об ошибке

        // Дополнительно можем проверить, что список TODO не изменился
//        ValidatedTodoRequest valAuthReq = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());
//        List<Todo> todos = valAuthReq.readAll();
        List<Todo> todos = todoRequester.getValidatedRequest().readAll();
        // В данном случае, поскольку мы не добавляли задач с id 999, список должен быть пуст или содержать только ранее добавленные задачи
    }

    /// NB! Этот тест не нужен, т.к. этo контрактное тестирование!
    /**
     * TC5: Попытка удаления с некорректным форматом id (например, строка вместо числа).
     */
//    @Test
//    public void testDeleteTodoWithInvalidIdFormat() {
    // Отправляем DELETE запрос с некорректным id
//??? Как в этом случае посылать String вместо long в TodoRequest delete требует long id?
//        given()
//                .filter(new AllureRestAssured())
//                .auth()
//                .preemptive()
//                .basic("admin", "admin")
//                .when()
//                .delete("/todos/invalidId")
//                .then()
//                .statusCode(404);
//                .contentType(ContentType.JSON)
//                .body("error", notNullValue());
//    }
}

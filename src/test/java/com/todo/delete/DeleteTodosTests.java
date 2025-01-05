package com.todo.delete;

import com.todo.BaseTest;
import com.todo.annotations.PrepareTodo;
import com.todo.models.Todo;
import com.todo.requests.TodoRequest;
import com.todo.requests.ValidatedTodoRequest;
import com.todo.specs.request.RequestSpec;
import com.todo.specs.response.AccessErrorResponse;
import com.todo.specs.response.IncorrectDataResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@Epic("TODO Management")
@Feature("Delete Todos API")
public class DeleteTodosTests extends BaseTest {

    // не надо это делать в к. тесте, делается один раз в BaseTest в @AfterEach - паттерн Декоратор!
//    @BeforeEach
//    public void setupEach() {
//        deleteAllTodos();
//    }

    // описание того, что происходит в тесте надо поместить в аннотацию Allure @Description
    // Naming всегда лучше делать через название требования, кот. обычно звучит как юзер сценарий:
    // 1. Кто делает действие и какой у него статус
    // 2. Что он может сделать?
//    /**
//     * TC1: Успешное удаление существующего TODO с корректной авторизацией.
//     */
    @Test
    @Description("ТС1: Авторизированный юзер может удалить todo")
    @PrepareTodo(1)
    public void testDeleteExistingTodoWithValidAuth() {

        // Создаем TODO для удаления

//        Todo todo = new Todo(1, "Task to Delete", false);
//        ValidatedTodoRequest valAuthReq = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());
//        valAuthReq.create(todo);
        // создание todo делаем через аннотацию @PrepareTodo
        // чтобы понять, с каким id был создан тестовый todo для удаления:
        // (был использован Фасад паттерн - todoRequester)
        // используем здесь ValidatedRequest, тк нам надо, чтобы запрос возвращал уже сериализованную коллекцию,
        // где можно взять First
//        todoRequester.getValidatedRequest().create(todo);
        Todo createdTodo = todoRequester.getValidatedRequest().readAll().getFirst();

        // Отправляем DELETE запрос с корректной авторизацией
        // не надо здесь проверять, что тело ответа пустое, тк уже должно быть проверено в ValidatedTodoRequest
        // берём здесь ValidatedRequest, тк это положительный тест кейс
//        String responseBody = valAuthReq.delete(todo.getId());
//      // Assertions.assertEquals(emptyOrNullString(),responseBody);
//        assertThat(responseBody).isNullOrEmpty(); // Проверяем, что тело ответа пустое
        //        valAuthReq.delete(todo.getId());
//        todoRequester.getValidatedRequest().delete(todo.getId());
        todoRequester.getValidatedRequest().delete(createdTodo.getId());
        //

        // Получаем список всех TODO и проверяем, что удаленная задача отсутствует
//        List<Todo> todos = new ValidatedTodoRequest(RequestSpec.unauthSpec())
//                .readAll();
//        List<Todo> todos = todoRequester.getValidatedRequest().readAll();
//        // Проверяем, что удаленная задача отсутствует в списке
//        boolean found = false;
//        for (Todo t : todos) {
//            if (t.getId() == todo.getId()) {
//                found = true;
//                break;
//            }
//        }
//        Assertions.assertFalse(found, "Удаленная задача все еще присутствует в списке TODO");
        // Используем Soft ассерты, чтобы не падать на проверке, а накапливать ошибки в отчёт и проверять дальше
        // они нужны во всех тестах, поэтому нах-ся в BaseTest классе,
        // создаются при сетапе теста и
        // падают в конце в @AfterEach
        softly.assertThat(todoRequester.getValidatedRequest().readAll()).hasSize(0);
    }

    //    /**
//     * TC2: Попытка удаления TODO без заголовка Authorization.
//     */
    // Описание теста помещаем в аннотацию @Description
    @Description("ТС2: Неавторизированный юзер не может удалить todo")
    @PrepareTodo(1)
    @Test
    public void testDeleteTodoWithoutAuthHeader() {
        // Создаем TODO для удаления
//        Todo todo = new Todo(2, "Task to Delete", false);
//        new ValidatedTodoRequest(RequestSpec.unauthSpec())
//                .create(todo);
        Todo createdTodo = todoRequester.getValidatedRequest().readAll().getFirst();

////??? Отправляем DELETE запрос без заголовка Authorization ??? Как переписать, когда есть спецификация?
//        given()
//                .filter(new AllureRestAssured())
//                .when()
//                .delete("/todos/" + todo.getId())
//                .then()
//                .statusCode(401);
//        //.contentType(ContentType.JSON)
//        //.body("error", notNullValue()); // Проверяем наличие сообщения об ошибке
        // Далее используем новый Фасад, тк тот фасад был создан на основе аутентифицированного юзера!,
        // поэтому делаем новый todoRequest, параметризуем его с помощью неаутентифицированного юзера
        new TodoRequest(RequestSpec.unauthSpec()).delete(createdTodo.getId())
                .then().assertThat()
                // здесь используем паттерн Стратегия, в котором передаём разную стратегию обработки ошибок
                .spec(AccessErrorResponse.userIsUnauthorized());

        // Проверяем, что TODO не было удалено
//        List<Todo> todos = new ValidatedTodoRequest(RequestSpec.unauthSpec())
//                .readAll();
//        // Проверяем, что задача все еще присутствует в списке
//        boolean found = false;
//        for (Todo t : todos) {
//            if (t.getId() == todo.getId()) {
//                found = true;
//                break;
//            }
//        }
//        Assertions.assertTrue(found, "Задача отсутствует в списке TODO, хотя не должна была быть удалена");
        softly.assertThat(todoRequester.getValidatedRequest().readAll()).hasSize(1);
    }

    // Следующий тест кейс дублирует предыдущий!

//    /**
//     * TC3: Попытка удаления TODO с некорректными учетными данными.
//     */
//    //"Not-Authorized user. Delete ToDo."
//    @Test
//    @Disabled("Duplicates TC2")
//    public void testDeleteTodoWithInvalidAuth() {
//
//        // Создаем TODO для удаления
//        Todo todo = new Todo(3, "Task to Delete", false);
//        ValidatedTodoRequest valAuthReq = new ValidatedTodoRequest(RequestSpec.authSpecAsAdmin());
//        valAuthReq.create(todo);
//
//        // Отправляем DELETE запрос с некорректной авторизацией
////        User user = new User("invalidUser", "invalidPass");
////        TodoRequest todoReq = new TodoRequest(RequestSpec.authSpec(user));
//        // or !! с неавтроризованным юзером
////        TodoRequest todoReq = new TodoRequest(RequestSpec.unauthSpec());
////        todoReq
//        todoRequester.getRequest()
//                .delete(3)
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.SC_UNAUTHORIZED)
//                .body("error", notNullValue()); // проверяем, что есть сообщение об ошибке
//
//        // Проверяем, что TODO не было удалено
////        List<Todo> todos = valAuthReq.readAll();
//        List<Todo> todos = todoRequester.getValidatedRequest().readAll();
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

    //    /**
//     * TC4: Удаление TODO с несуществующим id.
//     */
    @Test
    @Description("ТС4: Авторизованный юзер не может удалить todo с несуществующим id")
    public void testDeleteNonExistentTodo() {

//        long id = 999;
        // не надо генерировать новый тодо, достаточно сгенерировать несуществующий ид
        var nonExistingId = new Random().nextInt();

        // Отправляем DELETE запрос для несуществующего TODO с корректной авторизацией
//        TodoRequest authReq = new TodoRequest(RequestSpec.authSpecForAdmin());
//        authReq.delete(id)
//        todoRequester.getRequest().delete(id)
        // тк негативный сценарий, поэтому надо использовать невалидированный запрос
        todoRequester.getRequest().delete(nonExistingId)
                .then().assertThat()
//                .statusCode(HttpStatus.SC_NOT_FOUND)
//                .body("error", notNullValue()); // проверяем, что есть сообщение об ошибке
                // здесь используем паттерн Стратегия, в котором передаём разную стратегию обработки ошибок
                .spec(IncorrectDataResponse.nonExistingId(nonExistingId));

        // Дополнительно можем проверить, что список TODO не изменился
        // NB! Не надо!
////        ValidatedTodoRequest valAuthReq = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());
////        List<Todo> todos = valAuthReq.readAll();
//        List<Todo> todos = todoRequester.getValidatedRequest().readAll();
//        // В данном случае, поскольку мы не добавляли задач с id 999, список должен быть пуст или содержать только ранее добавленные задачи
    }

    //    /**
//     * TC5: Попытка удаления с некорректным форматом id (например, строка вместо числа).
//     */
//    @Disabled("NB! Этот тест не нужен, т.к. этo контрактное тестирование!")
//    @Test
//    public void testDeleteTodoWithInvalidIdFormat() {
////     Отправляем DELETE запрос с некорректным id
////??? Как в этом случае посылать String вместо long в TodoRequest delete требует long id?
//        given()
//                .filter(new AllureRestAssured())
//                .auth()
//                .preemptive()
//                .basic("admin", "admin")
//                .when()
//                .delete("/todos/invalidId")
//                .then()
//                .statusCode(404)
//                .contentType(ContentType.JSON)
//                .body("error", notNullValue());
//    }
}

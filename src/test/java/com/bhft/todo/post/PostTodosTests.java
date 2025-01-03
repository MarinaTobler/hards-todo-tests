package com.bhft.todo.post;

import com.bhft.todo.BaseTest;
import com.todo.models.Todo;
import com.todo.models.TodoBuilder;
import com.todo.requests.TodoRequest;
import com.todo.requests.ValidatedTodoRequest;
import com.todo.specs.request.RequestSpec;
import com.todo.specs.response.IncorrectDataResponse;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PostTodosTests extends BaseTest {

    @BeforeEach
    public void setupEach() {
        deleteAllTodos();
    }

    @Test
    public void testCreateTodoWithValidData() {
// Updated version after Lesson-1:
        Todo newTodo = new Todo(1, "New Task", false);

        ValidatedTodoRequest valAuthReq = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());
        // Отправляем POST запрос для создания нового Todo
        valAuthReq.create(newTodo);
        // Не надо здесь проверять, что ответе получаем стринг и что он пустой.
//        String actualResponseBody = valAuthReq.create(newTodo);
//        Assertions.assertEquals(emptyOrNullString(), actualResponseBody); // Проверяем, что тело ответа пустое
//        assertThat(actualResponseBody).isNullOrEmpty(); // Проверяем, что тело ответа пустое

        // Проверяем, что TODO было успешно создано
        List<Todo> todos = valAuthReq.readAll();

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

    /// NB! Этот тест не нужен, т.к. этo контрактное тестирование!
    /**
     * TC2: Попытка создания TODO с отсутствующими обязательными полями.
     */
    @Test
    public void testCreateTodoWithMissingFields() {
//
//        // Создаем JSON без обязательного поля 'text'
//        String invalidTodoJson = "{ \"id\": 2, \"completed\": true }";
//// ?? как вставить json вместо Todo?
//// мы не можем передать некорректный тип данных, если в TodoRequest в create запрашивается Todo,
//// поэтому сделала UnvalidatedTodoRequest класс, в нём create принимает json и не имплементирует crud
//        new UnvalidatedTodoRequest(RequestSpec.unauthSpec())
//                .create(invalidTodoJson)
//                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
//                .contentType(ContentType.TEXT)
//                .body(notNullValue());
//   NB!   Вместо этого используем Builder (который позволяет не вставлять все поля):

        Todo newTodo = new TodoBuilder()
                .setText("text")
                .build();

        TodoRequest authReq = new TodoRequest(RequestSpec.authSpecForAdmin());
        authReq.create(newTodo)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .contentType(ContentType.TEXT)
                .body(notNullValue()); // Проверяем, что есть сообщение об ошибке
    }

    /**
     * TC3: Создание TODO с максимально допустимой длиной поля 'text'.
     */
    @Test
    public void testCreateTodoWithMaxLengthText() {
//Updated version after Lesson-1:

        // Предполагаем, что максимальная длина поля 'text' составляет 255 символов
        String maxLengthText = "A".repeat(255);
        Todo newTodo = new Todo(3, maxLengthText, false);

        // Отправляем POST запрос для создания нового TODO

        // Улучшение 1:
//        ValidatedTodoRequest authValReg = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());
//        authValReg.create(newTodo);

        // Улучшение 2: заменяем на использование фасада - todoRequester'a:
        todoRequester.getValidatedRequest().create(newTodo);

        // В этом тесте не надо проверять, что Response body empty, проверяем только, что тодо создаётся.
//      String actualResponseBody = new ValidatedTodoRequest(RequestSpec.unauthSpec())
//                .create(newTodo);
//        Assertions.assertEquals(emptyOrNullString(), actualResponseBody);

        // Проверяем, что TODO было успешно создано
//      Вместо:
//        List<Todo> todos = new ValidatedTodoRequest(RequestSpec.unauthSpec())
//                .readAll();
//      Пишем:
//        List<Todo> todos = authValReg.readAll();
        // Улучшение 2: заменяем на использование фасада - todoRequester'a:
        List<Todo> todos = todoRequester.getValidatedRequest().readAll();

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

    /// NB! Этот тест не нужен, т.к. этo контрактное тестирование!
    /**
     * TC4: Передача некорректных типов данных в полях.
     */
    @Test
    public void testCreateTodoWithInvalidDataTypes() {
//        // Поле 'completed' содержит строку вместо булевого значения

//        Todo newTodo = new Todo(3, "djjdjd", false);
//// ?? как вставить json вместо Todo?
//// мы не можем передать некорректный тип данных, если в TodoRequest в create запрашивается Todo,
//// поэтому сделала UnvalidatedTodoRequest класс, в нём create принимает json и не имплементирует crud
//        String invalidTodoJson = "{ \"id\": 3, \"text\": \"djjdjd\", \"completed\": \"String\" }";
//        UnvalidatedTodoRequest todoRequest = new UnvalidatedTodoRequest(RequestSpec.authSpecForAdmin());
//    Вместо этого используем Builder

        Todo newTodo = new TodoBuilder()
                .setText("text")
                .build();
// Нарушение инверсии зависимостей:
//        TodoRequest authReq = new TodoRequest(RequestSpec.authSpecForAdmin());
//        authReq.create(newTodo)
        todoRequester.getRequest().create(newTodo)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .contentType(ContentType.TEXT)
                .body(notNullValue()); // Проверяем, что есть сообщение об ошибке
    }

    /**
     * TC5: Создание TODO с уже существующим 'id' (если 'id' задается клиентом).
     */
    @Test
    public void testCreateTodoWithExistingId() {

        // Сначала создаем TODO с id = 5
        Todo firstTodo = new Todo(5, "First Task", false);
//        ValidatedTodoRequest authValReq = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());
//        authValReq.create(firstTodo);
        createTodo(firstTodo);

        // Пытаемся создать другую TODO с тем же id
        Todo duplicateTodo = new Todo(5, "Duplicate Task", true);

        // ? Надо здесь использовать ТоdoRequest или ValidatedTodoRequest?
        // 1. Улучшение:
//        TodoRequest authTodoReq = new TodoRequest(RequestSpec.authSpecForAdmin());
//        authTodoReq.create(duplicateTodo)
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.SC_BAD_REQUEST) // Конфликт при дублировании 'id'
//                .contentType(ContentType.TEXT)
//                .body(is(notNullValue())); // Проверяем, что есть сообщение об ошибке
//
//        // 2. Улучшение:
//        new TodoRequest(RequestSpec.authSpecForAdmin())
//                .create(duplicateTodo)
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.SC_BAD_REQUEST) // Конфликт при дублировании 'id'
//                .contentType(ContentType.TEXT)
//                .body(is(notNullValue())); // Проверяем, что есть сообщение об ошибке

        // 3. Улучшение - паттерн Стратегия: используем спецификацию IncorrectDataResponse
//        new TodoRequest(RequestSpec.authSpecForAdmin())
        // 4. Улучшение - используем фасад:
        todoRequester.getRequest()
                .create(duplicateTodo)
                .then()
                // для обычных вариантов:
//                .spec(new IncorrectDataResponse().sameId(firstTodo.getId()));
                .spec(new IncorrectDataResponse().sameId());
    }

    // sozddaj 100 todo
    // pomet' todo kak completed

    // padenije
    // FAIL FIRST
}

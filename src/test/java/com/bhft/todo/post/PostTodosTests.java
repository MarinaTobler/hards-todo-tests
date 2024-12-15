package com.bhft.todo.post;

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

        // Отправляем POST запрос для создания нового Todo; в ответе получаем стринг и проверяем, что он пустой.
        String actualResponseBody = new ValidatedTodoRequest(RequestSpec.unauthSpec())
                .create(newTodo);
//        Assertions.assertEquals(emptyOrNullString(), actualResponseBody); // Проверяем, что тело ответа пустое
        assertThat(actualResponseBody).isNullOrEmpty(); // Проверяем, что тело ответа пустое

        // Проверяем, что TODO было успешно создано
        List<Todo> todos = new ValidatedTodoRequest(RequestSpec.unauthSpec())
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

    /// NB! Этот тест не нужен, т.к. этo контрактное тестирование!
    /**
     * TC2: Попытка создания TODO с отсутствующими обязательными полями.
     */
//    @Test
//    public void testCreateTodoWithMissingFields() {
//
//        // Создаем JSON без обязательного поля 'text'
//        String invalidTodoJson = "{ \"id\": 2, \"completed\": true }";
//
//// ?? как вставить json вместо Todo?
//// мы не можем передать некорректный тип данных, если в TodoRequest в create запрашивается Todo,
//// поэтому сделала UnvalidatedTodoRequest класс, в нём create принимает json и не имплементирует crud
//        new UnvalidatedTodoRequest(RequestSpec.unauthSpec())
//                .create(invalidTodoJson)
//                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
//                .contentType(ContentType.TEXT)
//                .body(notNullValue());
//    }

    /**
     * TC3: Создание TODO с максимально допустимой длиной поля 'text'.
     */
    @Test
    public void testCreateTodoWithMaxLengthText() {
//Updated version after Lesson-1:

        // Предполагаем, что максимальная длина поля 'text' составляет 255 символов
        String maxLengthText = "A".repeat(255);
        Todo newTodo = new Todo(3, maxLengthText, false);

        ValidatedTodoRequest authValReg = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());
        // Отправляем POST запрос для создания нового TODO
        authValReg.create(newTodo);

        // В этом тесте не надо проверять, что Response body empty, проверяем только, что тодо создаётся.
//      String actualResponseBody = new ValidatedTodoRequest(RequestSpec.unauthSpec())
//                .create(newTodo);
//        Assertions.assertEquals(emptyOrNullString(), actualResponseBody);

        // Проверяем, что TODO было успешно создано
//      Вместо:
//        List<Todo> todos = new ValidatedTodoRequest(RequestSpec.unauthSpec())
//                .readAll();
//      Пишем:
        List<Todo> todos = authValReg.readAll();

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
//    @Test
//    public void testCreateTodoWithInvalidDataTypes() {
//        // Поле 'completed' содержит строку вместо булевого значения
//        Todo newTodo = new Todo(3, "djjdjd", false);
//// ?? как вставить json вместо Todo?
//// мы не можем передать некорректный тип данных, если в TodoRequest в create запрашивается Todo,
//// поэтому сделала UnvalidatedTodoRequest класс, в нём create принимает json и не имплементирует crud
//        String invalidTodoJson = "{ \"id\": 3, \"text\": \"djjdjd\", \"completed\": \"String\" }";
//
//        UnvalidatedTodoRequest todoRequest = new UnvalidatedTodoRequest(RequestSpec.authSpecForAdmin());
//
//        todoRequest
//                .create(invalidTodoJson)
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.SC_BAD_REQUEST)
//                .contentType(ContentType.TEXT)
//                .body(notNullValue()); // Проверяем, что есть сообщение об ошибке
//    }

    /**
     * TC5: Создание TODO с уже существующим 'id' (если 'id' задается клиентом).
     */
    @Test
    public void testCreateTodoWithExistingId() {
//Updated version after Lesson-1:

        // Сначала создаем TODO с id = 5
        Todo firstTodo = new Todo(5, "First Task", false);
        createTodo(firstTodo);

        // Пытаемся создать другую TODO с тем же id
        Todo duplicateTodo = new Todo(5, "Duplicate Task", true);

        ValidatedTodoRequest authValReq = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());
        // ? Надо здесь использовать ТоdoRequest или ValidatedTodoRequest?
        TodoRequest authSimpleReq = new TodoRequest(RequestSpec.authSpecForAdmin());

        authValReq.create(firstTodo);
        authSimpleReq
                .create(duplicateTodo)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST) // Конфликт при дублировании 'id'
                .contentType(ContentType.TEXT)
                .body(is(notNullValue())); // Проверяем, что есть сообщение об ошибке
    }
}

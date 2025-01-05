package com.todo.get;


import com.todo.BaseTest;
import com.todo.annotations.Mobile;
import com.todo.annotations.BeforeEachExtension;
import com.todo.annotations.PrepareTodo;
import com.todo.requests.TodoRequest;
import com.todo.requests.ValidatedTodoRequest;
import com.todo.specs.request.RequestSpec;
import com.todo.specs.response.IncorrectDataResponse;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.todo.generators.TestDataGenerator.generateTestData;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;

import com.todo.models.Todo;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@Epic("TODO Management")
@Feature("Get Todos API")
//@ExtendWith(DataPreparationExtension.class)
//@ExtendWith(MobileExecutionExtension.class)
@ExtendWith(BeforeEachExtension.class)
public class GetTodosTests extends BaseTest {

//    @BeforeEach
//    public void setupEach() {
//        deleteAllTodos();
//    }

    // Такой тест ничего не покрывает, он не нужен
    // Если хотим, удостовериться, что у нас есть connection к базе данных, то тест д.б.:
    // что можем приконнектиться к базе данных
//    @Test
//    @Description("Получение пустого списка TODO, когда база данных пуста")
//    @Disabled("Такой тест ничего не покрывает, он не нужен")
//    public void testGetTodosWhenDatabaseIsEmpty() {
////        TodoRequest todoRequest = new TodoRequest(RequestSpec.unauthSpec());
////        todoRequest.readAll()
//        todoRequester.getRequest().readAll()
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .body("", hasSize(0));
//    }

    // пример спец теста
//    @Test
//    public void userCanCreateTodoWithArabicText() {
//        // генерируем todo
//        // проставляем text=arabic
//        // если нам надо сделать тест на проверку арабских символов в тексте
//        Todo todo1 = generateTestData(Todo.class);
//        todo1.setText("arabic symbols");
//        // создаем
//        // проверяем успех
//    }

    @Test
//    @Mobile
//    @Description("Получение списка TODO с существующими записями")
    @Description("Авторизированный юзер может получить список всех todo")
    @PrepareTodo(5)
    public void testGetTodosWithExistingEntries() {
        // Предварительно создать несколько TODO
        // - делаем это с помощью аннотации @PrepareTodo(5)
////        Todo todo1 = new Todo(1, "Task 1", false);
//        // Random values:
////        Todo todo1 = new Todo(
////                Integer.valueOf(RandomStringUtils.randomNumeric(3)),
////                // если только буквы:
////                RandomStringUtils.randomAlphabetic(10),
////                new Random().nextBoolean());
//        // Использование сгенерированного dto:
//        Todo todo1 = generateTestData(Todo.class);
//        Todo todo2 = new Todo(2, "Task 2", true);
////        createTodo(todo1);
////        createTodo(todo2);
////        ValidatedTodoRequest validatedTodoRequest = new ValidatedTodoRequest(RequestSpec.unauthSpec());
////        validatedTodoRequest.create(todo1);
////        validatedTodoRequest.create(todo2);
//        todoRequester.getValidatedRequest().create(todo1);
//        todoRequester.getValidatedRequest().create(todo2);

        // Validated тк нам надо сериализовать readAll
        var createdTodos = todoRequester.getValidatedRequest().readAll();
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
////        Response response = new TodoRequest(RequestSpec.unauthSpec()).readAll()
//        // ?? д.б. validated request?
//        Response response = todoRequester.getRequest().readAll()
//                .then()
//                .statusCode(200)
//                .contentType("application/json")
//                .body("", hasSize(2))
//                .extract().response();
//        // Дополнительная проверка содержимого
        // - ну нужна, тк этот тест тогда должен быть разделён на подтесты:
        //если мы проверяем, что пользователь может получить все тодо, не надо проверять правильность каждого id,
        // тк каждый тест дб атомарным и правильность ид должна проверяться в группе тестов на создание сущности (тодо)
//        Todo[] todos = response.getBody().as(Todo[].class);
//
//        Assertions.assertEquals(1, todos[0].getId());
//        Assertions.assertEquals("Task 1", todos[0].getText());
//        Assertions.assertFalse(todos[0].isCompleted());
//
//        Assertions.assertEquals(2, todos[1].getId());
//        Assertions.assertEquals("Task 2", todos[1].getText());
//        Assertions.assertTrue(todos[1].isCompleted());

        softly.assertThat(createdTodos).hasSize(5);
    }

    @Test
    @Mobile // extension тут -> 1 вариант: properties (config), 2 вариант: storage request
    @PrepareTodo(5)
//    @Description("Использование параметров offset и limit для пагинации")
    @Description("Авторизованный юзер может получать список todo с учетом offset и limit для пагинации")
    public void testGetTodosWithOffsetAndLimit() {

        // Создаем 5 TODO
//        ValidatedTodoRequest validatedTodoRequest = new ValidatedTodoRequest(RequestSpec.unauthSpec());
//        for (int i = 1; i <= 5; i++) {
//            validatedTodoRequest.create(new Todo(i, "Task " + i, i % 2 == 0));
//        }
        // Проверяем, что получили задачи с id 3 и 4
//        List<Todo> todos = validatedTodoRequest.readAll(2, 2);
//        List<Todo> todos = todoRequester.getValidatedRequest().readAll(2, 2);
        var createdTodos = todoRequester.getValidatedRequest().readAll(2, 2);

//        Assertions.assertEquals(todos.size(), 2);
//        Assertions.assertEquals(createdTodos.size(), 2);
        softly.assertThat(createdTodos).hasSize(2);
    }

    // будет 3 или больше отдельных теста:
    // Тест с отрицательным offset
    // Тест с отрицательным limit
    // Тест с отрицательными limit и offset
    @Test
    @DisplayName("Передача некорректных значений в offset и limit")
    @Description("Авторизованный юзер получает ошибку, если введены некорректные значения offset и limit")
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

        // Тест с нечисловым limit - контрактное тестирование!!! - такой тест не нужен
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

        // Тест с отсутствующим значением offset - контрактное тестирование!!! - такой тест не нужен
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

        // нужен невалидированный реквест (getRequest), тк тест негативный
        todoRequester.getRequest().readAll(-1, -1)
                .then().assertThat().spec(IncorrectDataResponse.offsetOrLimitHaveIncorrectValues());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Set negative offset")
    @Description("Авторизованный юзер получает ошибку, если введен отрицательный offset")
    public void testSetNegativeOffset() {
//        TodoRequest todoRequest = new TodoRequest(RequestSpec.unauthSpec());
//        todoRequest.readAll(-1, 2)
//                .then()
//                .statusCode(400)
//                .contentType("text/plain")
//                .body(containsString("Invalid query string"));
        todoRequester.getRequest().readAll(-1, 2)
                .then().assertThat().spec(IncorrectDataResponse.offsetOrLimitHaveIncorrectValues());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Set negative offset")
    @Description("Авторизованный юзер получает ошибку, если введен отрицательный limit")
    public void testSetNegativeLimit() {
//        TodoRequest todoRequest = new TodoRequest(RequestSpec.unauthSpec());
//        todoRequest.readAll(1, -2)
//                .then()
//                .statusCode(400)
//                .contentType("text/plain")
//                .body(containsString("Invalid query string"));
        todoRequester.getRequest().readAll(1, -2)
                .then().assertThat().spec(IncorrectDataResponse.offsetOrLimitHaveIncorrectValues());
    }

    // NB! при превышении limit мы должны получить максимальный список всех todos!
    @Test
    @DisplayName("Проверка ответа при превышении максимально допустимого значения limit")
    @Description("Авторизованный юзер получает все todos, если значение limit больше максимально допустимого")
    @PrepareTodo(10)
    public void testGetTodosWithExcessiveLimit() {

        // Создаем 10 TODO

////      ValidatedTodoRequest validatedTodoRequest = new ValidatedTodoRequest(RequestSpec.unauthSpec());
//        for (int i = 1; i <= 10; i++) {
////            createTodo(new Todo(i, "Task " + i, i % 2 == 0));
////            validatedTodoRequest.create(new Todo(i, "Task " + i, i % 2 == 0));
//            todoRequester.getValidatedRequest().create(new Todo(i, "Task " + i, i % 2 == 0));
//        }
////        Response response =
////                given()
////                        .filter(new AllureRestAssured())
////                        .queryParam("limit", 1000)
////                        .when()
////                        .get("/todos")
////                        .then()
////                        .statusCode(200)
////                        .contentType("application/json")
////                        .extract().response();
////
////        Todo[] todos = response.getBody().as(Todo[].class);
//
////        List<Todo> todos = validatedTodoRequest.readAll(0, 1000);
//        List<Todo> todos = todoRequester.getValidatedRequest().readAll(0, 1000);
        var paginatedTodos = todoRequester.getValidatedRequest().readAll(1, 1000);
        // Проверяем, что вернулось 10 задач
//        Assertions.assertEquals(10, todos.length);
//        Assertions.assertEquals(10, todos.size());
//        Assertions.assertEquals(10, allTodos.size());
        var allTodos = todoRequester.getValidatedRequest().readAll();
        softly.assertThat(paginatedTodos).isEqualTo(allTodos);
    }
}

package com.todo.post;

import com.todo.BaseTest;
import com.todo.annotations.BeforeEachExtension;
import com.todo.annotations.PrepareTodo;
import com.todo.models.Todo;
import com.todo.models.TodoBuilder;
import com.todo.requests.TodoRequest;
import com.todo.specs.request.RequestSpec;
import com.todo.specs.response.IncorrectDataResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.checkerframework.checker.index.qual.Positive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static com.todo.generators.TestDataGenerator.generateRandomString;
import static com.todo.generators.TestDataGenerator.generateTestData;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Epic("TODO Management")
@Feature("Post Todos API")
@ExtendWith(BeforeEachExtension.class)
public class PostTodosTests extends BaseTest {

    // Positive

    // Negative:
    // запросить требования к каждому полю из JSON,
    // на их основании используя все техники тест дизайна,
    // сформировать тест кейсы

//    @BeforeEach
//    public void setupEach() {
//        deleteAllTodos();
//    }

    //    /**
//     * TC1: Успешное создание TODO с корректными данными авторизованным пользователем.
//     */
    @Test
    @Tag("Positive")
    @PrepareTodo(1)
    @Description("Authorized user can create todo with valid data")
    public void testCreateTodoWithValidData() {
//        Todo newTodo = new Todo(1, "New Task", false);

        // Отправляем POST запрос для создания нового TODO
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
//        ValidatedTodoRequest valAuthReq = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());
//        valAuthReq.create(newTodo);
//        // Не надо здесь проверять, что ответе получаем String и что он пустой.
//        String actualResponseBody = valAuthReq.create(newTodo);
//        Assertions.assertEquals(emptyOrNullString(), actualResponseBody); // Проверяем, что тело ответа пустое
//        assertThat(actualResponseBody).isNullOrEmpty(); // Проверяем, что тело ответа пустое
//        todoRequester.getValidatedRequest().create(newTodo);

        // ?? надо ли явно создавать новый тодо с авторизованным пользователем и конкретным тодо
        // new TodoRequest(RequestSpec.authSpecAsAdmin()).create(generateTestData(Todo.class));
        // todoRequester.getValidatedRequest().create(generateTestData(Todo.class));
        // Todo createdTodo = todoRequester.getValidatedRequest().readAll().getFirst();
        // или достаточно рандомно сгенерировать в @PrepareTodo?
        // - если просто проверить создание - то в @PrepareTodo авторизованным пользователем,
        // - если проверить, что создаётся с конкретным ид, то писать .setId.

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

        // Проверяем, что TODO было успешно создано
//        Todo[] todos = given()
//                .when()
//                .get("/todos")
//                .then()
//                .statusCode(200)
//                .extract()
//                .as(Todo[].class);

//        List<Todo> todos = valAuthReq.readAll();
//        List<Todo> todos = todoRequester.getValidatedRequest().readAll();

        // Ищем созданную задачу в списке
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

        softly.assertThat(todoRequester.getValidatedRequest().readAll()).hasSize(1);
    }

//    /**
//     * TC2: Попытка создания TODO с отсутствующими обязательными полями.
//     */
//    @Disabled("NB! Этот тест не нужен, т.к. этo контрактное тестирование!")
//    @Test
//    public void testCreateTodoWithMissingFields() {
////
////        // Создаем JSON без обязательного поля 'text'
////        String invalidTodoJson = "{ \"id\": 2, \"completed\": true }";
////// как вставить json вместо Todo?
////// мы не можем передать некорректный тип данных, если в TodoRequest в create запрашивается Todo,
////// поэтому сделала UnvalidatedTodoRequest класс, в нём create принимает json и не имплементирует crud
////        new UnvalidatedTodoRequest(RequestSpec.unauthSpec())
////                .create(invalidTodoJson)
////                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
////                .contentType(ContentType.TEXT)
////                .body(notNullValue());
////   NB!   Вместо этого используем Builder (который позволяет не вставлять все поля):
//
//        Todo newTodo = new TodoBuilder()
//                .setText("text")
//                .build();
//
//        TodoRequest authReq = new TodoRequest(RequestSpec.authSpecAsAdmin());
//        authReq.create(newTodo)
//                .then()
//                .statusCode(HttpStatus.SC_BAD_REQUEST)
//                .contentType(ContentType.TEXT)
//                .body(notNullValue()); // Проверяем, что есть сообщение об ошибке
//    }

    /**
     * TC3: Создание TODO с максимально допустимой длиной поля 'text'.
     */
    // NB! - также дб тест с мин длиной поля текст; проходимся по всем условиям и по всем требованиям к каждому из полей!
    // Нужен, если
    @Test
    @Tag("PositiveTestBoundaryValues")
    @PrepareTodo(1)
    @Description("Авторизованный юзер может создать тодо с максимально допустимой длиной поля 'текст'")
    public void testCreateTodoWithMaxLengthText() {

        // Предполагаем, что максимальная длина поля 'text' составляет (255) 200 символов
//        String maxLengthText = "A".repeat(255);
//        Todo newTodo = new Todo(3, maxLengthText, false);

        // Отправляем POST запрос для создания нового TODO

        // Улучшение 1:
//        ValidatedTodoRequest authValReg = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());
//        authValReg.create(newTodo);

        // Улучшение 2: заменяем на использование фасада - todoRequester'a:
//        todoRequester.getValidatedRequest().create(newTodo);

        // Улучшение3:
        String maxLengthText = generateRandomString(201);
        Todo newTodo = generateTestData(Todo.class);
        newTodo.setText(maxLengthText);
//        // создаем, проверям
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
//        // Улучшение 2: заменяем на использование фасада - todoRequester'a:
//        List<Todo> todos = todoRequester.getValidatedRequest().readAll();
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
        softly.assertThat(todoRequester.getValidatedRequest().readAll()).hasSize(1);
    }

    @Test
    @Tag("PositiveTestBoundaryValues")
    @PrepareTodo(1)
    @Description("Авторизованный юзер может создать тодо с минимальной допустимой длиной поля 'текст'")
    public void testCreateTodoWithMixLengthText() {
        String mixLengthText = generateRandomString(1);
        Todo newTodo = generateTestData(Todo.class);
        newTodo.setText(mixLengthText);
        todoRequester.getValidatedRequest().create(newTodo);
        softly.assertThat(todoRequester.getValidatedRequest().readAll()).hasSize(1);
    }

//    /**
//     * TC4: Передача некорректных типов данных в полях.
//     */
//    @Disabled("NB! Этот тест не нужен, т.к. этo контрактное тестирование!")
//    @Test
//    public void testCreateTodoWithInvalidDataTypes() {
////        // Поле 'completed' содержит строку вместо булевого значения
//
////        Todo newTodo = new Todo(3, "djjdjd", false);
////// ?? как вставить json вместо Todo?
////// мы не можем передать некорректный тип данных, если в TodoRequest в create запрашивается Todo,
////// поэтому сделала UnvalidatedTodoRequest класс, в нём create принимает json и не имплементирует crud
////        String invalidTodoJson = "{ \"id\": 3, \"text\": \"djjdjd\", \"completed\": \"String\" }";
////        UnvalidatedTodoRequest todoRequest = new UnvalidatedTodoRequest(RequestSpec.authSpecForAdmin());
////    Вместо этого используем Builder
//
//        Todo newTodo = new TodoBuilder()
//                .setText("text")
//                .build();
//// Нарушение инверсии зависимостей:
////        TodoRequest authReq = new TodoRequest(RequestSpec.authSpecForAdmin());
////        authReq.create(newTodo)
//        todoRequester.getRequest().create(newTodo)
//                .then()
//                .statusCode(HttpStatus.SC_BAD_REQUEST)
//                .contentType(ContentType.TEXT)
//                .body(notNullValue()); // Проверяем, что есть сообщение об ошибке
//    }

    //далее см. из negative_cases_strategy.md:
    // bad request -> Negative
    // передаём json, в котором какие-то из полей не удовлетворяют требованиям
        @Test
    @Tag("Negative")
    @DisplayName("Проверка, что нельзя создать тодо с отрицательным ид")
    @Description("Авторизованный юзер получает ошибку, если пытается создать тодо с отрицательным id")
    public void userCannotCreateTodoWithNegativeId() {}

    @Test
    @Tag("Negative")
    @DisplayName("Проверка, что нельзя создать тодо с ид больше, чем 1000000 (1000001)")
    @Description("Авторизованный юзер получает ошибку, если пытается создать тодо с id=1000001")
    public void userCannotCreateTodoWithIdMoreThanPermitted() {}

    @Test
    @Tag("Negative")
    @DisplayName("Проверка, что нельзя создать тодо без текста")
    @Description("Авторизованный юзер получает ошибку, если пытается создать тодо без текста")
    public void userCannotCreateTodoWithoutText() {}

    @Test
    @Tag("Negative")
    @PrepareTodo(1)
    @DisplayName("Проверка, что нельзя создать тодо с текстом больше, чем 200 символов")
    @Description("Авторизованный юзер получает ошибку, если пытается создать тодо с длиной текста 201 символ")
//    @Description("Авторизованный юзер получает ошибку, если длина текста больше, чем максимально допустимая")
    public void userCannotCreateTodoWithTextWithExceededLength() {
        String textLengthOverMax = generateRandomString(201);
        Todo newTodo = generateTestData(Todo.class);
        newTodo.setText(textLengthOverMax);
        todoRequester.getRequest().create(newTodo)
                .then().assertThat().spec(IncorrectDataResponse.textLengthIsOverThanMax());
    }

    @Test
    @Tag("Negative")
    // тест д.б. параметризирован (@DataProvider), всего 3 теста, на каждый символ - отдельный
    @DisplayName("Проверка, что нельзя создать тодо с запрещёнными символами (#,$,%) в тексте")
    @Description("Авторизованный юзер получает ошибку, если пытается создать тодо с запрещёнными символами (#,$,%)")
    public void userCannotCreateTodoWithTextWithForbiddenSymbols() {}

    /**
     * TC5: Создание TODO с уже существующим 'id' (если 'id' задается клиентом).
     */
    @Test
    @Tag("Negative")
//    public void testCreateTodoWithExistingId() {
    public void userCannotCreateTodoWithExistingId() {
        // Сначала создаем TODO с id = 5
        Todo firstTodo = new Todo(5, "First Task", false);
//        createTodo(firstTodo);
//        ValidatedTodoRequest authValReq = new ValidatedTodoRequest(RequestSpec.authSpecForAdmin());
//        authValReq.create(firstTodo);
        todoRequester.getValidatedRequest().create(firstTodo);

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
//                .spec(new IncorrectDataResponse().sameId(firstTodo.getId()));
                .spec(IncorrectDataResponse.sameId(firstTodo.getId()));
    }

    // sozddaj 100 todo
    // pomet' todo kak completed

    // padenije
    // FAIL FIRST
}

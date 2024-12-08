package com.todo.requests;

import com.todo.models.Todo;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class ValidatedTodoRequest extends Request implements CrudInterface<Todo>{
    public ValidatedTodoRequest(RequestSpecification reqSpec) {
        super(reqSpec);
    }

    // ?? как и где проверять, что response body is emptyOrNullString?
//    @Override
//    public Object create(Todo entity) {
//        return new TodoRequest(reqSpec).create(entity)
//                .then().assertThat().statusCode(HttpStatus.SC_CREATED)
//                .extract().as(Todo.class);
//    }

//    @Override
//    public String create(Todo entity) {
//        return new TodoRequest(reqSpec).create(entity)
//                .then().assertThat().statusCode(HttpStatus.SC_CREATED)
//                .extract().asString();
//    }

    @Override
    public Object create(Todo entity) {
        return new TodoRequest(reqSpec).create(entity)
                .then().assertThat()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Override
    public Object update(long id, Todo entity) {
        return new TodoRequest(reqSpec).update(id, entity)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Todo.class);
    }

    @Override
    public String delete(long id) {
        return new TodoRequest(reqSpec).delete(id)
                .then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().asString();
    }
}

package com.todo.specs.response;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

public class IncorrectDataResponse {

    // должно быть так:
    public static ResponseSpecification sameId(long id) {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_BAD_REQUEST);
        responseSpecBuilder
                .expectBody("message",
                        Matchers.containsString("You are trying to use the same id: " + id));
        return responseSpecBuilder.build();

        // в нашем примере так, поэтому тест не проходит
//        public static ResponseSpecification sameId() {
//            ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
//            responseSpecBuilder.expectStatusCode(HttpStatus.SC_BAD_REQUEST);
//            responseSpecBuilder.expectBody(Matchers.containsString("You are trying to use the same id"));
//            return responseSpecBuilder.build();
    }

    public static ResponseSpecification nonExistingId(long id) {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_BAD_REQUEST);
        responseSpecBuilder
                .expectBody("message",
                        Matchers.containsString("You are trying to use non-existing id: " + id));
        return responseSpecBuilder.build();
    }

    public static ResponseSpecification offsetOrLimitHaveIncorrectValues() {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_BAD_REQUEST);
        responseSpecBuilder.expectBody("message", Matchers.containsString("Offset or limit has incorrect values"));
        return responseSpecBuilder.build();
    }

    public static ResponseSpecification textLengthIsOverThanMax() {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_BAD_REQUEST);
        responseSpecBuilder.expectBody("message", Matchers.containsString("text length is over than max"));
        return responseSpecBuilder.build();
    }

}

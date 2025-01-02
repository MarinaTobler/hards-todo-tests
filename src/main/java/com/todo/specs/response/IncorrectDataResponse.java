package com.todo.specs.response;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

public class IncorrectDataResponse {
    // обычно так:
//    public ResponseSpecification sameId(long id) {
//        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
//        responseSpecBuilder.expectStatusCode(HttpStatus.SC_BAD_REQUEST);
//        responseSpecBuilder.expectBody("message", Matchers.containsString("You are trying to use the same " +
//                "id: " + id));
//        return responseSpecBuilder.build();

    // в нашем примере:
        public ResponseSpecification sameId() {
            ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
            responseSpecBuilder.expectStatusCode(HttpStatus.SC_BAD_REQUEST);
            responseSpecBuilder.expectBody(Matchers.containsString("You are trying to use the same id"));
            return responseSpecBuilder.build();



    }
}

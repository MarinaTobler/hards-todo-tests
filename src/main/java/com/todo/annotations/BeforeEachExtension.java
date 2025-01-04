package com.todo.annotations;

import com.todo.conf.Configuration;
import com.todo.models.Todo;
import com.todo.requests.TodoRequest;
import com.todo.specs.request.RequestSpec;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;

import static com.todo.generators.TestDataGenerator.generateTestData;

public class BeforeEachExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        var testMethod = extensionContext.getRequiredTestMethod();

        // в тестах аннотации могут быть проставлены в любом порядке,
        // но здесь порядок выполнения зафиксирован
        mobileExecutionExtension(testMethod);
        prepareTodoExtension(testMethod);
    }


    private void mobileExecutionExtension(Method testMethod) {
        var mobile = testMethod.getAnnotation(Mobile.class);

        if (mobile != null) {
            Configuration.setProperty("version", "mobile");
        }
    }

    private void prepareTodoExtension(Method testMethod) {
        var prepareTodo = testMethod.getAnnotation(PrepareTodo.class);

        if (prepareTodo != null) {
            for (int i = 0; i < prepareTodo.value(); i++) {
                new TodoRequest(RequestSpec.authSpecForAdmin())
//                        .create(new TodoBuilder()
////                                .setId(Long.valueOf(RandomStringUtils.randomNumeric(3)))
//                                .setId(Long.parseLong(RandomStringUtils.randomNumeric(3)))
//                                .setText("123").build());
                        .create(generateTestData(Todo.class));
            }
        }
    }
}

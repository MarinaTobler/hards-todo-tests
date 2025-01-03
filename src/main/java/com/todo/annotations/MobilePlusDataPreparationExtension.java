package com.todo.annotations;

import com.todo.conf.Configuration;
import com.todo.models.TodoBuilder;
import com.todo.requests.TodoRequest;
import com.todo.specs.request.RequestSpec;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class MobilePlusDataPreparationExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        var testMethod = extensionContext.getRequiredTestMethod();
        var mobile = testMethod.getAnnotation(Mobile.class);
        if (mobile != null) {
            // добавляем property для mobile:
            Configuration.setProperty("version", "mobile");
        }

        var prepareTodo = testMethod.getAnnotation(PrepareTodo.class);
        if (prepareTodo != null) {
            for (int i = 0; i < prepareTodo.value(); i++) {
                new TodoRequest(RequestSpec.authSpecForAdmin())
                        .create(new TodoBuilder()
//                                .setId(Long.valueOf(RandomStringUtils.randomNumeric(3)))
                                .setId(Long.parseLong(RandomStringUtils.randomNumeric(3)))
                                .setText("123").build());
            }
        }
    }
}

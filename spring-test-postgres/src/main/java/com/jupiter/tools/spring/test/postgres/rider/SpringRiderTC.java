package com.jupiter.tools.spring.test.postgres.rider;

import java.lang.annotation.Annotation;

import com.github.database.rider.core.AbstractRiderTestContext;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.DataSetExecutor;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.TestContext;

/**
 * Created on 25/02/2020
 * <p>
 * TODO: replace on the JavaDoc
 *
 * @author Korovin Anatoliy
 */
public class SpringRiderTC extends AbstractRiderTestContext {


    private final TestContext testContext;

    static SpringRiderTC create(TestContext testContext) {
        return new SpringRiderTC(createDataSetExecutor(testContext), testContext);
    }

    private static DataSetExecutor createDataSetExecutor(TestContext testContext) {
        String executorId = getExecutorIdFromDataSetAnnotation(testContext);
        return SpringDataSetExecutorWrapper.instance(testContext.getApplicationContext(), executorId);
    }

    private static String getExecutorIdFromDataSetAnnotation(TestContext testContext) {
        DataSet dataSet = AnnotationUtils.findAnnotation(testContext.getTestMethod(), DataSet.class);
        if (dataSet == null) {
            dataSet = AnnotationUtils.findAnnotation(testContext.getTestClass(), DataSet.class);
        }
        return dataSet != null ? dataSet.executorId() : "default";
    }


    private SpringRiderTC(DataSetExecutor executor, TestContext testContext) {
        super(executor);
        this.testContext = testContext;
    }

    @Override
    public String getMethodName() {
        return testContext.getTestMethod().getName();
    }

    @Override
    public <T extends Annotation> T getMethodAnnotation(Class<T> clazz) {
        return AnnotatedElementUtils.findMergedAnnotation(testContext.getTestMethod(), clazz);
    }

    @Override
    public <T extends Annotation> T getClassAnnotation(Class<T> clazz) {
        return AnnotatedElementUtils.findMergedAnnotation(testContext.getTestClass(), clazz);
    }

}

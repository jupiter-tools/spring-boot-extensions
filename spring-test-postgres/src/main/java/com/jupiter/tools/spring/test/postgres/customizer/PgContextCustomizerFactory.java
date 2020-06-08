package com.jupiter.tools.spring.test.postgres.customizer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;

/**
 * Created on 20/02/2020
 * <p>
 * TODO: replace on the JavaDoc
 *
 * @author Korovin Anatoliy
 */
public class PgContextCustomizerFactory implements ContextCustomizerFactory {

    @Override
    public ContextCustomizer createContextCustomizer(Class<?> testClass,
                                                     List<ContextConfigurationAttributes> configAttributes) {

        Set<PostgresTestContainer> annotations = AnnotationUtils.getRepeatableAnnotations(testClass,
                                                                                          PostgresTestContainer.class);

        Set<String> descriptions = annotations.stream()
                                              .map(PostgresTestContainer::value)
                                              .collect(Collectors.toSet());

        return new PgContextCustomizer(descriptions);
    }
}

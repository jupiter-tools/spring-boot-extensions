package com.jupiter.tools.spring.test.postgres.customizer;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PostgresTestContainer {

	/**
	 * Datasource properties mapping prefix name,
	 * this is a path to the spring.datasource properties,
	 * you can customize this prefix if you need to start a multiple
	 * database in one test or if you use not standard path to the
	 * spring datasource properties.
	 * <p>
	 * If you want multiple postgres containers then put in your test case
	 * multiple @{@link PostgresTestContainer} annotations, and specify
	 * for each annotation the separate datasource properties mapping.
	 *
	 * @return datasource name for binding the postgres container
	 */
	String value() default "spring.datasource";
}

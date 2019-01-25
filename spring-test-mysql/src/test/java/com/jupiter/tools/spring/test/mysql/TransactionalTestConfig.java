package com.jupiter.tools.spring.test.mysql;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created on 12.07.2018.
 *
 * Transaction Persistence layer configuration,
 * for testing transactional methods in wrappers and service.
 *
 * @author Korovin Anatoliy
 */
@TestConfiguration
@EnableJpaRepositories(considerNestedRepositories = true)
@EntityScan(basePackageClasses = {TransactionalTestConfig.class})
public class TransactionalTestConfig {

    @Repository
    public interface FooRepository extends JpaRepository<Foo, Long> {
        @Query(value = "SELECT JSON_OBJECT('id', 87, 'name', 'prime')", nativeQuery = true)
        String nativeJson();
    }

    @Entity
    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Foo {
        @Id
        @GeneratedValue
        private Long id;

        @Column(nullable = false)
        private String field;
    }

    @Service
    public class TestService {
        @Autowired
        private FooRepository fooRepository;

        @Transactional
        public Foo ok() {
            Foo foo = new Foo();
            foo.setField("tru la la..");
            return fooRepository.save(foo);
        }

        @Transactional
        public void fail() {
            fooRepository.save(new Foo());
        }

        @Transactional(readOnly = true)
        public long size() {
            return fooRepository.count();
        }

        @Transactional(isolation = Isolation.SERIALIZABLE)
        public void clear() {
            fooRepository.deleteAll();
        }
    }
}
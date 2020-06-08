package com.jupiter.tools.spring.test.postgres.rider;

import javax.sql.DataSource;

import com.github.database.rider.core.api.dataset.DataSetExecutor;
import com.github.database.rider.core.dataset.DataSetExecutorImpl;
import org.jetbrains.annotations.NotNull;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

/**
 * Created on 26/02/2020
 * <p>
 * TODO: replace on the JavaDoc
 *
 * @author Korovin Anatoliy
 */
public class SpringDataSetExecutorWrapper {

    private SpringDataSetExecutorWrapper() {
        // nop
    }

    public static DataSetExecutor instance(ApplicationContext applicationContext, String executorId) {

        DataSource dataSource = wrapInTransactionAwareProxy(applicationContext.getBean(DataSource.class));
        String executorName = getExecutorName(applicationContext, executorId);

        DataSetExecutorImpl dataSetExecutor = DataSetExecutorImpl.instance(executorName,
                                                                           dataSource::getConnection);
        dataSetExecutor.clearRiderDataSource();
        return dataSetExecutor;
    }

    private static String getExecutorName(ApplicationContext applicationContext, String executorId) {
        return "appContext#" + System.identityHashCode(applicationContext) + " : executorId#" + executorId;
    }

    private static DataSource wrapInTransactionAwareProxy(DataSource dataSource) {
        if (dataSource instanceof TransactionAwareDataSourceProxy) {
            return dataSource;
        } else {
            return new TransactionAwareDataSourceProxy(dataSource);
        }
    }
}

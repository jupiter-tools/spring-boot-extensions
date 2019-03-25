package com.jupiter.tools.spring.test.core.importdata;



/**
 * Import data to MongoDb from {@link DataSet}
 *
 * @author Korovin Anatoliy
 */
public interface DataSetImport {

    /**
     * Import {@link DataSet} to the MongoDb
     *
     * @param dataSet source data set
     */
    void importFrom(DataSet dataSet);
}

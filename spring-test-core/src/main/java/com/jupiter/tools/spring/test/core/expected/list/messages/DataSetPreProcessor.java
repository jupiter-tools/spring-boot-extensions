package com.jupiter.tools.spring.test.core.expected.list.messages;


import com.jupitertools.datasetroll.DataSet;

/**
 * Pre-processing the DataSet from one state to another
 *
 * @author Korovin Anatoliy
 */
public interface DataSetPreProcessor {

	/**
	 * transform the source dataset
	 *
	 * @param source input dataset
	 * @return transformed version of the dataset
	 */
	DataSet run(DataSet source);
}

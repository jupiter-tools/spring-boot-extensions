package com.jupiter.tools.spring.test.activemq.importdata;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;


import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Load text from a file.
 *
 * @author Korovin Anatoliy
 */
public class ImportFile implements Text {

    private final String fileName;
    private final Logger log = LoggerFactory.getLogger(ImportFile.class);

    public ImportFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String read() {
        try (InputStream inputStream = getResourceStream()) {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            log.error("Error while trying to read data from file: {}", fileName, e);
            throw new RuntimeException("Error while reading file with a DataSet", e);
        }
    }

    private InputStream getResourceStream() {
        String dataFileName = (!fileName.startsWith("/"))
                              ? "/" + fileName
                              : fileName;

        InputStream inputStream = getClass().getResourceAsStream(dataFileName);
        if (inputStream == null) {
            inputStream = getClass().getResourceAsStream("/dataset" + dataFileName);
        }
        return inputStream;
    }
}

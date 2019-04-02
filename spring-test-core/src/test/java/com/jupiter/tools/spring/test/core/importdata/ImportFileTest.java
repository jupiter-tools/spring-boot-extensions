package com.jupiter.tools.spring.test.core.importdata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created on 25.03.2019.
 *
 * @author Korovin Anatoliy
 */
class ImportFileTest {


    @Test
    void read() {
        // Arrange
        Text fileText = new ImportFile("/dataset/internal/test_file.txt");
        // Act
        String text = fileText.read();
        // Asserts
        assertThat(text).isEqualTo("secret-123");
    }

    @Test
    void readWithoutSlashInPath() {
        // Arrange
        Text fileText = new ImportFile("dataset/internal/test_file.txt");
        // Act
        String text = fileText.read();
        // Asserts
        assertThat(text).isEqualTo("secret-123");
    }

    @Test
    void readWithoutDataSetInPath() {
        // Arrange
        Text fileText = new ImportFile("internal/test_file.txt");
        // Act
        String text = fileText.read();
        // Asserts
        assertThat(text).isEqualTo("secret-123");
    }

    @Test
    void tryToReadNotExistsFile() {
        // Arrange
        Text fileText = new ImportFile("dataset/not_exist_file_name.txt");
        // Act
        Assertions.assertThrows(RuntimeException.class,
                                fileText::read);
    }

}
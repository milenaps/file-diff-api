package com.mps.sample.filediff.service;

import com.mps.sample.filediff.dao.FileDiffRepository;
import com.mps.sample.filediff.domain.Bucket;
import com.mps.sample.filediff.domain.FileDiff;
import com.mps.sample.filediff.exception.FileDiffAlreadyExistsException;
import com.mps.sample.filediff.exception.FileDiffIdNotFoundException;
import com.mps.sample.filediff.exception.FileDiffNotReadyException;
import com.mps.sample.filediff.service.impl.FileDiffServiceImpl;
import com.mps.sample.filediff.utils.FileDiffConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Base64;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext
public class FileDiffServiceTest {

    private final byte[] decodedLeftBinaryData = Base64.getDecoder().decode("TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQ=");
    private final byte[] decodedRightBinaryData = Base64.getDecoder().decode("TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXM=");

    @TestConfiguration
    static class FileDiffServiceImplTestContextConfiguration {

        @Bean
        @Primary
        public FileDiffService diffService() {
            return new FileDiffServiceImpl();
        }
    }

    @Autowired
    private FileDiffService service;

    @MockBean
    private FileDiffRepository repository;

    @Test
    public void shouldUploadLeftData() throws FileDiffAlreadyExistsException {
        when(repository.findByName("1")).thenReturn(Optional.empty());
        assertNotNull(service.uploadLeft("1", decodedLeftBinaryData));
    }

    @Test
    public void shouldUploadRightData() throws FileDiffAlreadyExistsException {
        when(repository.findByName("2")).thenReturn(Optional.empty());
        assertNotNull(service.uploadRight("2", decodedRightBinaryData));
    }

    @Test
    public void shouldThrowExceptionWhenTryingToUploadSameDataTwice() throws FileDiffAlreadyExistsException {
        Bucket bucket = new Bucket();
        bucket.setName("3");
        bucket.setLeftFile(decodedLeftBinaryData);

        when(repository.findByName("3")).thenReturn(Optional.of(bucket));

        Exception exception = assertThrows(FileDiffAlreadyExistsException.class, () -> {
            service.uploadLeft("3", decodedLeftBinaryData);
        });
        assertThat(exception.getMessage(), equalTo(FileDiffConstants.LEFT_FILE_ALREADY_PRESENT + "3"));
    }

    @Test
    public void shouldThrowErrorOnTryingToReadNonExistingDiffs() throws FileDiffAlreadyExistsException, FileDiffNotReadyException, FileDiffIdNotFoundException {
        when(repository.existsByName("4")).thenReturn(false);

        Exception exception = assertThrows(FileDiffIdNotFoundException.class, () -> {
            service.getFileDiff("4");
        });
        assertThat(exception.getMessage(), equalTo(FileDiffConstants.NO_FILE_WITH_ID + "4"));
    }

    @Test
    public void shouldThrowErrorOnTryingToFetchDiffNotReadyYet() throws FileDiffAlreadyExistsException, FileDiffNotReadyException, FileDiffIdNotFoundException {
        when(repository.existsByName("5")).thenReturn(true);

        Exception exception = assertThrows(FileDiffNotReadyException.class, () -> {
            service.getFileDiff("5");
        });
        assertThat(exception.getMessage(), equalTo(FileDiffConstants.DIFF_STILL_PROCESSING));
    }

    @Test
    public void shouldUploadDataAndProcessDiffs() throws FileDiffAlreadyExistsException, FileDiffNotReadyException, FileDiffIdNotFoundException {
        when(repository.existsByName("5")).thenReturn(true);

        FileDiff diff = new FileDiff();
        diff.setEqual(true);
        when(repository.getFileDiff("5")).thenReturn(Optional.of(diff));

        diff = service.getFileDiff("5");
        assertNotNull(diff);
        assertTrue(diff.isEqual());
    }
}

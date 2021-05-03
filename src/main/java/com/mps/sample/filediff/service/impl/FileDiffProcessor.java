package com.mps.sample.filediff.service.impl;

import com.mps.sample.filediff.dao.FileDiffRepository;
import com.mps.sample.filediff.domain.Bucket;
import com.mps.sample.filediff.domain.FileDiff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

@Service
public class FileDiffProcessor {

    private static final Logger logger = LoggerFactory.getLogger(FileDiffProcessor.class);

    private FileDiffRepository diffRepository;

    public FileDiffProcessor(final FileDiffRepository diffRepository) {
        this.diffRepository = diffRepository;
    }

    @Async
    public void execute(final Bucket bucket) {
        FileDiff diff = new FileDiff();

        final byte[] leftFile = bucket.getLeftFile();
        final byte[] rightFile = bucket.getRightFile();

        boolean filesAreEqual = Arrays.equals(leftFile, rightFile);
        diff.setEqual(filesAreEqual);

        if (!filesAreEqual) {
            boolean filesHaveSameSize = leftFile.length == rightFile.length;
            diff.setSameSize(filesHaveSameSize);

            if (filesHaveSameSize) {//FIXME Reimplement sameSize logic
                try (ByteArrayInputStream leftFileStream = new ByteArrayInputStream(leftFile);
                     ByteArrayInputStream rightFileStream = new ByteArrayInputStream(rightFile)) {

                    byte[] bytes = new byte[128];
                    for (int pos = 0; pos < leftFileStream.read(bytes); pos++) {
                        if (rightFileStream.read(bytes) != pos) {
                            diff.setDiffOffset(pos);
                            diff.setDiffLength(bytes.length);
                        }
                    }
                } catch (IOException e) {
                    logger.warn("Error reading the files for comparison for ID {}", bucket.getName(), e);
                }
            }
        }
        bucket.setDiff(diff);
        diffRepository.save(bucket);
    }
}

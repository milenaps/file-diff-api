package com.mps.sample.filediff.service.impl;

import com.mps.sample.filediff.dao.FileDiffRepository;
import com.mps.sample.filediff.domain.Bucket;
import com.mps.sample.filediff.domain.FileDiff;
import com.mps.sample.filediff.exception.FileDiffAlreadyExistsException;
import com.mps.sample.filediff.exception.FileDiffIdNotFoundException;
import com.mps.sample.filediff.exception.FileDiffNotReadyException;
import com.mps.sample.filediff.service.FileDiffService;
import com.mps.sample.filediff.utils.FileDiffConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class FileDiffServiceImpl implements FileDiffService {

    @Autowired
    private FileDiffRepository diffRepository;

    @Override
    public void uploadLeft(String id, byte[] data) throws FileDiffAlreadyExistsException {
        Optional<Bucket> optBucket = diffRepository.findByName(id);
        Bucket bucket;
        if (optBucket.isPresent()) {
            bucket = optBucket.get();
            if (bucket.getLeftFile() != null) {
                throw new FileDiffAlreadyExistsException(FileDiffConstants.LEFT_FILE_ALREADY_PRESENT + id);
            }
        } else {
            bucket = new Bucket();
            bucket.setName(id);
        }
        bucket.setLeftFile(data);
        diffRepository.save(bucket);

        if (bucket.getRightFile() != null) processDiffs(bucket);
    }

    @Override
    public void uploadRight(String id, byte[] data) throws FileDiffAlreadyExistsException {
        Optional<Bucket> optBucket = diffRepository.findByName(id);
        Bucket bucket;
        if (optBucket.isPresent()) {
            bucket = optBucket.get();
            if (bucket.getRightFile() != null) {
                throw new FileDiffAlreadyExistsException(FileDiffConstants.RIGHT_FILE_ALREADY_PRESENT + id);
            }
        } else {
            bucket = new Bucket();
            bucket.setName(id);
        }
        bucket.setRightFile(data);
        diffRepository.save(bucket);

        if (bucket.getLeftFile() != null) processDiffs(bucket);
    }

    @Override
    public FileDiff getFileDiff(String id) throws FileDiffIdNotFoundException, FileDiffNotReadyException {
        boolean bucketExists = diffRepository.existsByName(id);
        if (!bucketExists) {
            throw new FileDiffIdNotFoundException(FileDiffConstants.NO_FILE_WITH_ID + id);
        }
        Optional<FileDiff> diff = diffRepository.getFileDiff(id);
        if (!diff.isPresent()) {
            throw new FileDiffNotReadyException(FileDiffConstants.DIFF_STILL_PROCESSING);
        }
        return diff.get();
    }

    @Async
    void processDiffs(final Bucket bucket) {
        FileDiff diff = new FileDiff();

        final byte[] leftFile = bucket.getLeftFile();
        final byte[] rightFile = bucket.getRightFile();

        boolean filesAreEqual = Arrays.equals(leftFile, rightFile);
        diff.setEqual(filesAreEqual);

        if (!filesAreEqual) {
            boolean filesHaveSameSize = leftFile.length == rightFile.length;
            diff.setSameSize(filesHaveSameSize);

            if (filesHaveSameSize) {
                for (int pos = 0; pos < leftFile.length; pos++) {
                    if (leftFile[pos] != rightFile[pos]) {
                        diff.setOffset(pos);
                    }
                }
                diff.setFilesLength(leftFile.length);
            }
        }
        bucket.setDiff(diff);
        diffRepository.save(bucket);
    }
}

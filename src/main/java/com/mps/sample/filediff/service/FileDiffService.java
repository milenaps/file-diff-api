package com.mps.sample.filediff.service;

import com.mps.sample.filediff.domain.FileDiff;
import com.mps.sample.filediff.exception.FileDiffAlreadyExistsException;
import com.mps.sample.filediff.exception.FileDiffIdNotFoundException;
import com.mps.sample.filediff.exception.FileDiffNotReadyException;

public interface FileDiffService {

    void uploadLeft(String id, byte[] data) throws FileDiffAlreadyExistsException;
    void uploadRight(String id, byte[] data) throws FileDiffAlreadyExistsException;
    FileDiff getFileDiff(String id) throws FileDiffIdNotFoundException, FileDiffNotReadyException;
}

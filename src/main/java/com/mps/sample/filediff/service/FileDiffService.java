package com.mps.sample.filediff.service;

import com.mps.sample.filediff.domain.FileDiff;
import com.mps.sample.filediff.exception.FileDiffAlreadyExistsException;
import com.mps.sample.filediff.exception.FileDiffIdNotFoundException;
import com.mps.sample.filediff.exception.FileDiffNotReadyException;

public interface FileDiffService {

    /**
     * Stores the binary data into a bucket named after the id
     *
     * @param id identifier for the binary data comparison
     * @param data base64 binary data representing a file
     * @throws FileDiffAlreadyExistsException
     */
    void uploadLeft(String id, byte[] data) throws FileDiffAlreadyExistsException;

    /**
     * Stores the binary data into a bucket named after the id
     *
     * @param id identifier for the binary data comparison
     * @param data base64 binary data representing a file
     * @throws FileDiffAlreadyExistsException
     */
    void uploadRight(String id, byte[] data) throws FileDiffAlreadyExistsException;

    /**
     * Fetches the resulting diff of two files uploaded and stored as binary data
     *
     * @param id identifier of the files uploaded for comparison
     * @return the result of files comparison
     * @throws FileDiffIdNotFoundException
     * @throws FileDiffNotReadyException
     */
    FileDiff getFileDiff(String id) throws FileDiffIdNotFoundException, FileDiffNotReadyException;
}

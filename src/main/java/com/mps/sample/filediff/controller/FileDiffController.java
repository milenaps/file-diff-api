package com.mps.sample.filediff.controller;

import com.mps.sample.filediff.domain.FileDiff;
import com.mps.sample.filediff.exception.FileDiffAlreadyExistsException;
import com.mps.sample.filediff.exception.FileDiffIdNotFoundException;
import com.mps.sample.filediff.exception.FileDiffInvalidPayloadException;
import com.mps.sample.filediff.exception.FileDiffNotReadyException;
import com.mps.sample.filediff.service.FileDiffService;
import com.mps.sample.filediff.utils.FileDiffConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

import static com.mps.sample.filediff.utils.FileDiffConstants.BINARY_DATA;

@RestController
@RequestMapping("v1/diff")
public class FileDiffController {

    @Autowired
    private FileDiffService diffService;

    @PostMapping(value = "{id}/left", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void uploadLeftData(@PathVariable String id, @RequestBody Map<String, String> payload) throws FileDiffInvalidPayloadException, FileDiffAlreadyExistsException {
        if (payload == null || payload.get(BINARY_DATA) == null) {
            throw new FileDiffInvalidPayloadException(FileDiffConstants.INVALID_PAYLOAD);
        }
        diffService.uploadLeft(id, Base64.getDecoder().decode(payload.get(BINARY_DATA)));
    }

    @PostMapping(value = "{id}/right", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void uploadRightData(@PathVariable String id, @RequestBody Map<String, String> payload) throws FileDiffInvalidPayloadException, FileDiffAlreadyExistsException {
        if (payload == null || payload.get(BINARY_DATA) == null) {
            throw new FileDiffInvalidPayloadException(FileDiffConstants.INVALID_PAYLOAD);
        }
        diffService.uploadRight(id, Base64.getDecoder().decode(payload.get(BINARY_DATA)));
    }

    @GetMapping("{id}")
    public FileDiff getResult(@PathVariable String id) throws FileDiffNotReadyException, FileDiffIdNotFoundException {
        return diffService.getFileDiff(id);
    }
}

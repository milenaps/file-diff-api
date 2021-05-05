package com.mps.sample.filediff.controller;

import com.mps.sample.filediff.controller.dto.FileDiffRequest;
import com.mps.sample.filediff.controller.dto.FileDiffResponse;
import com.mps.sample.filediff.exception.FileDiffAlreadyExistsException;
import com.mps.sample.filediff.exception.FileDiffIdNotFoundException;
import com.mps.sample.filediff.exception.FileDiffNotReadyException;
import com.mps.sample.filediff.service.FileDiffService;
import com.mps.sample.filediff.service.converter.FileDiffConverter;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;

@RestController
@RequestMapping("v1/diff")
@Validated
public class FileDiffController {

    @Autowired
    private FileDiffService diffService;

    @Autowired
    private FileDiffConverter diffConverter;

    @PostMapping(value = "{id}/left", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Expects a base64 binary data representing a file to store it for comparing with another file under same ID")
    public void uploadLeftData(@PathVariable String id, @Valid @RequestBody FileDiffRequest payload) throws FileDiffAlreadyExistsException {
        diffService.uploadLeft(id, Base64.getDecoder().decode(payload.getData()));
    }

    @PostMapping(value = "{id}/right", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Expects a base64 binary data representing a file to store it for comparing with another file under same ID")
    public void uploadRightData(@PathVariable String id, @Valid @RequestBody FileDiffRequest payload) throws FileDiffAlreadyExistsException {
        diffService.uploadRight(id, Base64.getDecoder().decode(payload.getData()));
    }

    @GetMapping("{id}")
    @Operation(summary = "Returns the diffs of two binary data previously stored for the given ID")
    public FileDiffResponse getResult(@PathVariable String id) throws FileDiffNotReadyException, FileDiffIdNotFoundException {
        return diffConverter.convert(diffService.getFileDiff(id));
    }
}

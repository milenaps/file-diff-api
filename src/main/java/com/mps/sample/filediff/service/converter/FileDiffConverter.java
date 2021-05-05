package com.mps.sample.filediff.service.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mps.sample.filediff.controller.dto.FileDiffResponse;
import com.mps.sample.filediff.domain.FileDiff;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class FileDiffConverter implements Converter<FileDiff, FileDiffResponse> {

    @Override
    public FileDiffResponse convert(FileDiff source) {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(source, FileDiffResponse.class);
    }
}

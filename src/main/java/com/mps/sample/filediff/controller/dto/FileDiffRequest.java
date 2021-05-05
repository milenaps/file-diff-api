package com.mps.sample.filediff.controller.dto;

import javax.validation.constraints.NotEmpty;

public class FileDiffRequest {

    /**
     * Base64 encoded binary data
     */
    @NotEmpty
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

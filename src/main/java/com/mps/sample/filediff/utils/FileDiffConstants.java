package com.mps.sample.filediff.utils;

public interface FileDiffConstants {

    String BINARY_DATA = "data";
    String INVALID_PAYLOAD = "Invalid payload. The expected body is a JSON with data field with base64 encoded binary value.";
    String LEFT_FILE_ALREADY_PRESENT = "The left binary data has already been uploaded for ID: ";
    String RIGHT_FILE_ALREADY_PRESENT = "The right binary data has already been uploaded for ID: ";
    String NO_FILE_WITH_ID = "No files have been uploaded for comparison with ID: ";
    String DIFF_STILL_PROCESSING = "Please confirm you've uploaded both files, hold on a second and try again.";
}

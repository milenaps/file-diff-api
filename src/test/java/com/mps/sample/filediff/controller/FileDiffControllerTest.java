package com.mps.sample.filediff.controller;

import com.mps.sample.filediff.controller.dto.FileDiffRequest;
import com.mps.sample.filediff.controller.dto.FileDiffResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class FileDiffControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldUploadLeftData() {
        ResponseEntity<Void> response = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/v1/diff/0/left", getFileDiffRequest(), Void.class, "");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void shouldUploadRightData() {
        ResponseEntity<Void> response = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/v1/diff/0/right", getFileDiffRequest(), Void.class, "");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void shouldReturnBadRequestOnTryingToPostInvalidRequest() {
        ResponseEntity<Void> response = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/v1/diff/1/right", new FileDiffRequest(), Void.class, "");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void shouldReturnNotAcceptableOnTryingToUploadInvalidData() {
        ResponseEntity<Void> response = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/v1/diff/1/right", getInvalidFileDiffRequest(), Void.class, "");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_ACCEPTABLE));
    }

    @Test
    public void shouldReturnConflictOnTryingToUploadSameDataTwice() {
        this.restTemplate.postForEntity(
                "http://localhost:" + port + "/v1/diff/1/right", getFileDiffRequest(), Void.class, "");

        ResponseEntity<Void> response = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/v1/diff/1/right", getFileDiffRequest(), Void.class, "");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CONFLICT));
    }

    @Test
    public void shouldReturnNotFoundWhenTryingToFetchNonExistingDiff() {
        ResponseEntity<FileDiffResponse> response = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/v1/diff/2", FileDiffResponse.class, "");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void shouldReturnBadGatewayWhenTryingToFetchDiffNotComparedYet() {
        this.restTemplate.postForEntity(
                "http://localhost:" + port + "/v1/diff/3/left", getFileDiffRequest(), Void.class, "");

        ResponseEntity<FileDiffResponse> response = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/v1/diff/3", FileDiffResponse.class, "");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_GATEWAY));
    }

    @Test
    public void shouldSucceedOnFetchingDiffResults() {
        this.restTemplate.postForEntity(
                "http://localhost:" + port + "/v1/diff/4/left", getFileDiffRequest(), Void.class, "");
        this.restTemplate.postForEntity(
                "http://localhost:" + port + "/v1/diff/4/right", getFileDiffRequest(), Void.class, "");

        ResponseEntity<FileDiffResponse> response = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/v1/diff/4", FileDiffResponse.class, "");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    private FileDiffRequest getFileDiffRequest() {
        FileDiffRequest payload = new FileDiffRequest();
        payload.setData("TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQ=");
        return payload;
    }

    private FileDiffRequest getInvalidFileDiffRequest() {
        FileDiffRequest payload = new FileDiffRequest();
        payload.setData("TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQ1=");
        return payload;
    }
}

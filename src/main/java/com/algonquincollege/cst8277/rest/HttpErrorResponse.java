/*****************************************************************c******************o*******v******id********
 * File: ErrorResponse.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman

 * Note: students do NOT need to change anything in this class
 */
package com.algonquincollege.cst8277.rest;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HttpErrorResponse implements Serializable {
    /**
     * Explicit set of serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * The statusCode
     */
    private final int statusCode;
    /**
     * The reasonPhrase
     */
    private final String reasonPhrase;

    /**
     * Instantiate HttpErrorResponse
     * @param code the statusCode
     * @param reasonPhrase the reasonphtase
     */
    public HttpErrorResponse(int code, String reasonPhrase) {
        this.statusCode = code;
        this.reasonPhrase = reasonPhrase;
    }
    /**
     * Get Status Code
     * @return value of statusCode
     */
    @JsonProperty("status-code")
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Get Reason Phrase
     * @return value of reasonPhrase
     */
    @JsonProperty("reason-phrase")
    public String getReasonPhrase() {
        return reasonPhrase;
    }

}
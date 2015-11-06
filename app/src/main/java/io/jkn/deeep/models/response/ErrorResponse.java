package io.jkn.deeep.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by n3tr on 1/4/2015 AD.
 */
public class ErrorResponse {
    private String error;
    @SerializedName("error_desciption") private String errorDesciption;

    public String getError() {
        return error;
    }

    public String getErrorDesciption() {
        return errorDesciption;
    }
}

package io.jkn.deeep.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by n3tr on 1/3/2015 AD.
 */
public class TokenResponse {

    @SerializedName("access_token") private String accessToken;
    @SerializedName("token_type") private String tokenType;
    private String scope;


    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getScope() {
        return scope;
    }


}

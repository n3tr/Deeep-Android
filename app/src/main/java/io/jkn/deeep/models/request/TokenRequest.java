package io.jkn.deeep.models.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by n3tr on 1/3/2015 AD.
 */
public class TokenRequest {

    @SerializedName("client_id")
    String clientId;

    @SerializedName("client_secret")
    String clientSecret;

    @SerializedName("code")
    String code;

    @SerializedName("redirect_uri")
    String redirectUri;

    public TokenRequest(String code) {
        this.code = code;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}



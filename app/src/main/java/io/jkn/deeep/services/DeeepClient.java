package io.jkn.deeep.services;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import io.jkn.deeep.models.AttachmentDO;
import io.jkn.deeep.models.CommentDO;
import io.jkn.deeep.models.ShotDO;
import io.jkn.deeep.models.UserDO;
import io.jkn.deeep.models.request.TokenRequest;
import io.jkn.deeep.models.response.ErrorResponse;
import io.jkn.deeep.models.response.TokenResponse;
import io.jkn.deeep.manager.ApplicationManager;
import retrofit.Callback;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by n3tr on 1/3/2015 AD.
 */
public class DeeepClient {


    private static final String API_ENDPOINT = "https://api.dribbble.com/v1/";
    private static final String CLIENT_ID = "6541140c3e8404eab0ec46a715cf7a039f44911b42ca8d9e60f24d4ab32ed122";
    private static final String CLIENT_SECRET = "e0bca8b49e614a01bfb1d0564ac655c3c93404c5a04b25e95941b64517d6022b";
    private static final int DEFAULT_PER_PAGE = 12;

    private static DeeepClient instance;
    private Context mContext;

    private DribbbleService service;

    public static DeeepClient  getInstance(Context c) {
        if (instance == null)
            instance = new DeeepClient(c.getApplicationContext());
        return instance;
    }

    private DeeepClient(Context c) {
        mContext = c;

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                String accessToken = ApplicationManager.getInstance(mContext).getUserAccessToken();
                if (accessToken != null) {
                    accessToken = "Bearer " + accessToken;
                    request.addHeader("Authorization", accessToken);
                }
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_ENDPOINT)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        service = restAdapter.create(DribbbleService.class);

    }

    public static DribbbleService getSharedService(Context c) {
        return DeeepClient.getInstance(c).service;
    }

    /**
     * Request access token wih code
     * Note. This is use specific for token request because BASE_URL differently from API
     * @param token
     */
    public void doAuthWithCode(String code,Callback<TokenResponse> cb) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://dribbble.com/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        DribbbleService service = restAdapter.create(DribbbleService.class);


        TokenRequest tokenReq = new TokenRequest(code);
        tokenReq.setClientId(CLIENT_ID);
        tokenReq.setClientSecret(CLIENT_SECRET);
        tokenReq.setRedirectUri("http://deeep.co/callback/android");

        service.requestTokenWithCode(tokenReq,cb);
    }

    public void fetchCurrentUser(final OnClientResponseCallback<UserDO,ErrorResponse> callback){
        service.currentUser(new Callback<UserDO>() {
            @Override
            public void success(UserDO user, Response response) {
                callback.onResponse(user,response);
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorResponse errorRes = (ErrorResponse) error.getBodyAs(ErrorResponse.class);
                callback.onFailure(errorRes, error);
            }
        });
    }
    public void fetchPopularShots(int page, final OnClientResponseCallback<ShotDO[],ErrorResponse> callback){
        service.getShots(page,DEFAULT_PER_PAGE,new Callback<ShotDO[]>() {
            @Override
            public void success(ShotDO[] shots, Response response) {
                callback.onResponse(shots,response);
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorResponse errorRes = (ErrorResponse) error.getBodyAs(ErrorResponse.class);
                callback.onFailure(errorRes,error);
            }
        });
    }

    public void fetchRecentlyShots(int page, final  OnClientResponseCallback<ShotDO[],ErrorResponse> callback) {
        Map<String,Object> options = new HashMap<>();
        options.put("page", page);
        options.put("per_page",DEFAULT_PER_PAGE);
        options.put("sort","recent");

        service.getShots(options, new Callback<ShotDO[]>() {
            @Override
            public void success(ShotDO[] shotDOs, Response response) {
                callback.onResponse(shotDOs, response);
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorResponse errorRes = (ErrorResponse) error.getBodyAs(ErrorResponse.class);
                callback.onFailure(errorRes, error);
            }
        });
    }

    public void getFollowedShots(int page, final OnClientResponseCallback<ShotDO[],ErrorResponse> callback) {
        service.getFollowedShots(page,DEFAULT_PER_PAGE, new Callback<ShotDO[]>() {
            @Override
            public void success(ShotDO[] shotDOs, Response response) {
                callback.onResponse(shotDOs, response);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onFailure((ErrorResponse)error.getBodyAs(ErrorResponse.class),error);
            }
        });
    }

    public void getShot(int shotId, final  OnClientResponseCallback<ShotDO,ErrorResponse> callback) {
        service.getShot(shotId,new Callback<ShotDO>() {
            @Override
            public void success(ShotDO shotDO, Response response) {
                callback.onResponse(shotDO,response);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onFailure((ErrorResponse)error.getBodyAs(ErrorResponse.class),error);
            }
        });
    }

    public void getAttachmentsForShot(int shotId, final  OnClientResponseCallback<AttachmentDO[],ErrorResponse> callback) {
        service.getAttachmentsForShot(shotId, new Callback<AttachmentDO[]>() {
            @Override
            public void success(AttachmentDO[] attachmentDOs, Response response) {
                callback.onResponse(attachmentDOs, response);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onFailure((ErrorResponse) error.getBodyAs(ErrorResponse.class), error);
            }
        });
    }

    public void getComment(int shotId,int page,final OnClientResponseCallback<CommentDO[],ErrorResponse> callback){
        service.getCommentsForShot(shotId,page,16, new Callback<CommentDO[]>() {
            @Override
            public void success(CommentDO[] commentDOs, Response response) {
                callback.onResponse(commentDOs,response);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onFailure((ErrorResponse) error.getBodyAs(ErrorResponse.class), error);
            }
        });
    }


    public interface OnClientResponseCallback<T,E> {
        public void onResponse(T data, Response response);

        public void onFailure(E error, RetrofitError rawError);
    }


}

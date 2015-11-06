package io.jkn.deeep.services;

import java.util.Map;

import io.jkn.deeep.models.AttachmentDO;
import io.jkn.deeep.models.CommentDO;
import io.jkn.deeep.models.ShotDO;
import io.jkn.deeep.models.UserDO;
import io.jkn.deeep.models.request.TokenRequest;
import io.jkn.deeep.models.response.TokenResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by n3tr on 1/3/2015 AD.
 */
public interface DribbbleService {

    @GET("/user")
    void currentUser(Callback<UserDO> cb);

    @GET("/shots")
    void getShots(@Query("page") int page, @Query("per_page") int perPage,Callback<ShotDO[]> cb);

    @GET("/shots")
    void getShots(@QueryMap Map<String, Object> options,Callback<ShotDO[]> cb);

    @GET("/user/following/shots")
    void getFollowedShots(@Query("page") int page, @Query("per_page") int perPage,Callback<ShotDO[]> cb);

    @GET("/shots/{id}")
    void  getShot(@Path("id") int shotId,Callback<ShotDO> cb);

    @GET("/shots/{id}/attachments")
    void  getAttachmentsForShot(@Path("id") int shotId,Callback<AttachmentDO[]> cb);

    @GET("/shots/{id}/comments")
    void getCommentsForShot(@Path("id") int shotId, @Query("page") int page, @Query("per_page") int per_page,Callback<CommentDO[]> cb);

    @POST("/oauth/token")
    void requestTokenWithCode(@Body TokenRequest tokenRequest, Callback<TokenResponse> cb);




}

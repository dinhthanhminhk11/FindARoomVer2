package com.example.findaroomver2.api;

import com.example.findaroomver2.model.DataChat;
import com.example.findaroomver2.model.DataUser;
import com.example.findaroomver2.model.MessageChat;
import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.model.SearchModel;
import com.example.findaroomver2.request.ViewUpdatePost;
import com.example.findaroomver2.request.bookmark.Bookmark;
import com.example.findaroomver2.request.changeInfo.UserEditProfileRequest;
import com.example.findaroomver2.request.changepass.ChangePasswordRequest;
import com.example.findaroomver2.request.changepass.Email;
import com.example.findaroomver2.request.changepass.Verify;
import com.example.findaroomver2.request.comment.Comment;
import com.example.findaroomver2.request.favourite.Favourite;
import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.request.login.UserRequestTokenDevice;
import com.example.findaroomver2.request.money.CashFlowRequest;
import com.example.findaroomver2.request.register.UserRegisterRequest;
import com.example.findaroomver2.response.ListNotificationResponse;
import com.example.findaroomver2.response.TextResponse;
import com.example.findaroomver2.response.UserResponseLogin;
import com.example.findaroomver2.response.bookmark.BookmarkResponse;
import com.example.findaroomver2.response.bookmark.ListBookmarkResponse;
import com.example.findaroomver2.response.comment.CommentListResponse;
import com.example.findaroomver2.response.comment.CommentResponse;
import com.example.findaroomver2.response.favourite.CountFavourite;
import com.example.findaroomver2.response.favourite.FavouriteResponse;
import com.example.findaroomver2.response.money.CashFlowResponse;
import com.example.findaroomver2.response.post.PostHome;
import com.example.findaroomver2.response.post.PostResponse;
import com.example.findaroomver2.response.supplement.DataSupplement;
import com.example.findaroomver2.response.updateUser.UserUpdateResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRequest {
    @POST("auth/login")
    Call<UserResponseLogin> login(@Body UserLoginRequest userLoginRequest);

    @GET("auth/getUserByToken")
    Call<UserResponseLogin> getUserByToken(@Header("x-access-token") String token);

    @POST("auth/register")
    Call<UserResponseLogin> register(@Body UserRegisterRequest userRegisterRequest);

    @POST("auth/checkEmailForgot")
    Call<TextResponse> checkEmail(@Body Email email);

    @POST("auth/validateUserPass")
    Call<TextResponse> checkOtpPass(@Body Verify verify);

    @POST("auth/newPass")
    Call<TextResponse> newPassWord(@Body UserLoginRequest userLogin);

    @GET("supplements/getAllSupplements")
    Call<DataSupplement> getListSupplement();

    @POST("post")
    Call<PostResponse> createPost(@Body Post post);

    @GET("auth/getUserById/{id}")
    Call<Data> getUserById(@Path("id") String idUser);

    @GET("postHome")
    Call<PostHome> getListPost();

    @GET("postHomeAds")
    Call<PostHome> getListPostHomeAds();

    @GET("searchLocationCty/{textLocation}")
    Call<PostHome> getListSearchByLocation(@Path("textLocation") String textLocation);

    @GET("searchLocationCtyAndPrice/{textLocation}&startPrice={startPrice}&endPrice={endPrice}")
    Call<PostHome> getListSearchLocationCtyAndPrice(@Path("textLocation") String textLocation, @Path("startPrice") String startPrice, @Path("endPrice") String endPrice);

    @GET("searchPrice/startPrice={startPrice}&endPrice={endPrice}")
    Call<PostHome> getListSearchPrice(@Path("startPrice") String startPrice, @Path("endPrice") String endPrice);

    @GET("post/{id}")
    Call<PostResponse> getPostById(@Path("id") String idUser);

    @POST("favourite")
    Call<FavouriteResponse> addFavourite(@Body Favourite favourite);

    @DELETE("favourite/{idUser}/{idPost}")
    Call<FavouriteResponse> deleteFavourite(@Path("idUser") String idUser, @Path("idPost") String idPost);

    @GET("favourite/{idUser}/{idPost}")
    Call<FavouriteResponse> getFavouriteByIdUserAndIdPost(@Path("idUser") String idUser, @Path("idPost") String idPost);

    @POST("bookmark")
    Call<BookmarkResponse> addBookmark(@Body Bookmark bookmark);

    @DELETE("bookmark/{idUser}/{idPost}")
    Call<BookmarkResponse> deleteBookmark(@Path("idUser") String idUser, @Path("idPost") String idPost);

    @GET("bookmark/{idUser}/{idPost}")
    Call<BookmarkResponse> getBookmarkByIdUserAndIdPost(@Path("idUser") String idUser, @Path("idPost") String idPost);

    @GET("getCountFavourite/{idPost}")
    Call<CountFavourite> getCountFavourite(@Path("idPost") String idPost);

    @POST("comment")
    Call<CommentResponse> addComment(@Body Comment comment);

    @GET("getListCommentParentByIdPost/{idPost}")
    Call<CommentListResponse> getListCommentParent(@Path("idPost") String idPost);

    @GET("getListCommentChildrenByIdPost/{parentCommentId}")
    Call<CommentListResponse> getListCommentChildren(@Path("parentCommentId") String parentCommentId);

    @GET("getmsg/{send}&{sendTo}")
    Call<DataChat> getDataChat(@Path("send") String sendId, @Path("sendTo") String sendToId);

    @GET("getMessage/{send}")
    Call<DataChat> getMsgId(@Path("send") String send);

    @GET("getMessageSendTo/{send}")
    Call<DataChat> getMessageSendTo(@Path("send") String send);

    @GET("getHost/{id}")
    Call<DataUser> getHost(@Path("id") String id);

    @POST("addmsg/")
    Call<MessageChat> addMessage(@Body MessageChat message);

    @GET("getCash/{id}")
    Call<Integer> getPriceCash(@Path("id") String id);

    @GET("getCountPost/{id}")
    Call<Integer> getCountPost(@Path("id") String id);

    @GET("listCashFlow/{id}")
    Call<List<CashFlowResponse>> getListCashFlow(@Path("id") String id);

    @POST("createCashFlow")
    Call<TextResponse> createCashFlow(@Body CashFlowRequest cashFlowRequest);

    @POST("changeInFo")
    Call<UserUpdateResponse> updateUserInFo(@Body UserEditProfileRequest userEditProfileRequest);

    @POST("updateAccount/{id}")
    Call<UserUpdateResponse> updateAccount(@Path("id") String id);

    @PATCH("updatePassword")
    Call<TextResponse> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @GET("bookmark/getListBookmarkByUserId/{id}")
    Call<ListBookmarkResponse> getListBookmarkByIdUser(@Path("id") String id);

    @GET("postByIdUser/{id}")
    Call<PostHome> getListPostByMyself(@Path("id") String id);

    @DELETE("post/{id}")
    Call<PostResponse> deletePost(@Path("id") String idUser);

    @PATCH("post/{id}")
    Call<PostResponse> editPost(@Body Post post, @Path("id") String idUser);

    @POST("post/postUpdateStatusRoom")
    Call<PostResponse> updateStatusRoom(@Body Post post);

    @POST("ads/updatePostAds")
    Call<PostResponse> adsPost(@Body Post post);

    @GET("post/getStatusPost/{id}")
    Call<Boolean> getStatusPost(@Path("id") String idPost);

    @GET("post/getStatusAds/{id}")
    Call<Boolean> getStatusAds(@Path("id") String idPost);

    @POST("post/updateView")
    Call<String> updateView(@Body ViewUpdatePost viewUpdatePost);

    @GET("searchLocationAndPost/{textLocation}")
    Call<List<SearchModel>> getListSearchLocationPost(@Path("textLocation") String textLocation);

    @POST("auth/updateCheckTokenDevice")
    Call<TextResponse> updateTokenDevice(@Body UserRequestTokenDevice userRequestTokenDevice);

    @GET("getListNotificationByIdUser/{id}")
    Call<ListNotificationResponse> getListNotificationByIdUser(@Path("id") String id);

    @PATCH("updateNotificationSeen/{id}")
    Call<TextResponse> updateNotiSeen(@Path("id") String id);

    @POST("updateAds/idPost={id}&dayAds={day}")
    Call<String> updateAds(@Path("id") String id, @Path("day") String day);

}

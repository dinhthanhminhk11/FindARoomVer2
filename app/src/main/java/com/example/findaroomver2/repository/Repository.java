package com.example.findaroomver2.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.airbnb.lottie.animation.content.Content;
import com.example.findaroomver2.api.ApiRequest;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.model.ContentChat;
import com.example.findaroomver2.model.DataChat;
import com.example.findaroomver2.model.DataUser;
import com.example.findaroomver2.model.MessageChat;
import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.request.bookmark.Bookmark;
import com.example.findaroomver2.request.changeInfo.UserEditProfileRequest;
import com.example.findaroomver2.request.changepass.ChangePasswordRequest;
import com.example.findaroomver2.request.changepass.Email;
import com.example.findaroomver2.request.changepass.Verify;
import com.example.findaroomver2.request.comment.Comment;
import com.example.findaroomver2.request.favourite.Favourite;
import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.request.money.CashFlowRequest;
import com.example.findaroomver2.request.register.UserRegisterRequest;
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
import com.example.findaroomver2.retrofit.AndroidUtilities;
import com.example.findaroomver2.retrofit.RetrofitRequest;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("NewApi")
public class Repository {
    private ApiRequest apiRequest;

    public Repository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void login(UserLoginRequest userLoginRequest, Consumer<UserResponseLogin> consumer) {
        AndroidUtilities.runOnUIThread(() -> {
            apiRequest.login(userLoginRequest).enqueue(new Callback<UserResponseLogin>() {
                @Override
                public void onResponse(Call<UserResponseLogin> call, Response<UserResponseLogin> response) {
                    if (response.isSuccessful()) {
                        consumer.accept(response.body());
                    } else {
                        Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                    }
                }

                @Override
                public void onFailure(Call<UserResponseLogin> call, Throwable t) {
                    Log.e(AppConstant.CALL_ERROR, t.getMessage());
                }
            });
        });
    }

    public void loginByToken(String token, Consumer<UserResponseLogin> consumer) {
        AndroidUtilities.runOnUIThread(() -> {
            apiRequest.getUserByToken(token).enqueue(new Callback<UserResponseLogin>() {
                @Override
                public void onResponse(Call<UserResponseLogin> call, Response<UserResponseLogin> response) {
                    if (response.isSuccessful()) {
                        consumer.accept(response.body());
                    } else {
                        Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                    }
                }

                @Override
                public void onFailure(Call<UserResponseLogin> call, Throwable t) {
                    Log.e(AppConstant.CALL_ERROR, t.getMessage());
                }
            });
        });
    }

    public void register(UserRegisterRequest userRegisterRequest, Consumer<UserResponseLogin> consumer) {
        AndroidUtilities.runOnUIThread(() -> {
            apiRequest.register(userRegisterRequest).enqueue(new Callback<UserResponseLogin>() {
                @Override
                public void onResponse(Call<UserResponseLogin> call, Response<UserResponseLogin> response) {
                    if (response.isSuccessful()) {
                        consumer.accept(response.body());
                    } else {
                        Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                    }
                }

                @Override
                public void onFailure(Call<UserResponseLogin> call, Throwable t) {
                    Log.e(AppConstant.CALL_ERROR, t.getMessage());
                }
            });
        });
    }

    public void sendMailForgotPass(Email email, Consumer<TextResponse> consumer) {
        AndroidUtilities.runOnUIThread(() -> {
            apiRequest.checkEmail(email).enqueue(new Callback<TextResponse>() {
                @Override
                public void onResponse(Call<TextResponse> call, Response<TextResponse> response) {
                    if (response.isSuccessful()) {
                        consumer.accept(response.body());
                    } else {
                        Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                    }
                }

                @Override
                public void onFailure(Call<TextResponse> call, Throwable t) {
                    Log.e(AppConstant.CALL_ERROR, t.getMessage());
                }
            });
        });
    }

    public void checkOTPPass(Verify verify, Consumer<TextResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.checkOtpPass(verify).enqueue(new Callback<TextResponse>() {
                    @Override
                    public void onResponse(Call<TextResponse> call, Response<TextResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<TextResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void newPassWord(UserLoginRequest userLoginRequest, Consumer<TextResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.newPassWord(userLoginRequest).enqueue(new Callback<TextResponse>() {
                    @Override
                    public void onResponse(Call<TextResponse> call, Response<TextResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<TextResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void getListSupplement(Consumer<DataSupplement> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.getListSupplement().enqueue(new Callback<DataSupplement>() {
                    @Override
                    public void onResponse(Call<DataSupplement> call, Response<DataSupplement> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<DataSupplement> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void createPost(Post post, Consumer<PostResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.createPost(post).enqueue(new Callback<PostResponse>() {
                    @Override
                    public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void getUserById(String idUser, Consumer<Data> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.getUserById(idUser).enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void getListPost(Consumer<PostHome> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.getListPost().enqueue(new Callback<PostHome>() {
                    @Override
                    public void onResponse(Call<PostHome> call, Response<PostHome> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostHome> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void getPostById(String id, Consumer<PostResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.getPostById(id).enqueue(new Callback<PostResponse>() {
                    @Override
                    public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void addFavourite(Favourite favourite, Consumer<FavouriteResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.addFavourite(favourite).enqueue(new Callback<FavouriteResponse>() {
                    @Override
                    public void onResponse(Call<FavouriteResponse> call, Response<FavouriteResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<FavouriteResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void addBookmark(Bookmark bookmark, Consumer<BookmarkResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.addBookmark(bookmark).enqueue(new Callback<BookmarkResponse>() {
                    @Override
                    public void onResponse(Call<BookmarkResponse> call, Response<BookmarkResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void deleteFavourite(String idUser, String idPost, Consumer<FavouriteResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.deleteFavourite(idUser, idPost).enqueue(new Callback<FavouriteResponse>() {
                    @Override
                    public void onResponse(Call<FavouriteResponse> call, Response<FavouriteResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<FavouriteResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void deleteBookmark(String idUser, String idPost, Consumer<BookmarkResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.deleteBookmark(idUser, idPost).enqueue(new Callback<BookmarkResponse>() {
                    @Override
                    public void onResponse(Call<BookmarkResponse> call, Response<BookmarkResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void getFavouriteByIdUserAndIdPost(String idUser, String idPost, Consumer<FavouriteResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.getFavouriteByIdUserAndIdPost(idUser, idPost).enqueue(new Callback<FavouriteResponse>() {
                    @Override
                    public void onResponse(Call<FavouriteResponse> call, Response<FavouriteResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<FavouriteResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void getBookmarkByIdUserAndIdPost(String idUser, String idPost, Consumer<BookmarkResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.getBookmarkByIdUserAndIdPost(idUser, idPost).enqueue(new Callback<BookmarkResponse>() {
                    @Override
                    public void onResponse(Call<BookmarkResponse> call, Response<BookmarkResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void getCountFavouriteByIdPost(String idPost, Consumer<CountFavourite> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.getCountFavourite(idPost).enqueue(new Callback<CountFavourite>() {
                    @Override
                    public void onResponse(Call<CountFavourite> call, Response<CountFavourite> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<CountFavourite> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void addComment(Comment comment, Consumer<CommentResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.addComment(comment).enqueue(new Callback<CommentResponse>() {
                    @Override
                    public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<CommentResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void getListCommentParent(String idPost, Consumer<CommentListResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.getListCommentParent(idPost).enqueue(new Callback<CommentListResponse>() {
                    @Override
                    public void onResponse(Call<CommentListResponse> call, Response<CommentListResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<CommentListResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void getListCommentChildren(String idCommentParent, Consumer<CommentListResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.getListCommentChildren(idCommentParent).enqueue(new Callback<CommentListResponse>() {
                    @Override
                    public void onResponse(Call<CommentListResponse> call, Response<CommentListResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<CommentListResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public MutableLiveData<List<ContentChat>> getContentChat(String sendId, String sendToId) {
        final MutableLiveData<List<ContentChat>> data = new MutableLiveData<>();
        apiRequest.getDataChat(sendId, sendToId).enqueue(new Callback<DataChat>() {
            @Override
            public void onResponse(Call<DataChat> call, Response<DataChat> response) {
                data.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<DataChat> call, Throwable t) {
                Log.e(AppConstant.CALL_ERROR, t.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<List<ContentChat>> getMsgId(String send) {
        final MutableLiveData<List<ContentChat>> data = new MutableLiveData<>();
        apiRequest.getMsgId(send).enqueue(new Callback<DataChat>() {
            @Override
            public void onResponse(Call<DataChat> call, Response<DataChat> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<DataChat> call, Throwable t) {
                Log.e(AppConstant.CALL_ERROR, t.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<List<ContentChat>> getMsgIdSendTo(String send) {
        final MutableLiveData<List<ContentChat>> data = new MutableLiveData<>();
        apiRequest.getMessageSendTo(send).enqueue(new Callback<DataChat>() {
            @Override
            public void onResponse(Call<DataChat> call, Response<DataChat> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<DataChat> call, Throwable t) {
                Log.e(AppConstant.CALL_ERROR, t.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<List<Data>> getHost(String id) {
        final MutableLiveData<List<Data>> user = new MutableLiveData<>();
        apiRequest.getHost(id).enqueue(new Callback<DataUser>() {
            @Override
            public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                if (response.isSuccessful()) {
                    user.postValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<DataUser> call, Throwable t) {
                Log.e(AppConstant.CALL_ERROR, t.getMessage());
            }
        });
        return user;
    }

    public void insertMessage(MessageChat message) {
        apiRequest.addMessage(message).enqueue(new Callback<MessageChat>() {
            @Override
            public void onResponse(Call<MessageChat> call, Response<MessageChat> response) {
                Log.e(AppConstant.CALL_ERROR, "da gui");
            }

            @Override
            public void onFailure(Call<MessageChat> call, Throwable t) {
                Log.e(AppConstant.CALL_ERROR, t.getMessage());
            }
        });
    }

    public void getPriceCashPay(String id, Consumer<Integer> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.getPriceCash(id).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void getCountPost(String id, Consumer<Integer> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.getCountPost(id).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }


    public void getListPayCashFlow(String id, Consumer<List<CashFlowResponse>> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.getListCashFlow(id).enqueue(new Callback<List<CashFlowResponse>>() {
                    @Override
                    public void onResponse(Call<List<CashFlowResponse>> call, Response<List<CashFlowResponse>> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CashFlowResponse>> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void createCashByUser(CashFlowRequest cashFlowRequest, Consumer<TextResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.createCashFlow(cashFlowRequest).enqueue(new Callback<TextResponse>() {
                    @Override
                    public void onResponse(Call<TextResponse> call, Response<TextResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<TextResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void updateUserInFo(UserEditProfileRequest userEditProfileRequest, Consumer<UserUpdateResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.updateUserInFo(userEditProfileRequest).enqueue(new Callback<UserUpdateResponse>() {
                    @Override
                    public void onResponse(Call<UserUpdateResponse> call, Response<UserUpdateResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserUpdateResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void updateAccount(String id, Consumer<UserUpdateResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.updateAccount(id).enqueue(new Callback<UserUpdateResponse>() {
                    @Override
                    public void onResponse(Call<UserUpdateResponse> call, Response<UserUpdateResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserUpdateResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest, Consumer<TextResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.changePassword(changePasswordRequest).enqueue(new Callback<TextResponse>() {
                    @Override
                    public void onResponse(Call<TextResponse> call, Response<TextResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<TextResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void getListBookmarkByIdUser(String id, Consumer<ListBookmarkResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.getListBookmarkByIdUser(id).enqueue(new Callback<ListBookmarkResponse>() {
                    @Override
                    public void onResponse(Call<ListBookmarkResponse> call, Response<ListBookmarkResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<ListBookmarkResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }
}

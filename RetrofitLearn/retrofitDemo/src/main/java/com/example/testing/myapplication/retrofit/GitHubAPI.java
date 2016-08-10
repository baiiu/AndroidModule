package com.example.testing.myapplication.retrofit;

import com.example.testing.myapplication.bean.Repo;
import com.example.testing.myapplication.bean.User;
import java.util.List;
import java.util.Map;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * author: baiiu
 * date: on 16/5/16 14:40
 * description:
 *
 * github的API
 *
 * baiiu is an example user.
 */
public interface GitHubAPI {

    /**
     * ============================GET请求==============================
     */

  /*
    普通的
    https://api.github.com/users/baiiu
   */
    @GET("users/{user}") Call<User> userInfo(@Path("user") String user);

    @GET("users/{user}") Call<String> userInfoString(@Path("user") String user);

    @GET("users/{user}") Observable<User> userInfoRx(@Path("user") String user);

    @GET("users/{user}") Observable<String> userInfoRxS(@Path("user") String user);

    /*
      带路径参数
      使用 @Path 表示
      https://api.github.com/users/baiiu/repos
     */
    @GET("users/{user}/repos") Call<List<Repo>> listRepos(@Path("user") String user);

    /*
      带一个查询参数
      使用 @Query 表示
      https://api.github.com/group/baiiu/users?sort=desc
     */
    @GET("group/{id}/users") Call<List<User>> groupList(@Path("id") int groupId,
            @Query("sort") String sort);

    /*
      带很多查询参数,用map封装
      @QueryMap
     */
    @GET("group/{id}/users") Call<List<User>> groupList(@Path("id") int groupId,
            @QueryMap Map<String, String> options);

    /**
     * ========================POST请求==============================
     */
  /*
   POST请求体的方式向服务器传入json字符串

   @body 表示请求体,converter自动转换
  */
    @POST("users/new") Call<User> createUser(@Body User user);

    //======================POST请求提交==================================
  /*
  可查看了解:
    [四种常见的 POST 提交数据方式](https://imququ.com/post/four-ways-to-post-data-in-http.html)
    [Retrofit2 完全解析 探索与okhttp之间的关系](http://blog.csdn.net/lmj623565791/article/details/51304204)
  */

    /*
      以表单的方式传递简单的键值对

      @FormUrlEncoded表示:
      Content-Type: application/x-www-form-urlencoded; 这是最常见的POST提交方式
     */
    @FormUrlEncoded @POST("user/edit") Call<User> updateUser(@Field("first_name") String first,
            @Field("last_name") String last);

    /*
      以PUT表单的方式上传文件,并且可以携带参数

      @Multipart表示:使用表单上传文件时
      Content-Type:multipart/form-data;

      @Part 表示每个部分

      传文件时用MultipartBody.Part类型: @Part MultipartBody.Part photo

     */
    @Multipart @PUT("user/photo") Call<User> updateUser(@Part("photo") RequestBody photo,
            @Part("description") RequestBody description);

    /*
      多文件上传
      @PartMap

      可以在Map中put进一个或多个文件，并且还可以放键值对

      示例:
      File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
          RequestBody photo = RequestBody.create(MediaType.parse("image/png", file);
      Map<String,RequestBody> photos = new HashMap<>();
      photos.put("photos\"; filename=\"icon.png", photo);
      photos.put("username",  RequestBody.create(null, "abc"));

      Call<User> call = userBiz.registerUser(photos, RequestBody.create(null, "123"));
     */
    @Multipart @PUT("register") Call<User> registerUser(@PartMap Map<String, RequestBody> params,
            @Part("password") RequestBody password);

    /*
      文件下载,可以考虑使用OkHttp直接来做

      Call<ResponseBody> call = userBiz.downloadTest();
      call.enqueue(new Callback<ResponseBody>()
      {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
          {
              InputStream is = response.body().byteStream();
              //save file,异步处理
          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t)
          {

          }
      });
     */
    @GET("download") Call<ResponseBody> downloadTest();

}

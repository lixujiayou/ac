package com.inspur.resources.http;




import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2016/11/29.
 */
public interface MallRequest {

  /*  @FormUrlEncoded
    @POST(AllUrl.loginUrl)
    Call<CallBackBean> login(@Field("useraccount") String userName, @Field("password") String pwd);

    @FormUrlEncoded
    @POST(AllUrl.queryList)
    Call<RightResult> getBookList(@Field("UID") String uid
            , @Field("start") String page
            , @Field("pageSize") String pageSize
            , @Field("cityId") String cityId
            , @Field("countyId") String countyId);*/


    /**
     * 上传一张图片
     * @return
     */
    /*@Multipart
    @POST(AllUrl.uploadImageTrue)
    Call<StringBean> uploadImageTrue(@PartMap Map<String, RequestBody> params);



    @FormUrlEncoded
    @POST(AllUrl.uploadImage)
    Call<UpLoadResult> uploadImage(
            @Field("id") String id
            , @Field("photoString") String photoString);*/



    @Multipart
    @POST()
    Call <ResponseBody> uploadFiles(@Url String url, @PartMap() Map<String, RequestBody> maps);


    @Multipart
    @POST
    Call<ResponseBody> uploadFileWithPartMap(
            @Url() String url,
            @PartMap() Map<String, RequestBody> partMap,
            @Part("file") MultipartBody.Part file);



/*    @Multipart
    @POST(AllUrl.uploadImage)
    Call<String> updateImage(@Part MultipartBody.Part[] file, @QueryMap Map<String, String> maps);*/


    @Streaming //大文件时要加不然会OOM
    @FormUrlEncoded
    @POST
    Call<ResponseBody> downloadFile(@Url String fileUrl, @Field("jsonRequest") String jsonRequest);
}

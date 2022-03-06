package top.yang;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IMvtuiCom {

    @GET("/category/pic/page/{id}/")
    Call<ResponseBody> getHTML(@Path("id") Integer id);

    @GET("/{id}.html/{page}")
    Call<ResponseBody> getIdHTML(@Path("id") String id, @Path("page") Integer page);

    @GET("/{path1}/{path2}/{path3}/{path4}/{name}")
    Call<ResponseBody> getImgs(@Path("path1") String path1, @Path("path2") String path2, @Path("path3") String path3, @Path("path4") String path4, @Path("name") String name);
}

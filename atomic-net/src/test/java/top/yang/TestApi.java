package top.yang;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import top.yang.net.annotation.RetrofitClient;

@RetrofitClient(baseUrl = "https://mmzztt.com/")
public interface TestApi {

    @POST("/photo/${path}")
    public Call<RequestBody> getString(@Path("path") String path);
}

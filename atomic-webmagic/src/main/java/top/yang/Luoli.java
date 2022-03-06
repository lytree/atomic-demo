package top.yang;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author pride
 */
public interface Luoli {

    @GET("index.php/category/luolimengmei/page/{id}/")
    Call<ResponseBody> getHTML(@Path("id") Integer id);
}

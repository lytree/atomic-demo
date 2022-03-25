package top.yang;

import java.io.IOException;
import okhttp3.RequestBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import retrofit2.Call;
import retrofit2.Response;

@SpringBootTest(classes = RetrofitTestApplication.class)
@RunWith(SpringRunner.class)
public class RetrofitRunTest {

    @Autowired
    private TestApi testApi;

    @Test
    public void test() throws IOException {
        Call<RequestBody> string1 = testApi.getString("61543");
        Response<RequestBody> execute = string1.execute();
        RequestBody body1 = execute.body();
        String body = body1.toString();
        System.out.println(body);
    }
}

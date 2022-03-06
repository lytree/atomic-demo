package top.yang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author pride
 */
public class Mvtui {

    public static void main(String[] args) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new LoggingInterceptor()).build();
            Retrofit build = new Builder().baseUrl("https://mvtui.com").addConverterFactory(JacksonConverterFactory.create()).client(okHttpClient).build();
            IMvtuiCom luoli = build.create(IMvtuiCom.class);
            for (int i = 1; i <= 1; i++) {
                Call<ResponseBody> html = luoli.getHTML(i);
                String string1 = html.execute().body().string();
                Document parse = Jsoup.parse(string1);
                Element posts = parse.getElementById("posts");
                Elements children = posts.children();
                for (Element element : children) {
                    String attr = element.attr("data-id");
                    Call<ResponseBody> idHTML = luoli.getIdHTML(attr, 1);
                    String string = idHTML.execute().body().string();
                    Document document = Jsoup.parse(string);
                    Elements pages = document.getElementsByClass("article-paging");
                    Elements a = pages.get(0).getElementsByTag("a");
                    int pageSize = a.size();

                    for (int page = 1; page <= pageSize; page++) {
                        String string2 = luoli.getIdHTML(attr, page).execute().body().string();
                        Document document1 = Jsoup.parse(string2);
                        Elements elementsByClass = document1.getElementsByClass("article-content");
                        Elements imgs = elementsByClass.get(0).getElementsByTag("img");
                        for (Element img : imgs) {
                            String src = img.attr("src");
                            String replace = src.replace("https://mvtui.com/", "");
                            String[] split = replace.split("/");
                            Call<ResponseBody> imgs1 = luoli.getImgs(split[0], split[1], split[2], split[3], split[4]);
                            InputStream inputStream = Objects.requireNonNull(imgs1.execute().body()).byteStream();
                            byte[] bytes = Main.readInputStream(inputStream);
                            String name = "D:/" + split[0] + File.separator + split[1] + File.separator + split[2] + File.separator + split[3] + File.separator + split[4];
                            Main.createFile(name);
                            FileOutputStream fileOutputStream = new FileOutputStream(
                                    name);
                            fileOutputStream.write(bytes);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

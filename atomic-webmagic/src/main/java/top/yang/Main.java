package top.yang;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author pride
 */
public class Main {
    public static void main(String[] args) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new LoggingInterceptor()).build();
            Retrofit build = new Builder().baseUrl("https://www.gcavi.com/").addConverterFactory(JacksonConverterFactory.create()).client(okHttpClient).build();
            Luoli luoli = build.create(Luoli.class);
            for (int i = 1; i < 35; i++) {
                Call<ResponseBody> html = luoli.getHTML(i);
                String string1 = html.execute().body().string();
                System.out.println(string1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {

        //构造一个ByteArrayOutputStream
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        //设置一个缓冲区
        byte[] buffer = new byte[1024];

        int len = 0;

        //判断输入流长度是否等于-1  ，即非空
        while ((len = inStream.read(buffer)) != -1) {

            //把缓冲区的内容写入到输出流中，从0开始读取，长度为len
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            System.out.println("目标文件所在目录不存在，准备创建它！");
            if (!file.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                System.out.println("创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                System.out.println("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            return false;
        }
    }


    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }

}

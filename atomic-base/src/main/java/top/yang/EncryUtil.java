package top.yang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 加密解密工具类
 */
public class EncryUtil {
    /**
     * 使用默认密钥进行DES加密
     */
    public static String encrypt(String plainText) {
        try {
            return new DES().encrypt(plainText);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 使用指定密钥进行DES解密
     */
    public static String encrypt(String plainText, String key) {
        try {
            return new DES(key).encrypt(plainText);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 使用默认密钥进行DES解密
     */
    public static String decrypt(String plainText) {
        try {
            return new DES().decrypt(plainText);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 使用指定密钥进行DES解密
     */
    public static String decrypt(String plainText, String key) {
        try {
            return new DES(key).decrypt(plainText);
        } catch (Exception e) {
            return null;
        }
    }

    public static int compareDate(Long deadLine, Long now) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
//            Date dt1 = df.parse(now);
//            Date dt2 = df.parse(deadLine);
            if (now < deadLine) {
                //System.out.println("now = "+now);
                //System.out.println("deadLine = "+deadLine);
                //System.out.println("在截止日期前");
                return 1;
            } else if (now > deadLine) {
                //System.out.println("在截止日期后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        String encrypt = encrypt("1234554321","Windit");
        String decrypt = decrypt("d6d1c70b5191d93a3263d349e53ad45a");
        System.out.println(encrypt);
        System.out.println(decrypt);
    }
}
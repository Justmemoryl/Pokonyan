package cn.jml.pokonyan.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密方法
 * 
 * @version 1.0 created by chenzhenwei on 2018年6月19日 下午5:42:18
 */
public class MD5Util {
    /**
     * 对string进行MD5加密(标准方法)
     *
     * @param string
     *            String
     * @return String
     */
    public static String generateMD5String(String string) {
        String result = "";

        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(string.getBytes("UTF8"));
            byte s[] = m.digest();

            for (byte value : s) {
                result += Integer.toHexString((0x000000ff & value) | 0xffffff00)
                    .substring(6);
            }
        } catch (Exception e) {
            result = null;
        }

        return result;
    }

    /**
     * 对string进行md5加密后与md5String进行比较
     *
     * @param string
     *            String 要进行md5加密的字符串
     * @param md5String
     *            String 预期得到加密后的md5密文
     * @return boolean
     */
    public static boolean compareMD5String(String string, String md5String) {
        boolean result;

        if (generateMD5String(string).equalsIgnoreCase(md5String)) {
            result = true;
        } else if (generateMD5StringOld(string).equalsIgnoreCase(md5String)) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    /**
     * 兼容以前的产生的md5加密方法
     * 
     * @param string
     *            String
     * @return String
     */
    public static String generateMD5StringOld(String string) {
        try {
            byte[] res = string.getBytes();

            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] result = md.digest(res);
            for (byte aResult : result) {
                md.update(aResult);
            }

            byte[] hash = md.digest();
            StringBuilder d = new StringBuilder("");

            for (byte aHash : hash) {
                int v = aHash & 0xFF;
                if (v < 16) {
                    d.append("0");
                }
                d.append(Integer.toString(v, 16).toUpperCase()).append("");
            }
            return d.toString();

        } catch (Exception e) {
            return null;
        }

    }

    protected static char          hexDigits[]   = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    protected static MessageDigest messagedigest = null;
    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对byte类型的数组进行MD5加密
     * 
     * @author 高焕杰
     */
    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuilder stringBuilder = new StringBuilder(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            char c0 = hexDigits[(bytes[l] & 0xf0) >> 4];
            char c1 = hexDigits[bytes[l] & 0xf];
            stringBuilder.append(c0);
            stringBuilder.append(c1);
        }
        return stringBuilder.toString();
    }

    /**
     * 测试方法
     * 
     * @param args
     */
    public static void main(String[] args) {
        // MD5 md5 = new MD5();
        System.out.println(MD5Util.generateMD5String("123456").toUpperCase());
        System.out.println(MD5Util.compareMD5String("a",
            "0cc175b9c0f1b6a831c399e269772661"));
        System.out.println(MD5Util.compareMD5String("a",
            "B6FF9A06B7E20BCB2858C5B8FF744AEA"));
        // B6FF9A06B7E20BCB2858C5B8FF744AEA
        // 0cc175b9c0f1b6a831c399e269772661
        System.out.println(getMD5String("sfsf".getBytes()));
    }

}

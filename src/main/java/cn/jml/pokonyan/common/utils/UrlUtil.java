package cn.jml.pokonyan.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.net.URLEncoder;

/**
 * @version 1.0 created by chenzhenwei_fh on 2018/7/4 13:56
 */
public class UrlUtil {
    /**
     * 对URL进行Encode加密处理
     *
     * @param srt
     * @return
     */
    public static String encode(String srt) {
        try {

            if (StringUtils.isEmpty(srt)) {
                return "";
            }
            return URLEncoder.encode(srt, "UTF-8");
        } catch (Exception e) {
            return srt;
        }
    }

    public static void main(String[] args) {
//        String uid = "fb36f550-c715-4b23-b989-48ace83a3a60";
//        String actionUrl = "mgmusic://user-home-page?id=" + uid;
//        String encodeUrl = encode(actionUrl);
//        System.out.println(encodeUrl);
    }
}

package cn.jml.pokonyan.common.utils;

import java.util.UUID;

/**
 * uuid(用户ID)生成工具类
 * 
 * @version 1.0 created by chenzhenwei on 2018年6月19日 下午5:36:25
 */
public class UUIDGenerator {
    /**
     * 工具类,不允许实例化
     */
    private UUIDGenerator() {}

    /**
     * 获得一个UUID,只包含数字与字母,不包含分隔符
     *
     * @return String UUID
     */
    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        return s;
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number
     *            int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }
}

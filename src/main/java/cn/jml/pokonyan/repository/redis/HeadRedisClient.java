package cn.jml.pokonyan.repository.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Tuple;

public interface HeadRedisClient {
    String OPT_SUCCESS = "OK";

    byte[] serialize(Object t);

    Object deserialize(byte[] bytes);

    Long rpush(String key, String... val);

    Long lpush(String key, String... val);

    Long llen(String key);

    List<String> lrange(String key, long start, long end);

    String lset(String key, long index, String value);

    String lpop(String key);

    String rpop(String key);

    Boolean exists(String key);

    String set(String key, String val);

    String setex(String key, String value, long expire);

    String get(String key);

    String getset(String key, String value);

    Long lrem(String key, long count, String val);

    Long sadd(String key, String val);

    String spop(String key);

    /**
     * 仅3.2以上版本的redis支持该方法
     * 
     * @param key
     * @param count
     * @return
     */
    Set<String> spop(String key, long count);

    /**
     * 3.2之前的版本需要弹出多个set中元素使用该接口
     * 
     * @param key
     * @param count
     * @return
     */
    Set<String> spop30(String key, long count);

    Set<String> smembers(String key);

    Long scard(String key);

    /**
     * @param key
     * @param expire
     *            单位：秒
     * @return
     */
    Boolean expire(String key, int expire);

    Long srem(String key, String... val);

    Boolean sismember(String key, String member);

    Long incr(String key);

    Long incrBy(String key, long count);

    Long decr(String key);

    Long hincrBy(String key, String field, long value);

    List<String> hmget(String key, String... fields);

    Long hgetNum(String key, String field);

    Boolean hsetnx(String key, String field, String value);

    Map<String, String> hgetAll(String key);

    String hmset(String key, Map<String, String> hash);

    String hmsetExpire(String key, Map<String, String> hash, int expire);

    Boolean zadd(String key, double score, String member);

    Long zadd(String key, Map<String, Double> stringDoubleMap);

    Long zrem(String key, String member);

    Double zscore(String key, String member);

    Set<String> zquery(String key, long start, long end, boolean isAsc);

    Long zcard(String key);

    String hget(String key, String field);

    Long hdel(String key, String... fields);

    String hset(String key, String field, String value);

    Boolean hexists(String key, String field);

    /**
     * 获取对应key的缓存时间,如果不存在,就返回0
     * 
     * @return
     */
    default int getExpireTime(String key) {
        String i = hget("EXPIRETIMES", key);
        if (null == i) {
            return 0;
        }
        return Integer.valueOf(i);
    }

    Long sadd(String key, String... member);

    String mset(String... keyvalues);

    List<String> mget(String... keys);

    Boolean setnx(String key, String value);

    boolean setnxExpire(String key, String value, int seconds);

    void setPipeLine(Map<String, String> maps);

    boolean setnxExpireAt(String key, String value, long unixTime);

    void delPipeLine(String... keys);

    void delPipeLineSet(Set<String> keys);

    Set<String> zrangeByScore(String key, double start, double end, boolean isAsc);

    Set<String> zrangeByScore(String key, double start, double end, int offset,
                              int limit, boolean isAsc);

    Set<Tuple> zrangeByScoreWithScores(String key, double start, double end, int offset,
                                       int limit, boolean isAsc);

    Set<Tuple> zrangeByScoreWithScores(String key, double start, double end, boolean isAsc);

    /**
     * @param key
     * @param timeout
     * @return
     */
    List<String> blpop(String key, int timeout);

    Long zcount(String key, double start, double end);

    void publish(String channel, String msg);

    void subscribe(JedisPubSub jedisPubSub, String... channels);

    void binset(String key, Object val);

    void binset(String key, Object val, int expire);

    Object binget(String key);

    Object binhget(String key, Object field);

    void binhset(String key, Object field, byte[] value);

    void binhset(String key, Object field, Object value);

    Boolean bindel(String... keys);

    Set<String> keys(String regex);

    List<String> srandmember(String key, Integer count);

    Boolean expireAt(String key, long unixtime);

    Long zrank(String key, String member);

    String rename(String oldkey, String newkey);

    String lindex(String key, long index);

    JedisCommands getResource(boolean isread);

    Set<String> hkeys(String key);

    /**
     * 通过key删除
     * 
     * @param key
     */
    Boolean del(String key);

    /**
     * 通过keys删除
     * 
     * @param keys
     */
    Long del(String... keys);

    /**
     * 使用游标获取迭代key
     * 
     * @param regx
     *            正则
     * @param count
     *            需求的数量
     * @return List<String> key列表
     */
    List<String> scan(String regx, Integer count);

    /**
     * ADD by liufeng 20170508 查询hash中field的数量
     * 
     * @param key
     * @return
     */
    Long hlen(String key);

    default String getVHost() {
        return "";
    }

    /**
     * 每个存入缓存的数据需要首先调用这个方法，防止KEY的冲突
     * 
     * @param key
     * @return
     */
    default String createKey(String key) {
        return getVHost() + ":" + key;
    }

    /**
     * 每个存入缓存的数据需要首先调用这个方法，防止KEY的冲突
     * 
     * @param keys
     * @return
     */
    default String[] createKey(String[] keys) {
        String[] _keys = null;
        // 以HEAD和key生成新的key
        if (ArrayUtils.isNotEmpty(keys)) {
            _keys = new String[keys.length];
            // 以HEAD和key生成新的key
            for (int i = 0; i < keys.length; i++) {
                // 以HEAD和key生成新的key
                _keys[i] = createKey(keys[i]);
            }
        }
        return _keys;
    }
}

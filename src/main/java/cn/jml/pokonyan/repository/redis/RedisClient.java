package cn.jml.pokonyan.repository.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

import redis.clients.jedis.Tuple;

public interface RedisClient {
    Long rpush(String head, String key, String... val);

    Long lpush(String head, String key, String... val);

    Long llen(String head, String key);

    List<String> lrange(String head, String key, long start, long end);

    String lset(String head, String key, long index, String value);

    String lpop(String head, String key);

    String rpop(String head, String key);

    Boolean exists(String head, String key);

    Boolean del(String head, String key);

    Long del(String head, String... key);

    String set(String head, String key, String val);

    String get(String head, String key);

    String getset(String head, String key, String value);

    Long lrem(String head, String key, long count, String val);

    Long sadd(String head, String key, String val);

    String spop(String head, String key);

    /**
     * 仅3.2以上版本的redis支持该方法
     * 
     * @param head
     * @param key
     * @param count
     * @return
     */
    Set<String> spop(String head, String key, long count);

    /**
     * 3.2之前的版本需要弹出多个set中元素使用该接口
     * 
     * @param head
     * @param key
     * @param count
     * @return
     */
    Set<String> spop30(String head, String key, long count);

    Set<String> smembers(String head, String key);

    Long scard(String head, String key);

    Boolean expire(String head, String key);

    /**
     * @param head
     * @param key
     * @param expire
     *            单位：秒
     * @return
     */
    Boolean expire(String head, String key, int expire);

    String setex(String head, String key, String value);

    Long srem(String head, String key, String... val);

    Boolean sismember(String head, String key, String member);

    Long incr(String head, String key);

    Long incrBy(String head, String key, long count);

    Long decr(String head, String key);

    Long hincrBy(String head, String key, String field, long value);

    Boolean hexists(String head, String key, String field);

    List<String> hmget(String head, String key, String... fields);

    Long hgetNum(String head, String key, String field);

    Boolean hsetnx(String head, String key, String field, String value);

    Map<String, String> hgetAll(String head, String key);

    String hmset(String head, String key, Map<String, String> hash);

    String hmsetExpire(String head, String key, Map<String, String> hash);

    Boolean zadd(String head, String key, double score, String member);

    Long zadd(String head, String key, Map<String, Double> stringDoubleMap);

    Long zrem(String head, String key, String member);

    Double zscore(String head, String key, String member);

    Set<String> zquery(String head, String key, long start, long end, boolean isAsc);

    Long zcard(String head, String key);

    String hget(String head, String key, String field);

    Long hdel(String head, String key, String... fields);

    String hset(String head, String key, String field, String value);

    Long sadd(String head, String key, String... member);

    String mset(String head, String... keyvalues);

    List<String> mget(String head, String... keys);

    Boolean setnx(String head, String key, String value);

    boolean setnxExpire(String head, String key, String value, int seconds);

    void setPipeLine(String head, Map<String, String> maps);

    boolean setnxExpireAt(String head, String key, String value, long unixTime);

    void delPipeLine(String head, String... keys);

    void delPipeLineSet(String head, Set<String> keys);

    Set<String> zrangeByScore(String head, String key, double start, double end, boolean isAsc);

    Set<String> zrangeByScore(String head, String key, double start, double end, int offset,
                              int limit, boolean isAsc);

    Set<Tuple> zrangeByScoreWithScores(String head, String key, double start, double end, int offset,
                                       int limit, boolean isAsc);

    Set<Tuple> zrangeByScoreWithScores(String head, String key, double start, double end, boolean isAsc);

    /**
     * @param head
     * @param key
     * @param timeout
     * @return
     */
    List<String> blpop(String head, String key, int timeout);

    Long zcount(String head, String key, double start, double end);

    void binset(String head, String key, Object val);

    Object binget(String head, String key);

    Object binhget(String head, String key, Object field);

    void binhset(String head, String key, Object field, byte[] value);

    void binhset(String head, String key, Object field, Object value);

    Boolean bindel(String head, String... keys);

    List<String> srandmember(String head, String key, Integer count);

    Boolean expireAt(String head, String key, long unixtime);

    Long zrank(String head, String key, String member);

    String rename(String head, String oldkey, String newkey);

    String lindex(String head, String key, long index);

    Set<String> hkeys(String head, String key);

    /**
     * ADD by liufeng 20170508 查询hash中field的数量
     * 
     * @param head
     * @param key
     * @return
     */
    Long hlen(String head, String key);

    default String getVHost() {
        return "";
    }

    default String createKey(String head, String key) {
        return getVHost() + ":" + head + ":" + key;
    }

    /**
     * 每个存入缓存的数据需要首先调用这个方法，防止KEY的冲突
     * 
     * @param keys
     * @return
     */
    default String[] createKey(String head, String[] keys) {
        String[] _keys = null;
        // 以HEAD和key生成新的key
        if (ArrayUtils.isNotEmpty(keys)) {
            _keys = new String[keys.length];
            // 以HEAD和key生成新的key
            for (int i = 0; i < keys.length; i++) {
                // 以HEAD和key生成新的key
                _keys[i] = createKey(head, keys[i]);
            }
        }
        return _keys;
    }

    Long hgetNum(String key, String field);

}

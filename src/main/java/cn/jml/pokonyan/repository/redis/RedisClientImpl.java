package cn.jml.pokonyan.repository.redis;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.CollectionUtils;

import lombok.Data;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;
import redis.clients.util.Pool;

@Data
public class RedisClientImpl extends SerializeRedisClient {
    private Logger        log = LoggerFactory.getLogger(RedisClientImpl.class);
    protected Pool<Jedis> readPool;
    protected Pool<Jedis> writePool;

    public RedisClientImpl(String namespace, RedisSerializer serializer, Pool<Jedis> readPool, Pool<Jedis> writePool) {
        super(namespace, serializer);
        this.namespace = namespace;
        this.readPool = readPool;
        this.writePool = writePool;
    }

    public Long rpush(String head, String key, String... val) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.rpush(realkey, val));
    }

    public Long lpush(String head, String key, String... val) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.lpush(realkey, val));
    }

    public Long llen(String head, String key) {
        return invoke(readPool, head, key, (jedis, realkey) -> jedis.llen(realkey));
    }

    public List<String> lrange(String head, String key, long start, long end) {
        return invoke(readPool, head, key, (jedis, realkey) -> jedis.lrange(realkey, start, end));
    }

    @Override
    public Long rpush(String key, String... val) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.rpush(realkey, val));
    }

    @Override
    public Long lpush(String key, String... val) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.lpush(realkey, val));
    }

    @Override
    public Long llen(String key) {
        return invoke(readPool, createKey(key), (jedis, realkey) -> jedis.llen(realkey));
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        return invoke(readPool, createKey(key), (jedis, realkey) -> jedis.lrange(realkey, start, end));
    }

    @Override
    public String lset(String key, long index, String value) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.lset(realkey, index, value));
    }

    @Override
    public String lpop(String key) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.lpop(realkey));
    }

    @Override
    public String rpop(String key) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.rpop(realkey));
    }

    @Override
    public Boolean exists(String key) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.exists(realkey));
    }

    @Override
    public String set(String key, String val) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.set(realkey, val));
    }

    @Override
    public String setex(String key, String value, long expire) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.setex(realkey, (int) expire, value));
    }

    @Override
    public String get(String key) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.get(realkey));
    }

    @Override
    public String getset(String key, String value) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.getSet(realkey, value));
    }

    @Override
    public Long lrem(String key, long count, String val) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.lrem(realkey, count, val));
    }

    @Override
    public Long sadd(String key, String val) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.sadd(realkey, val));
    }

    @Override
    public String spop(String key) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.spop(realkey));
    }

    @Override
    public Set<String> spop(String key, long count) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.spop(realkey, count));
    }

    @Override
    public Set<String> spop30(String key, long count) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> {
            Pipeline pipeline = jedis.pipelined();
            Set<String> result;
            try {
                for (int i = 0; i < count; i++) {
                    Response<String> response = pipeline.spop(key);
                    if (null == response) {
                        break;
                    }
                }

                List<Object> results = pipeline.syncAndReturnAll();

                result = null;
                if (!CollectionUtils.isEmpty(results)) {
                    result = new HashSet<>();
                    for (Object response : results) {
                        result.add(String.valueOf(response));
                    }
                }
            } finally {
                if (null != pipeline) {
                    try {
                        pipeline.close();
                    } catch (IOException e) {
                        log.error("關閉管道異常");
                    }
                }
            }
            return result;
        });
    }

    @Override
    public Set<String> smembers(String key) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.smembers(realkey));
    }

    @Override
    public Long scard(String key) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.scard(realkey));
    }

    @Override
    public Boolean expire(String key, int expire) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.expire(realkey, expire)) > 0;
    }

    @Override
    public Long srem(String key, String... val) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.srem(realkey, val));
    }

    @Override
    public Boolean sismember(String key, String member) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.sismember(realkey, member));
    }

    @Override
    public Long incr(String key) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.incr(realkey));
    }

    @Override
    public Long incrBy(String key, long count) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.incrBy(realkey, count));
    }

    @Override
    public Long decr(String key) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.decr(realkey));
    }

    @Override
    public Long hincrBy(String key, String field, long value) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.hincrBy(realkey, field, value));
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.hmget(realkey, fields));
    }

    @Override
    public Long hgetNum(String key, String field) {
        return Long.valueOf(hget(key, field));
    }

    @Override
    public Boolean hsetnx(String key, String field, String value) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.hsetnx(realkey, field, value)) > 0;
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        return invoke(readPool, createKey(key), (jedis, realkey) -> jedis.hgetAll(realkey));
    }

    @Override
    public String hmset(String key, Map<String, String> hash) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.hmset(realkey, hash));
    }

    @Override
    public String hmsetExpire(String key, Map<String, String> hash, int expire) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> {
            if (expire > 0) {
                jedis.expire(realkey, expire);
            }
            return jedis.hmset(realkey, hash);
        });
    }

    @Override
    public Boolean zadd(String key, double score, String member) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.zadd(realkey, score, member)) > 0;
    }

    @Override
    public Long zadd(String key, Map<String, Double> stringDoubleMap) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.zadd(realkey, stringDoubleMap));
    }

    @Override
    public Long zrem(String key, String member) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.zrem(realkey, member));
    }

    @Override
    public Double zscore(String key, String member) {
        return invoke(readPool, createKey(key), (jedis, realkey) -> jedis.zscore(realkey, member));
    }

    @Override
    public Set<String> zquery(String key, long start, long end, boolean isAsc) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        Set<String> resultSet;
        if (isAsc) {
            resultSet = invoke(readPool, createKey(key), (jedis, realkey) -> jedis.zrange(realkey, start, end));
        } else {
            resultSet = invoke(readPool, createKey(key), (jedis, realkey) -> jedis.zrevrange(realkey, start, end));
        }

        return resultSet;
    }

    @Override
    public Long zcard(String key) {
        return invoke(readPool, createKey(key), (jedis, realkey) -> jedis.zcard(realkey));
    }

    public String lset(String head, String key, long index, String value) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.lset(realkey, index, value));
    }

    public String lpop(String head, String key) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.lpop(realkey));
    }

    public String rpop(String head, String key) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.rpop(realkey));
    }

    public Boolean exists(String head, String key) {
        return invoke(readPool, head, key, (jedis, realkey) -> jedis.exists(realkey));
    }

    public Boolean del(String head, String key) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.del(realkey)) > 0;
    }

    @Override
    public Long del(String head, String... key) {
        return invoke(writePool, head, key, (jedis, realkeys) -> jedis.del(realkeys));
    }

    public String set(String head, String key, String val) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.set(realkey, val));
    }

    public String get(String head, String key) {
        return invoke(readPool, head, key, (jedis, realkey) -> jedis.get(realkey));
    }

    public String getset(String head, String key, String value) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.getSet(realkey, value));
    }

    public Long lrem(String head, String key, long count, String val) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.lrem(realkey, count, val));
    }

    public Long sadd(String head, String key, String val) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.sadd(realkey, val));
    }

    public String spop(String head, String key) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.spop(realkey));
    }

    public Set<String> spop(String head, String key, long count) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.spop(realkey, count));
    }

    public Set<String> spop30(String head, String key, long count) {
        return invoke(writePool, head, key, (jedis, realkey) -> {
            Pipeline pipeline = jedis.pipelined();
            Set<String> result;
            try {
                for (int i = 0; i < count; i++) {
                    Response<String> response = pipeline.spop(key);
                    if (null == response) {
                        break;
                    }
                }

                List<Object> results = pipeline.syncAndReturnAll();

                result = null;
                if (!CollectionUtils.isEmpty(results)) {
                    result = new HashSet<>();
                    for (Object response : results) {
                        result.add(String.valueOf(response));
                    }
                }
            } finally {
                if (null != pipeline) {
                    try {
                        pipeline.close();
                    } catch (IOException e) {
                        log.error("關閉管道異常");
                    }
                }
            }
            return result;
        });
    }

    public Set<String> smembers(String head, String key) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.smembers(realkey));
    }

    public Long scard(String head, String key) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.scard(realkey));
    }

    public Boolean expire(String head, String key) {
        int expire = getExpireTime(head);
        if (expire <= 0) {
            return false;
        }
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.expire(realkey, expire)) > 0;
    }

    public Boolean expire(String head, String key, int expire) {
        if (!StringUtils.isBlank(head) && key != null && expire != 0) {
            return invoke(writePool, head, key, (jedis, realkey) -> jedis.expire(realkey, expire)) > 0;
        } else {
            log.warn("传入参数不完整");
        }
        return false;
    }

    public String setex(String head, String key, String value) {
        int expire = getExpireTime(head);
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.setex(realkey, expire, value));
    }

    public Long srem(String head, String key, String... val) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.srem(realkey, val));
    }

    public Boolean sismember(String head, String key, String member) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.sismember(realkey, member));
    }

    public Long incr(String head, String key) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.incr(realkey));
    }

    public Long incrBy(String head, String key, long count) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.incrBy(realkey, count));
    }

    public Long decr(String head, String key) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.decr(realkey));
    }

    public Long hincrBy(String head, String key, String field, long value) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.hincrBy(realkey, field, value));
    }

    public Boolean hexists(String key, String field) {
        return invoke(readPool, createKey(key), (jedis, realkey) -> jedis.hexists(realkey, field));
    }

    public Boolean hexists(String head, String key, String field) {
        return invoke(readPool, head, key, (jedis, realkey) -> jedis.hexists(realkey, field));
    }

    public List<String> hmget(String head, String key, String... fields) {
        return invoke(readPool, head, key, (jedis, realkey) -> jedis.hmget(realkey, fields));
    }

    public Boolean hsetnx(String head, String key, String field, String value) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.hsetnx(realkey, field, value)) > 0;
    }

    public Map<String, String> hgetAll(String head, String key) {
        return invoke(readPool, head, key, (jedis, realkey) -> jedis.hgetAll(realkey));
    }

    public String hmset(String head, String key, Map<String, String> hash) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.hmset(realkey, hash));
    }

    public String hmsetExpire(String head, String key, Map<String, String> hash) {
        int expireSeconds = getExpireTime(head);
        return invoke(writePool, head, key, (jedis, realkey) -> {
            if (expireSeconds > 0) {
                jedis.expire(realkey, expireSeconds);
            }
            return jedis.hmset(realkey, hash);
        });
    }

    public Boolean zadd(String head, String key, double score, String member) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.zadd(realkey, score, member)) > 0;
    }

    @Override
    public Long zadd(String head, String key, Map<String, Double> stringDoubleMap) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.zadd(realkey, stringDoubleMap));
    }

    public Long zrem(String head, String key, String member) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.zrem(realkey, member));
    }

    public Double zscore(String head, String key, String member) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.zscore(realkey, member));
    }

    public Set<String> zquery(String head, String key, long start, long end, boolean isAsc) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        Set<String> resultSet;
        if (isAsc) {
            resultSet = invoke(readPool, head, key, (jedis, realkey) -> jedis.zrange(realkey, start, end));
        } else {
            resultSet = invoke(readPool, head, key, (jedis, realkey) -> jedis.zrevrange(realkey, start, end));
        }

        return resultSet;
    }

    public Long zcard(String head, String key) {
        if (StringUtils.isBlank(key)) {
            return 0L;
        }
        return invoke(readPool, head, key, (jedis, realkey) -> jedis.zcard(realkey));
    }

    public String hget(String head, String key, String field) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
            return null;
        }
        return invoke(readPool, head, key, (jedis, realkey) -> jedis.hget(realkey, field));
    }

    public String hget(String key, String field) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
            return null;
        }
        return invoke(readPool, createKey(key), (jedis, realkey) -> jedis.hget(realkey, field));
    }

    @Override
    public Long hdel(String key, String... fields) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.hdel(realkey, fields));
    }

    @Override
    public String hset(String key, String field, String value) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> String.valueOf(jedis.hset(realkey, field, value)));
    }

    @Override
    public Long sadd(String key, String... member) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.sadd(realkey, member));
    }

    @Override
    public String mset(String... keyvalues) {
        for (int i = 0; i < keyvalues.length; i += 2) {
            keyvalues[i] = createKey(keyvalues[i]);
        }
        return invoke(writePool, keyvalues, (jedis, realkeys) -> jedis.mset(realkeys));
    }

    @Override
    public List<String> mget(String... keys) {
        return invoke(readPool, keys, (jedis, realkeys) -> jedis.mget(realkeys));
    }

    @Override
    public Boolean setnx(String key, String value) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.setnx(realkey, value)) > 0;
    }

    @Override
    public boolean setnxExpire(String key, String value, int seconds) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> {
            Long result = jedis.setnx(realkey, value);
            if (result > 0) {
                jedis.expire(realkey, seconds);
                return true;
            }
            return false;
        });
    }

    @Override
    public void setPipeLine(Map<String, String> maps) {
        Jedis jedis = writePool.getResource();
        try {
            Pipeline pipeline = jedis.pipelined();
            for (Map.Entry<String, String> map : maps.entrySet()) {
                // 以HEAD和key生成新的key
                String tempKey = createKey(map.getKey());
                pipeline.set(tempKey, map.getValue());
            }
            pipeline.sync();
        } catch (Exception e) {
            log.error("redis操作发生异常", e);
        } finally {
            returnJedis(jedis);
        }
    }

    @Override
    public boolean setnxExpireAt(String key, String value, long unixTime) {
        return false;
    }

    @Override
    public void delPipeLine(String... keys) {

    }

    @Override
    public void delPipeLineSet(Set<String> keys) {

    }

    // @Override
    // public Set<Tuple> zqueryWithScores(String key, long start, long end, boolean isAsc) {
    // Set<Tuple> e;
    // if (isAsc) {
    // e = invoke(writePool, key, (jedis, realkey) -> jedis.zrangeWithScores(realkey, start, end));
    // } else {
    // e = invoke(writePool, key, (jedis, realkey) -> jedis.zrevrangeWithScores(realkey, start, end));
    // }
    // return e;
    // }

    public Set<String> zrangeByScore(String key, double start, double end, boolean isAsc) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        return invoke(readPool, createKey(key), (jedis, realkey) -> {
            if (isAsc) {
                return jedis.zrangeByScore(realkey, start, end);
            } else {
                return jedis.zrevrangeByScore(realkey, start, end);
            }
        });
    }

    public Set<String> zrangeByScore(String key, double start, double end, int offset,
                                     int limit, boolean isAsc) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        return invoke(readPool, createKey(key), (jedis, realkey) -> {
            if (isAsc) {
                return jedis.zrangeByScore(realkey, start, end, offset, limit);
            } else {
                return jedis.zrevrangeByScore(realkey, start, end, offset, limit);
            }
        });
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double start, double end, int offset,
                                              int limit, boolean isAsc) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        return invoke(readPool, createKey(key), (jedis, realkey) -> {
            if (isAsc) {
                return jedis.zrangeByScoreWithScores(realkey, start, end, offset, limit);
            } else {
                return jedis.zrangeByScoreWithScores(realkey, start, end, offset, limit);
            }
        });
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double start, double end, boolean isAsc) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        return invoke(readPool, createKey(key), (jedis, realkey) -> {
            if (isAsc) {
                return jedis.zrangeByScoreWithScores(realkey, start, end);
            } else {
                return jedis.zrangeByScoreWithScores(realkey, start, end);
            }
        });
    }

    @Override
    public List<String> blpop(String key, int timeout) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.blpop(timeout, realkey));
    }

    @Override
    public Long zcount(String key, double start, double end) {
        return invoke(readPool, key, (jedis, realkey) -> jedis.zcount(realkey, start, end));
    }

    public Long hdel(String head, String key, String... fields) {
        if (StringUtils.isBlank(key) || ArrayUtils.isEmpty(fields)) {
            return 0L;
        }

        return invoke(writePool, head, key, (jedis, realkey) -> jedis.hdel(realkey, fields));
    }

    public String hset(String head, String key, String field, String value) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(field) || StringUtils.isBlank(value)) {
            return "";
        }
        return invoke(readPool, head, key, (jedis, realkey) -> jedis.hset(realkey, field, value)) + "";
    }

    public void hset(String head, String key, Map<String, String> maps) {
        invoke(writePool, head, key, (jedis, realkey) -> {
            Pipeline pipeline = jedis.pipelined();
            boolean exists = jedis.exists(realkey);
            for (Map.Entry<String, String> tmp : maps.entrySet()) {
                pipeline.hset(realkey, tmp.getKey(), tmp.getValue());
            }
            pipeline.sync();
            if (!exists) {
                int expireTime = getExpireTime(head);
                if (expireTime > 0) {
                    jedis.expire(realkey, expireTime);
                }
            }
            return null;
        });
    }

    public Long sadd(String head, String key, String... member) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.sadd(realkey, member));
    }

    public String mset(String head, String... keyvalues) {
        for (int i = 0; i < keyvalues.length; i += 2) {
            keyvalues[i] = createKey(head, keyvalues[i]);
        }
        return invoke(writePool, keyvalues, (jedis, realkeys) -> jedis.mset(realkeys));
    }

    public List<String> mget(String head, String... keys) {
        return invoke(readPool, head, keys, (jedis, realkeys) -> jedis.mget(realkeys));
    }

    public String setnx(String head, String key, String value, int expireSeconds) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.set(realkey, value, "NX", "EX", expireSeconds));
    }

    public Boolean setnx(String head, String key, String value) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.setnx(realkey, value)) > 0;
    }

    @Override
    public boolean setnxExpire(String head, String key, String value, int seconds) {
        return invoke(writePool, head, key, (jedis, realkey) -> {
            Long result = jedis.setnx(realkey, value);
            if (result > 0) {
                jedis.expire(realkey, seconds);
                return true;
            }
            return false;
        });
    }

    public void setPipeLine(String head, Map<String, String> maps) {
        Jedis jedis = writePool.getResource();
        try {
            int exprie = getExpireTime(head);
            String tempKey = "";
            Pipeline pipeline = jedis.pipelined();
            if (exprie > 0) {
                for (Map.Entry<String, String> map : maps.entrySet()) {
                    // 以HEAD和key生成新的key
                    tempKey = createKey(head, map.getKey());
                    pipeline.setex(tempKey, exprie, map.getValue());
                }
            } else {
                for (Map.Entry<String, String> map : maps.entrySet()) {
                    // 以HEAD和key生成新的key
                    tempKey = createKey(head, map.getKey());
                    pipeline.set(tempKey, map.getValue());
                }
            }
            pipeline.sync();
        } catch (Exception e) {
            log.error("redis操作发生异常", e);
        } finally {
            returnJedis(jedis);
        }
    }

    @Override
    public boolean setnxExpireAt(String head, String key, String value, long unixTime) {
        return invoke(writePool, head, key, (jedis, realkey) -> {
            Long result = jedis.setnx(realkey, value);
            if (result > 0) {
                jedis.expireAt(realkey, unixTime);
                return true;
            }
            return false;
        });
    }

    public void delPipeLine(String head, String... keys) {
        invoke(writePool, head, keys, (jedis, realkeys) -> {
            Pipeline pipeline = jedis.pipelined();
            pipeline.del(realkeys);
            pipeline.sync();
            return null;
        });
    }

    public void delPipeLineSet(String head, Set<String> keys) {
        delPipeLine(head, keys.toArray(new String[] {}));
    }

    // @Override
    // public Set<Tuple> zqueryWithScores(String head, String key, long start, long end,
    // boolean isAsc) {
    // Set<Tuple> e;
    // if (isAsc) {
    // e = invoke(writePool, head, key, (jedis, realkey) -> jedis.zrangeWithScores(realkey, start, end));
    // } else {
    // e = invoke(writePool, head, key, (jedis, realkey) -> jedis.zrevrangeWithScores(realkey, start, end));
    // }
    // return e;
    // }

    public Set<String> zrangeByScore(String head, String key, double start, double end, boolean isAsc) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        return invoke(readPool, head, key, (jedis, realkey) -> {
            if (isAsc) {
                return jedis.zrangeByScore(realkey, start, end);
            } else {
                return jedis.zrevrangeByScore(realkey, start, end);
            }
        });
    }

    public Set<String> zrangeByScore(String head, String key, double start, double end, int offset,
                                     int limit, boolean isAsc) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        return invoke(readPool, head, key, (jedis, realkey) -> {
            if (isAsc) {
                return jedis.zrangeByScore(realkey, start, end, offset, limit);
            } else {
                return jedis.zrevrangeByScore(realkey, start, end, offset, limit);
            }
        });
    }

    public Set<Tuple> zrangeByScoreWithScores(String head, String key, double start, double end, int offset,
                                              int limit, boolean isAsc) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        return invoke(readPool, head, key, (jedis, realkey) -> {
            if (isAsc) {
                return jedis.zrangeByScoreWithScores(realkey, start, end, offset, limit);
            } else {
                return jedis.zrangeByScoreWithScores(realkey, start, end, offset, limit);
            }
        });
    }

    public Set<Tuple> zrangeByScoreWithScores(String head, String key, double start, double end, boolean isAsc) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        return invoke(readPool, head, key, (jedis, realkey) -> {
            if (isAsc) {
                return jedis.zrangeByScoreWithScores(realkey, start, end);
            } else {
                return jedis.zrangeByScoreWithScores(realkey, start, end);
            }
        });
    }

    /**
     * @param head
     * @param key
     * @param timeout
     * @return
     */
    public List<String> blpop(String head, String key, int timeout) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        return invoke(readPool, head, key, (jedis, realkey) -> jedis.blpop(timeout, realkey));
    }

    public Long zcount(String head, String key, double start, double end) {
        if (StringUtils.isBlank(key)) {
            return 0L;
        }
        return invoke(readPool, head, key, (jedis, realkey) -> jedis.zcount(realkey, start, end));
    }

    @Override
    public void binset(String head, String key, Object val) {

    }

    public void publish(String channel, String msg) {
        invoke(readPool, createKey(channel), (jedis, realkey) -> jedis.publish(realkey, msg));
    }

    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
        Jedis jedis = null;
        try {
            jedis = writePool.getResource();
            jedis.subscribe(jedisPubSub, channels);
            return;
        } catch (Exception var8) {
            log.error("redis操作发生异常", var8);
        } finally {
            returnJedis(jedis);
        }
    }

    @Override
    public void binset(String key, Object val) {
        invoke(writePool, createKey(key), (jedis, realkey) -> jedis.set(serializer.serialize(key), serializer.serialize(val)));
    }

    @Override
    public Object binget(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        return invoke(readPool, createKey(key), (jedis, realkey) -> serializer.deserialize(jedis.get(realkey.getBytes("UTF-8"))));
    }

    public Object binhget(String key, Object field) {
        return invoke(readPool, createKey(key), (jedis, realkey) -> serializer.deserialize(jedis.hget(realkey.getBytes("UTF-8"),
            serializer.serialize(field))));
    }

    public void binhset(String key, Object field, byte[] value) {
        invoke(writePool, createKey(key), (jedis, realkey) -> jedis.hset(realkey.getBytes("UTF-8"), serializer.serialize(field), value));
    }

    public void binhset(String key, Object field, Object value) {
        invoke(writePool, createKey(key), (jedis, realkey) -> jedis.hset(realkey.getBytes("UTF-8"), serializer.serialize(field),
            serializer.serialize(value)));
    }

    public Boolean bindel(String... keys) {
        return invoke(readPool, keys, (jedis, realkeys) -> {
            byte[][] binkeys = new byte[realkeys.length][];
            int i = 0;
            for (String key : realkeys) {
                binkeys[i] = createKey(key).getBytes("UTF-8");
                i++;
            }
            return jedis.del(binkeys);
        }) > 0;
    }

    public void binset(String key, Object val, int expire) {
        invoke(writePool, createKey(key), (jedis, realKey) -> {
            if (expire > 0) {
                jedis.setex(realKey.getBytes("UTF-8"), expire, serializer.serialize(val));
            } else {
                jedis.set(realKey.getBytes("UTF-8"), serializer.serialize(val));
            }
            return null;
        });
    }

    public Object binget(String head, String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        return invoke(readPool, head, key, (jedis, realkey) -> serializer.deserialize(jedis.get(realkey.getBytes("UTF-8"))));
    }

    public Object binhget(String head, String key, Object field) {
        return invoke(readPool, head, key, (jedis, realkey) -> serializer.deserialize(jedis.hget(realkey.getBytes("UTF-8"),
            serializer.serialize(field))));
    }

    public void binhset(String head, String key, Object field, byte[] value) {
        invoke(writePool, head, key, (jedis, realkey) -> jedis.hset(realkey.getBytes("UTF-8"), serializer.serialize(field), value));
    }

    public void binhset(String head, String key, Object field, Object value) {
        invoke(writePool, head, key, (jedis, realkey) -> jedis.hset(realkey.getBytes("UTF-8"), serializer.serialize(field),
            serializer.serialize(value)));
    }

    public Boolean bindel(String head, String... keys) {
        return invoke(readPool, head, keys, (jedis, realkeys) -> {
            byte[][] binkeys = new byte[realkeys.length][];
            int i = 0;
            for (String key : realkeys) {
                binkeys[i] = key.getBytes("UTF-8");
                i++;
            }
            return jedis.del(binkeys);
        }) > 0;
    }

    // 获取到所有正则keys
    public Set<String> keys(String regx) {
        if (StringUtils.isEmpty(regx)) {
            return null;
        }
        return invoke(readPool, createKey(regx), (jedis, realkey) -> jedis.keys(realkey));
    }

    @Override
    public List<String> srandmember(String key, Integer count) {
        return invoke(readPool, createKey(key), (jedis, realkey) -> jedis.srandmember(realkey, count));
    }

    @Override
    public Boolean expireAt(String key, long unixtime) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.expireAt(realkey, unixtime)) > 0;
    }

    @Override
    public Long zrank(String key, String member) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.zrank(realkey, member));
    }

    @Override
    public String rename(String oldkey, String newkey) {
        return invoke(writePool, createKey(oldkey), (jedis, _oldkey) -> jedis.rename(_oldkey, createKey(createKey(newkey))));
    }

    @Override
    public String lindex(String key, long index) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.lindex(realkey, index));
    }

    @Override
    public List<String> srandmember(String head, String key, Integer count) {
        return invoke(readPool, head, key, (jedis, realkey) -> jedis.srandmember(realkey, count));
    }

    @Override
    public Boolean expireAt(String head, String key, long unixtime) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.expireAt(realkey, unixtime)) > 0;
    }

    @Override
    public Long zrank(String head, String key, String member) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.zrank(realkey, member));
    }

    @Override
    public String rename(String head, String oldkey, String newkey) {
        return invoke(writePool, head, oldkey, (jedis, _oldkey) -> jedis.rename(_oldkey, createKey(head, newkey)));
    }

    @Override
    public String lindex(String head, String key, long index) {
        return invoke(writePool, head, key, (jedis, realkey) -> jedis.lindex(realkey, index));
    }

    @Override
    public JedisCommands getResource(boolean isread) {
        if (isread) {
            return readPool.getResource();
        }
        return writePool.getResource();
    }

    @Override
    public Set<String> hkeys(String head, String key) {
        return invoke(readPool, head, key, (jedis, realkey) -> jedis.hkeys(realkey));
    }

    @Override
    public Set<String> hkeys(String key) {
        return invoke(readPool, createKey(key), (jedis, realkey) -> jedis.hkeys(realkey));
    }

    @Override
    public Boolean del(String key) {
        return invoke(writePool, createKey(key), (jedis, realkey) -> jedis.del(realkey)) > 0;
    }

    @Override
    public Long del(String... keys) {
        return invoke(writePool, keys, (jedis, realkey) -> jedis.del(realkey));
    }

    @Override
    public List<String> scan(String regx, Integer count) {
        return invoke(writePool, regx, (jedis, realkey) -> {
            ScanParams scanParams = new ScanParams();
            scanParams.match(realkey);
            scanParams.count(count);
            String cursor = "0";
            List<String> keys = new LinkedList<>();
            do {
                ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
                if (scanResult == null)
                    break;
                cursor = scanResult.getStringCursor();
                keys.addAll(scanResult.getResult());
            } while (!Objects.equals("0", cursor));
            return keys;
        });
    }

    @Override
    public Long hlen(String key) {
        return invoke(readPool, createKey(key), (jedis, realkey) -> jedis.hlen(realkey));
    }

    @Override
    public Long hlen(String head, String key) {
        return invoke(readPool, head, key, (jedis, realkey) -> jedis.hlen(realkey));
    }

    public interface Callback<T> {
        T call(Jedis jedis, String key) throws Exception;
    }

    public interface MultiKeyCallback<T> {
        T call(Jedis jedis, String... keys) throws Exception;
    }

    public <T> T invoke(Pool<Jedis> pool, String head, String key, Callback<T> callback) {
        return invoke(pool, createKey(head, key), callback);
    }

    public <T> T invoke(Pool<Jedis> pool, String head, String[] key, MultiKeyCallback<T> callback) {
        return invoke(pool, createKey(head, key), callback);
    }

    public <T> T invoke(Pool<Jedis> pool, String key, Callback<T> callback) {
        Jedis jedis = null;
        T result = null;
        try {
            jedis = pool.getResource();
            result = callback.call(jedis, key);
        } catch (Exception e) {
            log.error("redis操作发生异常", e);
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    public <T> T invoke(Pool<Jedis> pool, String[] keys, MultiKeyCallback<T> callback) {
        Jedis jedis = null;
        T result = null;
        try {
            jedis = pool.getResource();
            result = callback.call(jedis, keys);
        } catch (Exception e) {
            log.error("redis操作发生异常", e);
        } finally {
            returnJedis(jedis);
        }
        return result;
    }

    private static void returnJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}

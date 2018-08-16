package cn.jml.pokonyan.repository.redis;

import org.springframework.data.redis.serializer.RedisSerializer;

import lombok.Data;

@Data
public abstract class SerializeRedisClient implements RedisClient, HeadRedisClient {
    @SuppressWarnings("rawtypes")
    protected RedisSerializer serializer;

    protected String          namespace;

    @SuppressWarnings("unchecked")
    public byte[] serialize(Object t) {
        return serializer.serialize(t);
    }

    public Object deserialize(byte[] bytes) {
        return serializer.deserialize(bytes);
    }

    public SerializeRedisClient(String namespace, RedisSerializer serializer) {
        this.namespace = namespace;
        this.serializer = serializer;
    }

    @Override
    public Long hgetNum(String head, String key, String field) {
        return Long.valueOf(hget(head, key, field));
    }

    public String getVHost() {
        return getNamespace();
    }
}

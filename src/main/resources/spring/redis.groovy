beans {
    jedisConnFactory(org.springframework.data.redis.connection.jedis.JedisConnectionFactory){
        usePool = true
        hostName = '${redis.host}'
        port = '${redis.port}'
        password = '${redis.pass}'
    }

    redisTemplate(org.springframework.data.redis.core.RedisTemplate){
        connectionFactory = ref("jedisConnFactory")
    }
}

mongo = [
    url : System.env.MONGO_URL ?: "mongodb://localhost:27017/test"
]

mq = [
    url : System.env.MQ_URL ?: "amqp://admin:admin@localhost:5672"
]

redis {
    URI redisUri = new URI(System.env.REDIS_URL ?: "http://localhost:32768")
    host = redisUri?.host ?: "localhost"
    port = redisUri?.port?: 6379
    pass = redisUri?.userInfo? redisUri.userInfo.split(":", 2)[1] : ''
}

web = [
    port : System.env.PORT ?: 8080
]

test = [
    urlToTest : System.env.URL_TO_TEST ?: "http://localhost:8080"
]

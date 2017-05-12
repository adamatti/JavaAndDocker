package adamatti.service

import adamatti.dao.MongoDAO
import org.apache.camel.Exchange
import org.apache.camel.builder.RouteBuilder

import static adamatti.helper.JsonHelper.*
import org.apache.camel.ProducerTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class EventEmitter extends RouteBuilder {
    private static final String QUEUE_NAME = "eventQueue"

    @Autowired
    private ProducerTemplate producerTemplate

    @Autowired
    private MongoDAO mongoDAO

    void configure(){
        errorHandler(noErrorHandler())

        from(configureEventsQueue("*",QUEUE_NAME))
            .bean("eventEmitter","processEvent")
    }

    void processEvent(Exchange exchange){
        String payload = new String(exchange.in.body)
        log.trace "Event received: ${payload}"
        Map json = toJsonMap(payload)
        mongoDAO.save(json)
    }

    void emit(String eventType, Object event){
        String endpoint = configureEventsQueue(eventType,QUEUE_NAME)
        String json = toJsonString(event)
        producerTemplate.asyncSendBody(endpoint,json)
    }

    private String configureEventsQueue(String routingKey, String queueName) {
        return "rabbitmq://localhost:5672/amq.topic?" +
            "queue=${queueName}&" +
            "exchangeType=topic&" +
            "durable=true&" +
            "routingKey=${routingKey}&" +
            "autoDelete=false&"+
            "connectionFactory=#customConnectionFactory"
    }
}

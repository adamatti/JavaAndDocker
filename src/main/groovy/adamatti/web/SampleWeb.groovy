package adamatti.web

import adamatti.service.EventEmitter

import javax.annotation.PostConstruct

import static adamatti.helper.JsonHelper.*
import adamatti.dao.MongoDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import spark.Request
import spark.Response
import spark.Spark

@Component
class SampleWeb {
    @Autowired
    private MongoDAO mongoDAO

    @Autowired
    private EventEmitter eventEmitter

    @PostConstruct
    void registerEndpoints(){
        Spark.get("/") { Request req, Response res ->
            res.header("Content-Type", "application/json")
            toJsonString([status:"ok"])
        }

        Spark.get("/person"){ Request req, Response res ->
            res.header("Content-Type","application/json")
            updateCacheCount()
            toJsonString(mongoDAO.list())
        }

        Spark.post("/person") { Request req, Response res ->
            res.header("Content-Type","application/json")
            Map json = toJsonMap(req.body())

            eventEmitter.emit("person.save.requested",json)

            toJsonString(["status":"ok"])
        }
    }

    private void updateCacheCount(){

    }
}

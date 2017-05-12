package adamatti.dao

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Slf4j
@Repository
class MongoDAO {
    private static final String COLLECTION_NAME = "person"

    @Autowired
    private MongoTemplate mongoTemplate

    List list(Map args = [:]){
        Query query = new Query()
        mongoTemplate.find(query, Map.class,COLLECTION_NAME)
    }

    Map save(Map record){
        mongoTemplate.save(record,COLLECTION_NAME)
        log.trace "Saved"
        record
    }
}

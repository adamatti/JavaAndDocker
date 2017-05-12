package adamatti.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(locations = "classpath:spring/test.groovy")
class MongoDAOTest extends Specification {
    @Autowired
    private MongoDAO mongoDAO

    def "test list and save"(){
        when:
            mongoDAO.save([name:"adamatti"])
        then:
            mongoDAO.list().size() == old(mongoDAO.list().size() + 1)
    }
}

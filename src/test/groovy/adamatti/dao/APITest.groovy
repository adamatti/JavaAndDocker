package adamatti.dao

import adamatti.Main
import adamatti.helper.ConfigHelper
import groovy.util.logging.Slf4j
import groovyx.net.http.RESTClient
import spock.lang.Specification

@Slf4j
class APITest extends Specification{
    private static final def cfg = ConfigHelper.cfg
    private static final RESTClient client = buildClient()

    def "test post/get"(){
        given:
            assert asyncCheck(30){
                client.get(path: "/").data.status == "ok"
            }
            def payload = [name:"adamatti"]
            def preCount = countPerson()
        when:
            def result = client.post(path:"/person",body:payload)
        then:
            result.data.status == "ok"
            asyncCheck(3) {
                countPerson() == preCount + 1
            }
    }

    int countPerson(){
        client.get(path:"/person").data.size()
    }

    private boolean asyncCheck(int retries, Closure cb){
        int currentRetries = 0
        while(currentRetries < retries ){
            log.trace "Current check: ${currentRetries}"
            try {
                if (cb.call()) {
                    return true
                }
            } catch (Exception e){
                log.error "Error: ${e.message}"
            }
            Thread.sleep(1000)
            currentRetries++
        }
        return false
    }

    private static RESTClient buildClient(){
        def client = new RESTClient(cfg.test.urlToTest,"application/json")
        client.handler.failure = client.handler.success
        client
    }
}

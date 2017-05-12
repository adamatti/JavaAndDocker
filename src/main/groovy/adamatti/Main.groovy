package adamatti

import adamatti.helper.ConfigHelper
import groovy.util.logging.Slf4j
import org.springframework.context.ApplicationContext
import org.springframework.context.support.GenericGroovyApplicationContext
import spark.Spark

@Slf4j
class Main {
    private static final def cfg = ConfigHelper.cfg

    static void main(String [] args){
        new Main().start()
    }

    private ApplicationContext context

    void start(){
        Spark.port(cfg.web.port as int)
        context = buildSpringContext()
        log.info "App started [port: ${cfg.web.port}]"
    }

    void stop(){
        Spark.stop()
    }

    private static buildSpringContext(){
        def context = new GenericGroovyApplicationContext("classpath:spring/root.groovy")
        context.registerShutdownHook()
        context
    }
}

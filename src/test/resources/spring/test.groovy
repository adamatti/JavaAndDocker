package spring

beans {
    xmlns context:"http://www.springframework.org/schema/context"

    context."component-scan"("base-package" : "adamatti.dao,adamatti.helper")

    importBeans('classpath:spring/mongo.groovy')
    importBeans('classpath:spring/camel.groovy')
    importBeans('classpath:spring/redis.groovy')
}

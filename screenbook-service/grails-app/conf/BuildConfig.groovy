//grails.project.class.dir = "target/classes"
//grails.project.test.class.dir = "target/test-classes"
//grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"


grails.war.resources = { stagingDir ->
    delete(file:"${stagingDir}/WEB-INF/lib/commons-logging-1.1.1.jar")
    delete(file:"${stagingDir}/WEB-INF/lib/slf4j-api-1.5.2.jar")
}


grails.project.dependency.resolution = {
    useOrigin true

    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        mavenRepo "http://nexus.intem.se/content/groups/public"
        mavenRepo "http://nexus.intem.se/content/groups/public-snapshots"

        grailsPlugins()
        grailsHome()
        grailsCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        //mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        // runtime 'mysql:mysql-connector-java:5.1.5'

        runtime "mysql:mysql-connector-java:5.1.6"
        compile "org.springframework.flex:spring-flex:1.0.1.RELEASE"
        compile "packtag:packtag:3.6"
        compile "com.yahoo.platform.yui:yuicompressor:2.3.6"

        compile ("se.pictosys:payment-web:2.0.1-SNAPSHOT") {
            /* exclude only because it pulls in slf4j 1.6.4 which is not compatible with 
             * Grails 1.3.7. Exclusion can be removed on upgrade. */
            excludes ([group: "com.mysema.querydsl", artifact:"querydsl-jpa"])
        }
        compile "se.pictosys:pictosys-web:2.0.1-SNAPSHOT"

        compile "org.slf4j:jcl-over-slf4j:1.5.8"
        compile "org.slf4j:jul-to-slf4j:1.5.8"
        compile "org.slf4j:slf4j-api:1.5.8"
        compile "org.slf4j:slf4j-log4j12:1.5.8"
    }
}

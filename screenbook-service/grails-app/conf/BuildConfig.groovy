//grails.project.class.dir = "target/classes"
//grails.project.test.class.dir = "target/test-classes"
//grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"


grails.war.resources = { stagingDir ->
    delete(file:"${stagingDir}/WEB-INF/lib/commons-logging-1.1.1.jar")
    delete(file:"${stagingDir}/WEB-INF/lib/slf4j-api-1.5.2.jar")
}


grails.project.dependency.resolution = {
    pom true

    useOrigin true

    /* Turn off ivy checksums because many artifacts on central contains wrong sha1 (old syntax) */
    ivySettings.setVariable("ivy.checksums", "")

    // inherit Grails' default dependencies
    inherits("global") {
        /* xml-apis conflicts with Java 6 */
        excludes "xml-apis", "xmlParserAPIs"
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
        mavenLocal()
        //mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        // runtime 'mysql:mysql-connector-java:5.1.5'

        compile "commons-collections:commons-collections:3.2.1"
    }
}

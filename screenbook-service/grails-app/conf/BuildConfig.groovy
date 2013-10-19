grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

/* Remove old conflicting jar files */
grails.war.resources = { stagingDir ->
    [
        "commons-logging-1.1.1.jar",
        "slf4j-api-1.5.2.jar"
    ].each {
        delete(file:"${stagingDir}/WEB-INF/lib/${it}")
    }
}

grails.project.dependency.resolution = {
    pom true
    /* Turn off ivy checksums because many artifacts on central contains wrong sha1 */
    checksums false

    // useOrigin true

    // inherit Grails' default dependencies
    inherits("global") {
        /* xml-apis conflicts with Java 6 */
        excludes "xml-apis", "xmlParserAPIs"
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        inherit false
        mavenRepo "http://nexus.intem.se/content/groups/public"
        mavenRepo "http://nexus.intem.se/content/groups/public-snapshots"
        grailsRepo "http://plugins.grails.org/"

        // grailsPlugins()
        // grailsHome()
        // grailsCentral()
        // mavenCentral()

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
    }

    plugins {
    }
}

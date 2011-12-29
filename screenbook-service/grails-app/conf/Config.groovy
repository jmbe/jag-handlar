import org.apache.log4j.DailyRollingFileAppender
import org.apache.log4j.Level
import org.apache.log4j.Priority
import org.apache.log4j.net.SMTPAppender
import org.apache.log4j.helpers.OnlyOnceErrorHandler

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]
// The default codec used to encode data with ${}
grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"
grails.converters.encoding="UTF-8"

// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true

// set context path
grails.app.context = "/"

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.jaghandlar.se"
        grails {
          mail {
            host = "localhost"
            port = 25
            username = ""
            password = ""
            /*
            props = ["mail.smtp.auth":"true",
                    "mail.smtp.socketFactory.port":"465",
                    "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                    "mail.smtp.socketFactory.fallback":"false"]
            */
          }
        }
        grails.mail.default.from="no-reply@jaghandlar.se"
    }
    development {
      grails {
        mail {
          host = "localhost"
          port = 25
          username = ""
          password = ""
          /*
          props = ["mail.smtp.auth":"true",
                  "mail.smtp.socketFactory.port":"465",
                  "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                  "mail.smtp.socketFactory.fallback":"false"]
          */
        }
      }

      grails.mail.default.from="no-reply@jaghandlar.se"
    }
}

// log4j configuration
log4j = {
    appenders {
        def dailyAppender = new DailyRollingFileAppender(name:'dailyAppender',
                                             datePattern: "'.'yyyy-MM-dd",
                                             file:'logs/jag-handlar.log',
                                             layout:pattern(conversionPattern: '%d %-5p [%c] %m%n'))
        dailyAppender.threshold = Priority.INFO

        def smtpAppender = new SMTPAppender(name:'smtpAppender',
                                            to: 'asynclog@gmail.com',
                                            from: "no-reply@jaghandlar.se",
                                            subject:"Problems with jag-handlar",
                                            layout:pattern(conversionPattern: '%d %-5p [%c] %m%n'));
        smtpAppender.errorHandler = new OnlyOnceErrorHandler()
        smtpAppender.threshold = Priority.INFO

        console name:'stdout', layout:pattern(conversionPattern: '%d %-5p [%c] %m%n')
        appender dailyAppender
        appender smtpAppender
        
    }

    root {
      additivity = true
      warn()
      debug 'stdout','dailyAppender', 'smtpAppender'
    }

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
	       'org.codehaus.groovy.grails.web.pages', //  GSP
	       'org.codehaus.groovy.grails.web.metaclass',
	       'org.codehaus.groovy.grails.web.sitemesh', //  layouts
	       'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
	       'org.codehaus.groovy.grails.web.mapping', // URL mapping
	       'org.codehaus.groovy.grails.commons', // core / classloading
	       'org.codehaus.groovy.grails.plugins', // plugins
	       'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
	       'org.springframework',
	       'org.hibernate'


    warn   'org.mortbay.log',
           'org.apache.commons',
           'org.apache.tomcat',
           'org.apache.catalina',
            'com.opensymphony',
            'org.apache',
            'net.sourceforge',
            'net.sf',
            'org.codehaus',
            'httpclient'

    info   'grails.app',
            'se.jaghandlar',
            'se.pictosys'

    debug  'grails.app.controller',
            'se.jaghandlar.web.subscribe'
}

//log4j.logger.org.springframework.security='off,stdout'

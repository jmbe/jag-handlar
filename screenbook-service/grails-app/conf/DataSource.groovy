dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    username = "screenbook-dev"
    password = "screenbook-dev"
    dialect = org.hibernate.dialect.MySQL5InnoDBDialect
    properties {
        testOnBorrow = "true"
        validationQuery = "/* ping */ SELECT 1 FROM DUAL;"
    }
}

hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class = "net.sf.ehcache.hibernate.EhCacheProvider"
}
// environment specific settings
environments {
    development { dataSource { url = "jdbc:mysql://localhost/screenbook-dev" } }

    test {
        dataSource {
            driverClassName = "org.hsqldb.jdbcDriver"
            dbCreate = "update"
            username = "screenbook-test"
            password = "screenbook-test"
            url = "jdbc:mysql://localhost/screenbook-test"
        }
    }

    qa {
        dataSource {
            url = "jdbc:mysql://localhost/screenbook_qa"
            username = "screenbook_qa"
            password = "screenbook_qa"
        }
    }

    production { dataSource { url = "jdbc:mysql://localhost/screenbook-dev" } }
}
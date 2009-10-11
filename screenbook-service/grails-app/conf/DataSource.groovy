dataSource {
	pooled = true
	driverClassName = "com.mysql.jdbc.Driver"
	username = "screenbook-dev"
	password = "screenbook-dev"
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='com.opensymphony.oscache.hibernate.OSCacheProvider'
}
// environment specific settings
environments {

    /* Production and Development data sources are defined in resources.groovy. */

	test {
		dataSource {
			driverClassName = "org.hsqldb.jdbcDriver"
			dbCreate = "update"
			username = "sa"
			password = ""
			url = "jdbc:hsqldb:mem:testDb"
		}
	}
}
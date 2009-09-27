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
	development {
		dataSource {
			dbCreate = "create-drop" // one of 'create', 'create-drop','update'
			url = "jdbc:mysql://localhost/screenbook-dev?autoReconnect=true"
		}
	}

	production {
		dataSource {
			dbCreate = "update"
			url = "jdbc:mysql://localhost/screenbook-dev?autoReconnect=true"
		}
	}

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
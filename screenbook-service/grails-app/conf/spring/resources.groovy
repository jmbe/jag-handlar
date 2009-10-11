import grails.util.Environment

// Place your Spring DSL code here
beans = {
		authenticationEntryPoint(org.springframework.security.ui.basicauth.BasicProcessingFilterEntryPoint) {
		    realmName = 'Screenbook service'
		}

        if ([Environment.DEVELOPMENT, Environment.PRODUCTION].contains(Environment.current)) {

			println("Using overridden datasource from resources.groovy since the environment is ${Environment.current.name}.")

			dataSource(org.apache.commons.dbcp.BasicDataSource) { bean ->
				bean.destroyMethod = "close"

				driverClassName="com.mysql.jdbc.Driver"
				url="jdbc:mysql://localhost/screenbook-dev"
				username = "screenbook-dev"
				password = "screenbook-dev"
				testOnBorrow = "true"
				validationQuery = "SELECT 1 FROM DUAL;"
                /* Warning! Setting auto commit to true since Grails seems to expect(?) it. */
				defaultAutoCommit = "true"
			}
		}
}
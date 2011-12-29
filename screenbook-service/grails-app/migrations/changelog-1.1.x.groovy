databaseChangeLog = {

	changeSet(author: "jmbe (generated)", id: "1325175517020-1") {
		createTable(tableName: "account") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "accountPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "apikey", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "contact_person", type: "varchar(255)")

			column(name: "date_created", type: "datetime")

			column(name: "day_before_notice_sent", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime")

			column(name: "new_account", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "newsletter_subscribe", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "passwd", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "phone_number", type: "varchar(255)")

			column(name: "show_bookmark_reminder", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "six_weeks_notice_sent", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "two_weeks_notice_sent", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false", unique: "true")
			}
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-2") {
		createTable(tableName: "answer") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "answerPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "answer", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "book_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "question_key", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-3") {
		createTable(tableName: "book") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "bookPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-4") {
		createTable(tableName: "encrypted_data") {
			column(name: "id", type: "varchar(32)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "encrypted_datPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "data_item", type: "varchar(512)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-5") {
		createTable(tableName: "purchase") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "purchasePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "account_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "amount", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "contact_person", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "currency", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "customer_number", type: "varchar(255)")

			column(name: "delivery_address_city", type: "varchar(255)")

			column(name: "delivery_address_country_code", type: "varchar(255)")

			column(name: "delivery_address_first_name", type: "varchar(255)")

			column(name: "delivery_address_last_name", type: "varchar(255)")

			column(name: "delivery_address_state", type: "varchar(255)")

			column(name: "delivery_address_street_line1", type: "varchar(255)")

			column(name: "delivery_address_street_line2", type: "varchar(255)")

			column(name: "delivery_address_zip", type: "varchar(255)")

			column(name: "end_date", type: "datetime")

			column(name: "invoice_address_city", type: "varchar(255)")

			column(name: "invoice_address_country_code", type: "varchar(255)")

			column(name: "invoice_address_first_name", type: "varchar(255)")

			column(name: "invoice_address_last_name", type: "varchar(255)")

			column(name: "invoice_address_state", type: "varchar(255)")

			column(name: "invoice_address_street_line1", type: "varchar(255)")

			column(name: "invoice_address_street_line2", type: "varchar(255)")

			column(name: "invoice_address_zip", type: "varchar(255)")

			column(name: "invoice_date", type: "datetime")

			column(name: "invoice_sent", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "license", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "phone_number", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "purchase_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "purchase_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-6") {
		createTable(tableName: "role") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false", unique: "true")
			}

			column(name: "description", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-7") {
		createTable(tableName: "role_people") {
			column(name: "account_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-8") {
		createTable(tableName: "student") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "studentPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "account_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "screen_keyboard", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-9") {
		createTable(tableName: "work_book") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "work_bookPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "book_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "student_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-10") {
		addPrimaryKey(columnNames: "role_id, account_id", tableName: "role_people")
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-11") {
		createIndex(indexName: "username_unique_1325175516950", tableName: "account", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-12") {
		createIndex(indexName: "FKABCA3FBE9C0CBEB", tableName: "answer") {
			column(name: "book_id")
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-13") {
		createIndex(indexName: "FK67E90501ED2A3E7A", tableName: "purchase") {
			column(name: "account_id")
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-14") {
		createIndex(indexName: "authority_unique_1325175516977", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-15") {
		createIndex(indexName: "FK28B75E7852388A1A", tableName: "role_people") {
			column(name: "role_id")
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-16") {
		createIndex(indexName: "FK28B75E78ED2A3E7A", tableName: "role_people") {
			column(name: "account_id")
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-17") {
		createIndex(indexName: "FK8FFE823BED2A3E7A", tableName: "student") {
			column(name: "account_id")
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-18") {
		createIndex(indexName: "FK40F7EF573FA913A", tableName: "work_book") {
			column(name: "book_id")
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-19") {
		createIndex(indexName: "FK40F7EF57B5AD9BBA", tableName: "work_book") {
			column(name: "student_id")
		}
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-20") {
		addForeignKeyConstraint(baseColumnNames: "book_id", baseTableName: "answer", constraintName: "FKABCA3FBE9C0CBEB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "work_book", referencesUniqueColumn: "false")
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-21") {
		addForeignKeyConstraint(baseColumnNames: "account_id", baseTableName: "purchase", constraintName: "FK67E90501ED2A3E7A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "account", referencesUniqueColumn: "false")
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-22") {
		addForeignKeyConstraint(baseColumnNames: "account_id", baseTableName: "role_people", constraintName: "FK28B75E78ED2A3E7A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "account", referencesUniqueColumn: "false")
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-23") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "role_people", constraintName: "FK28B75E7852388A1A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-24") {
		addForeignKeyConstraint(baseColumnNames: "account_id", baseTableName: "student", constraintName: "FK8FFE823BED2A3E7A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "account", referencesUniqueColumn: "false")
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-25") {
		addForeignKeyConstraint(baseColumnNames: "book_id", baseTableName: "work_book", constraintName: "FK40F7EF573FA913A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "book", referencesUniqueColumn: "false")
	}

	changeSet(author: "jmbe (generated)", id: "1325175517020-26") {
		addForeignKeyConstraint(baseColumnNames: "student_id", baseTableName: "work_book", constraintName: "FK40F7EF57B5AD9BBA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "student", referencesUniqueColumn: "false")
	}
}

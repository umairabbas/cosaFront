package de.rwthaachen.cosa.frontend.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class Connect {
	private MongoClient mongoClient;
	private MongoDatabase db;

	public Connect(String host, int port, String databaseName) {
		// TODO Auto-generated constructor stub

		try {
			this.mongoClient = new MongoClient(host, port);
			this.db = mongoClient.getDatabase(databaseName);

			if (this.db.getCollection("historicalInformation") == null) {
				this.db.createCollection("historicalInformation");
			}
		} catch (Exception e) {
			System.out.println("Failed to build connection");
		}
	}

	public MongoDatabase getDb() {
		return this.db;
	}
}

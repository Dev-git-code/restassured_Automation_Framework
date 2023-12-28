package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


//UserEndPoints.java
// Created for perform Create, Read, Update, Delete requests to the user API.

public class UserEndPoints {

		public static Response createJsonUser(User payload)
		{
			Response response=given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(payload) 
				.when()
				.post(Routes.post_url);
			
				
			return response;
		}
		
		public static Response createXmlUser(User payload)
		{
			Response response=given()
				.contentType(ContentType.XML)
				.accept(ContentType.JSON)
				.body(payload)
				.when()
				.post(Routes.post_url);
			
				
			return response;
		}
		
		public static Response readJsonUser(String userName)
		{
			Response response=given()
							.contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.pathParam("username",userName)
			.when()
				.get(Routes.get_url);	
			return response;
		}
		
		public static Response readXmlUser(String userName)
		{
			Response response=given()
							.contentType(ContentType.XML)
							.accept(ContentType.XML)
							.pathParam("username",userName)
			.when()
				.get(Routes.get_url);	
			return response;
		}
		
		
		public static Response updateJsonUser(String userName, User payload)
		{
			Response response=given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("username", userName)
				.body(payload)
				.auth().basic(payload.getUsername(), payload.getPassword())
			.when()
				.put(Routes.update_url);
				
			return response;
		}
		
		public static Response updateXmlUser(String userName, User payload)
		{
			Response response=given()
				.contentType(ContentType.XML)
				.accept(ContentType.XML)
				.pathParam("username", userName)
				.body(payload)
				.auth().basic(payload.getUsername(), payload.getPassword())
			.when()
				.put(Routes.update_url);
				
			return response;
		}
		
		
		public static Response deleteJsonUser(String userName)
		{
			Response response=given()
							.pathParam("username",userName)
							.contentType(ContentType.JSON)
							.accept(ContentType.JSON)
			.when()
				.delete(Routes.delete_url);
				
			return response;
		}	
		
		public static Response deleteXmlUser(String userName)
		{
			Response response=given()
							.pathParam("username",userName)
							.contentType(ContentType.XML)
							.accept(ContentType.XML)
			.when()
				.delete(Routes.delete_url);
				
			return response;
		}	
				
		
}

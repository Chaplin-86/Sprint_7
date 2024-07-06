package edu.praktikum.sprint7.courier;

import edu.praktikum.sprint7.courier_models.Courier;
import edu.praktikum.sprint7.courier_models.CourierCreds;
import edu.praktikum.sprint7.courier_models.DeleteCourier;


import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class CourierClient {

    private static final String CREATE_ENDPOINT = "api/v1/courier";
    private static final String LOGIN_ENDPOINT = "api/v1/courier/login";
    private static final String DELETE_PATH = "/api/v1/courier/";



    public Response create(Courier courier) {

        return  given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(CREATE_ENDPOINT);
    }

    public Response login(CourierCreds creds) {
        return  given()
                .header("Content-type", "application/json")
                .and()
                .body(creds)
                .when()
                .post(LOGIN_ENDPOINT);

    }

    public  ValidatableResponse delete(DeleteCourier del, String cId) {

      return given()
                .header("Content-type", "application/json")
                .and()
                .body(del)
                .when()
                .delete(DELETE_PATH + cId)
              .then().log().all();


    }
}








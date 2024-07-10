package edu.praktikum.sprint7.courier;

import edu.praktikum.sprint7.couriermodels.Courier;
import edu.praktikum.sprint7.couriermodels.CourierCreds;
import edu.praktikum.sprint7.couriermodels.DeleteCourier;


import edu.praktikum.sprint7.couriermodels.ResponseToCreateCourier;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class CourierClient {

    private static final String CREATE_ENDPOINT = "api/v1/courier";
    private static final String LOGIN_ENDPOINT = "api/v1/courier/login";
    private static final String DELETE_PATH = "api/v1/courier/";


    @Step ("Отправка POST запроса на ручку api/v1/courier")
    public Response createCourier(Courier courier) {

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(CREATE_ENDPOINT);
    }

    @Step ("Отправка POST запроса на ручку api/v1/courier/login")
    public Response loginCourier(CourierCreds creds) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(creds)
                .when()
                .post(LOGIN_ENDPOINT);

    }

    @Step ("Отправка DELETE запроса на ручку api/v1/courier/")

    public ValidatableResponse deleteCourier(DeleteCourier del, String cId) {

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(del)
                .when()
                .delete(DELETE_PATH + cId)
                .then().log().all();


    }

    public ResponseToCreateCourier responseToCreateCourier(Courier courier) {

        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("api/v1/courier")
                .body()
                .as(ResponseToCreateCourier.class);
    }
}








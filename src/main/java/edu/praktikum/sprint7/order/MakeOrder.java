package edu.praktikum.sprint7.order;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class MakeOrder {
    public static final String CREATE_ORDER_ENDPOINT = "/api/v1/orders";


    public Response createOrder(OrderModel order) {
        return (Response) given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER_ENDPOINT)
                .then().log().all();
    }


}

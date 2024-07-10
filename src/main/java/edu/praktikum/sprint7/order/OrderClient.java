package edu.praktikum.sprint7.order;

import edu.praktikum.sprint7.orderresponse.ListOfOrders;

import static edu.praktikum.sprint7.order.MakeOrder.CREATE_ORDER_ENDPOINT;
import static io.restassured.RestAssured.given;

public class OrderClient {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;


    private static final String GET_LIST_OF_ORDERS_ENDPOINT = "api/v1/orders";

    public ListOfOrders ListOfOrders() {
        return given()
                .header("Content-type", "application/json")
                .get(GET_LIST_OF_ORDERS_ENDPOINT)
                .body().as(ListOfOrders.class);

    }

    public OrderTrack ResponseToOrderBody() {
        OrderModel order = new OrderModel(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .post(CREATE_ORDER_ENDPOINT)
                .body()
                .as(OrderTrack.class);
    }
}
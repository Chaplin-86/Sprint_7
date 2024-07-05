package edu.praktikum.sprint7;


import edu.praktikum.sprint7.order.MakeOrder;
import edu.praktikum.sprint7.order.OrderModel;
import edu.praktikum.sprint7.order.OrderTrack;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static edu.praktikum.sprint7.order.MakeOrder.CREATE_ORDER_ENDPOINT;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

@RunWith( Parameterized.class)
public class OrderWithParamsTests {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Parameterized.Parameter( 0 )
    public String firstName;
    @Parameterized.Parameter( 1 )
    public String lastName;
    @Parameterized.Parameter( 2 )
    public String address;
    @Parameterized.Parameter( 3 )
    public String metroStation;
    @Parameterized.Parameter( 4 )
    public String phone;
    @Parameterized.Parameter( 5 )
    public int rentTime;
    @Parameterized.Parameter( 6 )
    public String deliveryDate;
    @Parameterized.Parameter( 7 )
    public String comment;
    @Parameterized.Parameter( 8 )
    public String[] color;

    @Parameterized.Parameters
    public static Object data()[][] {
        return new Object[][]{
                {"Гарри", "Поттер", "Тисовая улица", "Сокольники", "+79152776620", 1, "2024.07.03", "I solemnly swear that I'm up to no good", new String[]{"GREY"}},
                {"Гарри", "Поттер", "Тисовая улица", "Сокольники", "+79152776620", 1, "2024.07.03", "I solemnly swear that I'm up to no good", new String[]{"BLACK"}},
                {"Гарри", "Поттер", "Тисовая улица", "Сокольники", "+79152776620", 1, "2024.07.03", "I solemnly swear that I'm up to no good", new String[]{"GREY, BLACK"}},
                {"Гарри", "Поттер", "Тисовая улица", "Сокольники", "+79152776620", 1, "2024.07.03", "I solemnly swear that I'm up to no good", new String[]{" "}}

        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName( "Создание заказа" )
    @Description( "Проверка возможности выбора цвета самоката" )

    public void chooseColorWhenOrder() {
        OrderModel order = new OrderModel(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

        MakeOrder makeOrder = new MakeOrder();
        Response orderResponse = makeOrder.createOrder(order);

        assertEquals(201, orderResponse.statusCode());

    }

    @Test
    @DisplayName( "Проверка тела ответа на запрос о создании заказа" )
    public void checkResponseToOrderBody() {
        OrderModel order = new OrderModel(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        OrderTrack orderTrack = given()
                .header("Content-type", "application/json")
                .body(order)
                .post(CREATE_ORDER_ENDPOINT)
                .body()
                .as(OrderTrack.class);

        int trackNumber = orderTrack.getTrack();

        System.out.println("Track: " + trackNumber);

    }
}

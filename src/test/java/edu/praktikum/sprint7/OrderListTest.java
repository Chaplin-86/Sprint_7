package edu.praktikum.sprint7;

import edu.praktikum.sprint7.order.OrderClient;
import edu.praktikum.sprint7.orderresponse.ListOfOrders;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;



import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.notNullValue;


public class OrderListTest {
    private OrderClient orderClient;
    static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Before

    public void setUp() {
        RestAssured.baseURI = BASE_URL;

    }

    @Test
    @DisplayName("Список заказов")
    @Description("Проверяем, что в ответ на запрос возвращается список заказов")

    public void checkListOfOrders(){
       orderClient = new OrderClient();

        ListOfOrders response = orderClient.ListOfOrders();


        MatcherAssert.assertThat(response, notNullValue());

    }


}

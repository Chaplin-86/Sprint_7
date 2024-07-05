package edu.praktikum.sprint7;

import edu.praktikum.sprint7.courier.CourierClient;
import edu.praktikum.sprint7.courier_models.Courier;
import edu.praktikum.sprint7.courier_models.DeleteCourier;
import edu.praktikum.sprint7.courier_models.ResponseToCreateCourier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static edu.praktikum.sprint7.courier.CourierGenerator.randomCourier;
import static edu.praktikum.sprint7.courier_models.CourierCreds.credsFromCourier;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTests {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    private CourierClient courierClient;
    private Integer courierId;


    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName( "Создание курьера" )
    @Description( "Создание курьера со случайными данными" )
    public void createCourier() {
        Courier courier = randomCourier();
        courierClient = new CourierClient();

        Response response = courierClient.create(courier);

        assertEquals("Неверный статус код", 201, response.statusCode());

        Response loginResponse = courierClient.login(credsFromCourier(courier));
        courierId = loginResponse.path("id");

        assertEquals("Неверный статус код", 200, loginResponse.statusCode());

        System.out.println(courierId);

    }

    @Test
    @DisplayName( "Проверка тела ответа на запрос о создании курьера" )
    public void checkResponseToCreateCourier() {
        Courier courier = randomCourier();
        courierClient = new CourierClient();
        ResponseToCreateCourier response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("api/v1/courier")
                .body()
                .as(ResponseToCreateCourier.class);

        boolean ok = response.isOk();
        System.out.println(ok);

        assertTrue(ok);

        Response loginResponse = courierClient.login(credsFromCourier(courier));
        courierId = loginResponse.path("id");

    }

    @Test
    @DisplayName( "Создание двух одинаковых курьеров" )

    public void createSameCouriers() {
        Courier courier = new Courier("Padfoot_10", "Animagus1959", "Sirius");
        courierClient = new CourierClient();

        Response response = courierClient.create(courier);
        Response response_1 = courierClient.create(courier);

        assertEquals("Неверный статус код", 201, response.statusCode());
        assertEquals("Этот логин уже используется", 409, response_1.statusCode());

        Response loginResponse = courierClient.login(credsFromCourier(courier));
        courierId = loginResponse.path("id");

    }

    @Test
    @DisplayName( "Создание курьера без логина" )

    public void createCourierWithoutLogin() {
        Courier courier = new Courier("", "Animagus1959", "Sirius");
        courierClient = new CourierClient();

        Response response = courierClient.create(courier);


        assertEquals("Недостаточно данных для создания учетной записи", 400, response.statusCode());
    }

    @Test
    @DisplayName( "Создание курьера без пароля" )

    public void createCourierWithoutPassword() {
        Courier courier = new Courier("Padfoot", "", "Sirius");
        courierClient = new CourierClient();

        Response response = courierClient.create(courier);

        assertEquals("Недостаточно данных для создания учетной записи", 400, response.statusCode());
    }

    @Test
    @DisplayName( "Создание курьера без имени" )

    public void createCourierWithoutFirstName() {
        Courier courier = new Courier("Padfoot_11", "Animagus1959", "");
        courierClient = new CourierClient();

        Response response = courierClient.create(courier);

        assertEquals("Недостаточно данных для создания учетной записи", 400, response.statusCode());
    }


    @After

    public void tearDown() {
         DeleteCourier del = new DeleteCourier(String.valueOf(courierId));
        courierClient.delete(del, String.valueOf(courierId));
    }

}



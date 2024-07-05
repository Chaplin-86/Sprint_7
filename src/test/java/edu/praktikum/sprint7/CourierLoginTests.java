package edu.praktikum.sprint7;

import edu.praktikum.sprint7.courier.CourierClient;
import edu.praktikum.sprint7.courier_models.Courier;
import edu.praktikum.sprint7.courier_models.CourierCreds;
import edu.praktikum.sprint7.courier_models.DeleteCourier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static edu.praktikum.sprint7.courier.CourierGenerator.randomCourier;
import static edu.praktikum.sprint7.courier_models.CourierCreds.credsFromCourier;
import static org.junit.Assert.assertEquals;

public class CourierLoginTests {
    static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    private CourierClient courierClient;
    private Integer courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;

    }

    @Test
    @DisplayName("Проверка авторизации")
    @Description("Авторизация с логином и паролем")
    public void checkAuthCourier() {
        Courier courier = randomCourier();
        courierClient = new CourierClient();

        courierClient.create(courier);

        Response loginResponse = courierClient.login(credsFromCourier(courier));
        courierId = loginResponse.path("id");

        assertEquals("Неверный статус код", 200, loginResponse.statusCode());


    }

    @Test
    @DisplayName("Проверка авторизации только с логином")
    public void checkAuthWitLoginOnly() {
        Courier courier = randomCourier();
        courierClient = new CourierClient();

        courierClient.create(courier);

        CourierCreds login = new CourierCreds(courier.getLogin(), "");

        Response loginResponse = courierClient.login(login);
        courierId = loginResponse.path("id");

        assertEquals("Недостаточно данных для входа", 400, loginResponse.statusCode());

    }

    @Test
    @DisplayName("Проверка авторизации только с паролем")
    public void checkAuthWitPasswordOnly() {
        Courier courier = randomCourier();
        courierClient = new CourierClient();

        courierClient.create(courier);

        CourierCreds password = new CourierCreds("", courier.getPassword());

        Response loginResponse = courierClient.login(password);
        courierId = loginResponse.path("id");

        assertEquals("Недостаточно данных для входа", 400, loginResponse.statusCode());

    }

    @Test
    @DisplayName("Проверка авторизации с неверным логином")
    public void checkAuthWithWrongLogin() {
        Courier courier = new Courier("Prongs", "Animagus1959", "James");
        courierClient = new CourierClient();

        courierClient.create(courier);

        CourierCreds wrongLogin = new CourierCreds("Pronks", courier.getPassword());

        Response loginResponse = courierClient.login(wrongLogin);
        courierId = loginResponse.path("id");

        assertEquals("Учетная запись не найдена", 404, loginResponse.statusCode());
    }

    @Test
    @DisplayName("Проверка авторизации с неверным паролем")
    public void checkAuthWithWrongPassword() {
        Courier courier = new Courier("Prongs_1", "Animagus1959", "James");
        courierClient = new CourierClient();

        courierClient.create(courier);

        CourierCreds wrongPassword= new CourierCreds(courier.getLogin(), "animagus1959");

        Response loginResponse = courierClient.login(wrongPassword);
        courierId = loginResponse.path("id");

        assertEquals("Учетная запись не найдена", 404, loginResponse.statusCode());
    }

    @After
    public void tearDown() {
        DeleteCourier del = new DeleteCourier(String.valueOf(courierId));
        courierClient.delete(del, String.valueOf(courierId));
    }

}

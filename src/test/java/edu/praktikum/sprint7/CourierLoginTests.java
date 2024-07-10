package edu.praktikum.sprint7;

import edu.praktikum.sprint7.courier.CourierClient;
import edu.praktikum.sprint7.couriermodels.Courier;
import edu.praktikum.sprint7.couriermodels.CourierCreds;
import edu.praktikum.sprint7.couriermodels.DeleteCourier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;

import static edu.praktikum.sprint7.courier.CourierGenerator.randomCourier;
import static edu.praktikum.sprint7.couriermodels.CourierCreds.credsFromCourier;
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

        courierClient.createCourier(courier);

        Response loginResponse = courierClient.loginCourier(credsFromCourier(courier));
        courierId = loginResponse.path("id");

        assertEquals("Неверный статус код", SC_OK, loginResponse.statusCode());


    }

    @Test
    @DisplayName("Проверка авторизации только с логином")
    public void checkAuthWitLoginOnly() {
        Courier courier = randomCourier();
        courierClient = new CourierClient();

        courierClient.createCourier(courier);

        CourierCreds login = new CourierCreds(courier.getLogin(), "");

        Response loginResponse = courierClient.loginCourier(login);
        courierId = loginResponse.path("id");

        assertEquals("Недостаточно данных для входа", SC_BAD_REQUEST, loginResponse.statusCode());

    }

    @Test
    @DisplayName("Проверка авторизации только с паролем")
    public void checkAuthWitPasswordOnly() {
        Courier courier = randomCourier();
        courierClient = new CourierClient();

        courierClient.createCourier(courier);

        CourierCreds password = new CourierCreds("", courier.getPassword());

        Response loginResponse = courierClient.loginCourier(password);
        courierId = loginResponse.path("id");

        assertEquals("Недостаточно данных для входа", SC_BAD_REQUEST, loginResponse.statusCode());

    }

    @Test
    @DisplayName("Проверка авторизации с неверным логином")
    public void checkAuthWithWrongLogin() {
        Courier courier = new Courier("Prongs", "Animagus1959", "James");
        courierClient = new CourierClient();

        courierClient.createCourier(courier);

        CourierCreds wrongLogin = new CourierCreds("Pronks", courier.getPassword());

        Response loginResponse = courierClient.loginCourier(wrongLogin);
        courierId = loginResponse.path("id");

        assertEquals("Учетная запись не найдена", SC_NOT_FOUND, loginResponse.statusCode());
    }

    @Test
    @DisplayName("Проверка авторизации с неверным паролем")
    public void checkAuthWithWrongPassword() {
        Courier courier = new Courier("Prongs_1", "Animagus1959", "James");
        courierClient = new CourierClient();

        courierClient.createCourier(courier);

        CourierCreds wrongPassword= new CourierCreds(courier.getLogin(), "animagus1959");

        Response loginResponse = courierClient.loginCourier(wrongPassword);
        courierId = loginResponse.path("id");

        assertEquals("Учетная запись не найдена", SC_NOT_FOUND, loginResponse.statusCode());
    }

    @After
    public void tearDown() {
        DeleteCourier del = new DeleteCourier(String.valueOf(courierId));
        courierClient.deleteCourier(del, String.valueOf(courierId));
    }


}

package edu.praktikum.sprint7.courier;

import edu.praktikum.sprint7.courier_models.Courier;

import static edu.praktikum.sprint7.utils.Utils.randomString;

public class CourierGenerator {

    public static Courier randomCourier() {
        return new Courier()
                .withLogin(randomString(10))
                .withPassword(randomString(12))
                .withFirstName(randomString(20));
    }

}

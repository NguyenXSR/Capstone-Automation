package api;

import api.dto.SeatResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SeatApi {

    private static final String BASE_URL =
            "https://movie0706.cybersoft.edu.vn/api/QuanLyDatVe/LayDanhSachPhongVe";

    public static SeatResponse getSeatData(String showtimeId) {
        Response response =
                RestAssured.given()
                        .queryParam("MaLichChieu", showtimeId)
                        .get(BASE_URL);

        response.then().statusCode(200);
        return response.as(SeatResponse.class);
    }

}

package utils;

import api.dto.SeatResponse;
import api.dto.Seats;

import java.util.List;
import java.util.stream.Collectors;

public class SeatDataProvider {


    public static List<Seats> getAvailableSeats(
            SeatResponse response, String seatType, int limit) {

        return response.getDanhSachGhe().stream()
                .filter(g -> !g.isDaDat())
                .filter(g -> g.getLoaiGhe().equalsIgnoreCase(seatType))
                .limit(limit)
                .collect(Collectors.toList());
    }

    //get GiaVe of a seat by seat type
    public static double getSeatPriceByType(SeatResponse response, String seatNumber) {
        return response.getDanhSachGhe().stream()
                .filter(g -> g.getTenGhe().equalsIgnoreCase(seatNumber))
                .findFirst()
                .map(Seats::getGiaVe)
                .orElse(0.0);
    }

}

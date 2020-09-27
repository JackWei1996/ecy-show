package com.ecy.show.dto.admin;

import lombok.Data;

@Data
public class ParkingDto {
    private String number;
    private String parkingLot;
    private String floor;
    private String area;
    private Integer status;
}

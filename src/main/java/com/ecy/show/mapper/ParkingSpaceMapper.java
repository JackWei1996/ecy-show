package com.ecy.show.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecy.show.entity.ParkingSpace;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 南京白墨科技有限公司
 * @since 2019-10-29
 */
@Repository
public interface ParkingSpaceMapper extends BaseMapper<ParkingSpace> {

    List<Map<Integer, Integer>> statusCount(@Param("parkingLot") String parkingLot,
                                            @Param("countInCar") int countInCar);

    List<Map<String, String>> getParkingLot();

    List<String> getAllParking();

    void addUsageCount(@Param("parkingNumber") String parkingNumber);

    List<ParkingSpace> listParkingCar();

    Integer getStatus(@Param("parkingNumber") String parkingNumber);
}

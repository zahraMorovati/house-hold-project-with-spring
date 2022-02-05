package ir.maktab.data.dto.mappers;

import ir.maktab.data.dto.SubServiceEntityDto;
import ir.maktab.data.entity.SubService;

public class SubServiceEntityMapper {

    public static SubServiceEntityDto toSubServiceEntityDto(SubService subService){
        return SubServiceEntityDto.builder().setName(subService.getSubServiceName()).build();
    }
}

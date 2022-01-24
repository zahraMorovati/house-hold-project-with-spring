package ir.maktab.data.dto.mappers;

import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.entity.SubService;

public class SubServiceMapper {

    public static SubServiceDto toSubServiceDto(SubService subService){
        return SubServiceDto.builder()
                .setService(subService.getService())
                .setSubServiceName(subService.getSubServiceName())
                .setExplanations(subService.getExplanations())
                .setPrice(subService.getPrice()).build();
    }

    public static SubService toSubService(SubServiceDto subServiceDto){
        return SubService.builder()
                .setService(subServiceDto.getService())
                .setSubServiceName(subServiceDto.getSubServiceName())
                .setExplanations(subServiceDto.getExplanations())
                .setPrice(subServiceDto.getPrice()).build();
    }
}

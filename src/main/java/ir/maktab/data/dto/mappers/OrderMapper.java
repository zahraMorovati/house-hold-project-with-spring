package ir.maktab.data.dto.mappers;

import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.entity.Address;
import ir.maktab.data.entity.Order;

public class OrderMapper {

    public static OrderDto toOrderDto(Order order) {

        Address address = order.getAddress();
        String city = address.getCity();
        String cityState = address.getCityState();
        String plaque = address.getPlaque();
        String explanations = address.getExplanations();

        return getOrderDto(order, city, cityState, plaque, explanations);
    }

    private static OrderDto getOrderDto(Order order, String city, String cityState, String plaque, String explanations) {
        return OrderDto.builder()
                .setOrderCode(order.getOrderCode())
                .setSubService(order.getSubService().getSubServiceName())
                .setSuggestedPrice(order.getSuggestedPrice())
                .setExplanations(order.getExplanations())
                .setRegistrationDate(order.getRegistrationDate())
                .setStartDate(order.getStartDate())
                .setAddress(cityState + "  " + city + "  " + plaque + "  " + explanations)
                .setOrderState(order.getOrderState().toString().toLowerCase())
                .setSpecialist(order.getSpecialist().getName() + " " + order.getSpecialist().getFamily())
                .setCustomer(order.getCustomer().getName() + " " + order.getCustomer().getFamily())
                .setComment(order.getComment().getExplanations())
                .setPoint(order.getComment().getPoint()).build();
    }
}

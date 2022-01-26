package ir.maktab.data.dto.mappers;

import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.entity.Address;
import ir.maktab.data.entity.Comment;
import ir.maktab.data.entity.Order;
import ir.maktab.data.entity.Specialist;

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
        Specialist specialist = order.getSpecialist();
        Comment comment = order.getComment();
        String specialistName = "";
        String specialistFamily = "";
        String commentExplanations = "";
        double commentPoint = 0;
        if (specialist != null) {
            specialistName = specialist.getName();
            specialistFamily = specialist.getFamily();
        }
        if(comment!=null){
            commentExplanations = comment.getExplanations();
            commentPoint = comment.getPoint();
        }
        return buildOrder(order, city, cityState, plaque, explanations, specialistName, specialistFamily, commentExplanations, commentPoint);
    }

    private static OrderDto buildOrder(Order order, String city, String cityState, String plaque, String explanations, String specialistName, String specialistFamily, String commentExplanations, double commentPoint) {
        return OrderDto.builder()
                .setOrderCode(order.getOrderCode())
                .setSubService(order.getSubService().getSubServiceName())
                .setSuggestedPrice(order.getSuggestedPrice())
                .setExplanations(order.getExplanations())
                .setRegistrationDate(order.getRegistrationDate())
                .setStartDate(order.getStartDate())
                .setAddress(cityState + "  " + city + "  " + plaque + "  " + explanations)
                .setOrderState(order.getOrderState().toString().toLowerCase())
                .setSpecialist(specialistName + " " + specialistFamily)
                .setCustomer(order.getCustomer().getName() + " " + order.getCustomer().getFamily())
                .setComment(commentExplanations)
                .setPoint(commentPoint).build();
    }
}

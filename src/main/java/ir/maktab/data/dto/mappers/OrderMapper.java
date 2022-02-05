package ir.maktab.data.dto.mappers;

import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.entity.Address;
import ir.maktab.data.entity.Comment;
import ir.maktab.data.entity.Order;
import ir.maktab.data.entity.Specialist;

public class OrderMapper {

    public static OrderDto toOrderDto(Order order) {

        Address address = order.getAddress();
        String city = "";
        String cityState = "";
        String plaque = "";
        String explanations = "";
        if (order.getAddress() != null) {
            if (address.getCity() != null)
                city = address.getCity();
            if (address.getCityState() != null)
                cityState = address.getCityState();
            if (address.getPlaque() != null)
                plaque = address.getPlaque();
            if (address.getExplanations() != null)
                explanations = address.getExplanations();
        }

        Specialist specialist = order.getSpecialist();
        Comment comment = order.getComment();
        String specialistName = "";
        String specialistFamily = "";
        String commentExplanations = "";
        String specialistEmail = "";
        double commentPoint = 0;
        if (specialist != null) {
            specialistName = specialist.getName();
            specialistFamily = specialist.getFamily();
            specialistEmail = specialist.getEmail();
        }
        if (comment != null) {
            commentExplanations = comment.getExplanations();
            commentPoint = comment.getPoint();
        }
        return buildOrder(order, city, cityState, plaque, explanations, specialistName, specialistFamily, commentExplanations, commentPoint, specialistEmail);
    }


    private static OrderDto buildOrder(Order order, String city, String cityState, String plaque, String explanations, String specialistName, String specialistFamily, String commentExplanations, double commentPoint, String specialistEmail) {
        return OrderDto.builder()
                .setOrderCode(order.getOrderCode())
                .setSubService(order.getSubService().getSubServiceName())
                .setSuggestedPrice(order.getSuggestedPrice())
                .setExplanations(order.getExplanations())
                .setRegistrationDate(order.getRegistrationDate())
                .setStartDate(String.valueOf(order.getStartDate().getDate()))
                .setAddress(cityState + "  " + city + "  " + plaque + "  " + explanations)
                .setOrderState(order.getOrderState().toString().toLowerCase())
                .setSpecialist(specialistName + " " + specialistFamily)
                .setSpecialistEmail(specialistEmail)
                .setCustomer(order.getCustomer().getName() + " " + order.getCustomer().getFamily())
                .setComment(commentExplanations)
                .setPoint(commentPoint).build();
    }
}

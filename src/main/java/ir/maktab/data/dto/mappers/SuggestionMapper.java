package ir.maktab.data.dto.mappers;

import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.entity.Order;
import ir.maktab.data.entity.Specialist;
import ir.maktab.data.entity.Suggestion;

public class SuggestionMapper {


    public static SuggestionDto toSuggestionDto(Suggestion suggestion, Order order) {
        String orderCode = "";
        if (order != null) {
            orderCode = order.getOrderCode();
        }

        Specialist specialist = suggestion.getSpecialist();
        String specialistName = "";
        String specialistEmail = "";
        String startTime="";
        if(suggestion.getStartTime()!=null)
            startTime=suggestion.getStartTime().getHours()+":"+suggestion.getStartTime().getMinutes();
        if (specialist != null) {
            specialistName = specialist.getName() + " " + specialist.getFamily();
            specialistEmail = specialist.getEmail();
        }
        return SuggestionDto.builder()
                .setSuggestionCode(suggestion.getSuggestionCode())
                .setDate(suggestion.getDate())
                .setOrderCode(orderCode)
                .setSuggestedPrice(suggestion.getSuggestedPrice())
                .setTimeStart(startTime)
                .setWorkHour(suggestion.getWorkHour())
                .setSpecialistName(specialistName)
                .setSpecialistEmail(specialistEmail).build();
    }
}

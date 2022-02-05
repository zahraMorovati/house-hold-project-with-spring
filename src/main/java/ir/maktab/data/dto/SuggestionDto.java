package ir.maktab.data.dto;

import ir.maktab.data.entity.Specialist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SuggestionDto {

    private String suggestionCode;
    private Date date;
    private double suggestedPrice;
    private double workHour;
    private Date startTime;
    private String specialistEmail;
    private String orderCode;
    private String specialistName;
    private String timeStart;
}

package inhaus.inhaus.data;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Data {
    private String place;
    private String date_month;
    private String date_day;
    private String user;
    private String time_start;
    private String time_end;
}

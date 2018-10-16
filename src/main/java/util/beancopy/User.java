package util.beancopy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class User {

    private String name;

    private int age;

    private BigDecimal money;

    private String day;
}

package learn.reflect;

/**
 * Created by yunfan on 2018/5/14.
 */
public class TacticsComm {

    @Tactics(type = "equals")
    public String fields;
    private String value="100";
    private String standard_id;

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStandard_id() {
        return standard_id;
    }

    public void setStandard_id(String standard_id) {
        this.standard_id = standard_id;
    }
}

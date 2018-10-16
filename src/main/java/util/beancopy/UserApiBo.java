package util.beancopy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
public class UserApiBo {

	/**  */
	private int id;
	/**  */
	private String uid;
	/**  */
	private int api;
	/**  */
	private int remainPoint;
	/**  */
	private int invokedPoint;
	/**  */
	private String status;
	/**  */
	private Date lastUseTime;
	/** 单价元/次 */
	private BigDecimal price;
	/**  */
	private byte hasSpePrice;
	/** 申请数据状态，0未申请1审核中2已申请 */
	private int applyStatus;
	/** 申请时间 */
	@DateTimeFormat
	private Date applyTime;
	/**  */
	private int maxOccurs;
	/** 收藏状态：0未收藏，1已收藏 */
	private int favoriteStatus;
	/** 折扣：(0,1]—用于后期根据参数计费的接口 */
	private BigDecimal discount;
	/** 日接口最大查询数量 */
	private int maxCountDay;

	private Map<String,Object> basicMap;

	List<User> userList;
}

package util.circuitbreaker;

/**
 * Created by YScredit on 2018/11/1.
 */
public interface CircuitBreaker {

    /**
     * 重置熔断器
     */
    void reset();

    /**
     * 是否允许通过熔断器
     * @return
     */
    boolean canPassCheck();

    /**
     * 统计失败次数
     */
    void countFailNum();
}

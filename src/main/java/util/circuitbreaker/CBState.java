package util.circuitbreaker;

/**
 * Created by YScredit on 2018/11/1.
 */
public interface CBState {
    /**
     * 获取当前名称
     * @return
     */
    String getStateName();

    /**
     * 检查、校验当前状态是否需要扭转
     * @return
     */
    String checkAndSwitchState(AbstractCircuitBreaker cb);

    /**
     * 是否允许通过熔断器
     * @return
     */
    boolean canPassCheck(AbstractCircuitBreaker cb);

    /**
     * 统计失败数量
     * @return
     */
    void countFailNum(AbstractCircuitBreaker cb);
}

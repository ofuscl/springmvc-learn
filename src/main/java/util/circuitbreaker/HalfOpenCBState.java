package util.circuitbreaker;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by YScredit on 2018/11/1.
 */
public class HalfOpenCBState implements CBState {

    private long stateTime = System.currentTimeMillis();

    /**
     * 半开状态，失败计数器
     */
    private AtomicInteger failNum = new AtomicInteger(0);

    /**
     * 半开状态，允许通过计数器
     */
    private AtomicInteger passNum = new AtomicInteger(0);

    @Override
    public String getStateName(){
        return this.getClass().getSimpleName();
    }

    @Override
    public String checkAndSwitchState(AbstractCircuitBreaker cb) {
        long now = System.currentTimeMillis();
        long idleTime =   Long.valueOf(cb.thresholdFailRateForClose.split("/")[1]);
        if(stateTime + idleTime <= now){
            int maxFailNum = cb.thresholdFailNumForHalfOpen;
            if (failNum.get() >= maxFailNum){
                cb.setState(new OpenCBState());
            } else {
                cb.setState(new CloseCBState());
            }
        }
        return null;
    }

    /**
     * 关闭状态下所有请求都可以通过
     * @param cb
     * @return
     */
    @Override
    public boolean canPassCheck(AbstractCircuitBreaker cb) {
        // 检查释放切换状态
        checkAndSwitchState(cb);
        // 超过了阈值，不再放量
        int maxPassNum = Integer.valueOf(cb.thresholdPassRateForHalfOpen.split("/")[0]);
        if(passNum.get() > maxPassNum){
            return false;
        }
        if(passNum.incrementAndGet() <= maxPassNum){
            return true;
        }
        return false;
    }

    /**
     *
     * @param cb
     * @return
     */
    @Override
    public void countFailNum(AbstractCircuitBreaker cb) {
        // 失败计数
        failNum.incrementAndGet();
        // 检查释放切换状态
        checkAndSwitchState(cb);
    }
}

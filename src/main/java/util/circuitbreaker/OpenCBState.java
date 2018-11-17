package util.circuitbreaker;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 熔断器-开启状态
 * Created by YScredit on 2018/11/1.
 */
public class OpenCBState implements CBState{

    private long stateTime = System.currentTimeMillis();

    /**
     *
     */
    private AtomicInteger failNum = new AtomicInteger(0);
    private long failNumClearTime = System.currentTimeMillis();

    @Override
    public String getStateName(){
        return this.getClass().getSimpleName();
    }

    @Override
    public String checkAndSwitchState(AbstractCircuitBreaker cb) {
        long now = System.currentTimeMillis();
        long idleTime = cb.thresholdIdleTimeForOpen * 1000L;
        if(stateTime + idleTime <= now){
            cb.setState(new HalfOpenCBState());
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
        checkAndSwitchState(cb);
        return false;
    }

    /**
     *
     * @param cb
     * @return
     */
    @Override
    public void countFailNum(AbstractCircuitBreaker cb) {
        // nothing
    }
}

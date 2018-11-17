package util.circuitbreaker;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 熔断器-关闭状态
 * Created by YScredit on 2018/11/1.
 */
public class CloseCBState implements CBState{

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
        long maxFailNum = Long.valueOf(cb.thresholdFailRateForClose.split("/")[0]);
        if(failNum.get() >= maxFailNum){
            cb.setState(new OpenCBState());
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
        return true;
    }

    /**
     *
     * @param cb
     * @return
     */
    @Override
    public void countFailNum(AbstractCircuitBreaker cb) {

        long period =   Long.valueOf(cb.thresholdFailRateForClose.split("/")[1]);
        long now = System.currentTimeMillis();
        if (failNumClearTime + period <= now){
            failNum.set(0);
        }
        // 失败计数
        failNum.incrementAndGet();
        // 检查释放切换状态
        checkAndSwitchState(cb);
    }
}

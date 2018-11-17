package util.circuitbreaker;

/**
 * Created by YScredit on 2018/11/1.
 */
public abstract class AbstractCircuitBreaker implements CircuitBreaker{

    /**
     * 熔断器当前状态
     */
    private volatile CBState state = new CloseCBState();

    /**
     * 当熔断器关闭的情况下，多少秒内失败多少次进入，熔断器打开状态（默认10分钟内，失败10次进入）
     */
    public String thresholdFailRateForClose = "10/600";

    /**
     * 在熔断器打开情况下，熔断多少秒进入半开状态（默认30分钟）
     */
    public int thresholdIdleTimeForOpen = 1800;

    /**
     * 当熔断器半开的情况下，多少秒内释放多少次请求，去试探（默认10分钟内，释放10次请求）
     */
    public String thresholdPassRateForHalfOpen = "10/600";

    /**
     * 在熔断器半开情况下，试探期间，如果有超过多少次失败，重新进入熔断打开状态，否则进入熔断关闭
     */
    public int thresholdFailNumForHalfOpen = 1;

    public CBState getState(){
        return state;
    }

    public void setState(CBState state){
        // 当前状态不能切换
        CBState currentState = getState();
        if(currentState.getStateName().equals(state.getStateName())){
            return;
        }
        // 多线程加锁
        synchronized (this){
            currentState = getState();
            if(currentState.getStateName().equals(state.getStateName())){
                return;
            }
            this.state = state;
            System.out.println("熔断器状态转移："+currentState.getStateName()+" -> "+state);
        }
    }
}

package top.lytree;

public class ConsulHeartbeatTask implements Runnable {

    private final String serviceId;

    private final String checkId;

    private final TtlScheduler ttlScheduler;

    private final Supplier<CheckStatus> statusSupplier;

    @Override
    public void run() {

    }
}

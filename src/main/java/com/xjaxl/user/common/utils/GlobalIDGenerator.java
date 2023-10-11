package com.xjaxl.user.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Implement refer to twitter snowflake : https://github.com/twitter/snowflake
 * <pre>
 * The Global ID:
 * - long with 64-bit
 * - Incrementally on millisecond level
 * - structure:
 * -----------------------------------------
 * |timestamp|node identity|sequence number|
 * -----------------------------------------
 * timestamp: 41 bit; node identity: 10 bit; sequence number: 12 bit;
 * The node identity is not same as the twitter's snowfake as 5-bit datacenter and 5-bit machine
 * The node identity here is maintained by Activate itself. It is assigned an identical value from property file or DB table.
 * The node identity can be generally different VM in cluster env; Or can be set different value for different process running in same VM.
 * 
 * An exceptional case is that linux system time is changed during Activate running:
 * if the time is detected moving backwards, then the UUID would be a way as replacement to the Global ID generated until the time elapse than previous.
 * 
 * Global ID Usage:
 * 		long id = GlobalIDGenerator.getInstance().nextId();
 * 	    结果可以是"211585982560997537, 211585982560997539"等18, 19, 20位数字。
 * <pre>
 */
@Slf4j
public class GlobalIDGenerator {
    ElectricGlobalEnvConfiguration electricGlobalEnvConfiguration = SpringContextUtils.getBean(ElectricGlobalEnvConfiguration.class);

	private static long workerId; // assign different id for different node in cluster env; or for for different process running in same VM
    private static final long epoch = 1517241601000L; // starting time as benchmark, generally be set to nearest system time
    private static final long workerIdBits = 10L;      // node to occupy the bit count: 10-bit
    private static final long maxWorkerId = -1L ^ -1L << workerIdBits;// The maximum of node number allowed: 1023
    private static long sequence = 0L;                   // initially as 0, incrementally in one time unit(millisecond)
    private static final long sequenceBits = 12L;      // sequence to occupy the bit count: 12-bit
    private static final long workerIdShift = sequenceBits; // 12
    
    private static final long timestampLeftShift = sequenceBits + workerIdBits; // 22
    private static final long sequenceMask = -1L ^ -1L << sequenceBits; // 4095, 12-bit
    private long lastTimestamp = -1L; // last timestamp, be used to reset sequence in next millisecond 
    
    private static GlobalIDGenerator instance = new GlobalIDGenerator();
    
    public static GlobalIDGenerator getInstance() {
		return instance;
	}
    
    private GlobalIDGenerator() {
        log.warn("Initiate GlobalIDGenerator instance with workerId:{}", electricGlobalEnvConfiguration.getId());
        setWorkerId(electricGlobalEnvConfiguration.getId());
	}
    
	private GlobalIDGenerator(long workerId) {
		if (workerId > maxWorkerId || workerId <= 0) {
            throw new IllegalArgumentException(String.format("worker Id: '%d' can't be greater than %d or less than 0", workerId, maxWorkerId));
        }
        GlobalIDGenerator.workerId = workerId;
	}
    
	/**
	 * Be set during application startup, and should be only set once until application restart
	 * @param workerId
	 */
	public void setWorkerId(long workerId) {
		GlobalIDGenerator.workerId = workerId;
		log.warn("Set workerId to {}", workerId);
	}

    /**
     * 获取主键id, 如：“211585982560997537, 211585982560997539"等18, 19, 20位数字。
     * @return
     */
	public synchronized long nextId() {
        long timestamp = timeGen();
        // for in same millisecond, then sequence be increased in (0, 4095)；
        // for time elapsed more than previous millisecond, then reset sequence to 0
        if (lastTimestamp == timestamp) {
            sequence = sequence + 1 & sequenceMask;
            if (sequence == 0) {
                timestamp = tillNextMillis(lastTimestamp);// re-create timestamp
            }
        } else {
            sequence = 0;
        }

        if (timestamp < lastTimestamp) {
        	//TODO Throw exception or use UUID?
//        	 throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
            UUID uuid = UUID.randomUUID();
            return uuid.getMostSignificantBits();
        }

        lastTimestamp = timestamp;
        return (timestamp - epoch) << timestampLeftShift 
        		| workerId << workerIdShift 
        		| sequence;
    }

    /**
     * Wait until the next millisecond to ensure the returned timestamp is after the lastTimestamp
     */
    private long tillNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * Fetch system millisecond time
     */
    private long timeGen() {
    return System.currentTimeMillis();
}

    /**
     * Testing result on single thread:
     * Generate id count: 16777216 in time: 4255 ms
     * Rate: 3942000/s
     * @param args
     */
    public static void main(String[] args) {
        GlobalIDGenerator idGenerator = GlobalIDGenerator.getInstance();
        idGenerator.setWorkerId(1);
        long start = System.currentTimeMillis();
        long count = 0;
        for (int i = 0; i < (1 << 18); i++) {
            idGenerator.nextId();
            System.out.println(i+": "+idGenerator.nextId());
            count++;
        }
        long end = System.currentTimeMillis();
        System.out.println("Generate id count: " + count + " in time: " + (end - start) + " ms");
        System.out.println("rate: " + (count / (end - start) * 1000) + "/s");
    }
}
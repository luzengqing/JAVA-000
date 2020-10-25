package demo.jvm02;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class GCLogAnalysis {

    /**
     * java -Xmx128m -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc-%t.log demo.jvm02.GCLogAnalysis
     */

    /**
     * 串行/并行/CMS/G1总结：
     *  SerialGC: 只有单线程工作，耗时长，当发生FullGC时，只清理年老代
     *  ParallelGC: 多线程并行工作，目的就是减少GC停顿时间，增加吞吐量.特点就是，要么不工作，工作就是火力全开，适合多核处理器时代
     *  CMS: GC线程和应用线程并发工作，目的就是减少系统延迟，不妨碍业务。特点就是，一次少量清理，多次清理。
     *  G1: 算法复杂，目的更是减少停顿时间，减少延迟。特点是可以将暂停时间控制在预期目标值内
     *
     *  当然，堆内存配置越大，发送GC的次数相对来说约少
     */

    private static Random random = new Random();

    public static void main(String[] args) {


        long startMillis = System.currentTimeMillis();
        long timeoutMillis = TimeUnit.SECONDS.toMillis(1);
        long endMillis = startMillis + timeoutMillis;

        LongAdder counter = new LongAdder();

        System.out.println("正在执行...");

        // 缓存一部分对象，进入老年代
        int cacheSize = 2000;
        Object[] cachedGarbage = new Object[cacheSize];
        while (System.currentTimeMillis() < endMillis) {
            // 生产垃圾对象
            Object garbage = generateGarbage(100*1024);
            counter.increment();
            int randomIndex = random.nextInt(2 * cacheSize);
            if (randomIndex < cacheSize) {
                cachedGarbage[randomIndex] = garbage;
            }
        }
        System.out.println("执行结束!共生成对象次数:" + counter.longValue());

    }

    private static Object generateGarbage(int max) {
        int randomSize = random.nextInt(max);
        int type = randomSize % 4;
        Object result = null;
        switch (type) {
            case 0:
                result = new int[randomSize];
                break;
            case 1:
                result = new byte[randomSize];
                break;
            case 2:
                result = new double[randomSize];
                break;
            default:
                result = new char[randomSize];
                break;
        }
        return result;
    }



}

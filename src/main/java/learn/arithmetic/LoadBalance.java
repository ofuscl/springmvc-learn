package learn.arithmetic;

import java.util.*;

/**
 * 负载均衡算法
 * 负载均衡，英文名称为Load Balance，指由多台服务器以对称的方式组成一个服务器集合，每台服务器都具有等价的地位，
 * 都可以单独对外提供服务而无须其他服务器的辅助。通过某种负载分担技术，将外部发送来的请求均匀分配到对称结构中的某一台服务器上，而接收到请求的服务器独立地回应客户的请求。负载均衡能够平均分配客户请求到服务器阵列，借此提供快速获取重要数据，解决大量并发访问服务问题，这种集群技术可以用最少的投资获得接近于大型主机的性能。
 * Created by YScredit on 2019/1/17.
 */
public class LoadBalance {

    public static void main(String[] args) {

    }

    public Map<String, Integer> getIpMap() {

        // 待路由的Ip列表，Key代表Ip，Value代表该Ip的权重
        Map<String, Integer> serverWeightMap = new HashMap<String, Integer>();
        serverWeightMap.put("192.168.1.100", 1);
        serverWeightMap.put("192.168.1.101", 1);
        // 权重为4
        serverWeightMap.put("192.168.1.102", 4);
        serverWeightMap.put("192.168.1.103", 1);
        serverWeightMap.put("192.168.1.104", 1);
        // 权重为3
        serverWeightMap.put("192.168.1.105", 3);
        serverWeightMap.put("192.168.1.106", 1);
        // 权重为2
        serverWeightMap.put("192.168.1.107", 2);
        serverWeightMap.put("192.168.1.108", 1);
        serverWeightMap.put("192.168.1.109", 1);
        serverWeightMap.put("192.168.1.110", 1);

        return serverWeightMap;
    }

    /**
     * 轮询法。
     * 轮询法的优点在于：试图做到请求转移的绝对均衡。
     * 轮询法的缺点在于：为了做到请求转移的绝对均衡，必须付出相当大的代价，
     * 因为为了保证pos变量修改的互斥性，需要引入重量级的悲观锁synchronized，这将会导致该段轮询代码的并发吞吐量发生明显的下降。
     */
    public String doRoundRobin(){

        // 重建一个Map，避免服务器的上下线导致的并发问题
        Map<String, Integer> serverMap =  getIpMap();

        // 取得Ip地址List
        Set<String> keySet = serverMap.keySet();
        ArrayList<String> keyList = new ArrayList<String>();
        keyList.addAll(keySet);

        String server = null;
        Integer pos = 0;
        synchronized (pos)
        {
            if (pos > keySet.size())
                pos = 0;
            server = keyList.get(pos);
            pos ++;
        }
        return server;
    }

    /**
     * 加权轮询法。
     * @return
     */
    public String doWeigthRoundRobin(){

        // 重建一个Map，避免服务器的上下线导致的并发问题
        Map<String, Integer> serverMap =  getIpMap();
        // 取得Ip地址List
        Set<String> keySet = serverMap.keySet();
        Iterator<String> iterator = keySet.iterator();

        List<String> serverList = new ArrayList<String>();
        while (iterator.hasNext()){
            String server = iterator.next();
            int weight = serverMap.get(server);
            for (int i = 0; i < weight; i++)
                serverList.add(server);
        }

        String server = null;
        Integer pos = 0;
        synchronized (pos){
            if (pos > keySet.size())
                pos = 0;
            server = serverList.get(pos);
            pos ++;
        }

        return server;
    }

    /**
     * 随机法
     */
    public String doRandom(){

        // 重建一个Map，避免服务器的上下线导致的并发问题
        Map<String, Integer> serverMap =  getIpMap();
        // 取得Ip地址List
        Set<String> keySet = serverMap.keySet();
        ArrayList<String> keyList = new ArrayList<String>();
        keyList.addAll(keySet);

        java.util.Random random = new java.util.Random();
        int randomPos = random.nextInt(keyList.size());
        return keyList.get(randomPos);
    }

    /**
     * 加权随机法
     */
    public String doWeightRandom(){

        // 重建一个Map，避免服务器的上下线导致的并发问题
        Map<String, Integer> serverMap =  getIpMap();

        // 取得Ip地址List
        Set<String> keySet = serverMap.keySet();
        Iterator<String> iterator = keySet.iterator();

        List<String> serverList = new ArrayList<String>();
        while (iterator.hasNext()){
            String server = iterator.next();
            int weight = serverMap.get(server);
            for (int i = 0; i < weight; i++)
                serverList.add(server);
        }
        java.util.Random random = new java.util.Random();
        int randomPos = random.nextInt(serverList.size());
        return serverList.get(randomPos);
    }

    /**
     * Hash法
     */
    public String doHash(){
        // 重建一个Map，避免服务器的上下线导致的并发问题
        Map<String, Integer> serverMap =  getIpMap();
        // 取得Ip地址List
        Set<String> keySet = serverMap.keySet();
        ArrayList<String> keyList = new ArrayList<String>();
        keyList.addAll(keySet);

        // 在Web应用中可通过HttpServlet的getRemoteIp方法获取
        String remoteIp = "127.0.0.1";
        int hashCode = remoteIp.hashCode();
        int serverListSize = keyList.size();
        int serverPos = hashCode % serverListSize;

        return keyList.get(serverPos);
    }
}

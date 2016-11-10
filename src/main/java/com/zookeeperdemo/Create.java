package com.zookeeperdemo;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

public class Create {

    public static void main(String[] args) 
                throws IOException, KeeperException, InterruptedException {

         final ZooKeeper zk = new ZooKeeper("192.168.199.236:2181", 60000, new Watcher() {
        	
            public void process(WatchedEvent event) {
                System.out.println("State:" + event.getState());
            }
        });
        
        zk.create("/node", null,Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL );
        Thread.sleep(5000);
        zk.create("/node", null,Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL );
        Thread.sleep(5000);
        zk.create("/node", null,Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL );
        
        Stat stat = zk.exists("/node",new Watcher() {
        	
        	
            public void process(WatchedEvent event) {
                System.out.println(event.getPath() + " | " + event.getType().name() );
                try {
                	zk.exists("/node",this);
                }catch (KeeperException | InterruptedException e) {
                	
                }
            }
        	
        });
        
        Thread.sleep(100000);
        // 关闭连接
        zk.close();
    }
}

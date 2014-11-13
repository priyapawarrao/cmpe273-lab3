package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;
import com.google.common.hash.*;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        
        CacheServiceInterface cache1 = new DistributedCacheService(
                "http://localhost:3000");
        
        CacheServiceInterface cache2 = new DistributedCacheService(
                "http://localhost:3001");
        
        CacheServiceInterface cache3 = new DistributedCacheService(
                "http://localhost:3002");
        
        List servers = new ArrayList ();
        servers.add("http://localhost:3000");
        servers.add("http://localhost:3001");
        servers.add("http://localhost:3002");
       
        Ring ring = new Ring(servers);
        
        
        String []bucket = {ring.getNodeByObjectId("1"), ring.getNodeByObjectId("2"), ring.getNodeByObjectId("3"), ring.getNodeByObjectId("4"), ring.getNodeByObjectId("5"), ring.getNodeByObjectId("6"),ring.getNodeByObjectId("7"),ring.getNodeByObjectId("8"),ring.getNodeByObjectId("9"),ring.getNodeByObjectId("10")};
        String []values = {"a","b","c","d","e","f","g","h","i","j"};
        
       for (int i=0; i< bucket.length; i++)
       {
        	
        	if(bucket[i].equals("http://localhost:3000"))
        	{
        		cache1.put(i+1, values[i]);
        		System.out.println("Cached to Node: A: " + bucket[i]);
        		System.out.println("put(" + (i+1) + ")=>" + values[i]);
        		System.out.println("get(" + (i+1) + ")=>" + cache1.get(i+1));
        		
        	}else if(bucket[i].equals("http://localhost:3001"))
        	{
        		cache2.put(i+1, values[i]);
        		System.out.println("Cached to Node: B: " + bucket[i]);
        		System.out.println("put(" + (i+1) + ")=>" + values[i]);
        		System.out.println("get(" + (i+1) +  ")=>" + cache2.get(i+1));
        	}else if(bucket[i].equals("http://localhost:3002"))
        	{
        		cache3.put(i+1, values[i]);
        		System.out.println("Cached to Node: C: " +  bucket[i]);
        		System.out.println("put(" + (i+1) + ")=>" + values[i]);
        		System.out.println("get(" + (i+1) + ")=>" + cache3.get(i+1));
        	}
        	
       }
    }

}

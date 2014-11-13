package edu.sjsu.cmpe.cache.client;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;

public class Ring<T> {
	 
    private final SortedMap<Long, T> circle = new TreeMap<Long, T>();
    private MD5Hash hash = new MD5Hash();
    private int replicas = 10000;
 
    public Ring(Collection<T> pNodes) {
 
    	
       for (T pNode : pNodes) {
    	   System.out.println("Node: " + pNode);
            addNode(pNode);
        }
    }
 
    private void addNode(T pNode) {
        for (int i = 0; i < replicas; i++) {
            circle.put(hash.hash(pNode.toString() + i), pNode);
        }
    }
 
    public void removeNode(T pNode, int vnodeCount) {
          for (int i = 0; i < replicas; i++) {
            circle.remove(hash.hash(pNode.toString() + i));
            
          }
        }
    
    public String getNodeByObjectId(String objectId) {
   
        Long hashValue = hash.hash(objectId);
        
        if (!circle.containsKey(hashValue)) {
            SortedMap<Long, T> tailMap = circle.tailMap(hashValue);
            hashValue = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        System.out.println("Hash value: " + hashValue);
        return circle.get(hashValue).toString();
        
       }
 
    private static class MD5Hash {
        MessageDigest instance;
 
        public MD5Hash() {
            try {
                instance = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
            }
        }
 
        long hash(String key) {
            instance.reset();
            instance.update(key.getBytes());
            byte[] digest = instance.digest();
 
            long h = 0;
            for (int i = 0; i < 4; i++) {
                h <<= 8;
                h |= ((int) digest[i]) & 0xFF;
            }
           // System.out.println("Hash value : " + h);
            return h;
        }
    	
    };
}
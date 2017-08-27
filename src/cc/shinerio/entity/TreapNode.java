package cc.shinerio.entity;

import java.util.HashSet;
import java.util.Random;

public class TreapNode<K extends Comparable<K>, V> {
	//节点元素
	public K key;
	
	//节点附加域
	public HashSet<V> attach =  new HashSet<>();
	
	//优先级(随机数)
	public int priority;
	
	//左节点
	public TreapNode<K, V> left;
	
	//右节点
	public TreapNode<K, V> right;
	
	public TreapNode() {
	}

	public TreapNode(K key, V value,
			TreapNode<K, V> left, TreapNode<K, V> right) {
		super();
		this.key = key;
		this.attach.add(value);
		this.priority = new Random(System.nanoTime()).nextInt(Integer.MAX_VALUE);
		this.left = left;
		this.right = right;
	}

		
}

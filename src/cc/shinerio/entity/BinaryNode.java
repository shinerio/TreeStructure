package cc.shinerio.entity;

import java.util.HashSet;

public class BinaryNode<K extends Comparable<K>,V> {
	//节点元素
	public K key;
	
	//节点附加值
	public HashSet<V> attach = new HashSet<>();
	
	//左节点
	public BinaryNode<K, V> left;
	
	//右节点
	public BinaryNode<K, V> right;
	
	public BinaryNode() {
	}

	public BinaryNode(K key, V value, BinaryNode<K, V> left,
			BinaryNode<K, V> right) {
		super();
		this.key = key;
		attach.add(value);
		this.left = left;
		this.right = right;
	}
	
}

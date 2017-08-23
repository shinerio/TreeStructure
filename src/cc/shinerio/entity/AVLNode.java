package cc.shinerio.entity;

import java.util.HashSet;

public class AVLNode<K extends Comparable<K>, V> {
	//节点元素
	public K key;
	
	//节点附加值
	public HashSet<V> attach = new HashSet<>();
	
	//节点高度
	
	public int height;
	
	//左节点
	public AVLNode<K, V> left;
	
	//右节点
	public AVLNode<K, V> right;
	
	public AVLNode() {
	}

	public AVLNode(K key, V value, AVLNode<K, V> left,
			AVLNode<K, V> right) {
		super();
		this.key = key;
		this.attach.add(value);
		this.left = left;
		this.right = right;
	}
	
}

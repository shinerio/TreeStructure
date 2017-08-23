package cc.shinerio.entity;

import java.util.HashSet;

public class BinaryNode<K extends Comparable<K>,V> {
	//�ڵ�Ԫ��
	public K key;
	
	//�ڵ㸽��ֵ
	public HashSet<V> attach = new HashSet<>();
	
	//��ڵ�
	public BinaryNode<K, V> left;
	
	//�ҽڵ�
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

package cc.shinerio.entity;

import java.util.HashSet;

public class AVLNode<K extends Comparable<K>, V> {
	//�ڵ�Ԫ��
	public K key;
	
	//�ڵ㸽��ֵ
	public HashSet<V> attach = new HashSet<>();
	
	//�ڵ�߶�
	
	public int height;
	
	//��ڵ�
	public AVLNode<K, V> left;
	
	//�ҽڵ�
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

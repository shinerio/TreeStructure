package cc.shinerio.entity;

import java.util.HashSet;
import java.util.Random;

public class TreapNode<K extends Comparable<K>, V> {
	//�ڵ�Ԫ��
	public K key;
	
	//�ڵ㸽����
	public HashSet<V> attach =  new HashSet<>();
	
	//���ȼ�(�����)
	public int priority;
	
	//��ڵ�
	public TreapNode<K, V> left;
	
	//�ҽڵ�
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

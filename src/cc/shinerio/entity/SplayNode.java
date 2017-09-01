package cc.shinerio.entity;

public class SplayNode<T extends Comparable<T>> {
	//�ڵ�Ԫ��
	public T key;
	
	
	//��ڵ�
	public 	SplayNode<T> left;
	
	//�ҽڵ�
	public SplayNode<T> right;
	
	public SplayNode() {
	}

	public SplayNode(T key,SplayNode<T> left, SplayNode<T> right) {
		super();
		this.key = key;
		this.left = left;
		this.right = right;
	}
	
	
}

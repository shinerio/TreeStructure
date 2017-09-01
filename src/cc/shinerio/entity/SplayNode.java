package cc.shinerio.entity;

public class SplayNode<T extends Comparable<T>> {
	//节点元素
	public T key;
	
	
	//左节点
	public 	SplayNode<T> left;
	
	//右节点
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

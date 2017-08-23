package cc.shinerio.entity;

import java.util.HashSet;
/*
 * ��������õ���С������������
 */
public class BinaryTree<K extends Comparable<K>, V> {
	public BinaryNode<K, V> root;

	public void add(K key, V value) {
		if(key==null){
			return ;
		}
		if(root==null){
			root = new BinaryNode<K, V>(key, value, null, null);
		}else{
			root = add(key, value, root);
		}
	}
	public BinaryNode<K, V> add(K key, V value, BinaryNode<K, V> node) {
		if (node == null) {
			node = new BinaryNode<K, V>(key, value, null, null);
		}
		// ������
		else if (key.compareTo(node.key) > 0) {
			node.right=add(key, value, node.right);
		}
		// ������
		else if (key.compareTo(node.key) < 0) {
			node.left=add(key, value, node.left);
		}
		// ���ڵ�ǰ�ڵ�
		else {
			node.attach.add(value);
		}
		return node;

	}

	public HashSet<V> searchRange(K min, K max) {
		HashSet<V> result = new HashSet<>();
		if(root==null){
			return null;
		}
		result = searchRange(min, max, result, root);
		return result;
	}

	public HashSet<V> searchRange(K min, K max, HashSet<V> result,
			BinaryNode<K, V> node) {
		if (node == null) {
			return result;
		}
		// ���������������ֵҪ���ڽڵ���б�Ҫ������������
		if (max.compareTo(node.key) >0) { 
			searchRange(min, max, result, node.right);
			// �ҵ��Ͻ�,�ж��Ƿ������½磬��������
			 if (min.compareTo(node.key) < 0) {
				result.addAll(node.attach);
			}else{
				return result;     //������������ֵС����Сֵ��������û�б�Ҫ������ֱ�ӷ���
			}
		}
		//������������ֻ����СֵС�ڵ�ǰ�ڵ���б�Ҫ����������
		searchRange(min, max, result, node.left);
		return result;
	}
	
	public boolean Contain(K key){
		return Contain(key,root);
	}
	
	private boolean Contain(K key, BinaryNode<K, V> node) {
		if (node == null)
			return false;
		// ������
		if (key.compareTo(node.key) < 0)
			return Contain(key, node.left);
		// ������
		if (key.compareTo(node.key) > 0)
			return Contain(key, node.right);
		return true;
	}
	
	public BinaryNode<K, V> findMax(){
		if(root==null){
			return null;
		}
		return findMax(root);
	}
	

	private BinaryNode<K, V> findMax(BinaryNode<K, V> node){
		if(node.right!=null){
			return findMax(node.right);
		}
		return node;
	}
	
	public BinaryNode<K, V> findMin(){
		if(root==null){
			return null;
		}
		return findMin(root);
	}
	
	public BinaryNode<K, V> findMin(BinaryNode<K, V> node){
		if(node.left!=null){
			return findMin(node.left);
		}
		return node;
	}
	
	public  BinaryNode<K, V> remove(K key,V value,BinaryNode<K, V> node){
		if(node==null){
			return node;
		}
		if(key.compareTo(node.key)<0){
			node.left=remove(key,value,node.left);
		}else if(key.compareTo(node.key)>0){
			node.right=remove(key,value,node.right);
		}else{
			//��ֹһ��ֵ
			if(node.attach.size()>1){
				node.attach.remove(value);
			}//������ӽڵ�
			else if(node.right!=null&&node.left!=null){  //�˴�ʹ����������С�ڵ����滻��Ƶ��ɾ������ֶ�����ʧ�⣬�������ر��
				BinaryNode<K, V> replace = findMin(node.right);   //һ��û��������  
				node.key=replace.key;
				node.attach = replace.attach;
				remove(node.key,node.right);
			}//�������ӽڵ�
			else{
				node=node.left==null?node.right:node.left;
			}
		}
		return node;
	}
	
	public  BinaryNode<K, V> remove(K key,BinaryNode<K, V> node){
		if(node==null){
			return node;
		}
		if(key.compareTo(node.key)<0){
			node.left=remove(key,node.left);
		}else if(key.compareTo(node.key)>0){
			node.right=remove(key,node.right);
		}else{
			if(node.right!=null&&node.left!=null){  //�˴�ʹ����������С�ڵ����滻��Ƶ��ɾ������ֶ�����ʧ�⣬�������ر��
				BinaryNode<K, V> replace = findMin(node.right);   //һ��û��������  
				node.key=replace.key;
				node.attach = replace.attach;
				remove(node.key,node.right);
			}//�������ӽڵ��Ҷ�ڵ�
			else{
				node=node.left==null?node.right:node.left;
			}
		}
		return node;
	}
	
	public void remove(K key){
		root = remove(key,root);
	}
	
	public void remove(K key,V value){
		root = remove(key,value,root);
	}
}

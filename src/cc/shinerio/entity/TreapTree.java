package cc.shinerio.entity;

import java.util.HashSet;

public class TreapTree<K extends Comparable<K>,V> {
	public TreapNode<K,V> root;

	public void add(K key,V value){
		root = add(key,value,root);
	}

	public TreapNode<K,V> add(K key,V value,TreapNode<K,V> node){
		if(node==null){
			node = new TreapNode<>(key,value,null,null);
		}else if(node.key.compareTo(key)>0){
			node.left=add(key,value,node.left);
			//�ж����ȼ�,��С��
			if(node.left.priority<node.priority){
				//����
				node = rotateRight(node);
			}
		}else if(node.key.compareTo(key)<0){
			node.right=add(key,value,node.right);
			//�ж����ȼ�,��С��
			if(node.right.priority<node.priority){
				//����
				node = rotateLeft(node);
			}
		}else{
			node.attach.add(value);
		}
		return node;
	}
	
	public TreapNode<K,V> rotateLeft(TreapNode<K,V> node){
		TreapNode<K,V> top = node.right;
		node.right = top.left;
		top.left = node;
		return top;
	}
	
	
	public TreapNode<K,V> rotateRight(TreapNode<K,V> node){
		TreapNode<K,V> top = node.left;
		node.left = top.right;
		top.right = node;
		return top;
	}
	
	public TreapNode<K,V> findMax(){
		if(root==null){
			return null;
		}
		return findMax(root);
	}
	
	public TreapNode<K,V> findMax(TreapNode<K,V> node){
		if(node.right!=null){
			return  findMax(node.right);
		}
		return node;
	}
	
	public TreapNode<K, V> findMin(){
		if(root==null){
			return null;
		}
		return findMin(root);
	}
	
	public TreapNode<K,V> findMin(TreapNode<K, V> node){
		if(node.left!=null){
			return findMin(node.left);
		}
		return node;
	}
	
	public boolean Contain(K key){
		return Contain(key,root);
	}
	
	private boolean Contain(K key, TreapNode<K, V> node) {
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
	
	public HashSet<V> searchRange(K min, K max) {
		HashSet<V> result = new HashSet<>();
		if(root==null){
			return null;
		}
		result = searchRange(min, max, result, root);
		return result;
	}

	public HashSet<V> searchRange(K min, K max, HashSet<V> result,
			TreapNode<K, V> node) {
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
	
	public void remove(K key,V value){
		root = remove(key,value,root);
	}
	
	public TreapNode<K, V> remove(K key,V value,TreapNode<K, V> node){
		if(node==null){
			return null;
		}else if(node.key.compareTo(key)>0){
			node.left=remove(key, value, node.left);
		}else if(node.key.compareTo(key)<0){
			node.right=remove(key, value,node.right);
		}else{
			if(node.attach.size()>1){
				node.attach.remove(value);
			}else if(node.right!=null&&node.left!=null){
				if(node.right.priority>node.left.priority){  //�Һ��Ӵ������ӣ�����
					node = rotateRight(node);
				}else{
					node = rotateLeft(node);
				}
				node = remove(key,value,node);
			}else{
				node=node.left==null?node.right:node.left;
			}
		}
		return node;
	}
	
	
}

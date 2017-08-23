package cc.shinerio.entity;

import java.util.HashSet;

public class AVLTree<K extends Comparable<K>, V> {
	public AVLNode<K, V> root;
	
	public void add(K key, V value) {
		if(key==null){
			return;
		}
		if(root==null){
			root = new AVLNode<K, V>(key, value, null, null);
		}else{
			root = add(key, value, root);
		}
	}
	
	public AVLNode<K, V> add(K key, V value, AVLNode<K, V> node) {
		if(node==null){
			node = new AVLNode<>(key,value,null,null);
		}else if(node.key.compareTo(key)>0){   //������
			node.left = add(key,value,node.left);
			//ʧ����Ҫ����
			if(getHeight(node.left) - getHeight(node.right)==2){
				if(node.left.key.compareTo(key)>0){  //���������������Ҫ����
					node = rotateRight(node);
				}else{                             //���������������Ҫ�ȶ�����������������Ȼ��������
					 node.left = rotateLeft(node.left);
					 node = rotateRight(node);
				}
			}
		}else if(node.key.compareTo(key)<0){    //������
			node.right = add(key,value,node.right);
			//ʧ����Ҫ����
			if(getHeight(node.right) - getHeight(node.left)==2){
				if(node.right.key.compareTo(key)<0){  //���������������Ҫ����
					node = rotateLeft(node);
				}else{                             //���������������Ҫ�ȶ�����������������Ȼ��������
					 node.right = rotateRight(node.right);
					 node = rotateLeft(node);
				}
			}
		}else{
			node.attach.add(value);   //���������ֵ
		}
		//����ڵ�߶�,������������ļ�1��ҵ�ڵ�����0
		node.height = Math.max(getHeight(node.right), getHeight(node.left))+1; 
		return node;
	}
	/**
	 * ��ת������Ϊ����ʱ�Զ����е���,���ɵ���ʹ��
	 * @param node ƽ�����Ӿ���ֵ���ڶ��ĵ�
	 * @return �������ֶ������ĸ��ڵ�
	 */
	private AVLNode<K, V> rotateLeft(AVLNode<K, V> node){
		AVLNode<K,V> top = node.right;  //��ת����ڵ�
		node.right = top.left;  //�Ͽ�������
		top.left = node;
		//���¼���ڵ�߶�
		node.height = Math.max(getHeight(node.right), getHeight(node.left))+1; 
		top.height = Math.max(getHeight(top.right), getHeight(top.left))+1; 
		return top;
	}
	
	private AVLNode<K, V> rotateRight(AVLNode<K, V> node){
		AVLNode<K, V> top = node.left;
		node.left = top.right;
		top.right = node;
		//���¼���ڵ�߶�
		node.height = Math.max(getHeight(node.right), getHeight(node.left))+1; 
		top.height = Math.max(getHeight(top.right), getHeight(top.left))+1; 
		return top;
	}
	
	public int getHeight(AVLNode<K, V> node){
		return node==null?-1:node.height;
	}
	
	public boolean contain(K key){
		return contain(key,root);
	}
	
	public boolean contain(K key,AVLNode<K, V> node){
		if(node==null){
			return false;
		}
		if(node.key.compareTo(key)>0){
			return contain(key,node.left);
		}
		if(node.key.compareTo(key)<0){
			return contain(key,node.right);
		}
		return true;
	}
	
	public AVLNode<K, V> findMax(){
		if(root==null){
			return null;
		}
		return findMax(root);
	}
	
	public AVLNode<K, V> findMax(AVLNode<K, V> node){
		if(node.right!=null){
			return findMax(node.right);
		}
		return node;
	}
	
	public AVLNode<K,V> findMin(){
		if(root==null){
			return null;
		}
		return findMin(root);
	}
	
	public AVLNode<K, V> findMin(AVLNode<K, V> node){
		if(node.left!=null){
			return findMin(node.left);
		}
		return node;
	}
	
	public HashSet<V> searchRange(K min,K max){
		HashSet<V> result = new HashSet<>();
		if(root==null){
			return null;
		}else{
			return searchRange(min,max,result,root);
		}
	}
	
	public HashSet<V> searchRange(K min,K max,HashSet<V> result,AVLNode<K, V> node){
		if(node==null){
			return result;
		}
		//�ȱ���������
		if(node.key.compareTo(max)<0){
			searchRange(min, max, result, node.right);
			if(node.key.compareTo(min)>0){
				result.addAll(node.attach);
			}else{                     //��������ֵС����Сֵ��ֱ�ӷ��أ�������û��Ҫ����
				return result;
			}
		}
		//����������
		searchRange(min, max, result, node.left);
		return result;
	}
	
	public void remove(K key){
		root = remove(key,root);
	}
	
	public AVLNode<K, V> remove(K key,AVLNode<K, V> node){
		if(node==null){
			return node;
		}
		if(node.key.compareTo(key)>0){
		  node.left =remove(key,node.left);
			if(getHeight(node.right) - getHeight(node.left)==2){
				if(node.right.key.compareTo(key)<0){  //���������������Ҫ����
					node = rotateLeft(node);
				}else{                             //���������������Ҫ�ȶ�����������������Ȼ��������
					 node.right = rotateRight(node.right);
					 node = rotateLeft(node);
				}
			}
		}else if(node.key.compareTo(key)<0){
		  node.right = remove(key,node.right);
			//ʧ����Ҫ����
			if(getHeight(node.left) - getHeight(node.right)==2){
				if(node.left.key.compareTo(key)>0){  //���������������Ҫ����
					node = rotateRight(node);
				}else{                             //���������������Ҫ�ȶ�����������������Ȼ��������
					 node.left = rotateLeft(node.left);
					 node = rotateRight(node);
				}
			}
		}else{
			if(node.attach.size()>1){
				node.attach.remove(key);                    
			}
			if(node.left!=null&&node.right!=null){
				AVLNode<K, V> replace = findMin(node.right);
				node.key = replace.key;
				node.attach = replace.attach;
				node.right = remove(node.key,node.right);   //���ձ�ɾ���Ľڵ���Ӷ������������ʧ������
 			}else{//ֻ��һ�����ӽڵ��Ҷ�ڵ㣬ֱ���滻����
 				node=node.left== null?node.right:node.left;
 				if(node==null){   //ҵ�ڵ㣬�����ټ���߶�
 					return null;
 				}
 			}
		}
		//���¼���ڵ�߶�
		node.height = Math.max(getHeight(node.right), getHeight(node.left))+1; 
		return node;
	}
}

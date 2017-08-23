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
		}else if(node.key.compareTo(key)>0){   //左子树
			node.left = add(key,value,node.left);
			//失衡需要调整
			if(getHeight(node.left) - getHeight(node.right)==2){
				if(node.left.key.compareTo(key)>0){  //属于左左情况，需要右旋
					node = rotateRight(node);
				}else{                             //属于左右情况，需要先对左子树进行左旋，然后在右旋
					 node.left = rotateLeft(node.left);
					 node = rotateRight(node);
				}
			}
		}else if(node.key.compareTo(key)<0){    //右子树
			node.right = add(key,value,node.right);
			//失衡需要调整
			if(getHeight(node.right) - getHeight(node.left)==2){
				if(node.right.key.compareTo(key)<0){  //属于右右情况，需要左旋
					node = rotateLeft(node);
				}else{                             //属于右左情况，需要先对右子树进行右旋，然后在左旋
					 node.right = rotateRight(node.right);
					 node = rotateLeft(node);
				}
			}
		}else{
			node.attach.add(value);   //附加域添加值
		}
		//计算节点高度,左右子树最深的加1，业节点树高0
		node.height = Math.max(getHeight(node.right), getHeight(node.left))+1; 
		return node;
	}
	/**
	 * 旋转方法因为插入时自动进行调整,不可单独使用
	 * @param node 平衡因子绝对值大于二的点
	 * @return 调整部分二叉树的根节点
	 */
	private AVLNode<K, V> rotateLeft(AVLNode<K, V> node){
		AVLNode<K,V> top = node.right;  //旋转后根节点
		node.right = top.left;  //断开右子树
		top.left = node;
		//重新计算节点高度
		node.height = Math.max(getHeight(node.right), getHeight(node.left))+1; 
		top.height = Math.max(getHeight(top.right), getHeight(top.left))+1; 
		return top;
	}
	
	private AVLNode<K, V> rotateRight(AVLNode<K, V> node){
		AVLNode<K, V> top = node.left;
		node.left = top.right;
		top.right = node;
		//重新计算节点高度
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
		//先遍历右子树
		if(node.key.compareTo(max)<0){
			searchRange(min, max, result, node.right);
			if(node.key.compareTo(min)>0){
				result.addAll(node.attach);
			}else{                     //右子树有值小于最小值，直接返回，左子树没必要遍历
				return result;
			}
		}
		//遍历左子树
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
				if(node.right.key.compareTo(key)<0){  //属于右右情况，需要左旋
					node = rotateLeft(node);
				}else{                             //属于右左情况，需要先对右子树进行右旋，然后在左旋
					 node.right = rotateRight(node.right);
					 node = rotateLeft(node);
				}
			}
		}else if(node.key.compareTo(key)<0){
		  node.right = remove(key,node.right);
			//失衡需要调整
			if(getHeight(node.left) - getHeight(node.right)==2){
				if(node.left.key.compareTo(key)>0){  //属于左左情况，需要右旋
					node = rotateRight(node);
				}else{                             //属于左右情况，需要先对左子树进行左旋，然后在右旋
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
				node.right = remove(node.key,node.right);   //最终被删除的节点的子二叉树不会出现失衡问题
 			}else{//只有一个孩子节点或叶节点，直接替换就行
 				node=node.left== null?node.right:node.left;
 				if(node==null){   //业节点，不用再计算高度
 					return null;
 				}
 			}
		}
		//重新计算节点高度
		node.height = Math.max(getHeight(node.right), getHeight(node.left))+1; 
		return node;
	}
}

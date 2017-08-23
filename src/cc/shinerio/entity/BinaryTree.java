package cc.shinerio.entity;

import java.util.HashSet;
/*
 * 中序遍历得到从小到大有序序列
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
		// 右子树
		else if (key.compareTo(node.key) > 0) {
			node.right=add(key, value, node.right);
		}
		// 左子树
		else if (key.compareTo(node.key) < 0) {
			node.left=add(key, value, node.left);
		}
		// 等于当前节点
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
		// 遍历右子树，最大值要大于节点才有必要遍历其右子树
		if (max.compareTo(node.key) >0) { 
			searchRange(min, max, result, node.right);
			// 找到上界,判断是否满足下界，满足就添加
			 if (min.compareTo(node.key) < 0) {
				result.addAll(node.attach);
			}else{
				return result;     //右子树出现有值小于最小值，左子树没有必要遍历，直接返回
			}
		}
		//遍历左子树，只有最小值小于当前节点才有必要遍历左子树
		searchRange(min, max, result, node.left);
		return result;
	}
	
	public boolean Contain(K key){
		return Contain(key,root);
	}
	
	private boolean Contain(K key, BinaryNode<K, V> node) {
		if (node == null)
			return false;
		// 左子树
		if (key.compareTo(node.key) < 0)
			return Contain(key, node.left);
		// 右子树
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
			//不止一个值
			if(node.attach.size()>1){
				node.attach.remove(value);
			}//多个孩子节点
			else if(node.right!=null&&node.left!=null){  //此处使用右子树最小节点来替换，频繁删除会出现二叉树失衡，左子树特别大
				BinaryNode<K, V> replace = findMin(node.right);   //一定没有左子树  
				node.key=replace.key;
				node.attach = replace.attach;
				remove(node.key,node.right);
			}//单个孩子节点
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
			if(node.right!=null&&node.left!=null){  //此处使用右子树最小节点来替换，频繁删除会出现二叉树失衡，左子树特别大
				BinaryNode<K, V> replace = findMin(node.right);   //一定没有左子树  
				node.key=replace.key;
				node.attach = replace.attach;
				remove(node.key,node.right);
			}//单个孩子节点或叶节点
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

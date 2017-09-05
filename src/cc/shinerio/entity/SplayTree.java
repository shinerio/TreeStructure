package cc.shinerio.entity;

public class SplayTree<T extends Comparable<T>> {
	public SplayNode<T> root;

	private SplayNode<T> splay(SplayNode<T> node, T key) {
		if (node == null) {
			return null;
		}
		if (key.compareTo(node.key) < 0) {
			node.left = splay(node.left, key);
			node = rotateRight(node);
		} else if (key.compareTo(node.key) > 0) {
			node.right = splay(node.right, key);
			node = rotateLeft(node);
		} else {
			return node;
		}
		return node;
	}

	private SplayNode<T> rotateLeft(SplayNode<T> node) {
		SplayNode<T> top = node.right; // 旋转后根节点
		node.right = top.left; // 断开右子树
		top.left = node;
		return top;
	}

	private SplayNode<T> rotateRight(SplayNode<T> node) {
		SplayNode<T> top = node.left;
		node.left = top.right;
		top.right = node;
		return top;
	}

	public void add(T key) {
		if (key == null) {
			return;
		}
		root = add(key, root);
	}

	private SplayNode<T> add(T key, SplayNode<T> node) {
		if (node == null) {
			node = new SplayNode<>(key, null, null);
		} else if (node.key.compareTo(key) > 0) { // 左子树
			node.left = add(key, node.left);
		} else if (node.key.compareTo(key) < 0) { // 右子树
			node.right = add(key, node.right);
		}
		return node;
	}

	public SplayNode<T> search(T data) {
		return search(root, data);
	}

	private SplayNode<T> search(SplayNode<T> node, T data) {
		if (node == null) {
			return null;
		}
		int result = node.key.compareTo(data);
		if (result > 0) {
			return search(node.left, data);
		} else if (result < 0) {
			return search(node.right, data);
		} else {
			// 找到目标节点，进行伸展，使其成为根节点
			root = splay(root, data);
			return node;
		}
	}

	
    /**
     * 删除时，进行伸展：
     * a. 找到删除的节点
     * b. 对删除的节点进行旋转，使其成为根节点
     * c. 删除该节点后，问题是如何将左右子树进行拼接？
     * (1) 若左子树不为空，则找到左子树中的最大值，因为左子树的最大值节点没有右子树
     * (1.1) 将选中的最大值节点进行旋转，使其成为根节点
     * (1.2) 将原来的右子树拼接过来
     * (2) 若左子树为空，则右子树直接成为完整的树
     * @param data
     * @return
     */
    public SplayNode<T> remove(T data){
        SplayNode<T> newRoot, removeRoot;
        if (root == null){
            return null;
        }
        //找到要删除的节点
        removeRoot = search(root, data);
        //若没找到，则返回空
        if (removeRoot == null){
            return null;
        }
        //对要删除的节点旋转成为根节点
        root = splay(root, data);
        //左边不为空
        if (root.left != null){
            //找到左子树的最大节点值作为根节点，因为其没有右孩子存在
            newRoot = splay(root.left, findMax(root.left).key);
            //右子树直接赋值
            newRoot.right = root.right;
        }
        //否则直接赋值右子树
        else {
            newRoot = root.right;
        }
        //更新树
        root = newRoot;
        //返回删除的节点
        return removeRoot;
    }
    
    public SplayNode<T> findMax(){
		if(root==null){
			return null;
		}
		return findMax(root);
	}
	
	public SplayNode<T> findMax(SplayNode<T> node){
		if(node.right!=null){
			return findMax(node.right);
		}
		return node;
	}
	
	public SplayNode<T> findMin(){
		if(root==null){
			return null;
		}
		return findMin(root);
	}
	
	public SplayNode<T> findMin(SplayNode<T> node){
		if(node.left!=null){
			return findMin(node.left);
		}
		return node;
	}
	
}

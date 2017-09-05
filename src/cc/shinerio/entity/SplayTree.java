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
		SplayNode<T> top = node.right; // ��ת����ڵ�
		node.right = top.left; // �Ͽ�������
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
		} else if (node.key.compareTo(key) > 0) { // ������
			node.left = add(key, node.left);
		} else if (node.key.compareTo(key) < 0) { // ������
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
			// �ҵ�Ŀ��ڵ㣬������չ��ʹ���Ϊ���ڵ�
			root = splay(root, data);
			return node;
		}
	}

	
    /**
     * ɾ��ʱ��������չ��
     * a. �ҵ�ɾ���Ľڵ�
     * b. ��ɾ���Ľڵ������ת��ʹ���Ϊ���ڵ�
     * c. ɾ���ýڵ����������ν�������������ƴ�ӣ�
     * (1) ����������Ϊ�գ����ҵ��������е����ֵ����Ϊ�����������ֵ�ڵ�û��������
     * (1.1) ��ѡ�е����ֵ�ڵ������ת��ʹ���Ϊ���ڵ�
     * (1.2) ��ԭ����������ƴ�ӹ���
     * (2) ��������Ϊ�գ���������ֱ�ӳ�Ϊ��������
     * @param data
     * @return
     */
    public SplayNode<T> remove(T data){
        SplayNode<T> newRoot, removeRoot;
        if (root == null){
            return null;
        }
        //�ҵ�Ҫɾ���Ľڵ�
        removeRoot = search(root, data);
        //��û�ҵ����򷵻ؿ�
        if (removeRoot == null){
            return null;
        }
        //��Ҫɾ���Ľڵ���ת��Ϊ���ڵ�
        root = splay(root, data);
        //��߲�Ϊ��
        if (root.left != null){
            //�ҵ������������ڵ�ֵ��Ϊ���ڵ㣬��Ϊ��û���Һ��Ӵ���
            newRoot = splay(root.left, findMax(root.left).key);
            //������ֱ�Ӹ�ֵ
            newRoot.right = root.right;
        }
        //����ֱ�Ӹ�ֵ������
        else {
            newRoot = root.right;
        }
        //������
        root = newRoot;
        //����ɾ���Ľڵ�
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

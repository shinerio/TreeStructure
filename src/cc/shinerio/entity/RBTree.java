package cc.shinerio.entity;

/**
 * �ǵݹ�棬ÿ���ڵ���Ҫ���и��ڵ�
 * 
 * @author shinerio
 * 
 * @param <T>
 */
public class RBTree<T extends Comparable<T>> {
	private RBNode<T> root;

	private static final boolean RED = false;
	private static final boolean BLACK = true;

	/*
	 * �������ÿ���ڵ��϶��д洢λ��ʾ�ڵ����ɫ����ɫ�Ǻ�(Red)���(Black)�� �����������: (1) ÿ���ڵ�����Ǻ�ɫ�������Ǻ�ɫ��
	 * (2) ���ڵ��Ǻ�ɫ�� (3) ÿ��Ҷ�ӽڵ��Ǻ�ɫ�� [ע�⣺����Ҷ�ӽڵ㣬��ָΪ�յ�Ҷ�ӽڵ㣡]
	 * (4)���һ���ڵ��Ǻ�ɫ�ģ��������ӽڵ�����Ǻ�ɫ�ġ� (5) ��һ���ڵ㵽�ýڵ������ڵ������·���ϰ�����ͬ��Ŀ�ĺڽڵ㡣
	 */
	@SuppressWarnings("hiding")
	public class RBNode<T> {
		boolean color; // ��ɫ
		T key; // �ؼ���(��ֵ)
		RBNode<T> left; // ����
		RBNode<T> right; // �Һ���
		RBNode<T> parent; // �����

		public RBNode(T key, boolean color, RBNode<T> parent, RBNode<T> left,
				RBNode<T> right) {
			this.key = key;
			this.color = color;
			this.parent = parent;
			this.left = left;
			this.right = right;
		}

		public RBNode<T> getParent() {
			return this.parent;
		}
		
		public RBNode<T> getBrother(){
			if(parent==null){
				return null;
			}
			if(parent.left==this){
				return parent.right;
			}else{
				return parent.left;
			}
		}
		
		public T getKey(){
			return key;
		}
		
		public String getColor(){
			return color==RED?"R":"B";
		}
	}

	private void rotateLeft(RBNode<T> node) {
		if (node == null) {
			return;
		}
		RBNode<T> top = node.right; // ��ת����ڵ�
		node.right = top.left; // �Ͽ�������
		if (top.left != null) {
			top.left.parent = node;
		}
		top.left = node;
		top.parent = node.parent;
		if (node.parent == null) {// ���ڵ�
			root = top;
		} else {
			if (node.parent.left == node)
				node.parent.left = top; // ���node�������ڵ�����ӣ���top��Ϊ��x�ĸ��ڵ�����ӡ�
			else
				node.parent.right = top; // ���node�������ڵ���Һ��ӣ���top��Ϊ��node�ĸ��ڵ���Һ��ӡ�
		}
		node.parent = top;
	}

	private void rotateRight(RBNode<T> node) {
		if (node == null) {
			return;
		}
		RBNode<T> top = node.left; // ��ת����ڵ�
		node.left = top.right; // �Ͽ�������
		if (top.right != null) {
			top.right.parent = node;
		}
		top.right = node;
		top.parent = node.parent;
		if (node.parent == null) {// ���ڵ�
			root = top;
		} else {
			if (node.parent.left == node)
				node.parent.left = top; // ���node�������ڵ�����ӣ���top��Ϊ��x�ĸ��ڵ�����ӡ�
			else
				node.parent.right = top; // ���node�������ڵ���Һ��ӣ���top��Ϊ��node�ĸ��ڵ���Һ��ӡ�
		}
		node.parent = top;
	}

	/*
	 * ��һ��: �����������һ�Ŷ�������������ڵ���롣
	 * ������������һ�Ŷ�������������ڵ����󣬸�����Ȼ��һ�Ŷ����������Ҳ����ζ�ţ����ļ�ֵ��Ȼ�������
	 * �����⣬������������������������ת֮ǰ������Ƕ��������
	 * ����ת֮����һ�����Ƕ������������Ҳ����ζ�ţ��κε���ת��������ɫ������������ı�����Ȼ��һ�Ŷ������������ʵ��
	 * �ðɣ��ǽ����������Ǿ����뷽�跨����ת�Լ�������ɫ��ʹ��������³�Ϊ�������
	 * 
	 * �ڶ�����������Ľڵ���ɫΪ"��ɫ"�� Ϊʲô��ɫ�ɺ�ɫ�������Ǻ�ɫ�أ�Ϊʲô�أ��ڻش�֮ǰ��������Ҫ������ϰһ�º���������ԣ� (1)
	 * ÿ���ڵ�����Ǻ�ɫ�������Ǻ�ɫ�� (2) ���ڵ��Ǻ�ɫ�� (3) ÿ��Ҷ�ӽڵ��Ǻ�ɫ�� [ע�⣺����Ҷ�ӽڵ㣬��ָΪ�յ�Ҷ�ӽڵ㣡] (4)
	 * ���һ���ڵ��Ǻ�ɫ�ģ��������ӽڵ�����Ǻ�ɫ�ġ� (5) ��һ���ڵ㵽�ýڵ������ڵ������·���ϰ�����ͬ��Ŀ�ĺڽڵ㡣
	 * ������Ľڵ���ɫΪ��ɫ������Υ��
	 * "����(5)"����Υ��һ�����ԣ�����ζ��������Ҫ��������Խ�١�����������ҪŬ����������������������ʼ��ɣ������˵Ļ� ����������һ�ź������
	 * 
	 * ������: ͨ��һϵ�е���ת����ɫ�Ȳ�����ʹ֮���³�Ϊһ�ź������
	 * �ڶ����У�������ڵ���ɫΪ"��ɫ"֮�󣬲���Υ��"����(5)"���������׻�Υ����Щ�����أ�
	 * ����"����(1)"����Ȼ����Υ���ˡ���Ϊ�����Ѿ�����Ϳ�ɺ�ɫ�ˡ�
	 * ����"����(2)"����ȻҲ����Υ�����ڵ�һ���У������ǽ���������������������Ȼ��ִ�еĲ������
	 * �������ݶ�����������ص㣬�����������ı���ڵ㡣���ԣ����ڵ���Ȼ�Ǻ�ɫ��
	 * ����"����(3)"����Ȼ����Υ���ˡ������Ҷ�ӽڵ���ָ�Ŀ�Ҷ�ӽڵ㣬����ǿսڵ㲢������������Ӱ�졣 ����"����(4)"�����п���Υ���ģ�
	 * �ǽ���������취ʹ֮"��������(4)"���Ϳ��Խ������¹���ɺ�����ˡ�
	 */
	public void insert(T key) {
		int cmp;
		RBNode<T> temp = null; // ���������ĵ�
		RBNode<T> iterator = this.root;
		while (iterator != null) {
			temp = iterator;
			cmp = key.compareTo(iterator.key);
			if (cmp > 0) {
				iterator = iterator.right;
			} else if (cmp < 0) {
				iterator = iterator.left;
			} else {
				return; // �Ѵ���
			}
		}
		// �½��ڵ�Ϊ��ɫ
		RBNode<T> node = new RBNode<T>(key, RED, null, null, null);
		node.parent = temp;
		if (temp == null) { // ���ڵ�Ϊnull
			root = node;
		} else {
			cmp = key.compareTo(temp.key);
			if (cmp < 0) {
				temp.left = node;
			} else {
				temp.right = node;
			}
		}
		fixupTree(node);
	}

	// ֻ����Υ����2������4��,���У�2��ֻ��Ҫ�����ڵ���Ϊ��ɫ����
	private void fixupTree(RBNode<T> node) {
		RBNode<T> parent, gparent, uncle;
		while (node!=null&&(parent = node.parent) != null && parent.color == RED&&parent!=root) {// ���ڵ������Ϊ��ɫ��������游�ڵ�һ����Ϊ��
			gparent = parent.getParent();
			if (parent == gparent.left) { // ������ڵ����游�ڵ������
				uncle = gparent.right;
				if (uncle != null && uncle.color == RED) { // ����ڵ��Ǻ�ɫ
					uncle.color = BLACK;
					gparent.color = RED;
					parent.color = BLACK;
					node = gparent;
					continue;
				} else if (node == parent.right) { // ����ڵ��Ǻ�ɫ����ǰ�ڵ����Һ���
					RBNode<T> tmp;
					rotateLeft(parent);
					tmp = parent;
					parent = node;
					node = tmp;
				}
				// ����ڵ��Ǻ�ɫ����ǰ�ڵ�������
				parent.color = BLACK;
				gparent.color = RED;
				rotateRight(gparent);
				node = gparent.right;
			} else {
					uncle = gparent.left;
					if (uncle != null && uncle.color == RED) { // ����ڵ��Ǻ�ɫ
						uncle.color = BLACK;
						gparent.color = RED;
						parent.color = BLACK;
						node = gparent;
						continue;
					} else if (node == parent.left) { // ����ڵ��Ǻ�ɫ����ǰ�ڵ�������
						RBNode<T> tmp;
						rotateRight(parent);
						tmp = parent;
						parent = node;
						node = tmp;
					}
					// ����ڵ��Ǻ�ɫ����ǰ�ڵ����Һ���
					parent.color = BLACK;
					gparent.color = RED;
					rotateLeft(gparent);
					node = gparent.left;
			}
		}
		root.color = BLACK;
	}
	
	public RBNode<T> search(T key){
		RBNode<T> node = root;
		while(node!=null){
			int cmp = key.compareTo(node.key);
			if(cmp>0){
				node = node.right;
			}else if(cmp<0){
				node = node.left;
			}else{
				return node;
			}
		}
		return node;
	}
	
	public void remove(T key){
		RBNode<T> node = search(key);
		if(node==null){
			return;
		}
		if(node.left!=null&&node.right!=null){   //�����ӽڵ㶼��Ϊ��
			RBNode<T> replace = findMinChild(node.right);  //ȡ���ڵ�һ�����������ӣ�֮���ֱ�����Һ����滻
			T replaceKey = replace.key;
			//Ȼ��ɾ��replace
			remove(replace.key);
			node.key = replaceKey;
		}else if(node.left==null&&node.right==null){
			if(node.color==RED){
				if(node==node.parent.left){
					node.parent.left =null;
				}else{
					node.parent.right =null;
				}
			}else{                    //��ɫ�ڵ㣬�޺��ӣ����������Ҫ�������е���
				if(node.parent==null){  //���ڵ�
					root =null;
					return;
				}
				removeFixUp(node);
				if(node==node.parent.left){
					node.parent.left =null;
				}else{
					node.parent.right =null;
				}
			}
		}else{
			if(node.right!=null){
				RBNode<T> replace = node.right; 
				node.key = replace.key;
				node.right = null;
			}else{
				RBNode<T> replace = node.left; 
				node.key = replace.key;
				node.left = null;
			}
		}
	}

	private void removeFixUp(RBNode<T> node) {
		if(node.parent==null){
			return;
		}
		if(node.parent.color==RED&&node.getBrother().color==BLACK){  //���ڵ��ɫ���ֵܽڵ�Ϊ��ɫ
			if(node==node.parent.left){
				rotateLeft(node.parent);
			}else{
				rotateRight(node.parent);				
			}  //����������ṹ�Ѿ�ƽ�⣬����ݹ�
		}else if(node.parent.color==BLACK&&node.getBrother().color==RED){  //���ڵ��ɫ���ֵܽڵ�Ϊ��ɫ,���ӽڵ��Ϊ��ɫ
			node.getBrother().color =BLACK;
			if(node==node.parent.left){//�ֵܽڵ㺢�ӽڵ��Ϊ��ɫ���ﵽ�������Ŀ�ģ��������ʵ��������������
				rotateLeft(node.parent);
				node.parent.right.color=RED;   
				if(node.parent.right.left!=null&&node.parent.right.left.color==RED){
					fixupTree(node.parent.right.left);
				}
				if(node.parent.right.right!=null&&node.parent.right.right.color==RED){
					fixupTree(node.parent.right.right);
				}
			}else{
				rotateRight(node.parent);
				node.parent.left.color=RED;
				if(node.parent.left.left!=null&&node.parent.left.left.color==RED){
					fixupTree(node.parent.left.left);
				}
				if(node.parent.left.right!=null&&node.parent.left.right.color==RED){
					fixupTree(node.parent.left.right);
				}
			}
		}else{   //���ڵ�Ϊ��ɫ���ֵܽڵ�Ϊ��ɫ
			RBNode<T> brother = node.getBrother();   
			brother.color=RED;   
			removeFixUp(node.parent);   //��ͬ��ɾ����ɫ���ڵ�
			if(brother.left!=null&&brother.left.color==RED){   //��ֹ�ֵܽڵ��������������ɫ����
				fixupTree(brother.left);
			}
			if(brother.right!=null&&brother.right.color==RED){
				fixupTree(brother.right);
			}
		}
	}

	/**
	 * true for left
	 * flase for right
	 */
	public boolean belongTO(RBNode<T> node){
		if(node==root){
			return true;
		}
		while(node.parent!=root){
			node = node.parent;
		}
		if(root.parent.left==node){
			return true;
		}else{
			return false;
		}
	}
	
	public RBNode<T> findMax(){
		return findMaxChild(root);
	}
	
	public RBNode<T> findMaxChild(RBNode<T> node){
		RBNode<T> max = node;
		while(node!=null){
			max = node;
			node = node.right;
		}
		return max;
	}

	
	public RBNode<T> findMin(){
		return findMinChild(root);
	}
	
	public RBNode<T> findMinChild(RBNode<T> node){
		RBNode<T> min = node;
		while(node!=null){
			min = node;
			node = node.left;
		}
		return min;
	}
	

    /*
     * ��ӡ"�����"
     *
     * key        -- �ڵ�ļ�ֵ 
     * direction  --  0����ʾ�ýڵ��Ǹ��ڵ�;
     *               -1����ʾ�ýڵ������ĸ���������;
     *                1����ʾ�ýڵ������ĸ������Һ��ӡ�
     */
    private void print(RBNode<T> node, T key, int direction) {

        if(node != null) {

            if(direction==0)    // node�Ǹ��ڵ�
                System.out.printf("%2d(B) is root\n", node.key);
            else                // node�Ƿ�֧�ڵ�
                System.out.printf("%2d(%s) is %2d's %6s child\n", node.key, node.getColor(), key, direction==1?"right" : "left");

            print(node.left, node.key, -1);
            print(node.right,node.key,  1);
        }
    }

    public void print() {
        if (root != null)
            print(root, root.key, 0);
    }
    /*
     * ���ٺ����
     */
    private void destroy(RBNode<T> tree) {
        if (tree==null)
            return ;

        if (tree.left != null)
            destroy(tree.left);
        if (tree.right != null)
            destroy(tree.right);

        tree=null;
    }

    public void clear() {
        destroy(root);
        root = null;
    }

    /* 
     * �ҽ��(x)�ĺ�̽�㡣��������"�����������ֵ���ڸý��"��"��С���"��
     */
    public RBNode<T> successor(RBNode<T> x) {
        // ���x�����Һ��ӣ���"x�ĺ�̽��"Ϊ "�����Һ���Ϊ������������С���"��
        if (x.right != null)
            return findMinChild(x.right);

        // ���xû���Һ��ӡ���x���������ֿ��ܣ�
        // (01) x��"һ������"����"x�ĺ�̽��"Ϊ "���ĸ����"��
        // (02) x��"һ���Һ���"�������"x����͵ĸ���㣬���Ҹø����Ҫ��������"���ҵ������"��͵ĸ����"����"x�ĺ�̽��"��
        RBNode<T> y = x.parent;
        while ((y!=null) && (x==y.right)) {
            x = y;
            y = y.parent;
        }

        return y;
    }
     
    /* 
     * �ҽ��(x)��ǰ����㡣��������"�����������ֵС�ڸý��"��"�����"��
     */
    public RBNode<T> predecessor(RBNode<T> x) {
        // ���x�������ӣ���"x��ǰ�����"Ϊ "��������Ϊ���������������"��
        if (x.left != null)
            return findMaxChild(x.left);

        // ���xû�����ӡ���x���������ֿ��ܣ�
        // (01) x��"һ���Һ���"����"x��ǰ�����"Ϊ "���ĸ����"��
        // (01) x��"һ������"�������"x����͵ĸ���㣬���Ҹø����Ҫ�����Һ���"���ҵ������"��͵ĸ����"����"x��ǰ�����"��
        RBNode<T> y = x.parent;
        while ((y!=null) && (x==y.left)) {
            x = y;
            y = y.parent;
        }

        return y;
    }
    
    /*
     * ǰ�����"�����"
     */
    private void preOrder(RBNode<T> tree) {
        if(tree != null) {
            System.out.print(tree.key+" ");
            preOrder(tree.left);
            preOrder(tree.right);
        }
    }

    public void preOrder() {
        preOrder(root);
    }

    /*
     * �������"�����"
     */
    private void inOrder(RBNode<T> tree) {
        if(tree != null) {
            inOrder(tree.left);
            System.out.print(tree.key+" ");
            inOrder(tree.right);
        }
    }

    public void inOrder() {
        inOrder(root);
    }


    /*
     * �������"�����"
     */
    private void postOrder(RBNode<T> tree) {
        if(tree != null)
        {
            postOrder(tree.left);
            postOrder(tree.right);
            System.out.print(tree.key+" ");
        }
    }

    public void postOrder() {
        postOrder(root);
    }
}

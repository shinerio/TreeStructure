package cc.shinerio.entity;

/**
 * 非递归版，每个节点需要持有父节点
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
	 * 红黑树的每个节点上都有存储位表示节点的颜色，颜色是红(Red)或黑(Black)。 红黑树的特性: (1) 每个节点或者是黑色，或者是红色。
	 * (2) 根节点是黑色。 (3) 每个叶子节点是黑色。 [注意：这里叶子节点，是指为空的叶子节点！]
	 * (4)如果一个节点是红色的，则它的子节点必须是黑色的。 (5) 从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑节点。
	 */
	@SuppressWarnings("hiding")
	public class RBNode<T> {
		boolean color; // 颜色
		T key; // 关键字(键值)
		RBNode<T> left; // 左孩子
		RBNode<T> right; // 右孩子
		RBNode<T> parent; // 父结点

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
		RBNode<T> top = node.right; // 旋转后根节点
		node.right = top.left; // 断开右子树
		if (top.left != null) {
			top.left.parent = node;
		}
		top.left = node;
		top.parent = node.parent;
		if (node.parent == null) {// 根节点
			root = top;
		} else {
			if (node.parent.left == node)
				node.parent.left = top; // 如果node是它父节点的左孩子，则将top设为“x的父节点的左孩子”
			else
				node.parent.right = top; // 如果node是它父节点的右孩子，则将top设为“node的父节点的右孩子”
		}
		node.parent = top;
	}

	private void rotateRight(RBNode<T> node) {
		if (node == null) {
			return;
		}
		RBNode<T> top = node.left; // 旋转后根节点
		node.left = top.right; // 断开右子树
		if (top.right != null) {
			top.right.parent = node;
		}
		top.right = node;
		top.parent = node.parent;
		if (node.parent == null) {// 根节点
			root = top;
		} else {
			if (node.parent.left == node)
				node.parent.left = top; // 如果node是它父节点的左孩子，则将top设为“x的父节点的左孩子”
			else
				node.parent.right = top; // 如果node是它父节点的右孩子，则将top设为“node的父节点的右孩子”
		}
		node.parent = top;
	}

	/*
	 * 第一步: 将红黑树当作一颗二叉查找树，将节点插入。
	 * 红黑树本身就是一颗二叉查找树，将节点插入后，该树仍然是一颗二叉查找树。也就意味着，树的键值仍然是有序的
	 * 。此外，无论是左旋还是右旋，若旋转之前这棵树是二叉查找树
	 * ，旋转之后它一定还是二叉查找树。这也就意味着，任何的旋转和重新着色操作，都不会改变它仍然是一颗二叉查找树的事实。
	 * 好吧？那接下来，我们就来想方设法的旋转以及重新着色，使这颗树重新成为红黑树！
	 * 
	 * 第二步：将插入的节点着色为"红色"。 为什么着色成红色，而不是黑色呢？为什么呢？在回答之前，我们需要重新温习一下红黑树的特性： (1)
	 * 每个节点或者是黑色，或者是红色。 (2) 根节点是黑色。 (3) 每个叶子节点是黑色。 [注意：这里叶子节点，是指为空的叶子节点！] (4)
	 * 如果一个节点是红色的，则它的子节点必须是黑色的。 (5) 从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑节点。
	 * 将插入的节点着色为红色，不会违背
	 * "特性(5)"！少违背一条特性，就意味着我们需要处理的情况越少。接下来，就要努力的让这棵树满足其它性质即可；满足了的话 ，它就又是一颗红黑树了
	 * 
	 * 第三步: 通过一系列的旋转或着色等操作，使之重新成为一颗红黑树。
	 * 第二步中，将插入节点着色为"红色"之后，不会违背"特性(5)"。那它到底会违背哪些特性呢？
	 * 对于"特性(1)"，显然不会违背了。因为我们已经将它涂成红色了。
	 * 对于"特性(2)"，显然也不会违背。在第一步中，我们是将红黑树当作二叉查找树，然后执行的插入操作
	 * 。而根据二叉查找数的特点，插入操作不会改变根节点。所以，根节点仍然是黑色。
	 * 对于"特性(3)"，显然不会违背了。这里的叶子节点是指的空叶子节点，插入非空节点并不会对它们造成影响。 对于"特性(4)"，是有可能违背的！
	 * 那接下来，想办法使之"满足特性(4)"，就可以将树重新构造成红黑树了。
	 */
	public void insert(T key) {
		int cmp;
		RBNode<T> temp = null; // 最靠近被插入的点
		RBNode<T> iterator = this.root;
		while (iterator != null) {
			temp = iterator;
			cmp = key.compareTo(iterator.key);
			if (cmp > 0) {
				iterator = iterator.right;
			} else if (cmp < 0) {
				iterator = iterator.left;
			} else {
				return; // 已存在
			}
		}
		// 新建节点为红色
		RBNode<T> node = new RBNode<T>(key, RED, null, null, null);
		node.parent = temp;
		if (temp == null) { // 根节点为null
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

	// 只可能违背（2），（4）,其中（2）只需要将根节点设为黑色就行
	private void fixupTree(RBNode<T> node) {
		RBNode<T> parent, gparent, uncle;
		while (node!=null&&(parent = node.parent) != null && parent.color == RED&&parent!=root) {// 父节点存在且为红色，此情况祖父节点一定不为空
			gparent = parent.getParent();
			if (parent == gparent.left) { // 如果父节点是祖父节点的左孩子
				uncle = gparent.right;
				if (uncle != null && uncle.color == RED) { // 叔叔节点是红色
					uncle.color = BLACK;
					gparent.color = RED;
					parent.color = BLACK;
					node = gparent;
					continue;
				} else if (node == parent.right) { // 叔叔节点是黑色，当前节点是右孩子
					RBNode<T> tmp;
					rotateLeft(parent);
					tmp = parent;
					parent = node;
					node = tmp;
				}
				// 叔叔节点是黑色，当前节点是左孩子
				parent.color = BLACK;
				gparent.color = RED;
				rotateRight(gparent);
				node = gparent.right;
			} else {
					uncle = gparent.left;
					if (uncle != null && uncle.color == RED) { // 叔叔节点是红色
						uncle.color = BLACK;
						gparent.color = RED;
						parent.color = BLACK;
						node = gparent;
						continue;
					} else if (node == parent.left) { // 叔叔节点是黑色，当前节点是左孩子
						RBNode<T> tmp;
						rotateRight(parent);
						tmp = parent;
						parent = node;
						node = tmp;
					}
					// 叔叔节点是黑色，当前节点是右孩子
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
		if(node.left!=null&&node.right!=null){   //左右子节点都不为空
			RBNode<T> replace = findMinChild(node.right);  //取代节点一定不存在左孩子，之后可直接用右孩子替换
			T replaceKey = replace.key;
			//然后删除replace
			remove(replace.key);
			node.key = replaceKey;
		}else if(node.left==null&&node.right==null){
			if(node.color==RED){
				if(node==node.parent.left){
					node.parent.left =null;
				}else{
					node.parent.right =null;
				}
			}else{                    //黑色节点，无孩子，这种情况需要对树进行调整
				if(node.parent==null){  //根节点
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
		if(node.parent.color==RED&&node.getBrother().color==BLACK){  //父节点红色，兄弟节点为黑色
			if(node==node.parent.left){
				rotateLeft(node.parent);
			}else{
				rotateRight(node.parent);				
			}  //调整后该树结构已经平衡，无需递归
		}else if(node.parent.color==BLACK&&node.getBrother().color==RED){  //父节点黑色，兄弟节点为红色,孩子节点必为黑色
			node.getBrother().color =BLACK;
			if(node==node.parent.left){//兄弟节点孩子节点改为红色，达到黑子相等目的，但需调整实现无连续两个红
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
		}else{   //父节点为黑色，兄弟节点为黑色
			RBNode<T> brother = node.getBrother();   
			brother.color=RED;   
			removeFixUp(node.parent);   //等同于删除黑色父节点
			if(brother.left!=null&&brother.left.color==RED){   //防止兄弟节点出现连续两个红色孩子
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
     * 打印"红黑树"
     *
     * key        -- 节点的键值 
     * direction  --  0，表示该节点是根节点;
     *               -1，表示该节点是它的父结点的左孩子;
     *                1，表示该节点是它的父结点的右孩子。
     */
    private void print(RBNode<T> node, T key, int direction) {

        if(node != null) {

            if(direction==0)    // node是根节点
                System.out.printf("%2d(B) is root\n", node.key);
            else                // node是分支节点
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
     * 销毁红黑树
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
     * 找结点(x)的后继结点。即，查找"红黑树中数据值大于该结点"的"最小结点"。
     */
    public RBNode<T> successor(RBNode<T> x) {
        // 如果x存在右孩子，则"x的后继结点"为 "以其右孩子为根的子树的最小结点"。
        if (x.right != null)
            return findMinChild(x.right);

        // 如果x没有右孩子。则x有以下两种可能：
        // (01) x是"一个左孩子"，则"x的后继结点"为 "它的父结点"。
        // (02) x是"一个右孩子"，则查找"x的最低的父结点，并且该父结点要具有左孩子"，找到的这个"最低的父结点"就是"x的后继结点"。
        RBNode<T> y = x.parent;
        while ((y!=null) && (x==y.right)) {
            x = y;
            y = y.parent;
        }

        return y;
    }
     
    /* 
     * 找结点(x)的前驱结点。即，查找"红黑树中数据值小于该结点"的"最大结点"。
     */
    public RBNode<T> predecessor(RBNode<T> x) {
        // 如果x存在左孩子，则"x的前驱结点"为 "以其左孩子为根的子树的最大结点"。
        if (x.left != null)
            return findMaxChild(x.left);

        // 如果x没有左孩子。则x有以下两种可能：
        // (01) x是"一个右孩子"，则"x的前驱结点"为 "它的父结点"。
        // (01) x是"一个左孩子"，则查找"x的最低的父结点，并且该父结点要具有右孩子"，找到的这个"最低的父结点"就是"x的前驱结点"。
        RBNode<T> y = x.parent;
        while ((y!=null) && (x==y.left)) {
            x = y;
            y = y.parent;
        }

        return y;
    }
    
    /*
     * 前序遍历"红黑树"
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
     * 中序遍历"红黑树"
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
     * 后序遍历"红黑树"
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

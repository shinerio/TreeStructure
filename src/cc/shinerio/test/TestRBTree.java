package cc.shinerio.test;

import cc.shinerio.entity.RBTree;

public class TestRBTree {
	 private static final int a[] = {10, 40, 30, 60, 90, 70, 20, 50, 80,100,200,15,54,8474,98,154,8978,1874,14854,4,142,265,87};
	    private static final boolean mDebugInsert = false;    // "插入"动作的检测开关(false，关闭；true，打开)
	    private static final boolean mDebugDelete = true;    // "删除"动作的检测开关(false，关闭；true，打开)

	    public static void main(String[] args) {
	        int i, ilen = a.length;
	        RBTree<Integer> tree=new RBTree<Integer>();

	        System.out.printf("== 原始数据: ");
	        for(i=0; i<ilen; i++)
	            System.out.printf("%d ", a[i]);
	        System.out.printf("\n");

	        for(i=0; i<ilen; i++) {
	            tree.insert(a[i]);
	            // 设置mDebugInsert=true,测试"添加函数"
	            if (mDebugInsert) {
	                System.out.printf("== 添加节点: %d\n", a[i]);
	                System.out.printf("== 树的详细信息: \n");
	                tree.print();
	                System.out.printf("\n");
	            }
	        }

	        System.out.printf("== 前序遍历: ");
	        tree.preOrder();

	        System.out.printf("\n== 中序遍历: ");
	        tree.inOrder();

	        System.out.printf("\n== 后序遍历: ");
	        tree.postOrder();
	        System.out.printf("\n");

	        System.out.printf("== 最小值: %s\n", tree.findMin().getKey());
	        System.out.printf("== 最大值: %s\n", tree.findMax().getKey());
	        System.out.printf("== 树的详细信息: \n");
	        tree.print();
	        System.out.printf("\n");
	        System.out.println(tree.search(10).getColor());
	        // 设置mDebugDelete=true,测试"删除函数"
	        if (mDebugDelete) {
	            for(i=0; i<ilen; i++)
	            {
	                tree.remove(a[i]);

	                System.out.printf("== 删除节点: %d\n", a[i]);
	                System.out.printf("== 树的详细信息: \n");
	                tree.print();
	                System.out.printf("\n");
	            }
	        }

	        // 销毁二叉树
	        tree.clear();
	    }
}

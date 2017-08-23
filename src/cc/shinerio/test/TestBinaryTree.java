package cc.shinerio.test;


import java.util.HashSet;

import cc.shinerio.entity.BinaryTree;

public class TestBinaryTree {
	public static void main(String[] args) {
		BinaryTree<Integer, String> tree = new BinaryTree<>();
		for(int i=0;i<1000;i++){
			tree.add(i, i+"");
		}
		System.out.println(tree.Contain(0));
		tree.remove(0, "0");
		System.out.println(tree.Contain(0));
		HashSet<String> result= tree.searchRange(0, 10);
		for (String string : result) {
			System.out.println(string);
		}
		System.out.println(tree.findMin().key);
	}
}

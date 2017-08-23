package cc.shinerio.test;

import java.util.HashSet;

import cc.shinerio.entity.AVLTree;

public class TestAVLTree {
	public static void main(String[] args) {
		AVLTree<Integer, String> tree = new AVLTree<>();
		for (int i = 0; i < 1000; i++) {
			tree.add(i, i + "");
		}
		System.out.println(tree.contain(1010));
		System.out.println(tree.findMax().key);
		System.out.println(tree.findMin().key);
		HashSet<String> result= tree.searchRange(578, 589);
		for (String string : result) {
			System.out.print(string+";");
		}
		System.out.println();
		tree.remove(579);
		result= tree.searchRange(578, 589);
		for (String string : result) {
			System.out.print(string+";");
		}
		System.out.println();
	}
}

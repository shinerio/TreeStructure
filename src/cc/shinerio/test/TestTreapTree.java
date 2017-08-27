package cc.shinerio.test;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import cc.shinerio.entity.TreapTree;

public class TestTreapTree {
	public static void main(String[] args) throws InterruptedException {
		TreapTree<Integer, String> tree = new TreapTree<>();
		for(int i=0;i<1000;i++){
			TimeUnit.NANOSECONDS.sleep(1);
			tree.add(i, i+"");
		}
		System.out.println(tree.findMax().key);
		System.out.println(tree.findMin().key);
		System.out.println(tree.Contain(0));
		tree.remove(0, "0");
		System.out.println(tree.Contain(0));
		HashSet<String> result= tree.searchRange(875, 900);
		for (String string : result) {
			System.out.println(string);
		}
	}
}

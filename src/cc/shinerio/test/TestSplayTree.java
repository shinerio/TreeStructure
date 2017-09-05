package cc.shinerio.test;
import cc.shinerio.entity.SplayTree;


public class TestSplayTree {
	public static void main(String[] args) {
		SplayTree<Integer> tree = new SplayTree<>();
		for(int i=0;i<1000;i++){
			tree.add(i);
		}
		System.out.println(tree.root.key);
		tree.search(2);
		System.out.println(tree.root.key);
		tree.search(55);
		System.out.println(tree.root.key);
		
	}
}

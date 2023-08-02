import java.util.*;
/**
 * asdsa.
 * @author loc
 */
public class ExecutionTest{
	/**
	 * sad.
	 * @param args asd
	 */
	public static void main(String[] args) {
		String[] files = {"Merkle Tree Authentication Project", "CS 310 Project 3", "CS 310", "Data Structures Project 3","t1","t2","t3","t4","t5","t6","t7","t8","t9","t10","t11","t12"};	//"t1","t2","t3","t4","t5","t6","t7","t8","t9","t10","t11","t12"
		String root = null;

		MerkleTree merkleTree = new MerkleTree();

		// Building the MerkleTree
		System.out.println("Testing the constructMerkleTree method");
		root = merkleTree.constructMerkleTree(files);

		System.out.println("The root value after constructing the MerkleTree is " + root);
		System.out.println("The root value expected after constructing the MerkleTree is F118EFF0E779DE1594122DE1D9A1B68F24F8C93D793EC5068EE74E5F84CCE942A72839BDF61054D91F7665DF9F22F0115088C5DB17121ECD801A91B032372378");
		
		System.out.println("___________________");


		System.out.println("___________________");

		System.out.println(MerkleTree.verifyIntegrity(root, 12, files[12]));
		// System.out.println(merkleTree.swapFile(1, 3));
		System.out.println();
		// merkleTree.convertToDynamic();
		ArrayList<String> dynamicArrayofTree = MerkleTree.convertToDynamic();
		for(String s: dynamicArrayofTree){
			System.out.println(">> " + s);
		}
		System.out.println();

		System.out.println(MerkleTree.verifyIntegrityDynamic(root, 8, files[8], dynamicArrayofTree));



	}
}

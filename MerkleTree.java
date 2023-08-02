import java.util.*;
/**
 * This is the class representing the complete MerkleTree.
 * @author Loc Nguyen
 */
public class MerkleTree {
	/**
	 * root represent the top/first node of the tree.
	 */
	public static MerkleTreeNode root;
	/**
	 * numberOfFiles represent the total files or leaves.
	 */
	public static int numberOfFiles;
    /**
     * leaves is a list of leaves nodes only.
     */
    public static ArrayList<MerkleTreeNode> leaves;

	/**
	 * Construct a merkle tree using a file array of strings that will be hashed to get parents and root.
	 * @param files An array of strings. Must be 2^n size.
	 * @return The string of the root node.
	 */
	public String constructMerkleTree(String[] files){
		//Task 2: You are required to develop the code for the constructMerkleTree method.
		//Running time complexity of this method: O(n) where n is the number of files (size of the files array)
		//You can assume that the size of the files will be given as 2^n
		//throw IllegalArgumentException for invalid parameters
		try{
			numberOfFiles = files.length;
			leaves = new ArrayList<>();
			for(String file: files){
				MerkleTreeNode leafFile = new MerkleTreeNode(null,null,null, file);
				leaves.add(leafFile);
			}
			while(leaves.size() > 1){
				ArrayList<MerkleTreeNode> parentNodesList = new ArrayList<>();
				for(int i=0; i < leaves.size(); i+=2){
					String hashedParentStr = Hashing.cryptHash(leaves.get(i).getStr().concat(leaves.get(i+1).getStr()));
					// add node
					MerkleTreeNode parentNode = new MerkleTreeNode(null, leaves.get(i), leaves.get(i+1), hashedParentStr);
					leaves.get(i).setParent(parentNode); // Set left node's parent
					leaves.get(i+1).setParent(parentNode); // Set right node's parent
					// add to parent list
					parentNodesList.add(parentNode);
				}
				leaves = parentNodesList;	
			}
			root = leaves.get(0);
			return root.getStr();	
		}
		catch(Exception e){
			throw new IllegalArgumentException("Invalid parameters " + e);
		}
	}
	


	/**
	 * Verify the Merkle tree using the inputed rootValue, fileIndex, and file to determine if the files and tree have been tampered with.
	 * The file will recalculate with existing tree leaves and traverse back to the root to determine if the hashings are the same.
	 * @param rootValue String of the inputed root.
	 * @param fileIndex Integer index of the leaf file.
	 * @param file String file that will be used to hash back to root.
	 * @return True if file was not tampered with, fasle otherwise.
	 */
	public static boolean verifyIntegrity(String rootValue, int fileIndex, String file ){
		//Task 3: You are required to develop the code for the verifyIntegrity method
		//Running time complexity of this method: O(n)
		//throw IllegalArgumentException for invalid parameters
		try{
			// Checks for invalid parameters, (empty root, negative index)
			if(rootValue == null || fileIndex < 0){
				throw new IllegalArgumentException("Invalid Parameters");
			}
			// Checks if roots are equal
			if(!root.getStr().equals(rootValue)){	
				return false;
			}			
			ArrayList<MerkleTreeNode> listOfAllLeaves = getAllLeaves(root);	// Get list of all leaf nodes (helper method)
			//Checks if file exists in leavesList
			if(!listOfAllLeaves.get(fileIndex).getStr().equals(file)){
				return false;
			}
			int treeHeight = treeHeightEquation(listOfAllLeaves.size()); // (0/4) (1/8) (2/16) geometric sequence
			// Compute (fi + fi+1) and compare with (str(i+n,i) + str(i+1,i)) until root
			String tempHash;
			if(fileIndex % 2 == 0){	//If fileIndex is even
				String hashedFile = Hashing.cryptHash(listOfAllLeaves.get(fileIndex).getStr() + listOfAllLeaves.get(fileIndex + 1).getStr());
				MerkleTreeNode copiedNode = getEquivalentNode(hashedFile, root);
				while(treeHeight >= 0){
					if(copiedNode.getParent().getRight().equals(copiedNode)){	// If right node is copied node
						tempHash = Hashing.cryptHash( copiedNode.getParent().getLeft().getStr() + copiedNode.getStr());
						hashedFile = tempHash;
						copiedNode = copiedNode.getParent();
					} 
					else{
						tempHash = Hashing.cryptHash(copiedNode.getStr() + copiedNode.getParent().getRight().getStr());
						hashedFile = tempHash;
						copiedNode = copiedNode.getParent();
					}
					treeHeight--;
				}
				if(!copiedNode.equals(root)){
					return false;
				}
			}
			else{
				String hashedFile = Hashing.cryptHash(listOfAllLeaves.get(fileIndex-1).getStr() + listOfAllLeaves.get(fileIndex).getStr());
				MerkleTreeNode copiedNode = getEquivalentNode(hashedFile, root);
				while(treeHeight >= 0){
					if(copiedNode.getParent().getRight().equals(copiedNode)){	// If right node is copied node
						tempHash = Hashing.cryptHash( copiedNode.getParent().getLeft().getStr() + copiedNode.getStr());
						hashedFile = tempHash;
						copiedNode = copiedNode.getParent();
					} 
					else{
						tempHash = Hashing.cryptHash(copiedNode.getStr() + copiedNode.getParent().getRight().getStr());
						hashedFile = tempHash;
						copiedNode = copiedNode.getParent();
					}
					treeHeight--;
				}
				if(!copiedNode.equals(root)){
					return false;
				}
			}
			return true;
		}
		catch(Exception e){
			throw new IllegalArgumentException("Invalid Parameters " + e );
		}
	}

	/**
	 * Helper method to find the tree height equation.
	 * @param num The size of leaves list.
	 * @return The iteration number for the while loop.
	 */
	private static int treeHeightEquation(int num){
		int start = 4;
		int count = 0;
		if(num <= 4){
			return 0;
		}
		while(start <= num){
			if(start == num) return count;
			start += start;
			count++;
		}
		return -1;
	}


	/**
	 * Helper method to return a list of only leaf nodes.
	 * @param root The root node of the tree.
	 * @return ArrayList of MerkleTreeNode containing all leaf nodes.
	 */
	private static ArrayList<MerkleTreeNode> getAllLeaves(MerkleTreeNode root){
		ArrayList<MerkleTreeNode> leaves = new ArrayList<MerkleTreeNode>();
		if(root == null){
			return leaves;
		}
		if(root.getLeft() == null && root.getRight() == null){
			leaves.add(root);
		}
		else{
			leaves.addAll(getAllLeaves(root.getLeft()));
			leaves.addAll(getAllLeaves(root.getRight()));
		}
		return leaves;
	}

	// Use for debugging
	// private static boolean checkIfNodesExist(String nodeToFind, MerkleTreeNode root){
	// 	if(root == null){
	// 		return false;
	// 	}
	// 	if(nodeToFind.equals(root.getStr())){
	// 		return true;
	// 	}
	// 	return checkIfNodesExist(nodeToFind, root.getLeft()) || checkIfNodesExist(nodeToFind, root.getRight());
	// }


	/**
	 * Helper method to iterate through tree and return an equivalent node that has the same hashed string.
	 * @param hashedFile String to look for in tree.
	 * @param root The root node of the tree.
	 * @return The equivalent node, null otherwise.
	 */
	private static MerkleTreeNode getEquivalentNode(String hashedFile, MerkleTreeNode root){
		if(root == null){
			return null;
		}
		if(hashedFile.equals(root.getStr())){
			return root;
		}
		MerkleTreeNode leftNode = getEquivalentNode(hashedFile, root.getLeft());
		if(leftNode != null) return leftNode;

		MerkleTreeNode rightNode = getEquivalentNode(hashedFile, root.getRight());
		if(rightNode != null) return rightNode;
		return null;
	}


	/**
	 * This method recieves two indices of files to swap and updates all tree nodes with new hashed strings.
	 * @param fileIndex1 Index of first leaf file.
	 * @param fileIndex2 Index of second leaf file.
	 * @return The string of the updated root.
	 */
	public String swapFile(int fileIndex1, int fileIndex2){
		//Task 4: You are required to develop the code for the swapFile method.
		//Running time complexity of this method: O(n)
		//throw IllegalArgumentException for invalid parameters
		try{
			if(root == null) return null;
			ArrayList<MerkleTreeNode> listofLeaves = getAllLeaves(root);
			if(fileIndex1 < 0 || fileIndex2 < 0) throw new IllegalArgumentException();
			if(fileIndex1 > listofLeaves.size()-1 || fileIndex2 > listofLeaves.size()-1) throw new IllegalArgumentException();
			MerkleTreeNode fileToSwap1 = listofLeaves.get(fileIndex1);
			MerkleTreeNode fileToSwap2 = listofLeaves.get(fileIndex2); 
			listofLeaves.set(fileIndex1, fileToSwap2);
			listofLeaves.set(fileIndex2, fileToSwap1);
			String[] files = new String[listofLeaves.size()];
			for(int i = 0; i < listofLeaves.size(); i++){
				files[i] = listofLeaves.get(i).getStr();
			}
			String rootString = constructMerkleTree(files);
			return rootString;
		}
		catch(Exception e){
			throw new IllegalArgumentException("Invalid parameters " + e);
		}
	}

	/**
	 * The field is a helper list used to hold strings of nodes from the tree.
	 */
	private static ArrayList<String> merkleToArrayList;
	/**
	 * This method stores the nodes of the merkle tree into an ArrayList level-ordered sorted (root -> leafs).
	 * @return An arraylist containing the string of the nodes.
	 */
	public static ArrayList<String> convertToDynamic(){
		//Task 5: You are required to develop the code for the convertToDynamic method.
		//Running time complexity of this method: O(n)
		if(root == null) return null;
		merkleToArrayList = new ArrayList<>();
		int treeHeight = getTreeHeight(root);
		for(int i = 1; i <= treeHeight; i++){
			convertProcess(root, i);
		}
		return merkleToArrayList;
	}


	/**
	 * Helper method to convert tree into ArrayList, which will be stored in the private field merkleToArrayList.
	 * @param root The root node of the tree.
	 * @param treeLevel The depth of the tree, lowest being the root.
	 */
	private static void convertProcess(MerkleTreeNode root,  int treeLevel){
		if(root == null){
			return;
		}
		if(treeLevel == 1){
			merkleToArrayList.add(root.getStr());
		}
		else if(treeLevel > 1){
			convertProcess(root.getLeft(), treeLevel - 1);
			convertProcess(root.getRight(), treeLevel - 1);
		}
	}

	/**
	 * Helper method to find the height of the tree.
	 * @param root The root node of the tree.
	 * @return The height of the tree.
	 */
	private static int getTreeHeight(MerkleTreeNode root){
		if(root == null) return 0;
		else{
			int left = getTreeHeight(root.getLeft());
			int right = getTreeHeight(root.getRight());

			if(left > right){
				return (left + 1);
			}
			else{
				return (right +1);
			}
		}
	}


	/**
	 * This method verify the integrity of the file using the dynamic array version of the original merkle tree.
	 * @param rootValue	The string of the root.
	 * @param fileIndex The file index to verify.
	 * @param file The file string to verify.
	 * @param dynamicMerkle The Arraylist containing the tree nodes that will be used to verify the given file.
	 * @return True if files are not tampered with, false otherwise.
	 */
	public static boolean verifyIntegrityDynamic(String rootValue, int fileIndex, String file, ArrayList<String> dynamicMerkle) {
		//Task 6: You are required to develop the code for the verifyIntegrityDynamic method.
		//Running time complexity of this method: O(n)

		// Checks if root matches or is null
		if(!rootValue.equals(root.getStr()) || rootValue == null) return false;
		ArrayList<String> dynamicLeaves = getLeavesofDynamic(dynamicMerkle);
		int heightDynamic = getHeightDynamic(dynamicMerkle);
		if(fileIndex > dynamicLeaves.size() - 1 || fileIndex < 0) return false;
		// Checks if leaves contains file
		if(!dynamicLeaves.get(fileIndex).equals(file) ){
			return false;
		}
		/* 
			To iterate back to root in a dynamic array, fileIndex/file will hashed with sibling and checked if exist in the parents array.
			Through each iteration, dynamicLeaves will start with the actual leaves and move up toward the root.
			For example, leaves -> parents(N) -> parent(N-1) -> ... -> Root
			Root will be determined using the last two parent nodes.
			Then the root of the operation will be compared with the inputed array's root to verify validity.
		*/
		String siblingStr;
		String combinedStr;
		String newRootToFind = null;
		while(heightDynamic >= 0){
			if(dynamicLeaves.size() == 2){
				newRootToFind = Hashing.cryptHash(dynamicLeaves.get(0) + dynamicLeaves.get(1));
				break;
			}
			if(fileIndex % 2 ==0){
				siblingStr = dynamicLeaves.get(fileIndex + 1);
				combinedStr = Hashing.cryptHash(dynamicLeaves.get(fileIndex) + siblingStr);
			}
			else{
				siblingStr = dynamicLeaves.get(fileIndex - 1);
				combinedStr = Hashing.cryptHash(siblingStr + dynamicLeaves.get(fileIndex));
			}
			dynamicLeaves = getParentList(dynamicLeaves);
			if(!dynamicLeaves.contains(combinedStr)){
				return false;
			}
			fileIndex /= 2;
			heightDynamic--;
		}
		return newRootToFind.equals(rootValue);
	}

	/**
	 * Helper method gets the height of the tree based on how it is stored in the dynamic array.
	 * @param dynamicMerkle The Arraylist containing the strings of the tree nodes.
	 * @return The height of the tree.
	 */
	private static int getHeightDynamic(ArrayList<String> dynamicMerkle){
		int base = 2;
		int power = 0;
		int height = 0;
		ArrayList<String> dm = copyList(dynamicMerkle);
		while(!dm.isEmpty()){
			int level = (int) Math.pow(base, power);
			for(int i = 0; i < level; i ++){
				dm.remove(0);
			}
			power++;
			height++;
		}
		return height -1;
	}


	/**
	 * Helper method to read the dynamic array and only return a new list of the leaf strings.
	 * @param dynamicMerkle The Arraylist containing the strings of the tree nodes. 
	 * @return An arraylist of the leaf strings.
	 */
	private static ArrayList<String> getLeavesofDynamic( ArrayList<String> dynamicMerkle){
		int height = getHeightDynamic(dynamicMerkle);
		ArrayList<String> dm = copyList(dynamicMerkle);
		ArrayList<String> leaves = new ArrayList<>();
		int base = 2;
		int power = 0;
		while(!dm.isEmpty()){
			int level = (int) Math.pow(base, power);
			for(int i = 0; i < level; i ++){
				if(power == height){
					leaves.add(dm.get(0));
					dm.remove(0);
				}
				else{
					dm.remove(0);
				}
			}
			power++;		
		}		
		return leaves;
	}

	/**
	 * Helper method to hash leaf strings and store them as parents in a new parent list.
	 * @param leafList The list of leaf strings.
	 * @return An arraylist of the parent strings.
	 */
	private static ArrayList<String> getParentList(ArrayList<String> leafList){
		ArrayList<String> parentList = new ArrayList<>();
		if(leafList.size() % 2 != 0) return null;
		for(int i = 0; i < leafList.size(); i += 2){
			String hashedStr = Hashing.cryptHash(leafList.get(i) + leafList.get(i + 1));
			parentList.add(hashedStr);
		}
		return parentList;
	}

	/**
	 * Helper method to make a deep copy of a string arraylist.
	 * @param arryToCopy A string arraylist to copy.
	 * @return A new copied arraylist.
	 */
	private static ArrayList<String> copyList(ArrayList<String> arryToCopy){
		ArrayList<String> newArrayList = new ArrayList<>();
		for(String i: arryToCopy){
			newArrayList.add(i);
		}
		return newArrayList;
	}
}


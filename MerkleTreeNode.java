/**
 * This class is used to represent nodes in the Merkle Tree.
 * @author Loc Nguyen
 */
public class MerkleTreeNode{
    /**
     * The parent node of this node.
     */
    private MerkleTreeNode parent;
    /**
     * The left node of this node.
     */
    private MerkleTreeNode left;
    /**
     * The right node of this node.
     */
    private MerkleTreeNode right;
    /**
     * The string of this node.
     */
    private String str;

	/**
	 * MerkleTreeNode constructor that initializes the instance variables to null.
	 */
	public MerkleTreeNode(){
		setParent(null);
		setLeft(null);
		setRight(null);
		setLeft(null);
	}
	

	/**
	 * MerkleTreeNode Constructor that initiates the object with the parent, left, and right MerkleTreeNode objects.
	 * @param parent The parent node.
	 * @param left The left node.
	 * @param right The right node.
	 * @param str The string of the node.
	 */
	public MerkleTreeNode(MerkleTreeNode parent,MerkleTreeNode left,MerkleTreeNode right,String str){
		setParent(parent);
		setLeft(left);
		setRight(right);
		setStr(str);
	}

	/**
	 * Get parent node of this node.
	 * @return The parent node.
	 */
	public MerkleTreeNode getParent(){
		return parent;
	}
	/**
	 * Get left node of this node.
	 * @return The left node.
	 */
	public MerkleTreeNode getLeft(){
		return left;
	}
	/**
	 * Get right node of this node.
	 * @return The right node.
	 */
	public MerkleTreeNode getRight(){
		return right;
	}
	/**
	 * Get String of this node.
	 * @return This node's string.
	 */
	public String getStr(){
		return str;
	}
	
	/**
	 * Set parent node.
	 * @param parent The parent node to be set.
	 */
	public void setParent(MerkleTreeNode parent){
		//throw IllegalArgumentException for invalid parameters
		try{
			this.parent = parent;
		}
		catch(Exception e){
			throw new IllegalArgumentException("Invalid Paramters");
		}
	}
	/**
	 * Set left node.
	 * @param left The left node to be set.
	 */
	public void setLeft(MerkleTreeNode left){
		//throw IllegalArgumentException for invalid parameters
		try{
			this.left = left;
		}
		catch(Exception e){
			throw new IllegalArgumentException("Invalid Paramters");
		}
	}
	/**
	 * Set right node.
	 * @param right The right node to be set.
	 */
	public void setRight(MerkleTreeNode right){
		//throw IllegalArgumentException for invalid parameters
		try{
			this.right = right;
		}
		catch(Exception e){
			throw new IllegalArgumentException("Invalid Paramters");
		}
	}
	/**
	 * Set tge string for this node.
	 * @param str The string to be set.
	 */
	public void setStr(String str){
		//throw IllegalArgumentException for invalid parameters
		try{
			this.str = str;
		}
		catch(Exception e){
			throw new IllegalArgumentException("Invalid Paramters");
		}
	}        
        
}
/*
 * Name: Ben Mazerolle
 * ID: V00868691
 * Date: April 2, 2017
 * Filename: StackRefBased.java
 * Details: CSC 115, Spring 2017 Assignment 4. 
 *
 */

import java.util.*;
import java.util.AbstractCollection;

//
// An implementation of a binary search tree.
//
// This tree stores both keys and values associated with those keys.
//
// More information about binary search trees can be found here:
//
// http://en.wikipedia.org/wiki/Binary_search_tree
//
// Note: Wikipedia is using a different definition of
//       depth and height than we are using.  Be sure
//       to read the comments in this file for the
//	 	 height function.
//

/*
*	The class BinarySearchTree contains methods that implement a generic BST
*	and allow for insertion and traversal. The methods also enable users to 
*	read the contents of the BST in Pre, Post, In, and Level orders. 
*/	
class BinarySearchTree <K extends Comparable<K>, V>  {

	public static final int BST_PREORDER  = 1;
	public static final int BST_POSTORDER = 2;
	public static final int BST_INORDER   = 3;

	// These are package friendly for the TreeView class
	BSTNode<K,V>	root;
	int		count = 0;

	int		findLoops;
	int		insertLoops;

	//Purpose: 
	//Constructor for a new BinarySearchTree
	//
	//Return value: 
	//None
	public BinarySearchTree () {
		root = null;
		count = 0;
		resetFindLoops();
		resetInsertLoops();
	}
	
	//Purpose: 
	//Count the number of loops for find
	//
	//Return value:
	//The number of find loops
	public int getFindLoopCount() {
		return findLoops;
	}

	//Purpose: 
	//Count the number of loops for insert
	//
	//Return value:
	//The number of insert loops
	public int getInsertLoopCount() {
		return insertLoops;
	}

	//Purpose: 
	//Reset the counted number of loops for find
	//
	//Return value:
	//None
	public void resetFindLoops() {
		findLoops = 0;
	}
	
	//Purpose: 
	//Reset the counted number of loops for insert
	//
	//Return value:
	//None
	public void resetInsertLoops() {
		insertLoops = 0;
	}

	
	//
	// Purpose:
	//
	// Insert a new Key:Value Entry into the tree.  If the Key
	// already exists in the tree, update the value stored at
	// that node with the new value.
	//
	// Pre-Conditions:
	// 	the tree is a valid binary search tree
	//
	public void insert (K k, V v) {
		root = doInsert(root, k, v);
	}
	
	//Purpose: 
	//Recursively traverse the BST and insert Key:Value entry in correct location
	//
	//Return Value: 
	//The newly inserted node, or the overwritten node at the desired location
	private BSTNode<K,V> doInsert(BSTNode<K,V> n, K key, V value){ 
		
		//If the list is empty, or the current node has no children, make a new node 
		if (n == null){
			count++;
			return new BSTNode<K,V>(key, value);
		}
		
		//If the parameter key matches a key already in the tree, replace the old entry with the parameter entry
		if (key.compareTo(n.key) == 0){
			n.value = value; 
			return n;
		} 
		
		//If the parameter key is less than the current key, traverse to the left child node
		if (key.compareTo(n.key) < 0){
			insertLoops++;
			n.left = doInsert(n.left, key, value);
		}
		
		//If the parameter key is more than the current key, traverse to the right child node
		else{
			insertLoops++;
			n.right = doInsert(n.right, key, value);
		}
		
		return n;
	}

	//
	// Purpose:
	//
	// Return the value stored at key.  Throw a KeyNotFoundException
	// if the key isn't in the tree.
	//
	// Pre-conditions:
	//	the tree is a valid binary search tree
	//
	// Returns:
	//	the value stored at key
	//
	// Throws:
	//	KeyNotFoundException if key isn't in the tree
	//
	public V find (K key) throws KeyNotFoundException {
		BSTNode<K,V> trav = root; 
		
		//infinite loop
		for(;;){ 
			
			//If the list is empty or the key is not in the list, throw a KeyNotFoundException 
			if(trav == null){ 
				throw new KeyNotFoundException();
			}
			
			//If the key is found, return the value at that key
			if(key.compareTo(trav.key)==0){ 
				return trav.value; 
			}
			
			//If the parameter key is less than the current key, traverse to the left child node
			if(key.compareTo(trav.key)<0){ 
				findLoops++;
				trav = trav.left;
			}
			
			//If the parameter key is more than the current key, traverse to the right child node
			else{ 
				findLoops++;
				trav = trav.right;
			}
		}
	}

	//
	// Purpose:
	//
	// Return the number of nodes in the tree.
	//
	// Returns:
	//	the number of nodes in the tree.
	public int size() {
		return count;
	}

	//
	// Purpose:
	//	Remove all nodes from the tree.
	//
	public void clear() {
		root = null; 
		count = 0;
	}

	//
	// Purpose:
	//
	// Return the height of the tree.  We define height
	// as being the number of nodes on the path from the root
	// to the deepest node.
	//
	// This means that a tree with one node has height 1.
	//
	// Examples:
	//	See the assignment PDF and the test program for
	//	examples of height.
	//
	public int height() {
		return doHeight(root);
	}
	
	//Purpose: 
	//Recursively find the height of the tree
	//
	//Return value: 
	//The height of the tallest subtree plus 1, to account for the root node
	private int doHeight(BSTNode<K,V> n){ 
	
		//If the list is empty, or the end of the tree is reached, return 0
		if(n==null){ 
			return 0;
		
		}else{ 
		
			//Store the recursive height of the left subtree
			int leftHeight = doHeight(n.left);
			
			//Store the recursive height of the right subtree
			int rightHeight = doHeight(n.right);
			
			//Return the height of the right subtree if it is higher
			if(rightHeight>leftHeight){
				return (rightHeight+1); 
			}
			
			//Return the height of the left subtree if it is higher
			else{
				return (leftHeight+1);
			}
		}
	}

	
	//
	// Purpose:
	//
	// Return a list of all the key/value Entrys stored in the tree
	// The list will be constructed by performing a level-order
	// traversal of the tree.
	//
	// Level order is most commonly implemented using a queue of nodes.
	//
	//  From wikipedia (they call it breadth-first), the algorithm for level order is:
	//
	//	levelorder()
	//		q = empty queue
	//		q.enqueue(root)
	//		while not q.empty do
	//			node := q.dequeue()
	//			visit(node)
	//			if node.left != null then
	//			      q.enqueue(node.left)
	//			if node.right != null then
	//			      q.enqueue(node.right)
	//
	// Note that we will use the Java LinkedList as a Queue by using
	// only the removeFirst() and addLast() methods.
	//
	public List<Entry<K,V>> entryList() {
		
		//List that acts as a queue to store nodes from the BST
		List<BSTNode<K, V>> queue = new LinkedList<BSTNode<K,V> >();
		
		//List of BST entries
		List<Entry<K, V>> entries = new LinkedList<Entry<K,V> >();
		
		//Add the root to the queue
        queue.add(root);
		
        while (!queue.isEmpty()) {
			
			//Remove the first node in the queue
            BSTNode<K,V> x = queue.remove(0); 
			
			//If the node is null, restart the loop
            if (x == null) continue;
			
			//Add the non-null node to the list of entries
            entries.add(new Entry<K,V>(x.key, x.value));
			
			//Add the left child node to the queue
            queue.add(x.left);
			
			//Add the right child node to the queue
            queue.add(x.right);
        }
        return entries;
	}

	//
	// Purpose:
	//
	// Return a list of all the key/value Entrys stored in the tree
	// The list will be constructed by performing a traversal 
	// specified by the parameter which.
	//
	// If which is:
	//	BST_PREORDER	perform a pre-order traversal
	//	BST_POSTORDER	perform a post-order traversal
	//	BST_INORDER	perform an in-order traversal
	//
	public List<Entry<K,V> > entryList (int which) {
		
		//Create a new list to store the entries in the tree
		List<Entry<K,V> > l = new LinkedList<Entry<K,V> >();
		
		//Perform a pre-order traversal to create the list of entries
		if(which == BST_PREORDER){ 
			doPreOrder(root,l);
		} 
		
		//Perform an in-order traversal to create the list of entries
		if(which == BST_INORDER){ 
			doInOrder(root,l);
		} 
		
		//Perform a post-order traversal to create the list of entries
		if(which == BST_POSTORDER){ 
			doPostOrder(root,l);
		}
		
		//return the list of entries
		return l;
	}
	
	//Purpose: 
	//Recursively perform a pre-order traversal of the BST
	//
	//Return value: 
	//None
	private void doPreOrder (BSTNode<K,V> n, List <Entry<K,V> > l){ 
	
		//If the current node exists in the list
		if(n!=null){ 
		
			//Add the current entry to the list 
			l.add(new Entry<K,V>(n.key, n.value));
			
			//Recursively traverse the left subtree
			doPreOrder(n.left,l);
			
			//Recursively traverse the right subtree
			doPreOrder(n.right,l);
		}
	}
	
	//Purpose: 
	//Recursively perform an in-order traversal of the BST
	//
	//Return value: 
	//None
	private void doInOrder (BSTNode<K,V> n, List <Entry<K,V> > l){ 
	
		//If the current node exists in the list
		if(n!=null){ 
		
			//Recursively traverse the left subtree
			doInOrder(n.left,l);
			
			//Add the current entry to the list
			l.add(new Entry<K,V>(n.key, n.value));
			
			//Recursively traverse the right subtree
			doInOrder(n.right,l);
		}
	}
	
	//Purpose: 
	//Recursively perform a post-order traversal of the BST
	//
	//Return value: 
	//None
	private void doPostOrder (BSTNode<K,V> n, List <Entry<K,V> > l){ 
		
		//If the current node exists in the list
		if(n!=null){ 
		
			//Recursively traverse the right subtree
			doPostOrder(n.left,l);
			
			//Recursively traverse the right subtree
			doPostOrder(n.right,l);
			
			//Add the current entry to the list
			l.add(new Entry<K,V>(n.key, n.value));
		}
	}

	
	public static void main(String[] args){ 
	
		BinarySearchTree<String, String>t1;
		t1 = new BinarySearchTree<String,String>();
		t1.insert("1", "data1");
		t1.insert("3", "data3");
		t1.insert("2", "data2"); 
		t1.insert("-1", "data2"); 
		t1.insert("1", "data9");
		TreeView<String,String> v1 = new TreeView<String,String>(t1);
		v1.dotPrint();

	}
	// Your instructor had the following private methods in his solution:
	// private void doInOrder (BSTNode<K,V> n, List <Entry<K,V> > l);
	// private void doPreOrder (BSTNode<K,V> n, List <Entry<K,V> > l);
	// private void doPostOrder (BSTNode<K,V> n, List <Entry<K,V> > l);
	// private int doHeight (BSTNode<K,V> t)
}

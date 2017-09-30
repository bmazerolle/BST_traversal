/*
 * Name: Ben Mazerolle
 * ID: V00868691
 * Date: April 2, 2017
 * Filename: StackRefBased.java
 * Details: CSC 115, Spring 2017 Assignment 4. 		
 */

import java.util.*;

/*
*	The class BSTMap implements a Map and extends a BinarySearchTree. Its methods 
*	allow for Map searching in Pre, Post, In, and level order, and also enables 
*	users to determine the size, height, and number of loops used to accomplish 
*	tasks by the tree. 
*/
public class BSTMap<K extends Comparable<K>, V > implements  Map<K, V>  {

	BinarySearchTree<K,V> BST;
	
	//Purpose: 
	//
	//Constructor method for a new BSTMap
	//Return value: 
	//None
	public BSTMap () {
		BST = new BinarySearchTree<K,V>();
	}

	//Purpose: 
	//Check if the BSTMap contains a specific key
	//
	//Return value:
	//True if the key exists, false otherwise
	public boolean containsKey(K key) {
		try{ 
		
			//Search for the key, return true if found
			if(BST.find(key) != null){ 
				return true;
			}
		}catch(KeyNotFoundException k){
		}
		
		//If the key is not found, return false
		return false; 
	}

	//Purpose: 
	//Get and return the value stored at a key. Throw a KeyNotFoundException if the key isn't in the tree.
	//
	//Return value:
	//The value stored at a key.
	public V get (K key) throws KeyNotFoundException {
		return BST.find(key);
	}

	//Purpose: 
	//Return a list of all the key/value Entrys stored in the tree by performing a level-order traversal of the tree
	//
	//Return value: 
	//A level-order list of the BSTMap's entries
	public List<Entry<K,V> >	entryList() {
		return BST.entryList();
	}

	//Purpose: 
	//Insert a Key:Value entry into the BSTMap
	//
	//Return value:
	//None
	public void put (K key, V value) {
		BST.insert(key,value);
	}

	//Purpose: 
	//Return the number of entries in the BSTMap
	//
	//Return value:
	//The size of the BSTMap
	public int size() {
		return BST.size();
	}

	//Purpose: 
	//Empty the BSTMap, remove all references and entries
	//
	//Return value:
	//None
	public void clear() {
		BST.clear();
	}

	//Purpose: 
	//Count the number of loops for find
	//
	//Return value:
	//The number of find loops
	public int getGetLoopCount() {
		return BST.getFindLoopCount();
	}

	//Purpose: 
	//Count the number of loops for insert
	//
	//Return value:
	//The number of insert loops
	public int getPutLoopCount() {
		return BST.getInsertLoopCount();
	}
	
	//Purpose: 
	//Reset the counted number of loops for find
	//
	//Return value:
	//None
	public void resetGetLoops() {
		BST.resetFindLoops();
	}
	
	//Purpose: 
	//Reset the counted number of loops for insert
	//
	//Return value:
	//None
	public void resetPutLoops() {
		BST.resetInsertLoops();
	}
}

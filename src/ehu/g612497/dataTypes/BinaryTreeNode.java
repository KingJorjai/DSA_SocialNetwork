/**
 * BinaryTreeNode represents a node in a binary tree with a left and 
 * right child.
 * 
 * @author Dr. Lewis
 * @author Dr. Chase
 * @version 1.0, 8/19/08
 */
package ehu.g612497.dataTypes;
import ehu.g612497.model.*;

public class BinaryTreeNode<T extends Comparable<T>> implements Comparable<BinaryTreeNode<T>>
{  protected T element;
   protected BinaryTreeNode<T> left, right;

   /**
    * Creates a new tree node with the specified data.
    *
    * @param obj  the element that will become a part of the new tree node
   */
   public BinaryTreeNode (T obj) 
   {  element = obj;
      left = null;
      right = null;  }

   /**
    * Returns the number of non-null children of this node.
    * This method may be able to be written more efficiently.
    *
    * @return  the integer number of non-null children of this node 
    */
   public int numChildren() 
   {  int children = 0;

      if (left != null)
         children = 1 + left.numChildren();

      if (right != null)
         children = children + 1 + right.numChildren();

      return children;
   }
   
   public void setElement(T p) {
	   element = p;
   } 
   
   public void setRight(BinaryTreeNode<T> p) {
	   right = p;
   }
   
   public void setLeft(BinaryTreeNode<T> p) {
	   left = p;
   }
   
   public BinaryTreeNode<T> getLeft(){
	   return this.left;
   }
   
   public BinaryTreeNode<T> getRight(){
	   return this.right;
   }
   
   public T getElement() {
	   return element;
   }
   
   public boolean addPerson(T p) {
	   if(this.element.compareTo(p) > 0) {
		   if(this.getLeft() != null) 
			   return this.getLeft().addPerson(p);
		   else {
			   this.setLeft(new BinaryTreeNode<T>(p));
			   return true;
		   }
	   }else if(this.element.compareTo(p) < 0){
		   if(this.getRight() != null)
			   return this.getRight().addPerson(p);
		   else {
			   this.setRight(new BinaryTreeNode<T>(p));
			   return true;
		   }
		   
	   }else {
		   element = p;
		   return false;
	   }
   }
   
   public boolean findAndDestroy(T p) {
	   
	   if(this.element.equals(p)) {
		   this.remove();
		   return true;
	   }
	   else { 
		   if(this.element.compareTo(p) > 0) {
			   if(!this.getLeft().equals(p)) 
				   return this.getLeft().findAndDestroy(p);
		   }else if(this.element.compareTo(p) < 0){
			   if(!this.getRight().equals(p))
				   return this.getRight().findAndDestroy(p);
	   
		   }
	   }
	   return false;
   }
   
   public void remove() {
	   this.setElement(this.getLeft().findFarthestRight().getElement());
	   this.getLeft().findSecondFarthestRight().setRight(null);
   }
   
   public BinaryTreeNode<T> findFarthestRight() {
	   if(this.getRight() != null)
		   return this.getRight().findFarthestRight();
	   else
		   return this;
   }
   
   public BinaryTreeNode<T> findSecondFarthestRight() {
	   if(this.getRight().getRight() != null)
		   return this.getRight().findFarthestRight();
	   else
		   return this;
   }
   
   public int compareTo(BinaryTreeNode<T> o) {
	   return this.getElement().compareTo(o.getElement());
   }
   
}


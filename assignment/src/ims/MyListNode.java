package ims;

/*
 * This generic class provide work with MyList.java act as a generic linked list
 */
public class MyListNode<T> {
	
	private T item;
	private MyListNode<T> next;
	
	 public MyListNode() {
	        this.item = null;
	        this.next = null;
	    }
	    
	    public MyListNode(T item) {
	        this.item = item;
	        this.next = null;
	    }

	    public T getItem() {
	        return item;
	    }

	    public MyListNode<T> getNext() {
	        return next;
	    }

	    public void setNext(MyListNode<T> next) {
	        this.next = next;
	    }

}

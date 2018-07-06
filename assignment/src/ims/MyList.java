package ims;

import java.util.Iterator;

/*
 * This class is a singly generic linked list
 */
public class MyList<T> implements Iterable<T>{

	private MyListNode<T> start;
	private MyListNode<T> end;
	private static int size;
	
	public MyList() {
		this.start = null;
		this.end = null;
		MyList.size = 0;
	}

    public void clear() {
    	this.start = null;
    	this.end = null;
    	MyList.size = 0;
    	//throw new UnsupportedOperationException("Not yet implemented.");
    }

    public void addToStart(T t) {
    	MyListNode<T> newNode = new MyListNode<T>(t);
    	if(this.start == null) {
    		this.start = newNode;
    		this.end = newNode;
    	} else {
	    	MyListNode<T> oldStartNode = this.start;
	    	this.start = newNode;
	    	this.start.setNext(oldStartNode);
	    	//throw new UnsupportedOperationException("Not yet implemented.");
    	}
    	MyList.size++;
    }

    public void addToEnd(T t) {
    	MyListNode<T> newEndNode = new MyListNode<T>(t);
    	if(this.end == null) {
    		this.start = newEndNode;
    		this.end = newEndNode;
    	} else {
	    	MyListNode<T> oldEndNode = this.end;
	    	this.end = newEndNode;
	    	oldEndNode.setNext(this.end);
	    	//throw new UnsupportedOperationException("Not yet implemented.");
    	}
    	MyList.size++;
    }
    
    public boolean addAtIndex(T t, int index) {
    	MyListNode<T> newNode = new MyListNode<T>(t);
    	MyListNode<T> current = start;
    	boolean itemAdded = false;
    	if(index > MyList.size) {
    		this.addToEnd(t);
    		return true;
    	}else if(index <= 1) {
    		this.addToStart(t);
    		return true;
    	}else {
	    	for(int i = 1; i < index-1; i++) {
	    		if(current.getNext() == null) {
	    			return false;
	    		}
	    		current = current.getNext();
	    	}
	    	MyListNode<T> nextNode = current.getNext();
	    	current.setNext(newNode);
	    	newNode.setNext(nextNode);
	    	MyList.size++;
	    	itemAdded = true;
	    	return itemAdded;
    	}
    }
 
    
    public boolean addInvoiceNodeInOrder(T t) {
    	

    	if(MyList.size == 0) {
    		this.addToStart(t);
    		return true;
    	}
    	
    	MyListNode<T> current = start;
    	int retN = 99;
    	for(int i=1;i<=this.size;i++) {
    		int ret = ((Invoice)t).compareTo(current.getItem());
    		
    		// if the item about to add is greatest then add to the start. 
    		if(ret >= 1 && i == 1 ) {
    			this.addToStart(t);
    			return true;
    		}
    		
    		if(i == MyList.size) {
    			this.addToEnd(t);
    			return true;
    		}
    		// compare to find the higher total than the total that about to beig added
    		retN = ((Invoice)t).compareTo(current.getNext().getItem());
    		
    		if(ret <= 0 && retN >= 1) { // if it equal then just add ignore ordering. 
    			this.addAtIndex(t, i + 1);
    			return true;
    		}
    		//iterate 
    		current = current.getNext();
    	}// end fpr 
    	
		return false;
    	
    }
    

    public void remove(int position) {
    	if(position == 1) {
    		this.start = this.start.getNext();
    	} else if (position == MyList.size) {
    		this.end = this.getMyListNode(position-1);
    	} else {
    		MyListNode<T> node = this.getMyListNode(position).getNext();
    		this.getMyListNode(position).setNext(null);
	    	this.getMyListNode(position-1).setNext(node);
	    	
	    	//throw new UnsupportedOperationException("Not yet implemented.");
    	}
    	MyList.size--;
    }
    
    protected MyListNode<T> getMyListNode(int position) {
    	MyListNode<T> currentNode = this.start;
    	MyListNode<T> returnedNode = null;
    	if(position <= MyList.size) {
    		for(int i = 1; i < position; i++) {
	    		if (currentNode != null) {
		    		currentNode = currentNode.getNext();
	    		}
    		}
    		returnedNode = currentNode;
    	}
    	return returnedNode;
    	//throw new UnsupportedOperationException("Not yet implemented.");
    }
    
    public T getT(int position) {
    	return this.getMyListNode(position).getItem();
    	//throw new UnsupportedOperationException("Not yet implemented.");    	
    }
    
    public static int getSize() {
    	return MyList.size;
    }

    public void print() {
    	MyListNode<T> current = this.start;
    	for(int i = 1; i <= size; i++) {
    		if(current != null) {
    			System.out.println(current.getItem().toString());
    			System.out.println("");
    			current = current.getNext();
    		}
    	}
    	//throw new UnsupportedOperationException("Not yet implemented.");
    }

	public Iterator<T> iterator() {	
		return new MyListIterator<T>();
	}
	

	/*
	 * Iterator class.
	 */
	private class MyListIterator<E> implements Iterator<E> {
		private int position = 0; 
		
		
		public boolean hasNext() {
			// see if there is a next 
			if(position < MyList.getSize()) {
				return true;
			}
			return false;
		}
	
		public E next() {
			if(this.hasNext()) {
				position++;
				E myListNode = (E) getMyListNode(position).getItem();
				return myListNode;
			}else {
				System.out.println("no more");
				return null;
			}
		}
	
	}// end private class 



}// end Mylist class 


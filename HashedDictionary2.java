
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashedDictionary2<K, V>  {
	private TableEntry<K, V>[] hashTable; 
	private int numberOfEntries;
	private int locationsUsed; 
	private static final int DEFAULT_SIZE = 2500; 
	private static final double MAX_LOAD_FACTOR = 0.5;

	public HashedDictionary2() {
		this(DEFAULT_SIZE); 
	} 

	@SuppressWarnings("unchecked")
	public HashedDictionary2(int tableSize) {
		int primeSize = getNextPrime(tableSize);
		hashTable = new TableEntry[primeSize];
		numberOfEntries = 0;
		locationsUsed = 0;
	}

	public boolean isPrime(int num) {
		boolean prime = true;
		for (int i = 2; i <= num / 2; i++) {
			if ((num % i) == 0) {
				prime = false;
				break;
			}
		}
		return prime;
	}

	public int getNextPrime(int num) {
		if (num <= 1)
            return 2;
		else if(isPrime(num))
			return num;
        boolean found = false;   
        while (!found)
        {
            num++;     
            if (isPrime(num))
                found = true;
        }     
        return num;
	}

	public void add(K key, String value) {
		String oldValue; 
		if (isHashTableTooFull())
			rehash();
		
		int index = getHashIndex(key);
		index = probe(index, key); 

		if ((hashTable[index] == null) || hashTable[index].isRemoved()) { 
			hashTable[index] = new TableEntry<K, V>(key, value);
			numberOfEntries++;
			locationsUsed++;
			oldValue = null;
		} else { 
			oldValue = (String) hashTable[index].getValue();
			hashTable[index].setValue(oldValue + " " +value);
		} 
	}

	private int getHashIndex(K key) {
		int hashIndex = ssf(key) % hashTable.length;
		if (hashIndex < 0)
			hashIndex = hashIndex + hashTable.length;
		return hashIndex;
	}
	
	public int ssf(K key) {
		char[] alphabeth = {'a' , 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k' ,'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		
		char[] ch = new char[((String) key).length()];
		int[] ch2= new int[((String) key).length()];
  		  
        for (int i = 0; i < ((String) key).length(); i++) {
            ch[i] = ((String) key).charAt(i);
        }
        for (int i = 0; i < ch.length; i++) {
			for (int j = 0; j < alphabeth.length; j++) {
				if (ch[i] == alphabeth[j]) {
					ch2[i] = j+1;
				}
			}
		}
        int sum = 0;
        for (int i = 0; i < ch2.length; i++) 
        {
			sum = sum + ch2[i];
		}
		return sum;
	}
	
	public int paf(K key) {
		char[] alphabeth = {'a' , 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k' ,'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		char[] ch = new char[((String) key).length()];
		int[] ch2= new int[((String) key).length()];
  		int n = ((String) key).length() - 1;
  		
        for (int i = 0; i < ((String) key).length(); i++) {
            ch[i] = ((String) key).charAt(i);
        }
        for (int i = 0; i < ch.length; i++) {
			for (int j = 0; j < alphabeth.length; j++) 
			{
				if (ch[i] == alphabeth[j]) {
					ch2[i] = (int) ((j+1)*(Math.pow(31, n))) ;
				}
			}
			n = n - 1;
		}
		int sum = 0;
		for (int i = 0; i < ch2.length; i++) 
        {
			sum = sum + ch2[i];
		}
		return sum;
	}
	
	
	public boolean isHashTableTooFull() {
		int load_factor = locationsUsed / hashTable.length;
		if (load_factor >= MAX_LOAD_FACTOR)
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public void rehash() {
		TableEntry<K, V>[] oldTable = hashTable;
		int oldSize = hashTable.length;
		int newSize = getNextPrime(2 * oldSize);
		hashTable = new TableEntry[newSize]; 
		numberOfEntries = 0; 
		locationsUsed = 0;
		
		for (int index = 0; index < oldSize; index++) {
			if ((oldTable[index] != null) && oldTable[index].isIn())
				add(oldTable[index].getKey(), (String) oldTable[index].getValue());
		}
	}

	private int probe(int index, K key) {
		boolean found = false;
		int removedStateIndex = -1; 
		while (!found && (hashTable[index] != null)) {
			if (hashTable[index].isIn()) {
				if (key.equals(hashTable[index].getKey()))
					found = true; 
				else 
					index = (index + 1) % hashTable.length; 
			} 
			else 
			{
				if (removedStateIndex == -1)
					removedStateIndex = index;
				index = (index + 1) % hashTable.length; 
			} 
		} 
		if (found || (removedStateIndex == -1))
			return index; 
		else
			return removedStateIndex; 
	}
	
	private int probe2(int index, K key) {
		boolean found = false;
		int removedStateIndex = -1; 
		while (index < 0) {
			index = index + hashTable.length;
		}
		int i = 0;
		while (!found && (hashTable[index] != null) && index >0 ) {
			if (hashTable[index].isIn()) {
				if (key.equals(hashTable[index].getKey()))
					found = true; 
				else {
					index = ((index % hashTable.length) + (i*(31 - index % 31))) % hashTable.length;
					
					i++;
				}
			} 
			else 
			{
				if (removedStateIndex == -1)
					removedStateIndex = index;
				index = ((index % hashTable.length) + (i*(31 - index % 31))) % hashTable.length; 
				i++;
			} 
		} 
		if (found || (removedStateIndex == -1))
			return index; 
		else
			return removedStateIndex; 
	}
	
	public V remove(K key) {
		V removedValue = null;
		int index = getHashIndex(key);
		index = locate(index, key);
		if (index != -1) { 
			removedValue = (V) hashTable[index].getValue();
			hashTable[index].setToRemoved();
			numberOfEntries--;
		} 
		return removedValue;
	}

	//Follows the probe sequence that begins at index (key’s hash index) and returns either the index
	//of the entry containing key or -1, if no such entry exists.
	private int locate(int index, K key) {
		boolean found = false;
		while (!found && (hashTable[index] != null)) {
			if ( hashTable[index].isIn() && key.equals(hashTable[index].getKey()))
				found = true;
			else 
			index =  (index + 1) % hashTable.length;
			
		} 
		int result = -1;
		if (found)
			result = index;
		return result;
	}

	public String getValue(K key) {
		String result = null;
		int index = getHashIndex(key);
		index = locate(index, key);
		if (index != -1)
			result = (String) hashTable[index].getValue(); 
		return result;
	}

	public boolean contains(K key) {
		int index = getHashIndex(key);
		index = locate(index, key);
		if (index != -1)
			return true;
		return false;
	}

	public boolean isEmpty() {
		return numberOfEntries == 0;
	}

	public int getSize() {
		return numberOfEntries;
	}

	public void clear() {
		while(getKeyIterator().hasNext()) {
			remove(getKeyIterator().next());		
		}
	}
	
	public Iterator<K> getKeyIterator() {
		return new KeyIterator();
	}

	public Iterator<V> getValueIterator() {
		return new ValueIterator();
	}

	private class TableEntry<S, T> {
		private S key;
		private T value;
		private boolean inTable;

		@SuppressWarnings("unchecked")
		private TableEntry(S key, String value) {
			this.key = key;
			this.value = (T) value;
			inTable = true;
		}

		private S getKey() {
			return key;
		}

		private String getValue() {
			return (String) value;
		}

		private void setValue(String value) {
			this.value = (T) value;
		}

		private boolean isRemoved() {
			return inTable == false;
		}

		private void setToRemoved() {
			inTable = false;
		}

		private void setToIn() {
			inTable = true;
		}

		private boolean isIn() {
			return inTable == true;
		}
	}

	private class KeyIterator implements Iterator<K> {
		private int currentIndex; 
		private int numberLeft; 

		private KeyIterator() {
			currentIndex = 0;
			numberLeft = numberOfEntries;
		} 

		public boolean hasNext() {
			return numberLeft > 0;
		} 

		public K next() {
			K result = null;
			if (hasNext()) {
				while ((hashTable[currentIndex] == null) || hashTable[currentIndex].isRemoved()) {
					currentIndex++;
				} 
				result = hashTable[currentIndex].getKey();
				numberLeft--;
				currentIndex++;
			} else
				throw new NoSuchElementException();
			return result;
		} 

		public void remove() {
			throw new UnsupportedOperationException();
		} 
	}
	
	private class ValueIterator implements Iterator<V> {
		private int currentIndex; 
		private int numberLeft; 

		private ValueIterator() {
			currentIndex = 0;
			numberLeft = numberOfEntries;
		} 

		public boolean hasNext() {
			return numberLeft > 0;
		} 

		public V next() {
			V result = null;
			if (hasNext()) {
				while ((hashTable[currentIndex] == null) || hashTable[currentIndex].isRemoved()) {
					currentIndex++;
				} 
				result = (V) hashTable[currentIndex].getValue();
				numberLeft--;
				currentIndex++;
			} else
				throw new NoSuchElementException();
			return result;
		} 

		public void remove() {
			throw new UnsupportedOperationException();
		} 
	}
}

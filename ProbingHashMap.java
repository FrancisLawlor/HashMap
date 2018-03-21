public class ProbingHashMap<K, V> {
  private static final int DEFAULT_MAX_MAP_SIZE = 10;
  private int currentSize;
  private MapEntry<K, V>[] mapEntries;
  private int currentMaxMapSize;

  public ProbingHashMap() {
    this.currentSize = 0;
    this.mapEntries = new MapEntry[DEFAULT_MAX_MAP_SIZE];
    this.currentMaxMapSize = DEFAULT_MAX_MAP_SIZE;
  }

  public ProbingHashMap(int currentMaxMapSize) {
    this.currentSize = 0;
    this.currentMaxMapSize = currentMaxMapSize;
    this.mapEntries = new MapEntry[this.currentMaxMapSize];
  }

  public void put(K key, V value) {
    int indexForExistingKey = findKeyIndexIfItExists(key);

    if (indexForExistingKey != -1) {
      this.mapEntries[indexForExistingKey].setValue(value);
      return;
    }

    int newEntryIndex = probeForNewEntryIndex(key);
    this.mapEntries[newEntryIndex] = new MapEntry(key, value);
    this.currentSize++;
  }

  public V get(K key) {
    int indexForExistingKey = findKeyIndexIfItExists(key);

    if (indexForExistingKey != -1) {
      return this.mapEntries[indexForExistingKey].getValue();
    }

    return null;
  }

  public V remove(K key) {
    int indexForExistingKey = findKeyIndexIfItExists(key);

    if (indexForExistingKey != -1) {
      V temp = this.mapEntries[indexForExistingKey].getValue();
      this.mapEntries[indexForExistingKey] = null;
      this.currentSize--;
      return temp;
    }

    return null;
  }

  public void clear() {
    for (int i = 0; i < this.mapEntries.length; i++) {
      if (this.mapEntries[i] != null) {
        this.mapEntries[i] = null;
      }
    }
  }

  public int size() {
    return this.currentSize;
  }

  public boolean isEmpty() {
    return this.currentSize == 0;
  }

  public boolean containsKey(K key) {
    return findKeyIndexIfItExists(key) != -1;
  }

  private void resize() {
    this.currentMaxMapSize = 2 * this.currentMaxMapSize;
    MapEntry<K, V>[] newMapEntryArray = new MapEntry[this.currentMaxMapSize];

    for (int i = 0; i < this.mapEntries.length; i++) {
      newMapEntryArray[i] = this.mapEntries[i];
    }

    this.mapEntries = newMapEntryArray;
  }

  private int hashKey(K key) {
    int hashCode = Math.abs(key.hashCode()) % this.currentMaxMapSize;

    return hashCode;
	}

  private int findKeyIndexIfItExists(K key) {
    int index = hashKey(key);

    for (int i = 0; i < this.mapEntries.length; i++) {
      int currentIndex = (index + i) % this.currentMaxMapSize;
      if (this.mapEntries[currentIndex] != null) {
        if (this.mapEntries[currentIndex].getKey() == key) {
          return currentIndex;
        }
      }
    }

    return -1;
  }

  private int probeForNewEntryIndex(K key) {
    if (this.size() == this.currentMaxMapSize) {
      resize();
    }

    int index = hashKey(key);

    for (int i = 0; i < this.currentMaxMapSize; i++) {
      int currentIndex = (index + i) % this.currentMaxMapSize;
      if (this.mapEntries[currentIndex] == null) {
        return currentIndex;
      }
    }

    return -1;
  }
}

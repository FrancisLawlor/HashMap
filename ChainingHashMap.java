public class ChainingHashMap<K, V> {
  private static final int DEFAULT_MAX_MAP_SIZE = 10;
  private int currentSize;
  private MapEntry<K, V>[] mapEntries;
  private int maxMapSize;

  public ChainingHashMap() {
    this.currentSize = 0;
    this.mapEntries = new MapEntry[DEFAULT_MAX_MAP_SIZE];
    this.maxMapSize = DEFAULT_MAX_MAP_SIZE;
  }

  public ChainingHashMap(int maxMapSize) {
    this.currentSize = 0;
    this.maxMapSize = maxMapSize;
    this.mapEntries = new MapEntry[this.maxMapSize];
  }

  public void put(K key, V value) {
    MapEntry<K, V> entry = findKeyEntryIfItExists(key);

    if (entry == null) {
      this.mapEntries[hashKey(key)] = new MapEntry(key, value);
      this.currentSize++;
      return;
    }

    if (entry.getKey() == key) {
      entry.setValue(value);
    } else {
      entry.setNext(new MapEntry(key, value));
      this.currentSize++;
    }
  }

  public V get(K key) {
    MapEntry<K, V> entry = findKeyEntryIfItExists(key);

    if (entry == null || entry.getKey() != key) {
      return null;
    }

    return entry.getValue();
  }

  public V remove(K key) {
    MapEntry<K, V> removalEntry = findEntryForRemoval(key);

    if (removalEntry == null) {
      return null;
    }

    if (removalEntry.getKey() == key) {
      V temp = removalEntry.getValue();
      removalEntry = removalEntry.getNext();
      this.currentSize--;

      return temp;
    }

    V temp = removalEntry.getNext().getValue();
    removalEntry.setNext(removalEntry.getNext().getNext());
    this.currentSize--;

    return temp;
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
    MapEntry<K, V> entry = findKeyEntryIfItExists(key);

    if (entry == null || entry.getKey() != key) {
      return false;
    }

    return true;
  }

  private int hashKey(K key) {
    int hashCode = Math.abs(key.hashCode()) % this.maxMapSize;

    return hashCode;
	}

  private MapEntry<K, V> findKeyEntryIfItExists(K key) {
    int index = hashKey(key);

    if (this.mapEntries[index] == null) {
      return null;
    }

    MapEntry<K, V> runner = this.mapEntries[index];
    while (runner.getNext() != null) {
      if (runner.getKey() == key) {
        return runner;
      }
      runner = runner.getNext();
    }

    return runner;
  }

  private MapEntry<K, V> findEntryForRemoval(K key) {
    int index = hashKey(key);

    if (this.mapEntries[index] == null) {
      return null;
    }

    if (this.mapEntries[index].getKey() == key) {
      return this.mapEntries[index];
    }

    MapEntry<K, V> runner = this.mapEntries[index];
    while (runner.getNext() != null) {
      if (runner.getNext().getKey() == key) {
        return runner;
      }
      runner = runner.getNext();
    }

    return null;
  }
}

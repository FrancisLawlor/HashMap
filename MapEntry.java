public class MapEntry<K, V> {
  private K key;
  private V value;
  private MapEntry<K, V> next;

  public MapEntry(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public K getKey() {
    return this.key;
  }

  public void setKey(K key) {
    this.key = key;
  }

  public V getValue() {
    return this.value;
  }

  public void setValue(V value) {
    this.value = value;
  }

  public MapEntry<K, V> getNext() {
    return this.next;
  }

  public void setNext(MapEntry<K, V> newEntry) {
    this.next = newEntry;
  }

  public String toString() {
    return "MapEntry{" +"key = " + key + ", value = " + value +'}';
  }
}

package soccerapp.webapi.utils.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> {

	private Map<K, V> map = new LinkedHashMap<>();


	public LRUCache(final int limit) {
		map = new LinkedHashMap <K, V> (16, 0.75f, true) {
			@Override
			protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
				return super.size() > limit;
			}
		};
	}

	public void put(K key, V value) {
		map.put(key, value);
	}

	public V get(K key) {
		return map.get(key);
	}

	public void remove(K key) {
		map.remove(key);
	}

	public int size() {
		return map.size ();
	}

	public String toString() {
		return map.toString();
	}
}

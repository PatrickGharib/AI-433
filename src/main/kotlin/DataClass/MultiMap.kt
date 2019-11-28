package DataClass

data class MultiMap<K,V>(private val data: MutableMap<K,MutableSet<V>> = mutableMapOf()) {
    constructor(m: MultiMap<K,V>) : this(m.data.toMutableMap())

    fun get(key :K): Set<V>{
        return data.getOrDefault(key, setOf())
    }
    fun put(key:K, v:V){
        if (data[key] == null) {
            data[key] = mutableSetOf()
        }
        data[key]?.add(v)
    }

    fun remove(key:K){
        data[key] = mutableSetOf()
    }
    fun remove(key:K,v: V){
        data[key]?.remove(v)
    }

    fun keys() = data.keys
}
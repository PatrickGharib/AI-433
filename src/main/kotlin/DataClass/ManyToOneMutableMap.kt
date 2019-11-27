package DataClass



data class ManyToOneMutableMap<K, V>(
        private val manyToOne: MutableMap<K, V> = mutableMapOf(),
        private val oneToMany: MutableMap<V, MutableSet<K>> = mutableMapOf(),
        val keySet: MutableSet<K> = manyToOne.keys,
        val valSet: MutableSet<V> = oneToMany.keys
    ){
    constructor(l: List<Pair<K,V>>) : this() {
        l.forEach { set(it.first,it.second) }
    }
    operator fun get(key: K): V?{
        return manyToOne[key]
    }

    fun deepCopy(): ManyToOneMutableMap<K,V>{
        return ManyToOneMutableMap(manyToOne.toMutableMap(), oneToMany.toMutableMap())
    }

    fun set(key: K, value: V){
        oneToMany[manyToOne[key]]?.remove(key)
        manyToOne[key] = value
        if (oneToMany[value].isNullOrEmpty()){
            oneToMany[value] = mutableSetOf(key)
        }else{
            oneToMany[value]!!.add(key)
        }

    }

    fun getKeys(value: V): Set<K>{
        return oneToMany[value] ?: setOf()
    }

}
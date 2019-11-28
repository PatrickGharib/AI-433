package DataClass

import java.lang.Exception


data class ManyToOneMutableMap<K, V>(
        private val manyToOne: MutableMap<K, V> = mutableMapOf(),
        private val oneToMany: MultiMap<V?, K> = MultiMap()
    ){
    constructor(l: List<Pair<K,V>>) : this() {
        l.forEach { set(it.first,it.second) }
    }

    val keySet: MutableSet<K> get() = manyToOne.keys
    val valSet: MutableSet<V?> get() = manyToOne.values.toMutableSet()

    fun get(key: K): V?{
        return manyToOne[key]
    }

    fun deepCopy(): ManyToOneMutableMap<K,V>{
        return ManyToOneMutableMap(LinkedHashMap(manyToOne), MultiMap(oneToMany))
    }

    fun set(key: K, value: V){


        val old = manyToOne[key]
        manyToOne[key] = value
        oneToMany.remove(old,key)
        oneToMany.put(value,key)


    }

    fun getKeys(value: V): Set<K>{
        return manyToOne.keys.filter{ manyToOne[it] == value}.toSet()
        //return oneToMany.get(value)
    }

}
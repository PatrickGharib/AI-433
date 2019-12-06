package DataClass

import java.lang.Exception

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap

data class ManyToOneMutableMap<K, V>(
        private val manyToOne: MutableMap<K, V> = LinkedHashMap(),
        private val oneToMany: Multimap<V?, K> = HashMultimap.create()
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
        val x = HashMultimap.create(oneToMany)
        val y = LinkedHashMap(manyToOne)

        return ManyToOneMutableMap(y,x)
    }

    fun set(key: K, value: V){


        val old = manyToOne[key]
        manyToOne[key] = value
        oneToMany.remove(old,key)
        oneToMany.put(value,key)

    }

    val m = HashMap<V, Set<K>>()
    fun getKeys(value: V): Set<K>{
        return manyToOne.keys.filter{ manyToOne[it] == value}.toSet()
        return oneToMany[value].toSet()
        if (value !in m.keys){
            m[value] = manyToOne.keys.filter{ manyToOne[it] == value}.toSet()
        }
        return m[value]!!
    }

}
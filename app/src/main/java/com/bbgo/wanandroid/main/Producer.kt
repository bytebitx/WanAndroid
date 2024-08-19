package com.bbgo.wanandroid.main

import android.util.Log

interface Producer<out T> {
    fun produce(): T
}

open class Animal {
    var name: String = ""
}

class Dog : Animal() {
    var action: String = ""
}

class AnimalProducer : Producer<Animal> {
    override fun produce(): Animal {
        println("animal")
        return Animal()
    }
}

class DogProducer : Producer<Dog> {
    override fun produce(): Dog {
        println("dog")
        return Dog()
    }
}

fun main(args: Array<String>) {
    val producer: Producer<Animal> = DogProducer()
    producer.produce()
}

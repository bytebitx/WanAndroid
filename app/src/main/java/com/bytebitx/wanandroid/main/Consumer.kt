package com.bytebitx.wanandroid.main

interface Consumer<in T> {
    fun consume(item: T)
}

class AnimalConsumer : Consumer<Animal> {
    override fun consume(item: Animal) {
        // 消费Animal类型的值
        if (item is Dog) {
            println("item is dog")
        } else {
            println("item is animal")
        }

    }
}

class DogConsumer : Consumer<Dog> {
    override fun consume(item: Dog) {
        // 消费Dog类型的值
        println("animal")
    }
}

fun main(args: Array<String>) {
    val consumer: Consumer<Dog> = AnimalConsumer()
    val dog = Dog()
    consumer.consume(dog)
}


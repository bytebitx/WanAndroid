package com.bbgo.module_square.repository


/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
class SquareLocalRepository private constructor(){


    companion object {
        private var repository: SquareLocalRepository? = null

        fun getInstance(): SquareLocalRepository {
            if (repository == null) {
                synchronized(SquareRepository::class.java) {
                    if (repository == null) {
                        repository = SquareLocalRepository()
                    }
                }
            }
            return repository!!
        }
    }
}
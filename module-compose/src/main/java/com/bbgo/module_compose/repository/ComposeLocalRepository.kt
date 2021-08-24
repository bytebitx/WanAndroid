package com.bbgo.module_compose.repository


/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
class ComposeLocalRepository private constructor(){

    companion object {
        private var repository: ComposeLocalRepository? = null

        fun getInstance(): ComposeLocalRepository {
            if (repository == null) {
                synchronized(ComposeLocalRepository::class.java) {
                    if (repository == null) {
                        repository = ComposeLocalRepository()
                    }
                }
            }
            return repository!!
        }
    }
}
package com.bbgo.module_sys.repository

/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
class SysLocalRepository private constructor(){


    companion object {
        private var repository: SysLocalRepository? = null

        fun getInstance(): SysLocalRepository {
            if (repository == null) {
                synchronized(SysRepository::class.java) {
                    if (repository == null) {
                        repository = SysLocalRepository()
                    }
                }
            }
            return repository!!
        }
    }
}
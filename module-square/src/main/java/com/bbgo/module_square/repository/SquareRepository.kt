package com.bbgo.module_square.repository

import com.bbgo.module_square.bean.Articles
import kotlinx.coroutines.flow.Flow

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
class SquareRepository private constructor(private val remoteRepository: SquareRemoteRepository, private val localRepository: SquareLocalRepository) {

    companion object {
        private var repository: SquareRepository? = null

        fun getInstance(remoteRepository: SquareRemoteRepository, localRepository: SquareLocalRepository): SquareRepository {
            if (repository == null) {
                synchronized(SquareRepository::class.java) {
                    if (repository == null) {
                        repository = SquareRepository(remoteRepository, localRepository)
                    }
                }
            }
            return repository!!
        }
    }


    suspend fun getSquareList(pageNum: Int) : Flow<Articles> {
        return remoteRepository.getSquareList(pageNum)
    }

}
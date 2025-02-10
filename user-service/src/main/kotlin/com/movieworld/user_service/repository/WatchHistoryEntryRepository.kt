package com.movieworld.user_service.repository

import com.movieworld.user_service.model.WatchHistoryEntry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WatchHistoryEntryRepository: JpaRepository<WatchHistoryEntry, Long> {
    fun findByUserProfileId(userProfileId: Long): List<WatchHistoryEntry>
}
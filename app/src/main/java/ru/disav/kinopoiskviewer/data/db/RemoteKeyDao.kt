package ru.disav.kinopoiskviewer.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.disav.kinopoiskviewer.data.db.entity.RemoteKeys

@Dao
interface RemoteKeyDao {
    @Query("SELECT * FROM remote_keys WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): RemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<RemoteKeys>)

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAllRemoteKeys()
}

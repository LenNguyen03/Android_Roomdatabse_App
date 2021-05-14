package com.example.roomdatabase_app;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface PersonDao {
    @Query("SELECT * FROM Person")
    List<Person> getAll();

    @Query("SELECT * FROM Person WHERE id IN (:userIds)")
    List<Person> loadAllByIds(int[] userIds);

    @Insert
    void insertAll(Person... users);

    @Delete
    void delete(Person person);

    @Update
    void update(Person person);
}


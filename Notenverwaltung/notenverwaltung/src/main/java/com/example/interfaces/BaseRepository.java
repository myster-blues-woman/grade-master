package com.example.interfaces;

import java.util.List;

public interface BaseRepository<T> {
    List<T> load();

    void save(List<T> items);

    void save();
}

package com.space.service;

import com.space.model.Ship;
import java.util.List;
import java.util.Optional;

public interface ShipService {
    /**
     * Создает нового корабль
     *
     * @param ship - корабль для создания
     */

    void create(Ship ship);

    /**
     * Возвращает список всех имеющихся кораблей
     *
     * @return список кораблей
     */

    List<Ship> readAll();


    /**
     * Возвращает корабль по его ID
     *
     * @param id - ID корабля
     * @return - объект корабля с заданным ID
     */

    Optional<Ship> read(long id);


    /**
     * Обновляет корабль с заданным ID,
     * в соответствии с переданным кораблем
     *
     * @param ship - корабль в соответсвии с которым нужно обновить данные
     * @param id   - id корабля который нужно обновить
     * @return - true если данные были обновлены, иначе false
     */

    boolean update(Ship ship, long id);


    /**
     * Удаляет корабль с заданным ID
     *
     * @param id - id корабля, который нужно удалить
     * @return - true если корабль был удален, иначе false
     */

    boolean delete(long id);
}

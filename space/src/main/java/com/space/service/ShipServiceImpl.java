package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipServiceImpl implements ShipService{

    @Autowired
    private ShipRepository shipRepository;

    @Override
    public void create(Ship ship) {
        shipRepository.save(ship);
    }

    @Override
    public List<Ship> readAll() {
        return shipRepository.findAll();
    }

    public Integer count(){
        return (int) shipRepository.count();
    }

    @Override
    public Optional<Ship> read(long id) {
        return shipRepository.findById(id);
    }

    @Override
    public boolean update(Ship ship, long id) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        final boolean exist = shipRepository.existsById(id);
        if (exist) shipRepository.deleteById(id);
        return exist;
    }

}

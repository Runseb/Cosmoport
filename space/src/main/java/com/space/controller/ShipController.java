package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
public class ShipController {

    @Autowired
    private ShipServiceImpl shipServiceImpl;


    @GetMapping(value = "/rest/ships")
    public ResponseEntity<List<Ship>> readAll(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "planet", required = false) String planet,
                                   @RequestParam(value = "shipType", required = false) ShipType shipType,
                                   @RequestParam(value = "after", required = false) Long after,
                                   @RequestParam(value = "before", required = false) Long before,
                                   @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                   @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                   @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                   @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                   @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                   @RequestParam(value = "minRating", required = false) Double minRating,
                                   @RequestParam(value = "maxRating", required = false) Double maxRating,
                                   @RequestParam(value = "order", required = false) ShipOrder order,
                                   @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                   @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
        List<Ship> ships = shipServiceImpl.readAll();

        Iterator<Ship> iterator = ships.iterator();
        while (iterator.hasNext()){
            Ship ship = iterator.next();
            if (name != null && !ship.getName().contains(name)) iterator.remove();
            else if (planet !=null && !ship.getPlanet().contains(planet)) iterator.remove();
            else if (shipType !=null && !ship.getShipType().equals(shipType)) iterator.remove();
            else if (after !=null && ship.getProdDate().getTime()<after) iterator.remove();
            else if (before !=null && ship.getProdDate().getTime() > before) iterator.remove();
            else if (isUsed !=null && ship.getUsed() != isUsed) iterator.remove();
            else if (minSpeed !=null && ship.getSpeed() < minSpeed) iterator.remove();
            else if (maxSpeed !=null && ship.getSpeed() > maxSpeed) iterator.remove();
            else if (minCrewSize !=null && ship.getCrewSize() < minCrewSize) iterator.remove();
            else if (maxCrewSize !=null && ship.getCrewSize() > maxCrewSize) iterator.remove();
            else if (minRating !=null && ship.getRating() < minRating) iterator.remove();
            else if (maxRating !=null && ship.getRating() > maxRating) iterator.remove();
        }
        if (order != null) {
            switch (order){
                case ID:
                    ships.sort(Comparator.comparing(Ship::getId));
                    break;
                case SPEED:
                    ships.sort(Comparator.comparing(Ship::getSpeed));
                    break;
                case DATE:
                    ships.sort(Comparator.comparing(Ship::getProdDate));
                    break;
                case RATING:
                    ships.sort(Comparator.comparing(Ship::getRating));
                    break;
            }
        }
        ships = ships.subList(pageNumber * pageSize,Math.min(((pageNumber+1) * pageSize),ships.size()));
        return ships != null &&  !ships.isEmpty()
                ? new ResponseEntity<>(ships, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/rest/ships/count")
    public ResponseEntity<Integer> count (@RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "planet", required = false) String planet,
                                              @RequestParam(value = "shipType", required = false) ShipType shipType,
                                              @RequestParam(value = "after", required = false) Long after,
                                              @RequestParam(value = "before", required = false) Long before,
                                              @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                              @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                              @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                              @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                              @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                              @RequestParam(value = "minRating", required = false) Double minRating,
                                              @RequestParam(value = "maxRating", required = false) Double maxRating,
                                              @RequestParam(value = "order", required = false) ShipOrder order,
                                              @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                              @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
        List<Ship> ships = shipServiceImpl.readAll();

        Iterator<Ship> iterator = ships.iterator();
        while (iterator.hasNext()) {
            Ship ship = iterator.next();
            if (name != null && !ship.getName().contains(name)) iterator.remove();
            else if (planet != null && !ship.getPlanet().contains(planet)) iterator.remove();
            else if (shipType != null && !ship.getShipType().equals(shipType)) iterator.remove();
            else if (after != null && ship.getProdDate().getTime() < after) iterator.remove();
            else if (before != null && ship.getProdDate().getTime() > before) iterator.remove();
            else if (isUsed != null && ship.getUsed() != isUsed) iterator.remove();
            else if (minSpeed != null && ship.getSpeed() < minSpeed) iterator.remove();
            else if (maxSpeed != null && ship.getSpeed() > maxSpeed) iterator.remove();
            else if (minCrewSize != null && ship.getCrewSize() < minCrewSize) iterator.remove();
            else if (maxCrewSize != null && ship.getCrewSize() > maxCrewSize) iterator.remove();
            else if (minRating != null && ship.getRating() < minRating) iterator.remove();
            else if (maxRating != null && ship.getRating() > maxRating) iterator.remove();
        }
        return new ResponseEntity<>(ships.size(), HttpStatus.OK);
    }

    @GetMapping(value = "/rest/ships/{id}")
    public ResponseEntity<Ship> readById(@PathVariable(name = "id") long id) {
        if (id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return shipServiceImpl.read(id).map(ship -> new ResponseEntity<>(ship, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/rest/ships/{id}")
    public ResponseEntity<Ship> edit(@PathVariable(name = "id") long id,
                                     @RequestBody(required = false) Ship ship) {
        if (id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Optional<Ship> optShipForUpdate = shipServiceImpl.read(id);
        if (!optShipForUpdate.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Ship shipForUpdate = optShipForUpdate.get();
        if (ship != null){
            if (ship.getName() != null) shipForUpdate.setName(ship.getName());
            if (ship.getPlanet() != null) shipForUpdate.setPlanet(ship.getPlanet());
            if (ship.getShipType() != null) shipForUpdate.setShipType(ship.getShipType());
            if (ship.getProdDate() != null) shipForUpdate.setProdDate(ship.getProdDate());
            if (ship.getUsed() != null) shipForUpdate.setUsed(ship.getUsed());
            if (ship.getSpeed() != null) shipForUpdate.setSpeed(ship.getSpeed());
            if (ship.getCrewSize() != null) shipForUpdate.setCrewSize(ship.getCrewSize());
        }
        if (!shipForUpdate.paramsIsOk()) return new ResponseEntity<>(shipForUpdate, HttpStatus.BAD_REQUEST);
        shipServiceImpl.update(shipForUpdate,id);
        return new ResponseEntity<>(shipForUpdate, HttpStatus.OK);
    }

    @PostMapping("/rest/ships")
    public ResponseEntity<Ship> createShip(@RequestBody(required = false) Ship ship) {
        if (ship.getProdDate() == null || ship.getName() == null ||ship.getPlanet() == null ||
                ship.getSpeed() == null || ship.getCrewSize() == null || ship.getShipType() == null
                || ship.getName().isEmpty() || ship.getPlanet().isEmpty() || !ship.paramsIsOk())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        shipServiceImpl.create(ship);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @DeleteMapping (value = "/rest/ships/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id){
        if (id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        final boolean deleted = shipServiceImpl.delete(id);
        return deleted ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

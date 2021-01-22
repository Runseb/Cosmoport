package com.space.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;


@Entity
@Table(name = "ship")
public class Ship  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String planet;
    @Enumerated(value = EnumType.STRING)
    private ShipType shipType;
    private Date prodDate;
    private Boolean isUsed;
    private Double speed;
    private Integer crewSize;
    private Double rating;

    public boolean paramsIsOk() {
        if (name.length() > 50 || name.length() == 0) return false;
        if (planet.length() > 50 || planet.length() == 0) return false;
        if (prodDate.getTime() < 0L || prodDate.getYear()+1900 < 2800 || prodDate.getYear() + 1900 > 3019) return false;
        if (speed<0.01 || speed>0.99) return false;
        if (crewSize < 1 || crewSize > 9999) return false;
        if (isUsed == null) isUsed = false;
        double k = isUsed ? 0.5: 1;
        rating = Math.round((80 * speed * k)/(3019-(prodDate.getYear()+1899))*100)/100.;
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length()<=50) this.name = name;
        else this.name = name.substring(0,50);
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        if (planet.length()<=50) this.planet = planet;
        else this.planet = planet.substring(0,50);
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
//        if (prodDate < 2800) this.prodDate = 2800L;
//        else if (prodDate > 3019) this.prodDate = 3019L;
//        else this.prodDate = prodDate;
        this.prodDate = prodDate;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        if (speed<0.01) this.speed = 0.01;
        else if (speed>0.99) this.speed = 0.99;
        else this.speed = Math.round(speed*100)/100.;
    }

    public Double getRating() {
        return rating;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public Long getId() {
        return id;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }
}

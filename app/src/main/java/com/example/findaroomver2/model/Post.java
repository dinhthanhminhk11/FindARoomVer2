package com.example.findaroomver2.model;

import android.os.Parcelable;

import com.example.findaroomver2.response.supplement.Supplement;

import java.io.Serializable;
import java.util.List;

public class Post implements Serializable  {
    public String idUser;
    public String nameCategory;
    public String title;
    public List<String> images;
    public String cty;
    public String district;
    public String wards;
    public String street;
    public String address;
    public int acreage;
    public int deposit;
    public int bedroom;
    public int bathroom;
    public int countPerson;
    public String startDate;
    public int price;
    public int electricityPrice;
    public int waterPrice;
    public int wifi;
    public String describe;
    public String phone;
    public List<Supplement> supplements;
    public String time;
    public String date;
    public boolean statusConfirm;
    public String messageConfirm;
    public String textConfirm;
    public boolean statusRoom;
    public String messageRoom;
    public boolean statusEdit;
    public String timeEdit;
    public String dateEdit;
    public String _id;
    public int __v;

    public Post(String idUser, String nameCategory, String title, List<String> images, String cty, String district, String wards, String street, String address, int acreage, int deposit, int bedroom, int bathroom, int countPerson, String startDate, int price, int electricityPrice, int waterPrice, int wifi, String describe, String phone, List<Supplement> supplements, String time, String date, boolean statusConfirm, String messageConfirm, String textConfirm, boolean statusRoom, String messageRoom, boolean statusEdit, String timeEdit, String dateEdit, String _id, int __v) {
        this.idUser = idUser;
        this.nameCategory = nameCategory;
        this.title = title;
        this.images = images;
        this.cty = cty;
        this.district = district;
        this.wards = wards;
        this.street = street;
        this.address = address;
        this.acreage = acreage;
        this.deposit = deposit;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.countPerson = countPerson;
        this.startDate = startDate;
        this.price = price;
        this.electricityPrice = electricityPrice;
        this.waterPrice = waterPrice;
        this.wifi = wifi;
        this.describe = describe;
        this.phone = phone;
        this.supplements = supplements;
        this.time = time;
        this.date = date;
        this.statusConfirm = statusConfirm;
        this.messageConfirm = messageConfirm;
        this.textConfirm = textConfirm;
        this.statusRoom = statusRoom;
        this.messageRoom = messageRoom;
        this.statusEdit = statusEdit;
        this.timeEdit = timeEdit;
        this.dateEdit = dateEdit;
        this._id = _id;
        this.__v = __v;
    }

    public Post(String idUser, String nameCategory, String title, List<String> images, String cty, String district, String wards, String street, String address, int acreage, int deposit, int bedroom, int bathroom, int countPerson, String startDate, int price, int electricityPrice, int waterPrice, int wifi, String describe, String phone, List<Supplement> supplements) {
        this.idUser = idUser;
        this.nameCategory = nameCategory;
        this.title = title;
        this.images = images;
        this.cty = cty;
        this.district = district;
        this.wards = wards;
        this.street = street;
        this.address = address;
        this.acreage = acreage;
        this.deposit = deposit;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.countPerson = countPerson;
        this.startDate = startDate;
        this.price = price;
        this.electricityPrice = electricityPrice;
        this.waterPrice = waterPrice;
        this.wifi = wifi;
        this.describe = describe;
        this.phone = phone;
        this.supplements = supplements;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getImages() {
        return images;
    }

    public String getCty() {
        return cty;
    }

    public String getDistrict() {
        return district;
    }

    public String getWards() {
        return wards;
    }

    public String getStreet() {
        return street;
    }

    public String getAddress() {
        return address;
    }

    public int getAcreage() {
        return acreage;
    }

    public int getDeposit() {
        return deposit;
    }

    public int getBedroom() {
        return bedroom;
    }

    public int getBathroom() {
        return bathroom;
    }

    public int getCountPerson() {
        return countPerson;
    }

    public String getStartDate() {
        return startDate;
    }

    public int getPrice() {
        return price;
    }

    public int getElectricityPrice() {
        return electricityPrice;
    }

    public int getWaterPrice() {
        return waterPrice;
    }

    public int getWifi() {
        return wifi;
    }

    public String getDescribe() {
        return describe;
    }

    public String getPhone() {
        return phone;
    }

    public List<Supplement> getSupplements() {
        return supplements;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public boolean isStatusConfirm() {
        return statusConfirm;
    }

    public String getMessageConfirm() {
        return messageConfirm;
    }

    public String getTextConfirm() {
        return textConfirm;
    }

    public boolean isStatusRoom() {
        return statusRoom;
    }

    public String getMessageRoom() {
        return messageRoom;
    }

    public boolean isStatusEdit() {
        return statusEdit;
    }

    public String getTimeEdit() {
        return timeEdit;
    }

    public String getDateEdit() {
        return dateEdit;
    }

    public String get_id() {
        return _id;
    }

    public int get__v() {
        return __v;
    }
}

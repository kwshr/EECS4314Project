package com.common;

public class ShippingAddress {
    private String streetName;
    private int streetNumber;
    private String city;
    private String country;
    private String postalCode;

    public String getStreetName(){
        return streetName;
    } 
    public void setStreetName(String streetName){
        this.streetName= streetName;
    } 
    public int getStreetNumber(){
        return streetNumber;
    } 
    public void setStreetNumber(int streetNumber){
        this.streetNumber= streetNumber;
    } 
    public String getCity(){
        return city;
    } 
    public void setCity(String city){
        this.city= city;
    } 
    public String getCountry(){
        return country;
    } 
    public void setCountry(String country){
        this.country= country;
    } 
    public String getPostalCode(){
        return postalCode;
    } 
    public void setPostalCode(String postalCode){
        this.postalCode= postalCode;
    } 
}

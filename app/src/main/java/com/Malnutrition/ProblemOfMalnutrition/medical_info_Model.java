package com.Malnutrition.ProblemOfMalnutrition;

public class medical_info_Model
{

   private Double weight,hieght;
    String age;
    String Blood_type;


    public medical_info_Model()
    {

    }

    String heart_Rate;
    Boolean Vegetarian;

    public medical_info_Model(Double weight, Double hieght, String age, String blood_type, String heart_Rate, Boolean vegetarian, Boolean blood_pressure, Boolean diabetes) {
        this.weight = weight;
        this.hieght = hieght;
        this.age = age;
        Blood_type = blood_type;
        this.heart_Rate = heart_Rate;
        Vegetarian = vegetarian;
        Blood_pressure = blood_pressure;
        Diabetes = diabetes;
    }

    public Boolean getBlood_pressure() {
        return Blood_pressure;
    }

    public void setBlood_pressure(Boolean blood_pressure) {
        Blood_pressure = blood_pressure;
    }

    Boolean Blood_pressure;

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHieght() {
        return hieght;
    }

    public void setHieght(Double hieght) {
        this.hieght = hieght;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBlood_type() {
        return Blood_type;
    }

    public void setBlood_type(String blood_type) {
        Blood_type = blood_type;
    }

    public String getHeart_Rate() {
        return heart_Rate;
    }

    public void setHeart_Rate(String heart_Rate) {
        this.heart_Rate = heart_Rate;
    }

    public Boolean getVegetarian() {
        return Vegetarian;
    }

    public void setVegetarian(Boolean vegetarian) {
        Vegetarian = vegetarian;
    }

    public Boolean getDiabetes() {
        return Diabetes;
    }

    public void setDiabetes(Boolean diabetes) {
        Diabetes = diabetes;
    }

    Boolean Diabetes;


}

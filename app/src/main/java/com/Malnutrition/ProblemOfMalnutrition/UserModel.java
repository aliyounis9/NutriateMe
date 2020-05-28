package com.Malnutrition.ProblemOfMalnutrition;

public class UserModel
{
    public UserModel(String UID, String email, Boolean bool_check_dataMedical, String name, String phonenumber) {
        this.UID = UID;
        this.email = email;
        this.bool_check_dataMedical = bool_check_dataMedical;
        this.name = name;
        this.phonenumber = phonenumber;
    }

    String UID;
    String email;
    Boolean bool_check_dataMedical;

    public Boolean getBool_check_dataMedical() {
        return bool_check_dataMedical;
    }

    public void setBool_check_dataMedical(Boolean bool_check_dataMedical) {
        this.bool_check_dataMedical = bool_check_dataMedical;
    }




    String name;
    String phonenumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public UserModel() {
        this.email = email;

        this.phonenumber = phonenumber;
        this.UID = UID;
    }


    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }



}

package com.uk.fiveerhealthcare;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by bilalahmed on 04/05/2018.
 * bilalahmed.cs@live.com
 */

public class DModelUser {

    public int User_Id;
    public String Name;
    public String Phone;
    public String Email;
    public String Gender;
    public String DOB;
    public String Iqama_Id;
    public String Iqama_Expiry;
    public String Iqama_Image;
    public String Address;
    public String Image;
    public String Role;
    public int Status;
    public int Is_Under_Review;
    public String Additional_Notes;
    public String createdAt;
    public int Active;
    public boolean isPushOn;
    public boolean isLoggedIn;
    public String Authorization;
    public String Password_Token;

    public DModelUser() {
        this.User_Id = 0;
        this.Name = "";
        this.Phone = "";
        this.Email = "";
        this.Gender = "";
        this.DOB = "";
        this.Iqama_Id = "";
        this.Iqama_Expiry = "";
        this.Iqama_Image = "";
        this.Address = "";
        this.Image = "";
        this.Role = "";
        this.Status = 0;
        this.Is_Under_Review = 0;
        this.Additional_Notes = "";
        this.Active = 0;
        this.isPushOn = true;
        this.isLoggedIn = false;
        this.Authorization = "";
        this.Password_Token = "";
        this.createdAt = "";
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        try {
            Date     date = format.parse(createdAt);
            Log.d("logApiData", "date1:  " + date);


            SimpleDateFormat dayMonth = new SimpleDateFormat("dd-MMMM-yyyy", Locale.ENGLISH);
            String strmonth = dayMonth.format(date);
            Log.d("logApiData","formattedDate:  " +strmonth);

            createdAt = strmonth;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.createdAt = createdAt;
    }

    public int getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(int user_Id) {
        User_Id = user_Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getIqama_Id() {
        return Iqama_Id;
    }

    public void setIqama_Id(String iqama_Id) {
        Iqama_Id = iqama_Id;
    }

    public String getIqama_Expiry() {
        return Iqama_Expiry;
    }

    public void setIqama_Expiry(String iqama_Expiry) {
        Iqama_Expiry = iqama_Expiry;
    }

    public String getIqama_Image() {
        return Iqama_Image;
    }

    public void setIqama_Image(String iqama_Image) {
        Iqama_Image = iqama_Image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getIs_Under_Review() {
        return Is_Under_Review;
    }

    public void setIs_Under_Review(int is_Under_Review) {
        Is_Under_Review = is_Under_Review;
    }

    public String getAdditional_Notes() {
        return Additional_Notes;
    }

    public void setAdditional_Notes(String additional_Notes) {
        Additional_Notes = additional_Notes;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int active) {
        Active = active;
    }

    public boolean isPushOn() {
        return isPushOn;
    }

    public void setPushOn(boolean pushOn) {
        isPushOn = pushOn;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }

    public String getPassword_Token() {
        return Password_Token;
    }

    public void setPassword_Token(String password_Token) {
        Password_Token = password_Token;
    }
}
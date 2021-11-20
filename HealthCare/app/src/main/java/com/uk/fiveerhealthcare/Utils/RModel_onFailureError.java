package com.uk.fiveerhealthcare.Utils;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class RModel_onFailureError {



    public class Errors
    {

    }


        private String type;

        private String title;

        private int status;

        private String traceId;

        @SerializedName("errors")
        private JsonObject errors;

        public void setType(String type){
            this.type = type;
        }
        public String getType(){
            return this.type;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }
        public void setStatus(int status){
            this.status = status;
        }
        public int getStatus(){
            return this.status;
        }
        public void setTraceId(String traceId){
            this.traceId = traceId;
        }
        public String getTraceId(){
            return this.traceId;
        }
        public void setErrors(JsonObject errors){
            this.errors = errors;
        }
        public JsonObject getErrors(){
            return this.errors;
        }



}

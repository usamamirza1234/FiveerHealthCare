package com.uk.fiveerhealthcare.Utils;


public interface ApiMethod {

    String artistImage_Videos_baseURL= "https://art-station-bucket.s3.me-south-1.amazonaws.com/uploads_staging/";


    interface GET {

        String logout = "logout";
        String contactus = "contactus";

        String getNotifications = "notificationList";
        String readNotification = "readNotification";
        String getProfile = "getProfile";
        String changeLanguage = "changeLanguage";
        String getWeather = "getWether";
        String categoryList = "categoryList";
        String artistCategoryList = "getArtist";
        String artistPackage = "getPackagesByArtist";
        String artistCalender = "artistCalender";
        String upcomingBookings = "upcomingBookings";
        String previousBookings = "previousBookings";
        String bookingDetail = "bookingDetail";
        String reviewsList = "reviewList";
        String PayFortSdkToken = "paymentToken";
        String termsConditions = "termsConditions";
        String getCities = "getCities";
        String categoriesDropdown = "categoriesDropdown";
        String getInvoice = "invoicePdf/";
        String getVat = "getVat";
        String getArtistImages = "getArtistImages/";
    }

    interface POST {



        String farmerRegisteration = "FarmerRegistrationVerfication/FarmerRegistrationGeneratePinCode";
        String getDistrict ="FlatMouza/GetAllDistrict" ;
        String getTehsil ="FlatMouza/GetTeshilbyDistrictID" ;
        String getMouza ="FlatMouza/GetMouzaByTehsilID" ;



        String verifyEmploye = "Employee/VerifyEmployee";
        String verifyVA = "Employee/ConfrimPINCode";


        String verifyFarmer= "FarmerRegistrationVerfication/VerifyPinCode";









        String signIn = "login";
        String signUp = "signup";
        String forgotPassword = "forgot";
        String changePassword = "changePassword";
        String addUser = "addUser";
        String updateProfile = "updateProfile";

        String checkEmail = "checkEmail";
        String checkPhone = "checkPhone";
        String sendCode = "sendCode";
        String bookPackage = "bookPackage";
        String addReview = "addReview";
        String redeemPromo = "redeemPromo";
        String paymentRecived = "paymentRecived";
        String cancelBooking = "cancelBooking/";
        String rescheduleBooking = "rescheduleBooking/";

        String subscribe = "subscribe";
        String updateUser = "updateUserProfile";
        String notifications = "allowPush";
        String sociallogin = "sociallogin";
        String socialsignup = "socialsignup";


        String getDesignations = "Designation/GetAllDesignation";

        String updateFarmer = "Farmer/Update" ;
        String SaveFarmer = "Farmer/Save" ;
        String getSpices = "Species/GetAllSpecies" ;
        String addFarm = "FarmerFarms/Save";
        String refreshToken= "Account/generateTokenFromRefreshToken";
        String getMsgofEmp ="InboxMessage/GetMessageOfFarmer?" ;
        String sendSuggestion = "Suggestion/Save" ;
        String getFarmerByCNIC = "Farmer/GetByCNIC" ;
        String getFarmPopulation_GetByFarmID = "FarmerFarmPopulation/GetByFarmerIDAndFarmID";
        String getFarmFarm_GetByFarmerID = "FarmerFarms/GetByFarmerID" ;
        String getAniimalType = "AnimalType/GetSpecieTypes" ;
        String getDiseaseDefinition = "DiseaseDefinition/GetDiseaseDefinitionDetails" ;
        String DiseaseIntimationByFarmer = "DiseaseIntimation/DiseaseIntimationByFarmer" ;
        String DiseaseIntimationByVA = "DiseaseIntimation/DiseaseIntimationByVA";
        String getMsgofFarm ="InboxMessage/GetMessageOfEmployee?" ;

        String forInbox = "Inbox/DiseaseIntimationForInbox?";
        String getSuggestions = "Suggestion/GetByUser" ;
        String generateDiseaseIntimationReport = "Report/generateDiseaseIntimationReport" ;
        String addFarmPopulation = "FarmerFarmPopulation/Save" ;
        String getReport = "DiseaseIntimation/GetReportByReportID" ;
        String getFarmPop = "FarmerFarmPopulation/GetByFarmerID" ;
        String getEmpPerformance = "Employee/performancebyuserlogin" ;
        String getFarmPopbyFarmID = "FarmerFarmPopulation/GetByFarmID" ;
    }


    interface Patch {
        String forgotPassword = "forgot";
        String verifyCode = "verifyCode";
        String resetPassword = "resetPassword";
    }

    interface HEADER {
        String Authorization = "Authorization";
        String Authorization_TOKEN = "token";
        String Lang = "lang";
        String Default_Auth = "mankoosha!and$";
    }

}


package com.uk.fiveerhealthcare.Utils;

import android.os.Environment;

import java.io.File;


public interface AppConstt {

    interface ServerUrl {

        //Weather icon url...
        String Aqua_Weather_Start = "https://developer.accuweather.com/sites/default/files/";
        String Aqua_Weather_End = "-s.png";

        //Image url...
        String Artist_Image_Url = "https://art-station-bucket.s3.me-south-1.amazonaws.com/uploads_staging/";
        //DEBUG urls
        String DEB_BASE_URL = "http://74.208.144.99:9090/";
        String DEB_URL_API = DEB_BASE_URL + "api/";
//        String DEB_URL_API = DEB_BASE_URL + "api/v1/mobile/";

        String DEB_BASE_URL_CHAT = DEB_BASE_URL + "/chat";
        String DEB_URL_IMG = "https://art-station-bucket.s3.me-south-1.amazonaws.com/uploads_staging/";
        String DEB_URL_THUMBS = DEB_BASE_URL + "thumbs/";
        String DEB_URL_SOCKET = DEB_BASE_URL + ":5000";
        String DEB_PAYFORT_PAYMENT_URL = DEB_BASE_URL + "api/invoice/sdk-token/";



        //release server
        String REL_BASE_URL = "http://74.208.144.99:9090/";//TODO also add staging+live FCM files+ Googlemaps(debug+release) to this project
        String REL_URL_API = REL_BASE_URL + "api/v1/";
        String REL_BASE_URL_CHAT = REL_BASE_URL + "/chat";
        String REL_URL_IMG = REL_BASE_URL + "uploads/";
        String REL_URL_THUMBS = REL_BASE_URL + "thumbs/";
        String REL_URL_SOCKET = REL_BASE_URL + ":5000";
        String REL_PAYFORT_PAYMENT_URL = REL_BASE_URL + "api/invoice/sdk-token/";



        String DIRECTION_URL = "https://maps.googleapis.com/maps/api/directions/json";
    }

    interface ServerStatus {
        //Successes
        short OK = 200;
        short CREATED = 201;//
        short ACCEPTED = 202;//
        short NO_CONTENT = 204;//
        short RESET_CONTENT = 205;//

        //Failures
        short BAD_REQUEST = 400;//
        short UNAUTHORIZED = 401;
        short FORBIDDEN = 403;
        short DATABASE_NOT_FOUND = 404;//Items not found in db / Empty list
        short METHOD_NOT_ALLLOWED = 405;
        short NOT_ACCEPTABLE = 406;
        short CONFLICT = 409;//token expired
        short WRONG_ENTRY = 422;
        short INTERNAL_SERVER_ERROR = 500;
        short BAD_GATEWAY = 502;
        short REFRESH_TOKEN = 510;
        short HTTP_VERSION_NOTSUPPORTED = 505;
        short NETWORK_ERROR = 0;

        String GOOGLE_OK = "OK";
    }

    interface AppStates {
        public static int STATE_EMPTY = 0;
        public static int STATE_CHAT_SDK = 1;
    }

    //validateRequestPermissionsRequestCode requires requestCode to be of 8 bits, i.e. range: 0-255.
    int REQ_CODE_PERM_STORAGE = 220;
    int REQ_CODE_PERM_CAMERA = 221;
    int REQ_CODE_PERM_CAMERA_STORAGE = 222;
    int REQ_CODE_PERM_LOCATION = 223;
    int REQ_CODE_SETTINGS_SCREEN = 224;

    String IMAGE_DIR_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/Artstation" + File.separator;
    String PRIVATE_IMAGE_DIR = "myCategories";

    double MAX_PIC_SIZE_MB = (1024 * 1024) * 1.0;//1.5MB
    //Will be best at 1000*750
    int MAX_PIC_HEIGHT = 800;//Portrait case
    int MAX_PIC_WIDTH = 800;//Portrait case


    int VOID_LATLONG = 181;//as this is never possible on earth, so using as default value

    int PAGINATION_START_INDEX = 1;

    //Default Api Retry and Timeout in millis
    int LIMIT_API_RETRY = 0;
    int LIMIT_TIMOUT_MILLIS = 15000;


    String PREF_FILE_NAME = "pref_base_proj";
    String PREF_EXTRA_LANGSWITHCED = "lang_switched";


    //Notification States
    int STATE_NTF_OFF = 0;
    int STATE_NTF_ON = 1;


    //Currency Symbol
    String UNIT_CURRENCY_EN = "SAR ";
    String UNIT_CURRENCY_AR = "ريال";

    //Distance Symbol
    String UNIT_DISTANCE_EN = " KM";
    String UNIT_DISTANCE_AR = "كم ";

    //Duration Symbol
    String UNIT_DURATION_EN = " hrs";
    String UNIT_DURATION_AR = "hrs ";

    //Various quantities
    byte NUM_INTERESTS = 6;

    String PAYFORTLOG = "PAYFORTLOGCAT";


    //Various limits
    int LIMIT_QNTTY_FLOOR = 0;
    int LIMIT_QNTTY_CIELING = 99;
    int LIMIT_DIGIT_CONTACT = 1;//TODO keep it 10
    int LIMIT_DIGIT_PASSWORD = 6;
    int LIMIT_PAGINATION_ERROR = 0;//Number of times the error message shown if pagination couldn't fetch more results

    //General Strings
    String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    //    String PAK_MOBILE_PATTERN = "^((\+92)|(0092))-{0,1}\d{3}-{0,1}\d{7}$|^\d{11}$|^\d{4}-\d{7}\$";
    String PHONE_SEPARATOR = "-";
    String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";//2018-03-28 17:36:06
    String DATE_TIMEZONE = "UTC";


    interface AppLang {
        String LANG_EN = "en";
        String LANG_UR = "ur";
    }

    interface AppRole {
        String ROLE_FARMER = "farmer";
        String ROLE_VA = "va";
    }

    interface DeviceManufacturer {
        String Samsung = "samsung";
        String Huawei = "HUAWEI";
        String Xiaomi = "Xiaomi";
    }

    interface UserType {
        int CUSTOMER = 1;
        int DRIVER = 2;
    }

    interface FragTag {
        //Fragment Tags
        String FN_SplashFragment = "SplashFragment";
        String FN_SignInFragment = "SignInFragment";
        String FN_SignUpFragment = "SignUpFragment";
        String FN_ForgotPasswordFragment = "ForgotPasswordFragment";

        String FN_PreSignInFragment = "SignInVAFragment";

        String FN_HomeFragment = "HomeFragment";




        String FN_ElectricityHomeFragment = "ElectricityHomeFragment" ;

        String FN_WaterHomeFragment = "WaterHomeFragment" ;
        String FN_GasHomeFragment = "GasHomeFragment" ;
        String FN_InternetHomeFragment = "InternetHomeFragment" ;
        String FN_TermsConditionsFragment = "TermsConditionsFragment" ;
        String FN_CustomCalenderFragment = "CustomCalenderFragment" ;
        String FN_EditProfileFragment = "EditProfileFragment" ;
        String FN_MyBillsFragment = "MyBillsFragment" ;
        String FN_HistoryFragment = "HistoryFragment" ;
        String FN_SettingsFragment = "SettingsFragment" ;
        String FN_MaintenanceScheduleFragment = "MaintenanceScheduleFragment" ;
        String FN_OutageScheduleFragment = "OutageScheduleFragment" ;
        String FN_AlertsFragment = "AlertsFragment" ;
        String FN_BillAnaylsisFragment = "BillAnaylsisFragment" ;
        String FN_SignUPFBFragment ="SignUPFacebookFragment" ;
    }


    interface ActvtyTag {
        //Activity Tags
        String AN_IntroActivity = "IntroActivity";
        String AN_MainActivity = "MainActivity";

    }

    interface CalculationType {
        public String total = "total";
        public String delivery = "delivery";
        public String food = "food";

    }

    interface INTRO_ToolbarStates {

        byte choice = 0;
        byte signinVA = 1;
        byte signinFarmer = 2;

        byte defualt = 4;

    }

    interface ToolbarState {
        //State to show certian items in toolbar
        byte TOOLBAR_TITLE_HIDDEN = 0;
        byte TOOLBAR_BACK_HIDDEN = 1;
        byte TOOLBAR_MORE_HIDDEN = 2;



        byte TOOLBAR_HIDDEN=6;
        byte TOOLBAR_VISIBLE=7;
    }

    interface Notifications {
        //Notifications read/unread by the user
        int STATUS_UNREAD = 0;
        int STATUS_READ = 1;


        String LBCT_INTENT_REFRESH_NOTIFICATIONS = "LBCT_INTENT_REFRESH_NOTIFICATIONS";
        String LBCT_INTENT_TRACK_ORDER = "LBCT_INTENT_TRACK_ORDER";

        String PUSH_TYPE = "push_type";
        String PUSH_ORDER_ID = "push_order_id";

        int TYPE_NIL = -1;
        int TYPE_PLACED = 0;
        int TYPE_CONFIRMED = 1;
        int TYPE_OVEN = 2;
        int TYPE_READY = 3;
        int TYPE_CARRYOUT = 4;
        int TYPE_DELIVERY_ONTHEWAY = 5;//Will be fired in case of Ordertype delivery only
        int TYPE_COMPLETED = 6;
        int TYPE_ASSIGNED_TODRIVER = 7;//Will be fired in case of Ordertype delivery only
        int TYPE_ADMIN_PUSH = 8;

    }

    //General Network messages
    interface NetworkMsg {
        String ERROR_PREFIX = "";//"Error: ";
        String EXCEPTION_PREFIX = "Exception:\n";
        String ERROR_NETWORK_EN = "No internet connection";
        String ERROR_NETWORK_AR = " urdu add";
        String EXCEPTION_NETWORK_EN = "Oops! Something went wrong";
        String EXCEPTION_NETWORK_AR = "Oops! Something went wrong";
    }

    interface MsgError {
        //General App messages
        String PREFIX = "Error: ";
        String NETWORK = "Please check your Internet connection";
        String MSG_ERROR_NETWORK_EN = "No internet connection";
        String MSG_ERROR_NETWORK_AR = " urdu add";

        String ENABLE_LOCATION = "Please enable location from your device";
        String EMAIL = "Please enter valid email";
        String MOBNUM = "Please enter valid mobile number";
        String PASSWORD_EMPTY = "Password field cannot be left empty";
        String PASSWORD_SHORT = "Please enter atleast 6 characters password";
        String PASSWORD_NOMATCH = "Both the passwords donot match";
        String CODE_EMPTY = "Please fill-in the code above, you recieved via SMS";
        String CODE_WRONG = "Please enter the correct code, you recieved via SMS";
        String CONTACTS_SYNC = "Contacts syncing faile.\nPlease retry";
    }

    interface ToastTimeOut {
        int toastDuration = 500;
    }

    interface guestAuth {
        String guestAuth = "ARTS!and$ANDP";
    }

    // DEFINED/FIXED CATEGORY ID ON SERVER
    class IntentPreference {
        public static final int SOURCE_LOCATION_INTENT_CODE = 94;
        public static final int PACKAGE_LOCATION_INTENT_CODE = 93;
        public static final int HOME_SCREEN_INTENT_CODE = 95;
        public static final int FOOD_ID = 17;
        public static final int BEAUTY_ID = 64;
        public static final int FUN_ID = 15;
        public static final int RetailnServices_ID = 65;
    }

    interface OtpMessages {
        public String otpSucess = "Please check your email for OTP code";
    }

    class LocUpdate {
        public static final long MIN_TIME_BW_UPDATES = 1000 * 5;
        public static final float MIN_DISTANCE_BW_UPDATES = 5;
    }

    public interface EventStatus {
        String waiting = "waiting";
        String paid = "paid";
        String unpaid = "unpaid";
        String approved = "approved";
        String rejected = "rejected";
        String rescadule = "reschedule";
        String reschedule = "reschedule";
        String canceled = "canceled";
    }

    public interface ViewPagerState {
        public int upcomingBookingFragment = 0;
        public int previousBookingFragment = 1;
        public int addReviews = 0;
        public int AllReviews = 1;
    }

}


//(["'])(\\?.)*?\1


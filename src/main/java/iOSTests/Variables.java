package iOSTests;

import org.openqa.selenium.Dimension;

@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
public class Variables {

    @SuppressWarnings("RedundantStringConstructorCall")
    static String userlogin = new String();
    static String userpass;
    static String petname;
    static String breed;
    static String petweight;
    static String petheight;
    static String birthmonth;
    static String birthyear;
    static String petage;
    static Dimension screensize;
    static String devicename;
    static String phonenumber;

    static {
        userpass = "12345678";
        petname="Alessia";
        petweight="50";
        petheight="43";
        birthmonth="5";
        birthyear="2014";
        petage="3";
        breed="овчарка";
    }
}

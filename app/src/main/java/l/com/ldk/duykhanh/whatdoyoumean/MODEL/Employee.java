package l.com.ldk.duykhanh.whatdoyoumean.MODEL;

import java.util.Date;

public class Employee {
    private int mID;
    private String mFullName;
    private String mUser;
    private String mPass;
    private String mAddress;
    private Date mBirthday;
    private String mPhone;
    private String mOffice;
    private String mEmail;

    public Employee() {
    }

    public Employee(String mFullName, String mUser, String mPass, String mAddress, Date mBirthday, String mPhone, String mOffice, String mEmail) {
        this.mFullName = mFullName;
        this.mUser = mUser;
        this.mPass = mPass;
        this.mAddress = mAddress;
        this.mBirthday = mBirthday;
        this.mPhone = mPhone;
        this.mOffice = mOffice;
        this.mEmail = mEmail;
    }

    public Employee(int mID, String mFullName, String mUser, String mPass, String mAddress, Date mBirthday, String mPhone, String mOffice, String mEmail) {
        this.mID = mID;
        this.mFullName = mFullName;
        this.mUser = mUser;
        this.mPass = mPass;
        this.mAddress = mAddress;
        this.mBirthday = mBirthday;
        this.mPhone = mPhone;
        this.mOffice = mOffice;
        this.mEmail = mEmail;
    }

    public int getmID(int i) {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmFullName() {
        return mFullName;
    }

    public void setmFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public String getmUser() {
        return mUser;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }

    public String getmPass() {
        return mPass;
    }

    public void setmPass(String mPass) {
        this.mPass = mPass;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public Date getmBirthday() {
        return mBirthday;
    }

    public void setmBirthday(Date mBirthday) {
        this.mBirthday = mBirthday;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmOffice() {
        return mOffice;
    }

    public void setmOffice(String mOffice) {
        this.mOffice = mOffice;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }
}

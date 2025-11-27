package com.example.finalhamada.data.MyTaskTable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * فئة تمثل المستخدم في التطبيق
 * تحتوي على جميع صفات المستخدم، وتُستخدم كجدول داخل قاعدة البيانات
 */
@Entity
/**
 * Class: MyTask
 * Purpose (EN): Auto-generated documentation for class MyTask.
 * الهدف (AR): توثيق تلقائي للكلاس MyTask.
 * TODO: Add more detailed description about class functionality
 */
public class MyTask {

    @PrimaryKey(autoGenerate = true)
    /** رقم المستخدم (يتولّد تلقائياً) */
    private int id;

    /** الاسم الكامل للمستخدم */
    private String fullName;

    /** البريد الإلكتروني */
    private String email;

    /** كلمة المرور */
    private String password;

    /** رقم الهاتف */
    private String phone;

    /** الجنس (ذكر / أنثى) */
    private String gender;

    /** العمر */
    private int age;

    /** الطول بالسنتيمتر */
    private float height;

    /** الوزن الحالي بالكيلوغرام */
    private float weight;

    /** الوزن المستهدف */
    private float goalWeight;

    /** نوع الهدف (خسارة وزن / بناء عضلات / الحفاظ على الوزن) */
    private String goalType;

    /** مستوى النشاط (منخفض / متوسط / مرتفع) */
    private String activityLevel;

    /** عدد السعرات المستهدفة يومياً */
    private int dailyCaloriesTarget;

    /** رابط أو مسار صورة المستخدم */
    private String profileImage;

    /** تاريخ إنشاء الحساب */
    private String createdAt;

    /** آخر تسجيل دخول */
    private String lastLogin;

    /** هل التذكيرات مفعّلة */
    private boolean notificationsEnabled;

     public MyTask()
     {}



    // ===============================
    //       Getters & Setters
    // ===============================

/**
 * Method: getId
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return BasicType(dimensions=[], name=int) - description
 */
    /**
 * دالة getId: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public int getId() {
        return id;
    }
/**
 * Method: setId
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param id - description
 */
    /**
 * دالة setId: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setId(int id) {
        this.id = id;
    }

/**
 * Method: getFullName
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=String, sub_type=None) - description
 */
    /**
 * دالة getFullName: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public String getFullName() {
        return fullName;
    }
/**
 * Method: setFullName
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param fullName - description
 */
    /**
 * دالة setFullName: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setFullName(String fullName) {
        this.fullName = fullName;
    }

/**
 * Method: getEmail
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=String, sub_type=None) - description
 */
    /**
 * دالة getEmail: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public String getEmail() {
        return email;
    }
/**
 * Method: setEmail
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param email - description
 */
    /**
 * دالة setEmail: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setEmail(String email) {
        this.email = email;
    }

/**
 * Method: getPassword
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=String, sub_type=None) - description
 */
    /**
 * دالة getPassword: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public String getPassword() {
        return password;
    }
/**
 * Method: setPassword
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param password - description
 */
    /**
 * دالة setPassword: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setPassword(String password) {
        this.password = password;
    }

/**
 * Method: getPhone
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=String, sub_type=None) - description
 */
    /**
 * دالة getPhone: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public String getPhone() {
        return phone;
    }
/**
 * Method: setPhone
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param phone - description
 */
    /**
 * دالة setPhone: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setPhone(String phone) {
        this.phone = phone;
    }

/**
 * Method: getGender
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=String, sub_type=None) - description
 */
    /**
 * دالة getGender: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public String getGender() {
        return gender;
    }
/**
 * Method: setGender
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param gender - description
 */
    /**
 * دالة setGender: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setGender(String gender) {
        this.gender = gender;
    }

/**
 * Method: getAge
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return BasicType(dimensions=[], name=int) - description
 */
    /**
 * دالة getAge: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public int getAge() {
        return age;
    }
/**
 * Method: setAge
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param age - description
 */
    /**
 * دالة setAge: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setAge(int age) {
        this.age = age;
    }

/**
 * Method: getHeight
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return BasicType(dimensions=[], name=float) - description
 */
    /**
 * دالة getHeight: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public float getHeight() {
        return height;
    }
/**
 * Method: setHeight
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param height - description
 */
    /**
 * دالة setHeight: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setHeight(float height) {
        this.height = height;
    }

/**
 * Method: getWeight
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return BasicType(dimensions=[], name=float) - description
 */
    /**
 * دالة getWeight: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public float getWeight() {
        return weight;
    }
/**
 * Method: setWeight
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param weight - description
 */
    /**
 * دالة setWeight: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setWeight(float weight) {
        this.weight = weight;
    }

/**
 * Method: getGoalWeight
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return BasicType(dimensions=[], name=float) - description
 */
    /**
 * دالة getGoalWeight: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public float getGoalWeight() {
        return goalWeight;
    }
/**
 * Method: setGoalWeight
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param goalWeight - description
 */
    /**
 * دالة setGoalWeight: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setGoalWeight(float goalWeight) {
        this.goalWeight = goalWeight;
    }

/**
 * Method: getGoalType
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=String, sub_type=None) - description
 */
    /**
 * دالة getGoalType: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public String getGoalType() {
        return goalType;
    }
/**
 * Method: setGoalType
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param goalType - description
 */
    /**
 * دالة setGoalType: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

/**
 * Method: getActivityLevel
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=String, sub_type=None) - description
 */
    /**
 * دالة getActivityLevel: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public String getActivityLevel() {
        return activityLevel;
    }
/**
 * Method: setActivityLevel
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param activityLevel - description
 */
    /**
 * دالة setActivityLevel: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

/**
 * Method: getDailyCaloriesTarget
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return BasicType(dimensions=[], name=int) - description
 */
    /**
 * دالة getDailyCaloriesTarget: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public int getDailyCaloriesTarget() {
        return dailyCaloriesTarget;
    }
/**
 * Method: setDailyCaloriesTarget
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param dailyCaloriesTarget - description
 */
    /**
 * دالة setDailyCaloriesTarget: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setDailyCaloriesTarget(int dailyCaloriesTarget) {
        this.dailyCaloriesTarget = dailyCaloriesTarget;
    }

/**
 * Method: getProfileImage
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=String, sub_type=None) - description
 */
    /**
 * دالة getProfileImage: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public String getProfileImage() {
        return profileImage;
    }
/**
 * Method: setProfileImage
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param profileImage - description
 */
    /**
 * دالة setProfileImage: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

/**
 * Method: getCreatedAt
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=String, sub_type=None) - description
 */
    /**
 * دالة getCreatedAt: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public String getCreatedAt() {
        return createdAt;
    }
/**
 * Method: setCreatedAt
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param createdAt - description
 */
    /**
 * دالة setCreatedAt: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

/**
 * Method: getLastLogin
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=String, sub_type=None) - description
 */
    /**
 * دالة getLastLogin: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public String getLastLogin() {
        return lastLogin;
    }
/**
 * Method: setLastLogin
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param lastLogin - description
 */
    /**
 * دالة setLastLogin: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

/**
 * Method: isNotificationsEnabled
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return BasicType(dimensions=[], name=boolean) - description
 */
    /**
 * دالة isNotificationsEnabled: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }
/**
 * Method: setNotificationsEnabled
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param notificationsEnabled - description
 */
    /**
 * دالة setNotificationsEnabled: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }


    // ===============================
    //             toString
    // ===============================

    @Override
/**
 * Method: toString
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=String, sub_type=None) - description
 */
    /**
 * دالة toString: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", goalWeight=" + goalWeight +
                ", goalType='" + goalType + '\'' +
                ", activityLevel='" + activityLevel + '\'' +
                ", dailyCaloriesTarget=" + dailyCaloriesTarget +
                ", profileImage='" + profileImage + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                ", notificationsEnabled=" + notificationsEnabled +
                '}';
    }
}

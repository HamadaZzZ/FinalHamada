package com.example.finalhamada.data.MyFitTrackTable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * فئة لتخزين بيانات تتبّع النشاط الرياضي
 */
@Entity
/**
 * Class: FitTrack
 * TODO: Add more detailed description about class functionality
 */
public class FitTrack {

    /** رقم النشاط (المفتاح الأساسي) */
    @PrimaryKey(autoGenerate = true)
    public long id;

    /** رقم المستخدم المرتبط بالنشاط */
    public long userId;

    /** التاريخ */
    public String date;

    /** عدد الخطوات */
    public int steps;

    /** السعرات الحرارية المحروقة */
    public double calories;

    /** المسافة المقطوعة */
    public double distance;

    /** مدّة النشاط */
    public long duration;

    /** نوع النشاط (جري، مشي، ركض...) */
    public String activityType;

    /** ملاحظات إضافية */
    public String note;


    // Getters and Setters
/**
 * Method: getId
 * @return BasicType(dimensions=[], name=long) - description
 */
    /**
 * دالة getId: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public long getId() {
        return id;
    }

/**
 * Method: setId
 *
 * Parameters:
 * @param id - description
 */
    /**
 * دالة setId: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setId(long id) {
        this.id = id;
    }

/**
 * Method: getUserId
 * @return BasicType(dimensions=[], name=long) - description
 */
    /**
 * دالة getUserId: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public long getUserId() {
        return userId;
    }

/**
 * Method: setUserId
 * Parameters:
 * @param userId - description
 */
    /**
 * دالة setUserId: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setUserId(long userId) {
        this.userId = userId;
    }

/**
 * Method: getDate
 * @return ReferenceType(arguments=None, dimensions=[], name=String, sub_type=None) - description
 */
    /**
 * دالة getDate: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public String getDate() {
        return date;
    }

/**
 * Method: setDate
 * Purpose (EN): Describe what this method does.
 * Parameters:
 * @param date - description
 */
    /**
 * دالة setDate: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setDate(String date) {
        this.date = date;
    }

/**
 * Method: getSteps
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return BasicType(dimensions=[], name=int) - description
 */
    /**
 * دالة getSteps: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public int getSteps() {
        return steps;
    }

/**
 * Method: setSteps
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param steps - description
 */
    /**
 * دالة setSteps: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setSteps(int steps) {
        this.steps = steps;
    }

/**
 * Method: getCalories
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return BasicType(dimensions=[], name=double) - description
 */
    /**
 * دالة getCalories: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public double getCalories() {
        return calories;
    }

/**
 * Method: setCalories
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param calories - description
 */
    /**
 * دالة setCalories: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setCalories(double calories) {
        this.calories = calories;
    }

/**
 * Method: getDistance
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return BasicType(dimensions=[], name=double) - description
 */
    /**
 * دالة getDistance: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public double getDistance() {
        return distance;
    }

/**
 * Method: setDistance
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param distance - description
 */
    /**
 * دالة setDistance: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setDistance(double distance) {
        this.distance = distance;
    }

/**
 * Method: getDuration
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return BasicType(dimensions=[], name=long) - description
 */
    /**
 * دالة getDuration: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public long getDuration() {
        return duration;
    }

/**
 * Method: setDuration
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param duration - description
 */
    /**
 * دالة setDuration: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setDuration(long duration) {
        this.duration = duration;
    }

/**
 * Method: getActivityType
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=String, sub_type=None) - description
 */
    /**
 * دالة getActivityType: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public String getActivityType() {
        return activityType;
    }

/**
 * Method: setActivityType
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param activityType - description
 */
    /**
 * دالة setActivityType: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

/**
 * Method: getNote
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * @return ReferenceType(arguments=None, dimensions=[], name=String, sub_type=None) - description
 */
    /**
 * دالة getNote: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public String getNote() {
        return note;
    }

/**
 * Method: setNote
 * Purpose (EN): Describe what this method does.
 * الهدف (AR): شرح مختصر لوظيفة هذه الدالة.
 *
 * Parameters:
 * @param note - description
 */
    /**
 * دالة setNote: تقوم بتنفيذ الغرض الخاص بها كما هو موضح داخل الكود.
 */
public void setNote(String note) {
        this.note = note;
    }

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
        return "FitTrack{" +
                "id=" + id +
                ", userId=" + userId +
                ", date='" + date + '\'' +
                ", steps=" + steps +
                ", calories=" + calories +
                ", distance=" + distance +
                ", duration=" + duration +
                ", activityType='" + activityType + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}

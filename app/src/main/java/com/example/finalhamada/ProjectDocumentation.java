package com.example.finalhamada;

/**
 * ============================================================
 * PROJECT DOCUMENTATION – FINAL EXAM REVIEW (FULL VERSION)
 * ============================================================
 *
 * ================================
 * 🔹 BUTTONS & EVENTS
 * ================================
 *
 * 1) setOnClickListener(View.OnClickListener)
 * ينفذ الكود عند ضغط المستخدم على الزر.
 * يعتبر User Event.
 *
 * ================================
 * 🔹 FIREBASE AUTHENTICATION
 * ================================
 *
 * 2) FirebaseAuth.getInstance()
 * إنشاء اتصال مع Firebase Authentication.
 *
 * 3) signInWithEmailAndPassword(email, password)
 * تسجيل دخول مستخدم عبر السيرفر.
 *
 * 4) createUserWithEmailAndPassword(email, password)
 * إنشاء حساب جديد.
 *
 * 5) addOnCompleteListener()
 * لمعرفة نجاح أو فشل العملية غير المتزامنة.
 *
 * 6) getCurrentUser()
 * يرجع المستخدم الحالي أو null.
 *
 * 7) signOut()
 * تسجيل خروج المستخدم.
 *
 * ================================
 * 🔹 FIREBASE REALTIME DATABASE
 * ================================
 *
 * 8) FirebaseDatabase.getInstance().getReference()
 * الحصول على مرجع قاعدة البيانات.
 *
 * 9) child("users")
 * الدخول لمسار داخل JSON Tree.
 *
 * 10) push()
 * إنشاء عنصر جديد بمفتاح عشوائي.
 *
 * 11) setValue(object)
 * حفظ كائن داخل قاعدة البيانات.
 *
 * 12) updateChildren(map)
 * تحديث بيانات بدون حذف الباقي.
 *
 * 13) removeValue()
 * حذف عنصر من قاعدة البيانات.
 *
 * 14) addValueEventListener()
 * قراءة البيانات والاستماع لأي تغيير يحدث.
 *
 * ================================
 * 🔹 FRAGMENT (مهم جداً)
 * ================================
 *
 * 15) onCreate()
 * إنشاء الـ Fragment.
 *
 * 16) onCreateView()
 * ربط Fragment مع XML.
 * نستخدم فيه inflater.inflate().
 *
 * 17) onViewCreated()
 * ينفذ بعد إنشاء الواجهة.
 * غالباً نربط RecyclerView هنا.
 *
 * 18) getArguments()
 * استقبال بيانات مرسلة عبر Bundle.
 *
 * ================================
 * 🔹 RECYCLER VIEW
 * ================================
 *
 * 19) RecyclerView
 * عرض قائمة عناصر بكفاءة عالية.
 *
 * 20) setLayoutManager(new LinearLayoutManager(context))
 * تحديد شكل عرض القائمة.
 *
 * 21) setAdapter(adapter)
 * ربط البيانات مع الواجهة.
 *
 * 22) notifyDataSetChanged()
 * تحديث الشاشة بعد تغيير البيانات.
 *
 * 23) ViewHolder
 * يحتفظ بعناصر الواجهة لكل عنصر.
 *
 * ================================
 * 🔹 INTENT & NAVIGATION
 * ================================
 *
 * 24) Intent
 * الانتقال بين Activities.
 *
 * 25) startActivity(intent)
 * تشغيل Activity جديدة.
 *
 * 26) finish()
 * إغلاق الشاشة الحالية.
 *
 * ================================
 * 🔹 BUNDLE
 * ================================
 *
 * 27) putString(key, value)
 * إرسال بيانات.
 *
 * 28) getString(key)
 * استقبال بيانات.
 *
 * ================================
 * 🔹 ROOM DATABASE
 * ================================
 *
 * 29) @Entity
 * تمثل جدول.
 *
 * 30) @Dao
 * يحتوي أوامر قاعدة البيانات.
 *
 * 31) @Insert
 * إضافة عنصر.
 *
 * 32) @Query
 * تنفيذ استعلام.
 *
 * 33) @Delete
 * حذف عنصر.
 *
 * 34) AppDatabase.getDatabase(context)
 * الحصول على قاعدة البيانات المحلية.
 *
 * ================================
 * 🔹 LIVEDATA & VIEWMODEL (لو مستخدمة)
 * ================================
 *
 * 35) ViewModel
 * يحتفظ بالبيانات عند تدوير الشاشة.
 *
 * 36) LiveData
 * يراقب التغييرات ويرسل تحديث تلقائي للواجهة.
 *
 * 37) observe()
 * مراقبة البيانات.
 *
 * ================================
 * 🔹 VALIDATION & SAFETY
 * ================================
 *
 * 38) isEmpty()
 * فحص المدخلات.
 *
 * 39) try-catch
 * منع انهيار التطبيق.
 *
 * ================================
 * 🔹 SHARED PREFERENCES
 * ================================
 *
 * 40) getSharedPreferences()
 * تخزين بيانات بسيطة داخل الجهاز.
 *
 * 41) putString() / apply()
 * حفظ البيانات.
 *
 * ============================================================
 * END OF DOCUMENTATION
 * ============================================================
 */

public class ProjectDocumentation {
    // Documentation class only
}
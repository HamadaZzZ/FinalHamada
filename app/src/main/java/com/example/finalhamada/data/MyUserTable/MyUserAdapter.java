package com.example.finalhamada.data.MyUserTable; // مكان الكلاس داخل المشروع

import android.content.Context; // يمثل Context الحالي للتطبيق
import android.view.LayoutInflater; // يستخدم لتحويل XML إلى View حقيقي
import android.view.View; // يمثل عنصر واجهة داخل Android
import android.view.ViewGroup; // الحاوية الأب لعناصر RecyclerView
import android.widget.TextView; // يستخدم لعرض النصوص

import androidx.annotation.NonNull; // تعني أن القيمة لا يجب أن تكون null
import androidx.recyclerview.widget.RecyclerView; // يستخدم لعرض قائمة عناصر بكفاءة

import com.example.finalhamada.R; // للوصول إلى ملفات المشروع مثل layouts و ids

import java.util.List; // لتخزين قائمة المستخدمين

/**
 * ============================================================
 * MyUserAdapter
 * ============================================================
 *
 * هذا الكلاس هو Adapter
 * خاص بعرض المستخدمين
 * داخل RecyclerView.
 *
 * وظيفته الأساسية:
 * - أخذ قائمة المستخدمين.
 * - ربط البيانات مع الواجهة.
 * - عرض الاسم والإيميل والهاتف.
 * - التعامل مع الضغط على المستخدم.
 *
 * أهمية هذا الكلاس:
 * RecyclerView لا يستطيع
 * عرض البيانات وحده،
 * لذلك استخدمت Adapter
 * ليكون وسيط بين:
 * - البيانات
 * - والواجهة.
 */
public class MyUserAdapter extends RecyclerView.Adapter<MyUserAdapter.MyUserViewHolder> {

    /**
     * ============================================================
     * context
     * ============================================================
     *
     * يمثل Context الحالي للتطبيق.
     *
     * استخدمته للوصول إلى:
     * - LayoutInflater
     * - Resources
     * - ملفات XML
     */
    private Context context;

    /**
     * ============================================================
     * userList
     * ============================================================
     *
     * تحتوي على جميع المستخدمين
     * الذين سيتم عرضهم
     * داخل RecyclerView.
     */
    private List<MyUser> userList;

    /**
     * ============================================================
     * listener
     * ============================================================
     *
     * مستمع للتعامل
     * مع الضغط على المستخدم.
     *
     * استخدمته حتى لا يكون Adapter
     * مسؤولًا عن تنفيذ الأحداث مباشرة.
     */
    private OnUserClickListener listener;

    /**
     * ============================================================
     * Constructor
     * ============================================================
     *
     * يعمل عند إنشاء MyUserAdapter.
     *
     * يستقبل:
     * - Context
     * - قائمة المستخدمين
     * - Listener للتفاعل
     *
     * @param context Context الحالي
     * @param userList قائمة المستخدمين
     * @param listener مستمع الضغط على المستخدم
     */
    public MyUserAdapter(
            Context context,
            List<MyUser> userList,
            OnUserClickListener listener
    ) {

        /**
         * حفظ Context.
         */
        this.context = context;

        /**
         * حفظ قائمة المستخدمين.
         */
        this.userList = userList;

        /**
         * حفظ Listener.
         */
        this.listener = listener;
    }

    /**
     * ============================================================
     * onCreateViewHolder
     * ============================================================
     *
     * مسؤولة عن إنشاء
     * شكل العنصر الواحد
     * داخل RecyclerView.
     *
     * تقوم بتحويل useritems.xml
     * إلى View حقيقي.
     *
     * @param parent الحاوية الأب
     * @param viewType نوع العنصر
     *
     * @return ViewHolder جديد
     */
    @NonNull
    @Override
    public MyUserViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        /**
         * LayoutInflater:
         * يحول XML إلى View.
         */
        View view = LayoutInflater.from(context).inflate(
                R.layout.useritems,
                parent,
                false
        );

        /**
         * إرجاع ViewHolder جديد.
         */
        return new MyUserViewHolder(view);
    }

    /**
     * ============================================================
     * onBindViewHolder
     * ============================================================
     *
     * مسؤولة عن ربط البيانات
     * مع عناصر الواجهة.
     *
     * تعمل لكل عنصر
     * داخل RecyclerView.
     *
     * @param holder يحتوي عناصر الواجهة
     * @param position مكان العنصر داخل القائمة
     */
    @Override
    public void onBindViewHolder(
            @NonNull MyUserViewHolder holder,
            int position
    ) {

        /**
         * الحصول على المستخدم الحالي
         * حسب موقعه داخل القائمة.
         */
        MyUser user = userList.get(position);

        /**
         * عرض اسم المستخدم.
         */
        holder.tvName.setText(user.getFullName());

        /**
         * عرض البريد الإلكتروني.
         */
        holder.tvEmail.setText(user.getEmail());

        /**
         * عرض رقم الهاتف.
         */
        holder.tvPhone.setText(user.getPhone());

        /**
         * عند الضغط على العنصر:
         * يتم إرسال المستخدم الحالي
         * إلى Listener.
         */
        holder.itemView.setOnClickListener(v -> {

            /**
             * التحقق أن listener ليس null
             * حتى لا يحدث Crash.
             */
            if (listener != null) {

                /**
                 * إرسال المستخدم
                 * إلى الشاشة.
                 */
                listener.onUserClick(user);
            }
        });
    }

    /**
     * ============================================================
     * getItemCount
     * ============================================================
     *
     * ترجع عدد العناصر
     * داخل RecyclerView.
     *
     * @return عدد المستخدمين
     */
    @Override
    public int getItemCount() {
        return userList.size();
    }

    /**
     * ============================================================
     * MyUserViewHolder
     * ============================================================
     *
     * ViewHolder خاص
     * بعرض المستخدم.
     *
     * وظيفته:
     * تخزين مراجع عناصر الواجهة
     * لتحسين الأداء.
     *
     * بدل استخدام findViewById
     * في كل مرة،
     * يتم حفظ العناصر مرة واحدة.
     */
    public static class MyUserViewHolder extends RecyclerView.ViewHolder {

        /**
         * TextViews لعرض:
         * - الاسم
         * - الإيميل
         * - الهاتف
         */
        TextView tvName, tvEmail, tvPhone;

        /**
         * ============================================================
         * Constructor
         * ============================================================
         *
         * يعمل عند إنشاء ViewHolder جديد.
         *
         * يقوم بربط عناصر XML
         * مع متغيرات Java.
         *
         * @param itemView يمثل شكل العنصر الواحد
         */
        public MyUserViewHolder(@NonNull View itemView) {
            super(itemView);

            /**
             * ربط TextView الخاص بالاسم.
             */
            tvName = itemView.findViewById(R.id.tvName);

            /**
             * ربط TextView الخاص بالإيميل.
             */
            tvEmail = itemView.findViewById(R.id.tvEmail);

            /**
             * ربط TextView الخاص بالهاتف.
             */
            tvPhone = itemView.findViewById(R.id.tvPhone);
        }
    }

    /**
     * ============================================================
     * OnUserClickListener
     * ============================================================
     *
     * Interface تستخدم
     * للتعامل مع الضغط
     * على المستخدم.
     *
     * استخدمتها حتى يتم إرسال
     * المستخدم المضغوط عليه
     * إلى الشاشة أو الـ Fragment.
     */
    public interface OnUserClickListener {

        /**
         * ============================================================
         * onUserClick
         * ============================================================
         *
         * تعمل عند ضغط المستخدم
         * على عنصر داخل RecyclerView.
         *
         * @param user المستخدم الذي تم الضغط عليه
         */
        void onUserClick(MyUser user);
    }
}
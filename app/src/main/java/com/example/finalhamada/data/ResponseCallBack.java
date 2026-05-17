package com.example.finalhamada.data;

/**
 * واجهة لمعالجة رد Gemini (نجاح / خطأ)
 */
    public interface ResponseCallBack {

        /**
         * يتم استدعاؤها عند نجاح الطلب
         * @param response نص الرد من Gemini
         */
        void onResponse(String response);

        /**
         * يتم استدعاؤها عند حدوث خطأ
         * @param error تفاصيل الخطأ
         */
        void onError(Throwable error);
    }

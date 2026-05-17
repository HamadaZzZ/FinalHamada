package com.example.finalhamada.data;

import androidx.annotation.NonNull;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.type.GenerateContentResponse;

import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

/**
 * GeminiHelper
 * ----------------------------------------------
 * كلاس مساعد للتعامل مع Google Gemini AI.
 *
 * يوفر:
 * - إنشاء اتصال مع الموديل
 * - إرسال نص (prompt)
 * - استلام الرد باستخدام Callback
 *
 * يستخدم نمط Singleton لضمان وجود نسخة واحدة فقط.
 */
public class GeminiHelper {

    /** إصدار نموذج Gemini المستخدم */
    public static final String GEMINI_VERSION = "gemini-2.0-flash";

    /** مفتاح API الخاص بالخدمة */
    private static final String GEMINI_API_KEY = "PUT_YOUR_API_KEY_HERE";

    /** نسخة واحدة من الكلاس (Singleton) */
    private static GeminiHelper instance;

    /** كائن الموديل المسؤول عن توليد النص */
    private final GenerativeModel gemini;

    /**
     * Constructor (خاص)
     * ----------------------------------------------
     * يتم إنشاء الموديل وربطه بالمفتاح والإصدار.
     */
    private GeminiHelper() {
        gemini = new GenerativeModel(
                GEMINI_VERSION,
                GEMINI_API_KEY
        );
    }

    /**
     * getInstance
     * ----------------------------------------------
     * يرجع نفس الكائن دائماً (Singleton)
     *
     * @return نسخة من GeminiHelper
     */
    public static GeminiHelper getInstance() {
        if (instance == null) {
            instance = new GeminiHelper();
        }
        return instance;
    }

    /**
     * sendMessage
     * ----------------------------------------------
     * إرسال رسالة (prompt) إلى Gemini
     * واستلام الرد عبر Callback
     *
     * @param prompt النص المراد إرساله
     * @param callback واجهة لإرجاع النتيجة أو الخطأ
     */
    public void sendMessage(String prompt, ResponseCallBack callback) {

        gemini.generateContent(prompt, new Continuation<GenerateContentResponse>() {

            /**
             * Context الخاص بالكوروتين
             */
            @NonNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            /**
             * يتم استدعاؤها عند وصول النتيجة
             */
            @Override
            public void resumeWith(@NonNull Object result) {
                try {
                    // في حال حدوث خطأ
                    if (result instanceof Result.Failure) {
                        Throwable error = ((Result.Failure) result).exception;
                        callback.onError(error);
                    } else {
                        // في حال نجاح الطلب
                        GenerateContentResponse response =
                                (GenerateContentResponse) result;

                        callback.onResponse(response.getText());
                    }
                } catch (Exception e) {
                    // معالجة أي خطأ غير متوقع
                    callback.onError(e);
                }
            }
        });
    }
}
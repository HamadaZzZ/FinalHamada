package com.example.finalhamada.data;

import androidx.annotation.NonNull;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.Result;

/**
 * فئة مساعدة للتواصل مع خدمة الذكاء الاصطناعي التابعة google Gemini
 */
public class



GeminiHelper {

    public static final String GEMINI_VERSION = "gemini-2.0-flash"; // إصدار Gemini
    private static String GEMINI_API_KEY = "your_key"; // ضع مفتاحك هنا
    private static GeminiHelper instance; // Singleton
    private final GenerativeModel gemini; // موديل الذكاء الاصطناعي

    // دالة بنائية لبناء الموديل
    private GeminiHelper() {
        gemini = new GenerativeModel(
                GEMINI_VERSION,
                GEMINI_API_KEY
        );
    }

    // Singleton: عدم إنشاء أكثر من كائن واحد
    public static GeminiHelper getInstance() {
        if (instance == null) {
            instance = new GeminiHelper();
        }
        return instance;
    }

    /**
     * إرسال جملة لـ Gemini ومعالجة الرد عبر Callback
     * @param prompt   جملة الاستعلام أو الطلب
     * @param callback كائن لمعالجة الرد
     */
    public void sendMessage(String prompt, ResponseCallBack callback) {
        gemini.generateContent(prompt,
                new Continuation<GenerateContentResponse>() {

                    @NonNull
                    @Override
                    public CoroutineContext getContext() {
                        return EmptyCoroutineContext.INSTANCE;
                    }

                    @Override
                    public void resumeWith(@NonNull Object result) {
                        if (result instanceof Result.Failure) {
                            callback.onError(((Result.Failure) result).exception);
                        } else {
                            callback.onResponse(((GenerateContentResponse) result).getText());
                        }
                    }
                }
        );
    }
}

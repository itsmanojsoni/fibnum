#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_manojsoni_logitechinterview_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jlongArray JNICALL
Java_com_example_manojsoni_logitechinterview_MainActivity_getFibNum(JNIEnv *env, jobject instance,
                                                                    jint n) {


   jlongArray fiboarray  = env->NewLongArray(n);


    long first=0;
    long second=1;
    long next;
    int i;
    long fibo[n];

    for(i=0;i<n;i++)
    {
        if(i<=1)
            next = i;
        else
        {
            next = first + second;
            first = second;
            second = next;
        }

        fibo[i] = next;
    }




    env->SetLongArrayRegion(fiboarray,0,n,fibo);

    return fiboarray;
}

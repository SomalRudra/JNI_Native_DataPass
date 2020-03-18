#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_datatransferinterface_JNIInteract_stringFromNative(JNIEnv* env,jobject instance) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_datatransferinterface_JNIInteract_passingDataToJni(JNIEnv *env,
                                                                                   jobject instance,
                                                                                   jcharArray charArray_,
                                                                                   jint year,
                                                                                   jfloat tmpFloat) {
    jchar *tmpArray = env->GetCharArrayElements(charArray_, NULL);

    if (tmpArray[0] == 'a' && tmpArray[1] == 'b' && tmpArray[2] == 'c' && year == 2019) {
        // Correct Data received from JAVA
        year=2020;
    } else {
        // Incorrect Data got from JAVA
        year=2019;
    }
    env->ReleaseCharArrayElements(charArray_, tmpArray, 0);
    return year;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_datatransferinterface_JNIInteract_passObjectToJNI(JNIEnv *env,jobject instance,
                                                                                  jobject Obj) {

    // Get jclass of the object
    jclass OBJClass = env->GetObjectClass(Obj);

    // Get data field IDs of the object
    jfieldID sampIntField = env->GetFieldID(OBJClass, "sampleInt", "I");
    jfieldID sampBoolField = env->GetFieldID(OBJClass, "sampleBoolean", "Z");
    jfieldID sampStringField = env->GetFieldID(OBJClass, "sampleString",
                                               "Ljava/lang/String;");

    // Get individual Data
    jint sampleInt = env->GetIntField(Obj, sampIntField);
    jboolean sampleBoolean = env->GetBooleanField(Obj, sampBoolField);

    // Update data
    const char *resultString = "Java Object Says Hi From C++";
    env->SetObjectField(Obj, sampStringField, env->NewStringUTF(resultString));

    // return error code (in this case, return the int value form the object)
    return sampleInt;
}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_datatransferinterface_JNIInteract_getObjectFromJNI(JNIEnv *env,jobject instance) {

    // Creating a jclass from the Java object class path passed as an arguement
    jclass sampleObjClass = env->FindClass(
            "com/example/datatransferinterface/ClassPass");
    jmethodID methodId = env->GetMethodID(sampleObjClass, "<init>", "()V");
    jobject sampleObj = env->NewObject(sampleObjClass, methodId);

    //Update fields of object
    const char *resultString = "C++ Object Says Hi from Java";
    jfieldID sampStringField = env->GetFieldID(sampleObjClass, "sampleString",
                                               "Ljava/lang/String;");
    env->SetObjectField(sampleObj, sampStringField, env->NewStringUTF(resultString));

    //Update int data field
    int updatedData = 10;
    jfieldID sampIntField = env->GetFieldID(sampleObjClass, "sampleInt", "I");
    env->SetIntField(sampleObj, sampIntField, updatedData);
    return sampleObj;
}


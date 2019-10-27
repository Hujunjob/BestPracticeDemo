#include <jni.h>
#include <string>

//声明，导包
extern "C"{
    extern int main(int argc,char * argv[]);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_hiscene_opengl_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_hiscene_opengl_MainActivity_patch(JNIEnv *env, jobject instance, jstring oldApk_,
                                           jstring patch_, jstring output_) {
    const char *oldApk = env->GetStringUTFChars(oldApk_, 0);
    const char *patch = env->GetStringUTFChars(patch_, 0);
    const char *output = env->GetStringUTFChars(output_, 0);

    //
//    if(argc!=4) errx(1,"usage: %s oldfile newfile patchfile\n",argv[0]);

//    Open patch file */
//    if ((f = fopen(argv[3]

/* Write the new file */
//    if(((fd=open(argv[2]

    // TODO
    const char * argv[] ={
            "bspatch",
            oldApk,
            output,
            patch
    };
    main(4, const_cast<char **>(argv));

    env->ReleaseStringUTFChars(oldApk_, oldApk);
    env->ReleaseStringUTFChars(patch_, patch);
    env->ReleaseStringUTFChars(output_, output);
}
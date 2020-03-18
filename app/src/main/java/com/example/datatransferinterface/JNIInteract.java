package com.example.datatransferinterface;

public class JNIInteract {


        // Used to load the 'native-lib' library on application startup.
        static {
            System.loadLibrary("native-lib");
        }



        /**
         * A native method that is implemented by the 'native-lib' native library,
         * which is packaged with this application.
         */
        public native String stringFromNative();

       public native int passingDataToJni(char[] charArray, int tmpInt, float tmpFloat);

        public native int passObjectToJNI(ClassPass Obj);

        public native ClassPass getObjectFromJNI();

        //public native void callTheCallBackMethod();

        /*private void callBack(int data, String stringValue) {
            callBackInterface.callBackEvent(stringValue + String.valueOf(data));
        }*/
}

# =============================================================#
#  IQuiubo (com.zhei.iquiubo) - Reglas R8/ProGuard para RELEASE
#  Generado tras analizar: build.gradle.kts, AndroidManifest,
#  modelos de Firestore, Room, navegación, TFLite y login.
#
#  Base: proguard-android-optimize.txt (ya incluido en Gradle).
#  Las librerías con "consumer rules" propias (Hilt, Compose,
#  Coroutines, Firebase Auth/Functions, Lifecycle, Navigation)
#  NO necesitan reglas extra aquí.
# =============================================================#


# -------------------------------------------------------------#
# 1. ATRIBUTOS GENERALES
# -------------------------------------------------------------#
# Signature: CRÍTICO para Firestore. FirestoreMeasurement tiene
#   Map<String, Double>; sin este atributo, toObject() pierde los
#   tipos genéricos y falla o devuelve valores incorrectos.
# *Annotation* / InnerClasses / EnclosingMethod: kotlinx.serialization
#   y Firestore los leen por reflexión.
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault
-keepattributes InnerClasses,EnclosingMethod

# Stack traces legibles (Play Console desinfusca con el mapping.txt
# que sube el AAB automáticamente).
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile


# -------------------------------------------------------------#
# 2. LOGS Y PRINT'S (se eliminan en release)
# -------------------------------------------------------------#
# OJO: esto también elimina Log.e / Log.w. Si algún día agregas
# Crashlytics o quieres ver errores en logcat de release, quita
# las líneas w() y e().
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
    public static *** println(...);
}


# -------------------------------------------------------------#
# 3. FIRESTORE - modelos mapeados por reflexión
# -------------------------------------------------------------#
# toObject(FirestoreUser::class.java), toObject(FirestoreMeasurement::class.java)
# y .set(FirestoreUser) usan reflexión sobre constructor sin
# argumentos, getters/setters y nombres de campos. Si R8 renombra
# o elimina algo, Firestore lee/escribe campos con nombre incorrecto.
# Mantén aquí SOLO las clases que van a/desde Firestore.
-keep class com.zhei.iquiubo.core.data.model.** { *; }

# ProfileUpdate.toCustomMap() usa strings literales como claves
# ("heightCM", "familyHistoryDiabetes", ...): no depende de R8.
# Importante: esos strings deben coincidir con los nombres de
# propiedad de FirestoreUser (como ya lo tienes).


# -------------------------------------------------------------#
# 4. KOTLINX.SERIALIZATION + NAVIGATION COMPOSE (rutas tipadas)
# -------------------------------------------------------------#
# Scenes y sus subclases son @Serializable y se usan en
# composable<Scenes.X>, hasRoute(Scenes.X::class) y navigate().
# Reglas oficiales de kotlinx.serialization:
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}
-keepclassmembers class <2>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# Refuerzo explícito para las rutas de navegación (son pocas clases).
# Una ruta mal ofuscada = crash o pantalla en blanco al navegar.
-keep class com.zhei.iquiubo.core.navigation.Scenes { *; }
-keep class com.zhei.iquiubo.core.navigation.Scenes$** { *; }


# -------------------------------------------------------------#
# 5. TENSORFLOW LITE / LITERT (modelo .tflite)
# -------------------------------------------------------------#
# El código nativo (JNI) busca clases y campos Java por nombre
# (Interpreter, Tensor, NativeInterpreterWrapper, etc.). Si R8 los
# renombra, la inferencia falla en release con UnsatisfiedLinkError
# o NoSuchFieldError.
-keep class org.tensorflow.lite.** { *; }
-keep class com.google.ai.edge.litert.** { *; }

# Delegados opcionales (GPU) que no incluyes: evita fallo de R8
# por clases referenciadas pero ausentes.
-dontwarn org.tensorflow.lite.gpu.**
-dontwarn com.google.ai.edge.litert.gpu.**


# -------------------------------------------------------------#
# 6. CREDENTIAL MANAGER (Google Sign-In)
# -------------------------------------------------------------#
# El proveedor de Play Services se carga por reflexión desde
# CredentialManager. La librería ya trae una regla equivalente;
# esta es una red de seguridad: si falla, el login con Google
# muere solo en release.
-keep class androidx.credentials.playservices.** { *; }
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("com.android.library")
	`maven-publish`
	id("com.palantir.git-version")
}

kotlin.compilerOptions.jvmTarget = JvmTarget.JVM_17

android {
	namespace = "work.niggergo.app.futago"
	compileSdk = libs.versions.compileSdk.get().toInt()
	buildToolsVersion = libs.versions.buildTools.get()

	defaultConfig {
		minSdk = 1
		consumerProguardFiles("consumer-rules.pro")
	}
	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	buildFeatures {
		buildConfig = true
	}

	publishing {
		singleVariant("release") {
			withSourcesJar()
			// withJavadocJar()
		}
	}
}

// noinspection GradleDynamicVersion
dependencies {
	implementation(libs.kavaref)
}

afterEvaluate {
	publishing {
		publications {
			create<MavenPublication>("release", configurePublishConfig("Futago", "Application Delegate"))
		}
		repositories { mavenLocal() }
	}
}
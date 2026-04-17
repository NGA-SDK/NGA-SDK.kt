import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("com.android.library")
	`maven-publish`
	id("com.palantir.git-version")
}

kotlin.compilerOptions.jvmTarget = JvmTarget.JVM_1_8

android {
	namespace = "work.niggergo.app.gendoki"
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
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
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

afterEvaluate {
	publishing {
		publications {
			create<MavenPublication>(
				"release", configurePublishConfig("GenDoki", "Application Initializer")
			)
		}
		repositories { mavenLocal() }
	}
}
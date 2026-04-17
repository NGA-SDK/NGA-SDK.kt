import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("com.android.library")
	`maven-publish`
	id("com.palantir.git-version")
}

kotlin.compilerOptions.jvmTarget = JvmTarget.JVM_1_8

android {
	namespace = "work.niggergo.app.sandocube.moesa"
	compileSdk = libs.versions.compileSdk.get().toInt()
	buildToolsVersion = libs.versions.buildTools.get()

	defaultConfig {
		minSdk = 4
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

// noinspection GradleDynamicVersion
dependencies {
	implementation(project(":utils:gendoki"))
}

afterEvaluate {
	publishing {
		publications {
			create<MavenPublication>(
				"release", configurePublishConfig(
					"MoeSa", "SandoCube MoeSa", "work.niggergo.app.sandocube"
				)
			)
		}
		repositories { mavenLocal() }
	}
}
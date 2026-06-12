import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.*

fun Project.configurePublishConfig(
	modName: String = "", desc: String = "", group: String = "work.niggergo.app",
): MavenPublication.() -> Unit = {
	from(components["release"])
	groupId = group
	val versionDetails: Closure<VersionDetails> by extra
	version = versionDetails().lastTag ?: "0.0"

	pom {
		name = "NGA SDK${if (modName.isNotEmpty()) " $modName" else ""}"
		description = desc
		url = "https://app.niggergo.work/nga/"

		licenses {
			license {
				name = "File-to-Downloader"
				url = "https://license.fileto.download/LICENSE.txt"
				distribution = "repo"
			}
		}

		developers {
			developer {
				id = "shirorren"
				name = "ShIroRRen"
				email = "shiro@oom-wg.dev"
				url = "https://shiror.ren"
			}
		}

		organization {
			name = "OOM WG"
			url = "https://oom-wg.dev"
		}

		scm {
			connection = "scm:git:https://github.com/NGA-SDK/NGA-SDK.kt"
			developerConnection = "scm:git:https://github.com/NGA-SDK/NGA-SDK.kt"
			url = "https://github.com/NGA-SDK/NGA-SDK.kt"
		}
	}

	when (name) {
		"androidRelease" -> "releaseRuntimeClasspath"
		else             -> listOf(
			"${name}RuntimeClasspath", "${name}CompileKlibraries"
		).firstOrNull { project.configurations.findByName(it) != null }
	}?.let { versionMapping { allVariants { fromResolutionOf(it) } } }
}
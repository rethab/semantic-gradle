package io.github.rethab.semanticrelease

import org.gradle.api.DefaultTask
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class TagNewVersionTask : DefaultTask() {

    @Input
    val releaseBranches: ListProperty<String> = project.objects.listProperty(String::class.java)

    @TaskAction
    fun tagNewVersion() {
        val git = GitFacade(GitCli(project.projectDir))

        // TODO: how about re-evaluating the version again?
        // TODO: how about storing the version somewhere in this plugin to ensure it stays the same?
        val version = Version.parse(project.version.toString()) ?: throw Exception("Failed to determine version")

        git.tagCurrentHead(version)

        git.push()
    }
}


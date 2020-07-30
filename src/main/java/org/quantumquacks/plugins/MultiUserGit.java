package org.quantumquacks.plugins;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;


@Service
public final class MultiUserGit {
    private final Project myProject;

    public MultiUserGit(Project project) {
        myProject = project;
    }
}
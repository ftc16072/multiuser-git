package org.quantumquacks.plugins;

import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.psi.search.FilenameIndex;

import org.jetbrains.annotations.NotNull;

public class MultiUserGit implements ProjectComponent {
    private static final String COMPONENT_NAME = "MultiUserGit";

    @Override
    public void projectOpened() {
    }

    @Override
    public void projectClosed() {
    }

    @Override
    public void initComponent() {
        System.out.println(COMPONENT_NAME + " - Initialized");
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }
}

package org.quantumquacks.plugins;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;

import org.jetbrains.annotations.NotNull;

public class SelectUserAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        SelectUserDialogWrapper dialog = new SelectUserDialogWrapper();
        PsiFile[] files = FilenameIndex.getFilesByName(project,"gitUsers.xml", GlobalSearchScope.projectScope(project));
        for (PsiFile file : files) {
            XmlTag[] users = ((XmlFile)file).getRootTag().findSubTags("USER");
            for(XmlTag user : users){
                dialog.addUser(user.getAttributeValue("name"), user.getAttributeValue("email"));
            }
        }
        dialog.init();

        if(dialog.showAndGet()) {
            String[] stringArray = dialog.getSelectedUser().split(" ", 3);
            System.out.println("Selected:" + dialog.getSelectedUser());
            // user pressed ok
            ProcessHandler processHandler = null;
            try {
                GeneralCommandLine line = new GeneralCommandLine("git").
                                                withWorkDirectory(project.getBasePath()).
                                                withParameters("config", "user.name", stringArray[0] + ' ' + stringArray[1]);
                processHandler = new OSProcessHandler(line);
                processHandler.startNotify();
                processHandler.waitFor();
                line = new GeneralCommandLine("git").
                        withWorkDirectory(project.getBasePath()).
                        withParameters("config", "user.email", stringArray[2].replaceAll("[<> ]",""));
                processHandler = new OSProcessHandler(line);
                processHandler.startNotify();
                processHandler.waitFor();

                Notification notification =
                        new NotificationGroup("MultiUser Git",
                                                NotificationDisplayType.BALLOON, true).
                                createNotification("Git User changed to: " + dialog.getSelectedUser(), NotificationType.INFORMATION);
                notification.notify(project);
            } catch (ExecutionException ex) {
                ex.printStackTrace();
            }
        }
    }
}

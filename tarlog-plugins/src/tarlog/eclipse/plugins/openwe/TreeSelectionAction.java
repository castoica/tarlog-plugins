package tarlog.eclipse.plugins.openwe;

import java.io.File;
import java.net.URI;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.ObjectPluginAction;

@SuppressWarnings("restriction")
public abstract class TreeSelectionAction implements IObjectActionDelegate {

    protected TreeSelectionAction() {
        super();
    }

    public void run(IAction action) {
        if (action instanceof ObjectPluginAction) {
            ObjectPluginAction objectPluginAction = (ObjectPluginAction) action;
            ISelection selection = objectPluginAction.getSelection();
            if (selection instanceof TreeSelection) {
                TreeSelection treeSelection = (TreeSelection) selection;
                Object firstElement = treeSelection.getFirstElement();
                if (firstElement instanceof IResource) {
                    handleResource((IResource) firstElement);
                } else if (firstElement instanceof IJavaElement) {
                    IJavaElement javaElement = (IJavaElement) firstElement;
                    handleJavaElement(javaElement);
                }
            }
        }
    }

    private void handleJavaElement(IJavaElement javaElement) {
        IResource resource = javaElement.getResource();
        if (resource != null) {
            handleResource(resource);
        } else {
            String path = javaElement.getPath().toOSString();
            doAction(path);
        }
    }

    protected void handleResource(IResource resource) {
        URI locationURI = resource.getLocationURI();
        File file = new File(locationURI);
        String absolutePath = file.getAbsolutePath();
        doAction(absolutePath);
    }

    protected abstract void doAction(String path);

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        // TODO Auto-generated method stub

    }

    public void selectionChanged(IAction action, ISelection selection) {
        // TODO Auto-generated method stub

    }

}
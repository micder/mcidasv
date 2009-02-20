package edu.wisc.ssec.mcidasv.util;

import static edu.wisc.ssec.mcidasv.util.CollectionHelpers.newMap;
import static edu.wisc.ssec.mcidasv.util.CollectionHelpers.arrList;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import ucar.unidata.util.GuiUtils;
import ucar.unidata.util.StringUtil;
import ucar.unidata.util.TwoFacedObject;

import edu.wisc.ssec.mcidasv.McIDASV;

/**
 *  This class shows a tree on the left and a card panel on the right.
 *  
 *  Ripped right out of the IDV, for the time being.
 */
@SuppressWarnings("serial") 
public class TreePanel extends JPanel implements TreeSelectionListener {

    public static final String CATEGORY_DELIMITER = ">";

    /** The root */
    private final DefaultMutableTreeNode root = new DefaultMutableTreeNode("");

    /** the model */
    private final DefaultTreeModel treeModel = new DefaultTreeModel(root);

    /** the tree */
    private final JTree tree = new JTree(treeModel);

    /** The scroller */
    private final JScrollPane treeView = new JScrollPane(tree);

    /** The panel */
    private GuiUtils.CardLayoutPanel panel;

    /** _more_ */
    private final JPanel emptyPanel = new JPanel(new BorderLayout());

    /** _more_ */
    private final Map<String, Component> catComponents = newMap();

    /** Maps categories to tree node */
    private final Map<String, DefaultMutableTreeNode> catToNode = newMap();

    /** Maps components to tree node */
    private final Map<Component, DefaultMutableTreeNode> compToNode = newMap();

    /** ok to respond to selection changes */
    private boolean okToUpdateTree = true;

    private boolean okToSave = false;

    /**
     * ctor
     */
    public TreePanel() {
        this(true, -1);
    }

    /**
     * _more_
     *
     * @param useSplitPane _more_
     * @param treeWidth _more_
     */
    public TreePanel(boolean useSplitPane, int treeWidth) {
        setLayout(new BorderLayout());
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);

        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer() {
            public Component getTreeCellRendererComponent(JTree theTree,
                Object value, boolean sel, boolean expanded, boolean leaf, 
                int row, boolean hasFocus) 
            {
                super.getTreeCellRendererComponent(theTree, value, sel,
                    expanded, leaf, row, hasFocus);

                if (!(value instanceof MyTreeNode))
                    return this;

                MyTreeNode node = (MyTreeNode) value;
                if (node.icon != null)
                    setIcon(node.icon);
                else
                    setIcon(null);

                return this;
            }
        };
        renderer.setIcon(null);
        renderer.setOpenIcon(null);
        renderer.setClosedIcon(null);
        tree.setCellRenderer(renderer);

        panel = new GuiUtils.CardLayoutPanel() {
            public void show(Component comp) {
                super.show(comp);
                showPath(panel.getVisibleComponent());
            }
        };
        panel.addCard(emptyPanel);

        if (treeWidth > 0)
            treeView.setPreferredSize(new Dimension(treeWidth, 100));

        JComponent center;
        if (useSplitPane) {
            JSplitPane splitPane = ((treeWidth > 0)
                                    ? GuiUtils.hsplit(treeView, panel, treeWidth)
                                    : GuiUtils.hsplit(treeView, panel, 150));
            center = splitPane;
            splitPane.setOneTouchExpandable(true);
        } else {
            center = GuiUtils.leftCenter(treeView, panel);
        }

        this.add(BorderLayout.CENTER, center);
        tree.addTreeSelectionListener(this);
    }

    public Component getVisibleComponent() {
        return panel.getVisibleComponent();
    }

    /**
     * Handle tree selection changed
     *
     * @param e event
     */
    public void valueChanged(TreeSelectionEvent e) {
        if (!okToUpdateTree)
            return;

        DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        if (node == null)
            return;

        saveCurrentPath(node);

        if (node.isLeaf()) {
            TwoFacedObject tfo = (TwoFacedObject)node.getUserObject();
            panel.show((Component) tfo.getId());
        } else {
            if (node.getUserObject() instanceof TwoFacedObject) {
                TwoFacedObject tfo = (TwoFacedObject)node.getUserObject();
                JComponent interior = (JComponent)catComponents.get(tfo.getId());
                if (interior == null)
                    return;

                if (!panel.contains(interior))
                    panel.addCard(interior);

                panel.show(interior);
                return;
            }
            panel.show(emptyPanel);
        }
    }

    public void setIcon(Component comp, ImageIcon icon) {
        MyTreeNode node = (MyTreeNode)compToNode.get(comp);
        if (node != null) {
            node.icon = icon;
            tree.repaint();
        }
    }

    /**
     * Add the component to the panel
     *
     * @param component component
     * @param category tree category. May be null.
     * @param label Tree node label
     * @param icon Node icon. May be null.
     */
    public void addComponent(JComponent component, String category, 
        String label, ImageIcon icon) 
    {
        TwoFacedObject tfo = new TwoFacedObject(label, component);
        DefaultMutableTreeNode panelNode = new MyTreeNode(tfo, icon);
        compToNode.put(component, panelNode);

        if (category == null) {
            root.add(panelNode);
        } else {
            List<String> toks = StringUtil.split(category, CATEGORY_DELIMITER, true, true);
            String catSoFar = "";
            DefaultMutableTreeNode catNode  = root;
            for (int i = 0; i < toks.size(); i++) {
                String cat = toks.get(i);
                catSoFar = catSoFar + CATEGORY_DELIMITER + cat;
                DefaultMutableTreeNode node = catToNode.get(catSoFar);
                if (node == null) {
                    TwoFacedObject catTfo = new TwoFacedObject(cat, catSoFar);
                    node = new DefaultMutableTreeNode(catTfo);
                    catToNode.put(catSoFar, node);
                    catNode.add(node);
                }
                catNode = node;
            }
            catNode.add(panelNode);
        }
        panel.addCard(component);
        treeChanged();
    }

    private void treeChanged() {
        // presumably okay--this method is older IDV code.
        @SuppressWarnings("unchecked")
        Hashtable stuff = GuiUtils.initializeExpandedPathsBeforeChange(tree, root);
        treeModel.nodeStructureChanged(root);
        GuiUtils.expandPathsAfterChange(tree, stuff, root);
    }

    /**
     * _more_
     *
     * @param cat _more_
     * @param comp _more_
     */
    public void addCategoryComponent(String cat, JComponent comp) {
        catComponents.put(CATEGORY_DELIMITER + cat, comp);
    }

    /**
     * _more_
     *
     * @param component _more_
     */
    public void removeComponent(JComponent component) {
        DefaultMutableTreeNode node = compToNode.get(component);
        if (node == null) {
            return;
        }
        compToNode.remove(component);
        if (node.getParent() != null) {
            node.removeFromParent();
        }
        panel.remove(component);
        treeChanged();
    }

    public void show(Component component) {
        panel.show(component);
    }

    /**
     * Show the tree node that corresponds to the component
     *
     * @param component comp
     */
    public void showPath(Component component) {
        if (component != null) {
            DefaultMutableTreeNode node = compToNode.get(component);
            if (node != null) {
                TreePath path = new TreePath(treeModel.getPathToRoot(node));
                okToUpdateTree = false;
                tree.setSelectionPath(path);
                tree.expandPath(path);
                okToUpdateTree = true;
            }
        }
    }

    /**
     * Open all paths
     */
    public void openAll() {
        for (int i = 0; i < tree.getRowCount(); i++)
            tree.expandPath(tree.getPathForRow(i));
        showPath(panel.getVisibleComponent());
    }

    /**
     * Close all paths
     */
    public void closeAll() {
        for (int i = 0; i < tree.getRowCount(); i++)
            tree.collapsePath(tree.getPathForRow(i));
        showPath(panel.getVisibleComponent());
    }

    /**
     * Attempts to select the path from a previous McIDAS-V session. If no 
     * path was persisted, the method attempts to use the {@literal "first"} 
     * non-leaf node. 
     * 
     * <p>This method also sets {@link #okToSave} to {@code true}, so that 
     * user selections can be captured after this method quits.
     */
    public void showPersistedSelection() {
        okToSave = true;

        String path = loadSavedPath();

        TreePath tp = findByName(tree, tokenizePath(path));
        if (tp == null || tp.getPathCount() == 1)
            tp = getPathToFirstLeaf(new TreePath(root));

        tree.setSelectionPath(tp);
        tree.expandPath(tp);
    }

    private void saveCurrentPath(final DefaultMutableTreeNode node) {
        assert node != null;
        if (!okToSave)
            return;

        McIDASV mcv = McIDASV.getStaticMcv();
        if (mcv != null)
            mcv.getStore().put("mcv.treepanel.savedpath", getPath(node));
    }

    private String loadSavedPath() {
        String path = "";
        McIDASV mcv = McIDASV.getStaticMcv();
        if (mcv == null)
            return path;

        path = mcv.getStore().get("mcv.treepanel.savedpath", "");
        if (path.length() > 0)
            return path;

        TreePath tp = getPathToFirstLeaf(new TreePath(root));
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)tp.getLastPathComponent();
        path = TreePanel.getPath(node);
        mcv.getStore().put("mcv.treepanel.savedpath", path);

        return path;
    }

    public static List<String> tokenizePath(final String path) {
        if (path == null)
            throw new NullPointerException("Cannot tokenize a null path");

        List<String> tokens = arrList();
        StringTokenizer tokenizer = new StringTokenizer(path, CATEGORY_DELIMITER);
        tokens.add("");
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }
        return tokens;
    }

    public static String getPath(final DefaultMutableTreeNode node) {
        if (node == null)
            throw new NullPointerException("Cannot get the path of a null node");

        StringBuilder path = new StringBuilder("");
        TreeNode[] nodes = node.getPath();
        TreeNode root = nodes[0];
        for (TreeNode n : nodes) {
            if (n == root)
                path.append(n.toString());
            else
                path.append(CATEGORY_DELIMITER + n.toString());
        }
        return path.toString();
    }

    public static DefaultMutableTreeNode findNodeByPath(JTree tree, String path) {
        TreePath tpath = findByName(tree, tokenizePath(path));
        if (tpath == null)
            return null;

        return (DefaultMutableTreeNode)tpath.getLastPathComponent();
    }

    public static TreePath findByName(JTree tree, List<String> names) {
        TreeNode root = (TreeNode)tree.getModel().getRoot();
        return searchTree(new TreePath(root), names, 0);
    }

    @SuppressWarnings("unchecked") 
    private static TreePath searchTree(TreePath parent, List<String> nodes, int depth) {
        assert parent != null;
        assert nodes != null;
        assert depth >= 0;

        TreeNode node = (TreeNode)parent.getLastPathComponent();
        if (node == null)
            return null;

        String payload = node.toString();

        // If equal, go down the branch
        if (nodes.get(depth) == null)
            return null;

        if (payload.equals(nodes.get(depth).toString())) {
            // If at end, return match
            if (depth == nodes.size() - 1)
                return parent;

            // Traverse children
            if (node.getChildCount() >= 0) {
                for (Enumeration<TreeNode> e = node.children(); e.hasMoreElements();) {
                    TreeNode n = e.nextElement();
                    TreePath path = parent.pathByAddingChild(n);
                    TreePath result = searchTree(path, nodes, depth + 1);

                    // Found a match
                    if (result != null)
                        return result;
                }
            }
        }

        // No match at this branch
        return null;
    }

    @SuppressWarnings("unchecked") 
    private static TreePath getPathToFirstLeaf(final TreePath searchPath) {
        TreeNode node = (TreeNode)searchPath.getLastPathComponent();
        if (node == null)
            return null;

        if (node.isLeaf())
            return searchPath;

        for (Enumeration<TreeNode> e = node.children(); e.hasMoreElements();) {
            TreeNode n = e.nextElement();
            TreePath newPath = searchPath.pathByAddingChild(n);
            TreePath result = getPathToFirstLeaf(newPath);
            if (result != null)
                return result;
        }
        return null;
    }

    /**
     * TreeNode extensions that allows us to associate an icon with this node.
     */
    private static class MyTreeNode extends DefaultMutableTreeNode {
        public ImageIcon icon;

        public MyTreeNode(Object o, ImageIcon icon) {
            super(o);
            this.icon = icon;
        }
    }
}
package dgm.trees;

/**
 * @author wires
 */
public class LevelLimitingVisitor<A> implements TreeVisitor<A> {
    final TreeVisitor<A> adaptee;
    final int maxLevel;
    int currentLevel = 0;

    public LevelLimitingVisitor(int maxLevel, TreeVisitor<A> adaptee) {
        this.adaptee = adaptee;
        this.maxLevel = Math.max(0, maxLevel);
    }

    @Override
    public boolean visitNode(A node, TreeViewer<A> viewer) {
        // stop the recursion when maximum level is reached
        final boolean shouldStop = currentLevel >= maxLevel;
        return shouldStop || adaptee.visitNode(node, viewer);
    }

    @Override
    public void beginChildren(A node, TreeViewer<A> viewer) {
        currentLevel++;
        adaptee.beginChildren(node, viewer);
    }

    @Override
    public void endChildren(A node, TreeViewer<A> viewer) {
        currentLevel--;
        adaptee.endChildren(node, viewer);
    }
}

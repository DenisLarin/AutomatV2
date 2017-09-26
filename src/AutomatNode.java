import java.util.Map;
import java.util.Set;

//узел автомата
public class AutomatNode{
    private boolean isFinal;
    Map<String,AutomatNode> nextNodes;//словарь с переходом на следующую ноду

    public AutomatNode(boolean isFinal) {
        this.isFinal = isFinal;
        nextNodes = null;
    }

    public AutomatNode(Map<String, AutomatNode> nextNodes) {
        this.nextNodes = nextNodes;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public Map<String, AutomatNode> getNextNodes() {
        return nextNodes;
    }

    public void setNextNodes(Map<String, AutomatNode> nextNodes) {
        this.nextNodes = nextNodes;
    }
}
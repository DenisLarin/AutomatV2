import java.util.*;

public class Automat{
    private List<AutomatNode> automatNodeList;
    private AutomatNode starNode;

    public Automat() {
        automatNodeList = new LinkedList<>();
        //по умолчанию в автомате должна быть стартовый узел
        AutomatNode automatNode = new AutomatNode(false);
        starNode = automatNode;
        automatNodeList.add(starNode);
    }
    //добалвение нового узла
    public void addNewNode(String whatDoToGo, int numberFromGoNode, int numberToGoNode){
        //если узел откуда идем не находится в списке то выходим из добавления и не добавляем
        if(numberFromGoNode > automatNodeList.size()-1)
            return;
        //добавление нового узла происходит только в случае если он не финальный
        if(numberFromGoNode == numberToGoNode){
            addFinalNode(whatDoToGo,numberFromGoNode);
        }
        else{
            AutomatNode fromGoNode = automatNodeList.get(numberFromGoNode);//узел из которого мы будем идти
            AutomatNode toGoNode;//узел куда мы будем идти
            //если узел куда идем нет в списке, то создаем новый узел и помещаем его в список
            if (!(numberToGoNode <= automatNodeList.size()-1)){
                toGoNode = new AutomatNode(false);
                automatNodeList.add(numberToGoNode,toGoNode);
            }
            else
                toGoNode = automatNodeList.get(numberToGoNode);

            Map<String,AutomatNode> nodeMap = getMap(fromGoNode); //словарь перехода
            nodeMap.put(whatDoToGo,toGoNode);
        }
    }

    //достаем словарь перехода
    private Map<String, AutomatNode> getMap(AutomatNode fromGoNode) {
        Map<String, AutomatNode> nodeMap;
        //если в узле словарь равен null то надо создать новый словарь и поместить его в узел
        if(fromGoNode.getNextNodes() == null) {
            nodeMap = new HashMap<>();
            fromGoNode.setNextNodes(nodeMap);
        }
        else
            nodeMap = fromGoNode.getNextNodes();
        return nodeMap;
    }
    //финальный узел
    private void addFinalNode(String whatDoToGo, int numberFromGoNode) {
        AutomatNode finalNode = automatNodeList.get(numberFromGoNode);
        finalNode.setFinal(true);//делаем узел финальным
        Map<String,AutomatNode> nodeMap = getMap(finalNode);
        nodeMap.put(whatDoToGo,finalNode);
    }

    public void printAutomat(){
        for (int i = 0; i < automatNodeList.size(); i++) {
            Set set = automatNodeList.get(i).nextNodes.entrySet();
            for (Object o:set) {
                System.out.println("из узла: " + i + " мы можем попасть в: " + o );
            }
        }
    }

    public boolean isStringNumber(String numberString) {
        ArrayList<Character> characterArrayList = new ArrayList<>();
        for (char c:numberString.toCharArray()) {
            characterArrayList.add(c);
        }
        AutomatNode nextNode = null;
        AutomatNode currentNode = starNode;
        //пока в массиве символов есть символы
        while (characterArrayList.size()!= 0){
            //проверяем является ли текуший узел финальным
            if(currentNode.isFinal()){
                //пока есть куда идти (финальный узел идет сам в себя)
                while ((currentNode = checkWay(characterArrayList.get(0),currentNode))!=null){
                    //удаляем первый символ
                    characterArrayList.remove(0);
                    //проверяем на количество символов, чтобы избежать indexError
                    if(characterArrayList.size() == 0)
                        break;
                }
                return characterArrayList.size() == 0;
            }
            else{
                //передаем 1 символ на проверку
               nextNode = checkWay(characterArrayList.get(0),currentNode);
               if (nextNode == null)
                   return false;
               currentNode = nextNode;
               characterArrayList.remove(0);
               //если число состоит из одной цифры
               if(characterArrayList.size() == 0 && nextNode.isFinal())
                   return true;
            }
        }

        return false;
    }

    private AutomatNode checkWay(char symbol, AutomatNode currentNode) {
        AutomatNode nextNode = null;
        Map<String, AutomatNode> mapofCurrentNode = getMap(currentNode);
        if(!(mapofCurrentNode.size() <= 0)){
            for (int i = 0; i < mapofCurrentNode.size(); i++) {
                for (Map.Entry<String, AutomatNode> pair:mapofCurrentNode.entrySet()) {
                    String key = pair.getKey();
                    char[] temp = key.toCharArray();
                    for (char c:temp)
                        if ((symbol == c)) {
                            nextNode = pair.getValue();
                            return nextNode;
                        }
                }
            }
        }
        return nextNode;
    }

}
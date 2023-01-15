package tree;

import java.util.ArrayList;
import java.util.List;

public class Tree_2_3 {

    private Node_2_3 root;

    private final List<Integer> elementList = new ArrayList<>();

    public void insert(int e) {
        // 记录元素
        elementList.add(e);
        // 插入元素
        if (root == null) {
            root = new Node_2_3(e);
        } else {
            root = insert(e, root);
            if (root.numbers == 3) {
                root = split(root, null);
            }
        }
    }

    /**
     * 递归调用插入元素
     */
    private Node_2_3 insert(int e, Node_2_3 parent) {
        if (parent.isLeaf()) {
            parent.insert(e);
            return parent;
        }

        Node_2_3 child = null;
        if (parent.numbers == 1) {
            if (e < parent.getMinItem()) {
                child = insert(e, parent.getLeft());
            } else {
                child = insert(e, parent.getMiddle());
            }
        } else {
            if (e < parent.getMinItem()) {
                child = insert(e, parent.getLeft());
            } else if (e > parent.getMiddleItem()) {
                child = insert(e, parent.getRight());
            } else {
                child = insert(e, parent.getMiddle());
            }
        }

        if (child.numbers == 3) {
            return this.split(child, parent);
        }

        return parent;
    }

    //拆分，就是将中间元素晋升成父节点
    private Node_2_3 split(Node_2_3 node, Node_2_3 parent) {
        if (parent == null) {
            //整了一个空的父节点出来，node和parent是父子关系了
            parent = new Node_2_3(node);
        }
        //将中间元素，晋升到上面空的父节点
        parent.insert(node.getMiddleItem());

        //将老节点的第1、3元素拆成新节点，并且这个新节点关联老节点的子节点
        Node_2_3[] newNodes = this.triangle(node);
        //以上父节点，新的2个子节点创建了，但是还没建立关联，关联之
        this.replaceChild(parent, node, newNodes[0], newNodes[1]);

        return parent;
    }

    /**
     * 新的父亲节点与2个新的子节点关联
     * @param parent
     * @param oldChild
     * @param child01
     * @param child02
     */
    private void replaceChild(Node_2_3 parent, Node_2_3 oldChild, Node_2_3 child01, Node_2_3 child02) {
        if (oldChild == parent.children[0]) {
            parent.children[3] = parent.children[2];
            parent.children[2] = parent.children[1];
            parent.children[1] = child02;
            parent.children[0] = child01;
        } else if (oldChild == parent.children[1]) {
            parent.children[3] = parent.children[2];
            parent.children[2] = child02;
            parent.children[1] = child01;
        } else {
            parent.children[3] = child02;
            parent.children[2] = child01;
        }
    }

    /**
     * 拆分节点，中间节点上提，形成一个三角关系
     * [1,2,3]
     * <p>
     * /---- 3
     * 2
     * \---- 1
     */
    private Node_2_3[] triangle(Node_2_3 node) {
        //创建一个能存放2个元素的数组
        Node_2_3[] newNodes = new Node_2_3[2];
        //老节点的第一个、第三个元素值构建新的节点
        newNodes[0] = new Node_2_3(node.items[0]);
        newNodes[1] = new Node_2_3(node.items[2]);

        //依据老节点，构建了2个新节点，则老节点的孩子节点也不能丢，也要关联起来
        if (!node.isLeaf()) {
            // 左孩子
            newNodes[0].children[0] = node.children[0];
            newNodes[0].children[1] = node.children[1];
            // 右孩子
            newNodes[1].children[0] = node.children[2];
            newNodes[1].children[1] = node.children[3];
        }

        return newNodes;
    }

    /*****************************************************
     *                    打印2-3树节点
     *****************************************************/

    private String printSubTree(Node_2_3 node) {
        StringBuilder tree = new StringBuilder();
        if (node.getRight() != null) {
            printTree(node.getRight(), 2, "", tree);
        }
        if (elementList.size() <= 10) {
            if (node.getMiddle() != null && node.children[2] == null) {
                printTree(node.getMiddle(), 2, "", tree);
            }
        } else {
            if (node.getMiddle() != null) {
                printTree(node.getMiddle(), 2, "", tree);
            }
        }
        printNodeValue(node, tree);

        if (node.getLeft() != null) {
            printTree(node.getLeft(), 0, "", tree);
        }
        return tree.toString();
    }

    private void printTree(Node_2_3 node, int position, String indent, StringBuilder tree) {
        if (node.getRight() != null) {
            printTree(node.getRight(), 2, indent + (position == 2 ? "        " : " |      "), tree);
        }
        if (node.getMiddle() != null) {
            if (elementList.size() <= 10) {
                if (node.children[2] == null && node.getMiddle().isLeaf()) {
                    printTree(node.getMiddle(), 2, indent + (position == 2 ? "        " : " |      "), tree);
                }
            } else {
                printTree(node.getMiddle(), 2, indent + (position == 2 ? "        " : " |      "), tree);
            }
        }
        tree.append(indent);
        if (position == 2) {
            tree.append(" /");
        } else {
            tree.append(" \\");
        }
        tree.append("----- ");
        printNodeValue(node, tree);
        if (node.getLeft() != null) {
            printTree(node.getLeft(), 0, indent + (position == 2 ? " |      " : "        "), tree);
        }
    }

    private void printNodeValue(Node_2_3 node, StringBuilder tree) {
        if (null == node.items) {
            tree.append("<null>");
        } else {
            StringBuilder nodeStr = new StringBuilder();
            for (int v : node.items) {
                if (0 == v) continue;
                nodeStr.append(v).append(",");
            }
            tree.append("[").append(nodeStr.substring(0, nodeStr.lastIndexOf(","))).append("]");
            if (elementList.size() <= 10 && node.getMiddle() != null && node.children[2] != null) {
                tree.append("---- [").append(node.getMiddle().getMinItem());
                if (node.getMiddle().getMiddleItem() != 0) {
                    tree.append(",").append(node.getMiddle().getMiddleItem());
                }
                tree.append("]");
            }
        }
        tree.append("\r\n");
    }

    @Override
    public String toString() {
        String str = elementList.toString();
        str = str.substring(str.indexOf("[") + 1, str.lastIndexOf("]")).replace(" ", "");
        int nullIdx = str.indexOf("null");
        if (nullIdx > 0) {
            str = str.substring(0, str.indexOf("null"));
            str = str.substring(0, str.lastIndexOf(","));
        }

        System.out.println(this.getClass().getSimpleName() + "，输入节点(" + elementList.size() + "个)：" + str + "\r\n ");
        return printSubTree(root);
    }

    /**
     * 2-3树节点
     */
    static class Node_2_3 {

        // 元素
        public int[] items;
        // 元素个数
        public int numbers;
        // 孩子
        public Node_2_3[] children;
        // 父亲
        public Node_2_3 parent;

        public Node_2_3() {
            this.items = new int[3];
            this.numbers = 0;
            this.children = new Node_2_3[4];
            this.parent = null;
        }

        public Node_2_3(int item) {
            this();
            this.items[0] = item;
            this.numbers = 1;
        }

        public Node_2_3(Node_2_3 child){
            this();
            this.children[0] = child;
        }

        public void insert(int e) {
            int idx = this.numbers - 1;
            while (idx >= 0) {
                if (this.items[idx] < e) break;
                this.items[idx + 1] = this.items[idx];
                --idx;
            }
            this.items[idx + 1] = e;
            ++this.numbers;
        }

        public boolean isLeaf(){
            return this.children[0] == null;
        }

        public int getMinItem(){
            return this.items[0];
        }

        public int getMiddleItem(){
            return this.items[1];
        }

        public int getMaxItem(){
            return this.items[2];
        }

        public Node_2_3 getLeft() {
            return this.children[0];
        }

        public Node_2_3 getMiddle() {
            return this.children[1];
        }

        public Node_2_3 getRight() {
            return this.children[2];
        }

    }

}

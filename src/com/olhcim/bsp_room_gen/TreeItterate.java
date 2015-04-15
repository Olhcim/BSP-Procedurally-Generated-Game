
package com.olhcim.bsp_room_gen;

import java.util.Stack;

public abstract class TreeItterate {
    
    Node currentNode;
    Tree tree;
    Stack<Node> stack;
    
    public TreeItterate(Tree tree)
    {
        this.tree = tree;
        reset();
    }
    
    private void reset()
    {
        currentNode = tree.node;
        stack = new Stack<>();
    }
    
    protected abstract void step(Node n);

    public void itterateAll() {
        while (true) {
            if (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.left;
            } else {
                if (stack.empty()) {
                    break;
                }
                currentNode = stack.pop();
                step(currentNode);
                currentNode = currentNode.right;
            }
        }
        
        reset();
    }
    
    public void itterateEnds() {
        while (true) {
            if (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.left;
            } else {
                if (stack.empty()) {
                    break;
                }
                currentNode = stack.pop();
                if(currentNode.left == null && currentNode.right == null)
                {
                    step(currentNode);
                }
                currentNode = currentNode.right;
            }
        }
        
        reset();
    }
}

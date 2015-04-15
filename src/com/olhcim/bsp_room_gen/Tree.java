package com.olhcim.bsp_room_gen;

import java.util.Random;

public class Tree {

    public final Node node;
    final Random r;
    public final double ratio, minArea;
    public final int maxLevels;
    
    
    public Tree(int sizex, int sizey, int levels) {
        this(sizex, sizey, 0.45, 1600, levels, (long)(Math.random()*Long.MAX_VALUE));
    }

    public Tree(int sizex, int sizey, double ratio, double minArea, int maxLevels, long seed) {
        r = new Random(seed);
        node = new Node(sizex, sizey);
        this.ratio = ratio;
        this.minArea = minArea;
        this.maxLevels = maxLevels;

        splitTree();
        generateRooms();
    }

    public double ran(double min, double max) {
        double var = min + r.nextDouble() * (max - min);
        System.out.println(var);
        return var;
    }

    public int countTree() {
        return countNodes(node);
    }
    
    private int countNodes(Node node) {
        if (node == null) {
            return 0;
        } else {
            int count = 1; 
            count += countNodes(node.left);
            count += countNodes(node.right);
            return count;
        }
    }
    
    private void splitTree() {
        node.splitNode(ratio, minArea, r, maxLevels);
    }
    
    private void generateRooms()
    {
        generateRoom(node);
    }
    
    private void generateRoom(Node node)
    {
        if (node.left == null && node.right == null) {
            node.data.createSubRoom(r);
        } else if (node.left != null && node.right != null) {
            generateRoom(node.left);
            generateRoom(node.right);
        }
    }
    
    public Node getNodeAt(int x, int y)
    {
        return getNodeAt(node, x, y);
    }
    
    private Node getNodeAt(Node node, int x, int y)
    {
            if(node.left != null && node.right != null)
            {
                if(node.left.data.isPointInside(x, y))
                {
                    return getNodeAt(node.left, x, y);
                } else if(node.right.data.isPointInside(x, y))
                {
                    return getNodeAt(node.right, x, y);
                }
            } else if (node.data.isPointInside(x, y)) {
                return node;
            }
            
            return null;
    }
}

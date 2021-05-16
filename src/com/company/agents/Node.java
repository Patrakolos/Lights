package com.company.agents;

import java.util.LinkedList;

public class Node
{
    private gamestate gs;
    private LinkedList<Node> children = new LinkedList<Node>();

    public Node(gamestate gs ){ this.gs = gs; }

    public gamestate getId(){ return gs; }

    public void addNode( Node n ){ children.add( n ); }

    public boolean hasChildren(){ return children.size() > 0; }

    public LinkedList<Node> getChildren(){ return children; }



    public static void parcoursProfondeur( Node node, int profondeur )
    {
        System.out.println( String.format("profondeur : %d, noeud visité : %s", profondeur, node.getId() ) ); // Je fais un simple affichage en console pour avoir une trace des sommets visités

        if( !node.hasChildren() ) // Si il n'y a plus de de fils disponible
            return;                 // Je remonte d'un cran

        LinkedList<Node> list = node.getChildren(); // Sinon je récupère la liste des fils

        for( Node next : list )
            parcoursProfondeur( next, profondeur + 1 ); // Et je continue d'explorer mon arbre
    }


}
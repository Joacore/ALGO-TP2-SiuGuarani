package aed;
import java.util.ArrayList;

public class Trie<T> {
    private Nodo raiz;
    private int cantClaves = 0;

    private class Nodo{
        ArrayList<Nodo> hijos;
        T definicion;
    
        Nodo(T v) {
            definicion = v;
            hijos = new ArrayList<Nodo>(256);
            for (int i = 0; i < 256; i++) {
                hijos.add(null);
            }
        }
    }
    public Trie(){
        raiz= new Nodo(null);
    }

    public static void main(String[] args){
        ;
    }

    public boolean vacio(){
        for (int h =0 ; h<256; h++){
            if (raiz.hijos.get(h) != null){
                return false;
            }
        }
        return true;
    }

    public boolean pertenece(String clave){
        Nodo actual= raiz;
        for (int i=0; i< clave.length(); i++){
            char letra = clave.charAt(i);
            int num= (int) letra;
            if (actual.hijos.get(num) == null){
                return false;
            }else{
                actual= actual.hijos.get(num);
            }
        }
        return actual.definicion != null;
    }

    public void insertar (String clave, T valor){
        Nodo actual = raiz;
        for (int i=0; i < clave.length(); i++){
            char letra = clave.charAt(i);
            int num = (int) letra;
            if (actual.hijos.get(num) == null){
                actual.hijos.set(num, new Nodo(null));
            }
            actual = actual.hijos.get(num);
        }
        actual.definicion = valor;
    }

    public T def(String clave){
        Nodo actual = raiz;
        for (int i=0; i < clave.length(); i++){
            char letra = clave.charAt(i);
            int num = (int) letra;
            actual = actual.hijos.get(num);
        }
        T valor;
        if (actual.definicion==null){
            return null;
        } else{
            valor = actual.definicion;
            return valor;
        }
    }

    public void eliminar (String clave){
        if (pertenece(clave) == false){
            return;
        } else {
            Nodo ultimoNoInutil = raiz; // arrancamos desde la raiz y vamos bajando
            Nodo actual = raiz;
            for (int i=0; i< clave.length(); i++){
                char letra = clave.charAt(i);
                int num= (int) letra;
                if ((actual.definicion != null && actual.hijos.get(num) != null) || contador(actual.hijos)>1 && i<clave.length()-1){// O TIENE MAS DE UN HIJO (AGREGAR)
                    ultimoNoInutil = actual; // ultido definido o el ultimo con mas de un hijo
                    actual = actual.hijos.get(num);
                } else {
                    actual = actual.hijos.get(num); // recursion? padre?, chequear si letras de arriba tienen mas de 1 hijo y/o tienen significado
                }
            } if (contador(actual.hijos)>=1){
                actual.definicion = null; //1. tenemos amoroso, queremos borrar amor, solo borramos la def de la r
                return;
            } else {
                actual.definicion = null;
                for (int i=0; i< clave.length(); i++){
                    char letra = clave.charAt(i);
                    int num= (int) letra;
                    Nodo actual2 = raiz;
                    if (actual2 != ultimoNoInutil) {
                        actual2 = actual2.hijos.get(num);
                        //2.a si tiene solo un hijo hacemos q apunte a null
                    } else {
                        actual2 = actual2.hijos.get((int) clave.charAt(i+1));
                        actual2 = null;
                        return;
                    }
                }
            }
        }
    }

    private int contador(ArrayList a){
        int cont = 0;
        for (int i=0; i < a.size(); i++){
            if (a.get(i)!=null){
                cont += 1;
            }
        }
        return cont;
    }
}


// el ultimo nodo que tiene definicion no lo borramos
// desconecto hijos null
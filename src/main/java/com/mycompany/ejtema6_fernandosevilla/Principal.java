/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejtema6_fernandosevilla;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Fer
*/

public class Principal {
    public static void main(String[] args) throws IOException {
        DGT dgt = new DGT();
        Scanner teclado = new Scanner(System.in);
        int opcion;
        
        do {
            System.out.println("1. Importar el archivo");
            System.out.println("2. Listar");
            System.out.println("3. Consultar por matricula");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opcion: ");
            opcion = teclado.nextInt(); teclado.nextLine();
            
            switch (opcion) {
                case 1:
                    dgt.importarMultas("datosradares.txt");
                    break;
                case 2:
                    System.out.println(dgt.listadoMultas());
                    break;
                case 3:
                    System.out.println(dgt.consultarPorMatricula("4992MBW"));
                    break;
                case 4:
                    break;
            }
            
        } while (opcion != 4);
        
    }
}

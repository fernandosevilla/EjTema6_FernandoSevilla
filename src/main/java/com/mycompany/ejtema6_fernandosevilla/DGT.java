/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejtema6_fernandosevilla;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.regex.Pattern;

/**
 * @author Fer
 */
public class DGT {

    private LinkedHashSet<DGT> dgt;
    private String fechaInfraccion;
    private String horaInfraccion;
    private double velocidadRegistrada;
    private double velocidadMaxima;
    private String matricula;
    private int sancion;

    public DGT() {
        this.dgt = new LinkedHashSet<>();
    }

    public DGT(String fechaInfraccion, String horaInfraccion, double velocidadRegistrada, double velocidadMaxima, String matricula, int sancion) {
        this.dgt = new LinkedHashSet<>();
        this.fechaInfraccion = fechaInfraccion;
        this.horaInfraccion = horaInfraccion;
        this.velocidadRegistrada = velocidadRegistrada;
        this.velocidadMaxima = velocidadMaxima;
        this.matricula = matricula;
        this.sancion = sancion;
    }

    public LinkedHashSet<DGT> getDgt() {
        return dgt;
    }

    public void setDgt(LinkedHashSet<DGT> dgt) {
        this.dgt = dgt;
    }

    public String getFechaInfraccion() {
        return fechaInfraccion;
    }

    public void setFechaInfraccion(String fechaInfraccion) {
        this.fechaInfraccion = fechaInfraccion;
    }

    public String getHoraInfraccion() {
        return horaInfraccion;
    }

    public void setHoraInfraccion(String horaInfraccion) {
        this.horaInfraccion = horaInfraccion;
    }

    public double getVelocidadRegistrada() {
        return velocidadRegistrada;
    }

    public void setVelocidadRegistrada(double velocidadRegistrada) {
        this.velocidadRegistrada = velocidadRegistrada;
    }

    public double getVelocidadMaxima() {
        return velocidadMaxima;
    }

    public void setVelocidadMaxima(double velocidadMaxima) {
        this.velocidadMaxima = velocidadMaxima;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getSancion() {
        return sancion;
    }

    public void setSancion(int sancion) {
        this.sancion = sancion;
    }

    public int importarMultas(String archivo) throws FileNotFoundException, IOException {
        int multasImportadas = 0;
        String linea;

        BufferedReader archivoMultas = new BufferedReader(new FileReader(archivo));

        while ((linea = archivoMultas.readLine()) != null) {
            String[] delimitador = linea.split("[#\\$&]+");

            if (delimitador.length == 5) {
                fechaInfraccion = delimitador[0];
                horaInfraccion = delimitador[1];
                velocidadRegistrada = Double.parseDouble(delimitador[2]);
                velocidadMaxima = Double.parseDouble(delimitador[3]);
                matricula = delimitador[4];

                if (esMatriculaEspañola(matricula)) {
                    dgt.add(new DGT(fechaInfraccion, horaInfraccion, velocidadRegistrada, velocidadMaxima, matricula, sancionar(matricula)));
                    multasImportadas++;
                }
            }
        }

        System.out.println("Se han immportado " + multasImportadas + " multas");

        return multasImportadas;
    }

    public int sancionar(String matricula) {
        int multa;

        double diferencia = getVelocidadRegistrada() - getVelocidadMaxima();

        if (diferencia >= 5 && diferencia <= 20) {
            multa = 100;
        } else if (diferencia > 20 && diferencia <= 40) {
            multa = 200;
        } else if (diferencia > 40) {
            multa = 500;
        } else {
            multa = 0;
        }

        return multa;
    }

    public static boolean esMatriculaEspañola(String matricula) {
        String expMatriculaNueva = "^\\d{4}[A-Z]{3}$";
        String expMatriculaVieja = "^[A-Z]{2,3}\\d{4}$";

        Pattern patronMatrNuevas = Pattern.compile(expMatriculaNueva);
        Pattern patronMatrViejas = Pattern.compile(expMatriculaVieja);

        return patronMatrNuevas.matcher(matricula).matches() || patronMatrViejas.matcher(matricula).matches();
    }

    public String listadoMultas() {
        if (dgt.isEmpty()) {
            return "No hay cuentas en el banco";
        }

        StringBuilder listado = new StringBuilder();

        for (DGT xd : dgt) {
            listado.append("Fecha: ").append(xd.getFechaInfraccion()).append("\t\t")
                    .append("Hora: ").append(xd.getHoraInfraccion()).append("\t\t")
                    .append("Velocidad: ").append(xd.getVelocidadRegistrada()).append("\t\t")
                    .append("V. Maxima: ").append(xd.getVelocidadMaxima()).append("\t\t")
                    .append("Sancion (€): ").append(xd.getSancion()).append("\n");
        }

        return listado.toString();
    }

    public String consultarPorMatricula(String matricula) {
        StringBuilder consulta = new StringBuilder();

        for (DGT xd : dgt) {
            if (getMatricula().equals(matricula)) {
                consulta.append("Fecha: ").append(xd.getFechaInfraccion()).append("\t\t")
                        .append("Hora: ").append(xd.getHoraInfraccion()).append("\t\t")
                        .append("Velocidad: ").append(xd.getVelocidadRegistrada()).append("\t\t")
                        .append("V. Maxima: ").append(xd.getVelocidadMaxima()).append("\t\t")
                        .append("Sancion (€): ").append(xd.getSancion()).append("\n");
                return consulta.toString();
            }
        }

        System.out.println("La cuenta no se ha encontrado");

        return consulta.toString();
    }
}
